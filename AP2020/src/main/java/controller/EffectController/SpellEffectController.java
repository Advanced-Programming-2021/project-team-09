package controller.EffectController;

import controller.GameMenuController;
import controller.database.csvInfoGetter;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.card.monster.MonsterType;
import model.card.spell_traps.Limitation;
import model.card.spell_traps.Spell;
import model.card.spell_traps.SpellType;
import model.deck.Deck;
import model.game.*;
import model.card.Card;
import org.jetbrains.annotations.NotNull;
import view.CardEffectsView;
import view.responses.CardEffectsResponses;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class SpellEffectController extends EffectController {

    public void MonsterReborn(Game game, Card card) {
        Board board = getBoard(game, card);
        if (board.isMonsterZoneFull()) {
            CardEffectsView.respond(CardEffectsResponses.MONSTER_ZONE_IS_FULL);
            return;
        }
        while (true) {
            Card chosenCard = CardEffectsView.getCardFromBothGraveyards(game.getPlayerBoard().getGraveyard(), game.getRivalBoard().getGraveyard());
            if (chosenCard == null) return;
            if (!chosenCard.isMonster()) {
                board.addCardToMonsterZone(chosenCard);
                int cellNumber = MonsterEffectController.getCellNumberOfMonster(game, chosenCard);
                board.getMonsterZone(cellNumber).setState(State.FACE_UP_ATTACK);
                return;
            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
        }

    }

    public void TerraForming(Game game, Card card) {
        boolean hasFieldZoneSpell = false;
        for (Card tempCard : game.getPlayerDeck().getAllCards()) {
            if (tempCard.isSpell()) {
                Spell spell = (Spell) tempCard;
                if (spell.getSpellType().equals(SpellType.FIELD)) hasFieldZoneSpell = true;
            }
        }
        if (!hasFieldZoneSpell) CardEffectsView.respond(CardEffectsResponses.YOU_DONT_HAVE_ANY_FIELD_SPELL);
        while (hasFieldZoneSpell) {
            Card chosenCard = CardEffectsView.getCardFromDeck(game.getPlayerDeck());
            if (chosenCard.isSpell()) {
                Spell spell = (Spell) chosenCard;
                if (spell.getSpellType().equals(SpellType.FIELD)) {
                    game.addCardToHand(spell);
                    break;
                } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL);
            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL);
        }
    }

    public void PotofGreed(Game game, Card card) {
        ArrayList<Card> cardsInHands = getCardsInHand(game, card);
        Deck deck = getDeck(game, card);
        if (deck.getAllCards().size() >= 2) {
            cardsInHands.add(deck.getMainDeck().getCards().get(0));
            deck.getMainDeck().getCards().remove(0);
            cardsInHands.add(deck.getMainDeck().getCards().get(0));
            deck.getMainDeck().getCards().remove(0);
        } else game.setWinner(game.getRival());
    }

    public void Raigeki(Game game, Card card) {
        Board board = getBoard(game, card);
        for (Cell tempCell : board.getMonsterZone()) {
            if (tempCell.isOccupied()) board.removeCardFromMonsterZone(tempCell.getCard());
        }
    }

    public void ChangeofHeart(Game game, Card card) {
        Limits limits = getRivalsLimits(game, card);
        Board rivalBoard = getRivalBoard(game, card);
        if (rivalBoard.getMonsterZone().length == 0) CardEffectsView.respond(CardEffectsResponses.NO_MONSTERS);
        else {
            int monsterNumber = CardEffectsView.getCellNumber() - 1;
            if (isCellNumberValid(monsterNumber)) {
                if (rivalBoard.getMonsterZone(monsterNumber).isOccupied()) {
                    Card card1 = rivalBoard.getMonsterZone(monsterNumber).getCard();
                    limits.loseControlOfMonster(card1);
                } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
        }
    }


    public void harpiesFeatherDuster(Game game, Card card) {
        Board board = getBoard(game, card);
        for (Cell tempCell : board.getSpellZone()) {
            if (tempCell.isOccupied()) board.removeCardFromSpellZone(tempCell.getCard());
        }
    }

    public void SwordsofRevealingLight(Game game, Card card) {
        Limits limits = getRivalsLimits(game, card);
        Board board = getRivalBoard(game, card);
        Cell[] cells = board.getMonsterZone();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].isOccupied() && cells[i].isFaceDown()) {
                GameMenuController.rivalFlipSummon(game, i);
            }
        }
        limits.addLimit(EffectLimitations.CANT_ATTACK);
    }


    public void DarkHole(Game game, Card card) {
        destroyAllMonsters(game);
    }

    public void SupplySquad(Game game, Card card) {
        Deck deck = getDeck(game, card);
        if (deck.getMainDeck().getCards().size() == 0) {
            game.setWinner(getWinner(game, card));
        } else {
            if (doesCardBelongsToPlayer(game, card)) {
                GameMenuController.draw(game);
            } else {
                //ToDo draw!
            }
        }
    }

    public void SpellAbsorption(Game game, Card card) {
        if (doesCardBelongsToPlayer(game, card)) {
            game.increaseHealth(500);
        } else {
            game.increaseRivalHealth(500);
        }
    }

    public void Messengerofpeace(Game game, Card card) {
        game.getPlayerLimits().setAttackBound(1500);
        game.getPlayerLimits().addCardLimitOnATKBound(card);
        game.getRivalLimits().setAttackBound(1500);
        game.getRivalLimits().addCardLimitOnATKBound(card);
        if (CardEffectsView.doYouWantTo("do you want to 100 LP to keep this card?")) {
            if (doesCardBelongsToPlayer(game, card)) game.decreaseHealth(100);
            else game.decreaseRivalHealth(100);
        } else {
            GameMenuController.sendToGraveYard(game, card);
        }
    }

    public void TwinTwisters(Game game, Card card) {
        ArrayList<Card> cardsInHand = getCardsInHand(game, card);
        Board board = getBoard(game, card);
        if (cardsInHand.size() == 0) CardEffectsView.respond(CardEffectsResponses.HAVE_NO_CARDS);
        else {
            while (true) {
                int cardNumberInHand = CardEffectsView.getNumberOfCardInHand() - 1;
                if (cardNumberInHand < cardsInHand.size()) {
                    Card cardToBeRemoved = cardsInHand.get(cardNumberInHand);
                    cardsInHand.remove(cardToBeRemoved);
                    board.sendToGraveYard(card);
                    if (CardEffectsView.doYouWantTo("do you want to destroy a spell card?")) {
                        for (int i = 0; i < 2; ++i) {
                            if (i == 1 && CardEffectsView.doYouWantTo("do you want to destroy another spell card?")) {
                                while (true) {
                                    Card card1 = CardEffectsView.getCardFromBothBoards(game.getPlayerBoard().getSpellZone(), game.getRivalBoard().getSpellZone());
                                    if (card1 == null) {
                                        CardEffectsView.respond(CardEffectsResponses.HAVE_NO_CARDS);
                                        return;
                                    } else {
                                        GameMenuController.sendToGraveYard(game, card1);
                                    }
                                }
                            }
                        }
                    }
                } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
            }
        }
    }

    public void Mysticalspacetyphoon(Game game, Card card) {
        Card chosenCard;
        while (true) {
            chosenCard = CardEffectsView.getCardFromBothBoards(game.getPlayerBoard().getSpellZone(), game.getRivalBoard().getSpellZone());
            if (chosenCard == null) return;
            if (chosenCard.isSpell()) break;
            else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_AN_SPELL);
        }
        Board board = getBoard(game, card);
        board.removeCardFromSpellZone(chosenCard);
    }

    public void RingofDefense(Game game, Card card) {
        //ToDo
    }

    public void Yami(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.FAIRY, -200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.FIEND, +200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.SPELLCASTER, +200);
    }

    public void Forest(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.BEAST, 200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.BEAST_WARRIOR, 200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.INSECT, 200);
    }

    public void ClosedForest(Game game, Card card) {
        Limits limits;
        if (doesCardBelongsToPlayer(game, card)) limits = game.getPlayerLimits();
        else limits = game.getRivalLimits();
        limits.addFieldZoneATK(MonsterType.BEAST, 100);
    }

    public void UMIIRUKA(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        playerLimits.addFieldZoneATK(MonsterType.AQUA, 500);
        rivalLimits.addFieldZoneATK(MonsterType.AQUA, 500);
        playerLimits.addFieldZoneDEF(MonsterType.AQUA, -400);
        rivalLimits.addFieldZoneDEF(MonsterType.AQUA, -400);
    }


    public void SwordofDarkDestruction(Game game, Card card) {
        Board board = getBoard(game, card);
        Limits limits = getLimits(game, card);
        if (!isThereAnyFaceUpMonsters(board)) {
            board.removeCardFromSpellZone(card);
            CardEffectsView.respond(CardEffectsResponses.NO_MONSTERS);
            return;
        }
        while (true) {
            int cellNumber = CardEffectsView.getCellNumber() - 1;
            if (isCellNumberValid(cellNumber)) {
                Cell cell = board.getMonsterZone(cellNumber);
                if (cell.isOccupied() && cell.isFaceUp()) {
                    Monster monster = (Monster) board.getMonsterZone(cellNumber).getCard();
                    MonsterType monsterType = monster.getMonsterType();
                    if (monsterType.equals(MonsterType.FIEND) || monsterType.equals(MonsterType.SPELLCASTER)) {
                        limits.equipGadgetATKAddition(card, 400);
                        limits.equipGadgetDEFAddition(card, -200);
                    } else {
                        limits.equipGadgetATKAddition(card, 0);
                        limits.equipGadgetDEFAddition(card, 0);
                    }
                    limits.equipMonsterToCard(card, monster);
                    break;
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        }
    }

    public void BlackPendant(Game game, Card card) {
        Board board = getBoard(game, card);
        Limits limits = getLimits(game, card);
        if (!isThereAnyFaceUpMonsters(board)) {
            board.removeCardFromSpellZone(card);
            CardEffectsView.respond(CardEffectsResponses.NO_MONSTERS);
            return;
        }
        addLimitationForEquipments(card, board, limits, 500, 0);
    }

    public void UnitedWeStand(Game game, Card card) {
        Board board = getBoard(game, card);
        Limits limits = getLimits(game, card);
        if (!isThereAnyFaceUpMonsters(board)) {
            board.removeCardFromSpellZone(card);
            CardEffectsView.respond(CardEffectsResponses.NO_MONSTERS);
            return;
        }

        Cell[] cells = board.getMonsterZone();
        int countFaceUpMonsters = 0;
        for (Cell cell : cells) {
            if (cell.isOccupied() && cell.isFaceUp()) countFaceUpMonsters++;
        }
        addLimitationForEquipments(card, board, limits, 800 * countFaceUpMonsters, 800 * countFaceUpMonsters);
    }

    public void MagnumShield(Game game, Card card) {
        Board board = getBoard(game, card);
        Limits limits = getLimits(game, card);
        if (!isThereAnyFaceUpMonsters(board) && !isThereAnyFaceUpCardWithType(board, MonsterType.WARRIOR)) {
            board.removeCardFromSpellZone(card);
            CardEffectsView.respond(CardEffectsResponses.NO_MONSTERS);
            return;
        }
        while (true) {
            int cellNumber = CardEffectsView.getCellNumber() - 1;
            if (isCellNumberValid(cellNumber)) {
                Cell cell = board.getMonsterZone(cellNumber);
                if (cell.isOccupied() && cell.isFaceUp()) {
                    Monster monster = (Monster) board.getMonsterZone(cellNumber).getCard();
                    MonsterType monsterType = monster.getMonsterType();
                    if (monsterType.equals(MonsterType.WARRIOR)) {
                        int atk = cell.isAttack() ? monster.getAttack() : 0;
                        int def = cell.isDefence() ? monster.getDefense() : 0;
                        setEquipmentInLimits(card, board, limits, atk, def, cellNumber);
                        break;
                    } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_TYPE);
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        }
    }

    public void AdvancedRitualArt(Game game, Card card) {
        Board board = getBoard(game, card);
        ArrayList<Card> cardsInHand = getCardsInHand(game, card);
        if (canRitualSummon(game, card)) {
            CardEffectsView.respond(CardEffectsResponses.SPECIAL_SUMMON_NOW);
            main:
            while (true) {
                int cardNumber = CardEffectsView.getNumberOfCardInHand();
                Card card1 = cardsInHand.get(cardNumber);
                if (card1.isMonster()) {
                    Monster monster = (Monster) card1;
                    if (card1.getFeatures().contains(CardFeatures.RITUAL_SUMMON)) {
                        if (isThereAnyCombinationOfCardsThatTheyLevelEqualsTo(board, monster.getLevel())) {
                            int[] cellNumbers = CardEffectsView.getCellNumbers();
                            for (int cellNumber : cellNumbers) {
                                if (!isCellNumberValid(cellNumber)) {
                                    CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
                                    continue main;
                                }
                            }
                            int sumOfLevel = board.getSumLevel(cellNumbers);
                            if (sumOfLevel == monster.getLevel()) {
                                GameMenuController.tribute(game, cellNumbers);
                                game.summonMonster(monster);
                                int cellNumber1 = getCellNumberOfSpell(game, monster);
                                board.getMonsterZone(cellNumber1).setState(State.FACE_UP_ATTACK);
                                return;
                            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
                        } else CardEffectsView.respond(CardEffectsResponses.CANT_RITUAL_SUMMON);
                    } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_MONSTER);
                } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
            }
        } else CardEffectsView.respond(CardEffectsResponses.CANT_RITUAL_SUMMON);
        cantRitualSummon(game, card, board);
    }


    //traps!
    public void magicCylinder(Game game, Card card) {

    }

    public void mirrorForce(Game game, Card card) {
        Board board = getRivalBoard(game,card);
        Cell[] monsterZone = board.getMonsterZone();
        for (Cell cell : monsterZone) {
            if (cell.isOccupied() && cell.isFaceUp()) {
                Card cardToBeDestroyed = cell.getCard();
                GameMenuController.sendToGraveYard(game,cardToBeDestroyed);
            }
        }
    }

    public void mindCrush(Game game, Card card) {
        Board board = getBoard(game, card);
        Board opponentsBoard = getRivalBoard(game, card);
        ArrayList<Card> cards = getCardsInHand(game, card);
        ArrayList<Card> opponentsCards = getRivalsCardsInHand(game, card);

        String cardName = CardEffectsView.getCardName();
        if (csvInfoGetter.cardNameExists(cardName) && doWeHaveACardWithNameInHand(cardName, opponentsCards)) {
            for (Card card1 : opponentsCards) {
                if (card1.getCardName().equals(cardName)) {
                    opponentsBoard.sendToGraveYard(card1);
                    opponentsCards.remove(card1);
                }
            }
        } else {
            removeARandomCardFromHand(board, cards);
        }

    }


    public void trapHole(Game game, Card card) {

    }

    public void torrentialTribute(Game game, Card card) {
        destroyAllMonsters(game);
    }


    public void timeSeal(Game game, Card card) {
        Limits limits = getRivalsLimits(game, card);
        limits.addLimit(EffectLimitations.HAS_NO_DRAW_PHASE);
    }

    public void negateAttack(Game game, Card card) {

    }

    public void solemnWarning(Game game, Card card) {

    }

    public void magicJammer(Game game, Card card) {

    }

    public void calloftheHaunted(Game game, Card card) {

    }

    private void addPowerNumbersToType(Limits playerLimits, Limits rivalLimits, MonsterType type, int amount) {
        playerLimits.addFieldZoneATK(type, amount);
        playerLimits.addFieldZoneDEF(type, amount);
        rivalLimits.addFieldZoneATK(type, amount);
        rivalLimits.addFieldZoneDEF(type, amount);
    }

    public int getCellNumberOfSpell(Game game, Card card) {
        Board board;
        board = getBoard(game, card);
        Cell[] cells = board.getSpellZone();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getCard().equals(card)) return i;
        }
        return 0;
    }

    private void addLimitationForEquipments(Card card, Board board, Limits limits, int atk, int def) {
        while (true) {
            int cellNumber = CardEffectsView.getCellNumber() - 1;
            if (isCellNumberValid(cellNumber)) {
                Cell cell = board.getMonsterZone(cellNumber);
                if (cell.isOccupied() && cell.isFaceUp()) {
                    setEquipmentInLimits(card, board, limits, atk, def, cellNumber);
                    break;
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        }
    }

    private void setEquipmentInLimits(Card card, Board board, Limits limits, int atk, int def, int cellNumber) {
        Monster monster = (Monster) board.getMonsterZone(cellNumber).getCard();
        limits.equipGadgetATKAddition(card, atk);
        limits.equipGadgetDEFAddition(card, def);
        limits.equipMonsterToCard(card, monster);
    }

    private boolean isThereAnyFaceUpMonsters(Board board) {
        Cell[] cells = board.getMonsterZone();
        for (Cell cell : cells) {
            if (cell.isOccupied() && cell.isOccupied()) return true;
        }
        return false;
    }

    private boolean isThereAnyFaceUpCardWithType(Board board, MonsterType monsterType) {
        Cell[] cells = board.getMonsterZone();
        for (Cell cell : cells) {
            if (cell.isOccupied() && cell.isFaceUp() && ((Monster) cell.getCard()).getMonsterType().equals(monsterType))
                return true;
        }
        return false;
    }

    private void cantRitualSummon(Game game, Card card, Board board) {
        CardEffectsView.respond(CardEffectsResponses.CANT_RITUAL_SUMMON);
        int cellNumberSpell = getCellNumberOfSpell(game, card);
        board.getSpellZone(cellNumberSpell).setState(State.FACE_DOWN_SPELL);
    }

    private boolean isThereAnyCombinationOfCardsThatTheyLevelEqualsTo(Board board, int level) {
        return false;
    }

    private ArrayList<Integer> getLevesOfRitualMonstersInHand(ArrayList<Card> cardsInHand) {
        ArrayList<Integer> levelsOfRitualMonsters = new ArrayList<>();
        for (Card card1 : cardsInHand) {
            if (card1.getFeatures().contains(CardFeatures.RITUAL_SUMMON))
                levelsOfRitualMonsters.add(((Monster) card1).getLevel());
        }
        return levelsOfRitualMonsters;
    }

    private boolean canRitualSummon(Game game, Card card) {
        Board board = getBoard(game, card);
        ArrayList<Card> cardsInHand = getCardsInHand(game, card);
        ArrayList<Integer> levelsOfRitualMonsters = getLevesOfRitualMonstersInHand(cardsInHand);
        if (board.isMonsterZoneFull()) {
            return false;
        }
        if (levelsOfRitualMonsters.size() == 0) {
            return false;
        }
        boolean canSummon = false;
        for (Integer level : levelsOfRitualMonsters) {
            if (isThereAnyCombinationOfCardsThatTheyLevelEqualsTo(board, level)) {
                canSummon = true;
            }
        }
        return canSummon;
    }

    private boolean doWeHaveACardWithNameInHand(String cardName, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getCardName().equals(cardName)) return true;
        }
        return false;
    }

    private void removeARandomCardFromHand(Board board, ArrayList<Card> cards) {
        int cardToBeDeleted = LocalDateTime.now().getSecond() % cards.size();
        board.sendToGraveYard(cards.get(cardToBeDeleted));
        cards.remove(cardToBeDeleted);
    }

    private void destroyAllMonsters(Game game) {
        for (Cell cell : game.getPlayerBoard().getMonsterZone()) {
            if (cell.isOccupied()) GameMenuController.sendToGraveYard(game, cell.getCard());
        }
        for (Cell cell : game.getRivalBoard().getMonsterZone()) {
            if (cell.isOccupied()) GameMenuController.sendToGraveYard(game, cell.getCard());
        }
    }
}



