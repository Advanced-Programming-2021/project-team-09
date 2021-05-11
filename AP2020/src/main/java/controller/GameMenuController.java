package controller;

import model.User;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.deck.Graveyard;
import model.game.Cell;
import model.game.Game;
import model.game.Limits;
import model.game.State;
import view.TributeMenu;
import view.duelMenu.specialCardsMenu.ScannerMenu;
import view.responses.GameMenuResponse;
import view.responses.GameMenuResponsesEnum;

import java.util.ArrayList;

public class GameMenuController {
    public static String showTable(Game game) {
        return game.showTable();
    }

    public static GameMenuResponse selectMonsterFromPlayer(Game game, int cellNumber) {
        return selectCardFromPlayerArray(cellNumber, game.getPlayerBoard().getMonsterZone());
    }

    public static GameMenuResponse selectSpellAndTrapFromPlayer(Game game, int cellNumber) {
        return selectCardFromPlayerArray(cellNumber, game.getPlayerBoard().getSpellZone());
    }

    // for easier application of selectMonsterFromPlayer & selectSpellAndTrapFromPlayer
    private static GameMenuResponse selectCardFromPlayerArray(int cellNumber, Cell[] cells) {
        if (cellNumber < 1 || cellNumber > 5) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        Cell tempCell = cells[cellNumber - 1];
        if (!tempCell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(tempCell, GameMenuResponsesEnum.SUCCESSFUL);
    }


    public GameMenuResponse selectSpellAndTrapFromRival(Game game, int cellNumber) {
        return selectFromRivalArray(cellNumber, game.getRivalBoard().getSpellZone());
    }

    public static GameMenuResponse selectMonsterFromRival(Game game, int cellNumber) {
        return selectFromRivalArray(cellNumber, game.getRivalBoard().getMonsterZone());
    }

    // for easier application of selectSpellAndTrapFromRival & selectMonsterFromRival
    private static GameMenuResponse selectFromRivalArray(int cellNumber, Cell[] cells) {
        if (cellNumber < 1 || cellNumber > 5) return respond(GameMenuResponsesEnum.SUCCESSFUL);
        Cell tempCell = cells[cellNumber - 1];
        if (!tempCell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        State tempState = tempCell.getState();
        if (tempState == State.FACE_DOWN_DEFENCE || tempState == State.FACE_DOWN_SPELL)
            return respond(GameMenuResponsesEnum.CARD_IS_HIDDEN);
        return respondWithObj(tempCell, GameMenuResponsesEnum.SUCCESSFUL);
    }


    public static GameMenuResponse selectPlayerFieldZone(Game game) {
        return selectFieldZone(game.getPlayerBoard().getFieldZone());
    }

    public static GameMenuResponse selectRivalFieldZone(Game game) {
        return selectFieldZone(game.getRivalBoard().getFieldZone());
    }

    // for easier application of selectRivalFieldZone & selectPlayerFieldZone
    private static GameMenuResponse selectFieldZone(Cell cell) {
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell, GameMenuResponsesEnum.SUCCESSFUL);
    }

    // returns the name of card which was added to hand
    public static GameMenuResponse draw(Game game) {
        if (!game.playerHasCapacityToDraw()) return respond(GameMenuResponsesEnum.PLAYER_HAND_IS_FULL);
        if (game.getPlayerDeck().getMainDeck().getNumberOfAllCards() == 0)
            return respond(GameMenuResponsesEnum.NO_CARDS_IN_MAIN_DECK);
        String temp = game.getPlayerDeck().getMainDeck().getCards().get(0).toString();
        game.playerDrawCard();
        return respondWithObj(temp, GameMenuResponsesEnum.SUCCESSFUL);
    }
    public static GameMenuResponse drawRival(Game game) {
        if (!game.rivalHasCapacityToDraw()) return respond(GameMenuResponsesEnum.PLAYER_HAND_IS_FULL);
        if (game.getRivalDeck().getMainDeck().getNumberOfAllCards() == 0)
            return respond(GameMenuResponsesEnum.NO_CARDS_IN_MAIN_DECK);
        String temp = game.getRivalDeck().getMainDeck().getCards().get(0).toString();
        game.rivalDrawCard();
        return respondWithObj(temp /* :) */, GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse selectCardFromHand(Game game, int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size() || cardNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return respondWithObj(tempCards.get(cardNumber - 1), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse summon(Game game, int cardNumberInHand) {
        ArrayList<Card> cardsInHand = game.getPlayerHandCards();
        if (cardNumberInHand > cardsInHand.size() || cardNumberInHand < 1)
            return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        Card card = cardsInHand.get(cardNumberInHand - 1);
        if (card.isMonster()) {
            Monster monster = (Monster) card;
            if (!game.canSummon()) return respond(GameMenuResponsesEnum.ALREADY_SUMMONED);
            if (!canNormalSummon(card.getFeatures())) return respond(GameMenuResponsesEnum.CANT_NORMAL_SUMMON);
            if (game.isMonsterZoneFull()) return respond(GameMenuResponsesEnum.MONSTER_ZONE_IS_FULL);
            if (cardHasScannerEffect(card.getFeatures())) {
                return scannerController(game, card);
            }
            if (monster.getLevel() > 4) {
                int numberOfTributes = monster.getLevel() > 6 ? 2 : 1;
                Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
                int occupied = 0;
                for (Cell cell : tempCells) if (cell.isOccupied()) occupied++;
                if (numberOfTributes > occupied) return respond(GameMenuResponsesEnum.NOT_ENOUGH_MONSTERS);
                int[] tributes = TributeMenu.run(numberOfTributes);
                if (tributes == null) return respond(GameMenuResponsesEnum.ABORTED);
                while (!canTributeSelectedCards(tempCells, tributes)) {
                    TributeMenu.invalidTributes();
                    tributes = TributeMenu.run(numberOfTributes);
                    if (tributes == null) return respond(GameMenuResponsesEnum.ABORTED);
                }
                tribute(game, tributes);
            }
            putCardInNearestCell(card, game.getPlayerBoard().getMonsterZone(), State.FACE_UP_ATTACK);
        } else {
            if (game.isSpellZoneFull()) return respond(GameMenuResponsesEnum.SPELL_AND_TRAP_ZONE_IS_FULL);
            Cell[] tempCells = game.getPlayerBoard().getSpellZone();
            putCardInNearestCell(card, tempCells, State.FACE_UP_SPELL);
        }
        if (cardHasSummonEffect(card.getFeatures()))
            activeEffect(game, card, game.getRival(), 1);
        game.setCanSummonCard(false);
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    private static void putCardInNearestCell(Card card, Cell[] cells, State state) {
        for (Cell cell : cells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(state);
            return;
        }
    }

    private static Boolean canTributeSelectedCards(Cell[] cells, int[] cellNumbers) {
        for (int i : cellNumbers) if (!cells[i - 1].isOccupied()) return false;
        for (int i = 0; i < cellNumbers.length; i++) {
            for (int j = i + 1; j < cellNumbers.length; j++) {
                if (cellNumbers[j] == cellNumbers[i]) return false;
            }
        }
        return true;
    }

    public static void tribute(Game game, int[] cellNumbers) {
        for (int i : cellNumbers) game.getGraveyard().addCard(game.getPlayerBoard().getMonsterZone(i - 1).removeCard());
    }

    private static boolean cardHasSummonEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures cardFeatures : features) if (cardFeatures == CardFeatures.SUMMON_EFFECT) return true;
        return false;
    }

    private static boolean cardHasScannerEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures cardFeatures : features) if (cardFeatures == CardFeatures.SCANNER) return true;
        return false;
    }

    private static GameMenuResponse scannerController(Game game, Card card) {
        ArrayList<Card> graveyardCards = game.getRivalBoard().getGraveyard().getCards();
        ArrayList<Card> cardsToBeShown = new ArrayList<>();
        for (Card tempCard : graveyardCards) {
            if (!cardHasScannerEffect(tempCard.getFeatures())) {
                cardsToBeShown.add(tempCard);
            }
        }
        if (cardsToBeShown.size() == 0) {
            putCardInNearestCell(card, game.getPlayerBoard().getMonsterZone(), State.FACE_UP_ATTACK);
        } else {
            Card tempCard = ScannerMenu.run(cardsToBeShown);
            while (!tempCard.isMonster()) {
                ScannerMenu.pleaseSelectMonster();
                tempCard = ScannerMenu.run(cardsToBeShown);
            }
            Monster tempMonster = (Monster) tempCard;
            Monster scannerMonster = (Monster) card;
            scannerMonster.setCardName(tempMonster.getCardName());
            scannerMonster.setDescription(tempMonster.getDescription());
            scannerMonster.setFeatures(tempMonster.getFeatures());
            scannerMonster.setAttack(tempMonster.getAttack());
            scannerMonster.setDefense(tempMonster.getDefense());
            scannerMonster.setLevel(tempMonster.getLevel());
            scannerMonster.setMonsterEffectType(tempMonster.getMonsterEffectType());
            scannerMonster.setMonsterType(tempMonster.getMonsterType());
            scannerMonster.setMonsterCardType(tempMonster.getMonsterCardType());
            scannerMonster.addFeature(CardFeatures.SCANNER);
        }
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    // for cards like beast king barbaros
    private static boolean normalAndSpecialSummonChecker(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.HAS_SPECIAL_NORMAL_SUMMON) return true;
        return false;
    }

    private static boolean canNormalSummon(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.NORMAL_SUMMON) return true;
        return false;
    }

    public static GameMenuResponse attack(Game game, int attackerCellNumber, int defenderCellNumber) {
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        if (attackerCellNumber > 5 || attackerCellNumber < 1 || defenderCellNumber > 5 || defenderCellNumber < 1)
            return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        if (!tempCells[attackerCellNumber - 1].isOccupied()
                || !game.getRivalBoard().getMonsterZone()[defenderCellNumber - 1].isOccupied())
            return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        if (tempCells[attackerCellNumber - 1].getState() == State.FACE_DOWN_DEFENCE)
            return respond(GameMenuResponsesEnum.YOU_HAVENT_SUMMONED_YET);
        if (!tempCells[attackerCellNumber - 1].canAttack())
            return respond(GameMenuResponsesEnum.ALREADY_ATTACKED);
        Cell defender = game.getRivalBoard().getMonsterZone(defenderCellNumber - 1);
        Cell attacker = tempCells[attackerCellNumber - 1];
        Monster attackerMonster = (Monster) attacker.getCard();
        Monster defenderMonster = (Monster) defender.getCard();
        if (!game.getPlayerLimits().canAttackByThisLimitations(attackerMonster))
            return respond(GameMenuResponsesEnum.CANT_ATTACK);
        int attackerPoint, defenderPoint;
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        if (!game.getRivalLimits().canAttackCell(defenderCellNumber)) return respond(GameMenuResponsesEnum.CANT_ATTACK);
        if (hasMakeAttackerZeroEffect(defender.getCard().getFeatures()) || hasNotUsedEffect(defender.getCard().getFeatures())) {
            ((Monster) attacker.getCard()).setAttack(0);
            defender.getCard().addFeature(CardFeatures.USED_EFFECT);
            return respondWithObj("Your monster attack point was reduced to zero",
                    GameMenuResponsesEnum.ABORTED);
        }

        String answer = "";
        if (defender.getState() == State.FACE_DOWN_DEFENCE) {
            rivalFlipSummon(game, defenderCellNumber);
            attackerPoint = attackerMonster.getAttack() + playerLimits.getATKAddition(attackerMonster);
            defenderPoint = defenderMonster.getDefense() + rivalLimits.getDEFAddition(defenderMonster);
            if (attackerPoint == defenderPoint) { // both cards stay on board .. no player damage
                attacker.setCanAttack(false);
                if (hasLPReductionAfterDamage(defenderMonster.getFeatures())) {
                    activeEffect(game, defenderMonster, game.getRival(), 1);
                }
                return respondWithObj("opponent’s monster card was " + defenderMonster.getCardName() + " and no card was destroyed",
                        GameMenuResponsesEnum.SUCCESSFUL);
            } else if (attackerPoint > defenderPoint) { // defense monster destroyed .. no player damage
                ArrayList<CardFeatures> features = defender.getCard().getFeatures();
                String tempString = "";
                if (!hasCantBeDestroyedFeature(defenderMonster.getFeatures())) {
                    moveToRivalGraveyard(game, defenderCellNumber, true);
                    answer = " and the defense position monster was destroyed";
                    if (hasDestroyAttackerEffect(features)) {
                        moveToPlayerGraveyard(game, attackerCellNumber, true);
                        tempString = "\nYour card was also destroyed by rival's card effect";
                    }
                } else answer = " and the defense position monster was not destroyed";
                if (hasLPReductionAfterDamage(defenderMonster.getFeatures())) {
                    activeEffect(game, defenderMonster, game.getRival(), 1);
                }
                return respondWithObj("opponent’s monster card was " + defenderMonster.getCardName()
                                + answer + tempString,
                        GameMenuResponsesEnum.SUCCESSFUL);
            } else { // both stay on board ... player takes damage
                int damage = decreasePlayerLP(game, defenderPoint - attackerPoint);
                attacker.setCanAttack(false);
                if (hasLPReductionAfterDamage(defenderMonster.getFeatures())) {
                    activeEffect(game, defenderMonster, game.getRival(), 1);
                }
                return respondWithObj("opponent’s monster card was " + defenderMonster.getCardName() + " and no card is destroyed and you received "
                        + damage + " battle damage", GameMenuResponsesEnum.SUCCESSFUL);
            }
        } else if (defender.getState() == State.FACE_UP_ATTACK) {
            attackerPoint = attackerMonster.getAttack() + playerLimits.getATKAddition(attackerMonster);
            defenderPoint = defenderMonster.getAttack() + rivalLimits.getATKAddition(defenderMonster);
            if (attackerPoint == defenderPoint) { // destroy both cards .. no one takes damage
                answer = "Your card was not destroyed ";
                String answer2 = "and your opponents card was not destroyed ";
                if (!hasCantBeDestroyedFeature(attackerMonster.getFeatures())) {
                    moveToPlayerGraveyard(game, attackerCellNumber, true);
                    answer = "Your card was destroyed ";
                }
                if (!hasCantBeDestroyedFeature(defenderMonster.getFeatures())) {
                    moveToRivalGraveyard(game, defenderCellNumber, true);
                    answer2 = "and your opponents card was destroyed ";
                    if (hasCantBeDestroyedFeature(attackerMonster.getFeatures()) && hasDestroyAttackerEffect(defenderMonster.getFeatures())) {
                        moveToPlayerGraveyard(game, attackerCellNumber, true);
                        answer = "Your card was destroyed ";
                    }
                }
                return respondWithObj(answer + answer2 + "and no one receives damage",
                        GameMenuResponsesEnum.SUCCESSFUL);
            } else if (attackerPoint > defenderPoint) { // defender monster is destroyed .. rival takes damage
                attacker.setCanAttack(false);
                ArrayList<CardFeatures> features = defender.getCard().getFeatures();
                String tempString = "";
                if (!hasCantBeDestroyedFeature(defenderMonster.getFeatures())) {
                    moveToRivalGraveyard(game, defenderCellNumber, true);
                    if (hasDestroyAttackerEffect(features)) {
                        moveToPlayerGraveyard(game, attackerCellNumber, true);
                        tempString = "\nYour card was also destroyed by rival's card effect";
                    }
                    answer = "your opponent’s monster is destroyed and your opponent receives ";
                } else answer = "your opponent’s monster was not destroyed and your opponent receives ";
                int damage = decreaseRivalLP(game, attackerPoint - defenderPoint);
                return respondWithObj(answer + damage + " battle damage" + tempString,
                        GameMenuResponsesEnum.SUCCESSFUL);
            } else { // attacker monster is destroyed .. player takes damage
                if (!hasCantBeDestroyedFeature(attackerMonster.getFeatures())) {
                    moveToPlayerGraveyard(game, attackerCellNumber, true);
                    answer = "Your monster card is destroyed and you received ";
                } else {
                    answer = "Your monster card is not destroyed and you received";
                }
                int damage = decreasePlayerLP(game, defenderMonster.getAttack() - attackerMonster.getAttack());
                return respondWithObj(answer + damage + " battle damage",
                        GameMenuResponsesEnum.SUCCESSFUL);
            }
        } else {
            attackerPoint = attackerMonster.getAttack() + playerLimits.getATKAddition(attackerMonster);
            defenderPoint = defenderMonster.getDefense() + rivalLimits.getDEFAddition(defenderMonster);
            if (attackerPoint == defenderPoint) { // both cards stay on board .. no player damage
                attacker.setCanAttack(false);
                return respondWithObj("no card was destroyed",
                        GameMenuResponsesEnum.SUCCESSFUL);
            } else if (attackerPoint > defenderPoint) { // defense monster destroyed .. no player damage
                attacker.setCanAttack(false);
                ArrayList<CardFeatures> features = defender.getCard().getFeatures();
                String tempString = "";
                if (!hasCantBeDestroyedFeature(features)) {
                    moveToRivalGraveyard(game, defenderCellNumber, true);
                    answer = "the defense position monster is destroyed";
                    if (hasDestroyAttackerEffect(features)) {
                        moveToPlayerGraveyard(game, attackerCellNumber, true);
                        tempString = "\nYour card was also destroyed by rival's card effect";
                    }
                } else {
                    answer = "the defense position monster was not destroyed";
                }
                return respondWithObj(answer + tempString,
                        GameMenuResponsesEnum.SUCCESSFUL);
            } else { // no card is destroyed .. player takes damage
                attacker.setCanAttack(false);
                int damage = decreasePlayerLP(game, defenderPoint - attackerPoint);
                return respondWithObj("no card is destroyed and you received " + damage + " battle damage",
                        GameMenuResponsesEnum.SUCCESSFUL);
            }
        }
    }

    private static boolean hasNotUsedEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.USED_EFFECT) return true;
        return false;
    }

    private static boolean hasMakeAttackerZeroEffect(ArrayList<CardFeatures> cardFeatures) {
        for (CardFeatures feature : cardFeatures) if (feature == CardFeatures.MAKE_ATTACKER_ZERO) return true;
        return false;
    }

    private static boolean hasDestroyAttackerEffect(ArrayList<CardFeatures> cardFeatures) {
        for (CardFeatures feature : cardFeatures) if (feature == CardFeatures.DESTROY_ATTACKER) return true;
        return false;
    }

    private static boolean hasCantBeDestroyedFeature(ArrayList<CardFeatures> cardFeatures) {
        for (CardFeatures feature : cardFeatures) if (feature == CardFeatures.CANT_DESTROY_IN_ATTACK) return true;
        return false;
    }

    private static boolean hasLPReductionAfterDamage(ArrayList<CardFeatures> cardFeatures) {
        for (CardFeatures feature : cardFeatures) if (feature == CardFeatures.LP_REDUCTION_AFTER_DAMAGE) return true;
        return false;
    }

    private static int decreasePlayerLP(Game game, int damage) {
        game.decreaseRivalHealth(damage);
        return damage;
        // todo effects
    }

    private static int decreaseRivalLP(Game game, int damage) {
        game.decreaseHealth(damage);
        return damage;
        // todo effects
    }

    public static GameMenuResponse setMonsterCard(Game game, int cardNumberInHand) {
        ArrayList<Card> handCards = game.getPlayerHandCards();
        if (cardNumberInHand > handCards.size()) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        if (!handCards.get(cardNumberInHand - 1).isMonster())
            return respond(GameMenuResponsesEnum.PLEASE_SELECT_MONSTER);
        if (game.isMonsterZoneFull()) return respond(GameMenuResponsesEnum.MONSTER_ZONE_IS_FULL);
        if (!game.canSummon()) return respond(GameMenuResponsesEnum.ALREADY_SUMMONED);
        Card card = handCards.get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_DEFENCE);
            break;
        }
        game.setCanSummonCard(false);
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse setMonsterPosition(Game game, int cellNumber, String attackOrDefence) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        if (!tempCells[cellNumber - 1].isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        Card tempCard = tempCells[cellNumber - 1].getCard();
        if (tempCells[cellNumber - 1].getState() == State.FACE_DOWN_DEFENCE)
            return respond(GameMenuResponsesEnum.YOU_HAVENT_SUMMONED_YET);
        State tempState = attackOrDefence.equals("attack") ? State.FACE_UP_ATTACK : State.FACE_UP_DEFENCE;
        if (tempCells[cellNumber - 1].getState() == tempState)
            return respond(GameMenuResponsesEnum.ALREADY_IN_THIS_POSITION);
        if (tempCells[cellNumber - 1].isChangedPosition()) return respond(GameMenuResponsesEnum.ALREADY_CHANGED);
        tempCells[cellNumber - 1].setState(tempState);
        tempCells[cellNumber - 1].setChangedPosition(true);
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse setSpellAndTrap(Game game, int cardNumberInHand) {
        ArrayList<Card> handCards = game.getPlayerHandCards();
        if (cardNumberInHand < handCards.size()) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        if (handCards.get(cardNumberInHand - 1).isMonster())
            return respond(GameMenuResponsesEnum.PLEASE_SELECT_SPELL_OR_TRAP);
        if (game.isSpellZoneFull()) return respond(GameMenuResponsesEnum.SPELL_AND_TRAP_ZONE_IS_FULL);
        Card tempCard = handCards.get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(tempCard);
            cell.setState(State.FACE_DOWN_SPELL);
            break;
        }
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse flipSummon(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        Cell tempCell = game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
        if (!tempCell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        if (tempCell.getRoundCounter() == 0 || tempCell.getState() != State.FACE_DOWN_DEFENCE)
            return respond(GameMenuResponsesEnum.CANT_FLIP_SUMMON);
        tempCell.setState(State.FACE_UP_ATTACK);
        if (cardHasFlipEffect(tempCell.getCard().getFeatures()))
            activeEffect(game, tempCell.getCard(), game.getRival(), 1);
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static void rivalFlipSummon(Game game, int cellNumber) {
        Cell tempCell = game.getRivalBoard().getMonsterZone(cellNumber - 1);
        tempCell.setState(State.FACE_UP_DEFENCE);
        if (cardHasFlipEffect(tempCell.getCard().getFeatures()))
            activeEffect(game, tempCell.getCard(), game.getRival(), 1);
    }

    private static boolean cardHasFlipEffect(ArrayList<CardFeatures> cardFeatures) {
        for (CardFeatures cardFeature : cardFeatures) if (cardFeature == CardFeatures.FLIP_EFFECT) return true;
        return false;
    }

    private static boolean monsterHasFlipEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.FLIP_EFFECT) return true;
        return false;
    }

    // todo tribute level check
    public GameMenuResponse ritualSummon(Game game, int[] tributeCellNumbers, int cardNumberInHand) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cardNumberInHand > cards.size()) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        if (!cards.get(cardNumberInHand - 1).isMonster()) return respond(GameMenuResponsesEnum.PLEASE_SELECT_MONSTER);
        if (((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getMonsterCardType() != MonsterCardType.RITUAL)
            return respond(GameMenuResponsesEnum.SELECTED_MONSTER_IS_NOT_RITUAL);
        if (game.getPlayerBoard().getSumLevel(new int[]{1, 2, 3, 4, 5}) < ((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getLevel())
            return respond(GameMenuResponsesEnum.CANT_RITUAL_SUMMON);
        if (game.getPlayerBoard().getSumLevel(tributeCellNumbers) != ((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getLevel())
            return respond(GameMenuResponsesEnum.SELECTED_LEVELS_DONT_MATCH);
        tribute(game, tributeCellNumbers);
        Cell[] cells = game.getPlayerBoard().getSpellZone();
        for (int i = 1; i <= 5; i++) {
            if (cells[i - 1].isOccupied()) {
                if (cells[i - 1].getCard().getCardName().equals("Advanced Ritual Art")) {
                    moveToPlayerGraveyard(game, i, false);
                    break;
                }
            }
        }
        cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells) {
            if (!cell.isOccupied()) {
                cell.addCard(game.getPlayerHandCards().get(cardNumberInHand - 1));
                break;
            }
        }
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse directAttack(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied())
            return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        Cell[] tempCells = game.getRivalBoard().getMonsterZone();
        boolean hasMonster = false;
        for (Cell cell : tempCells)
            if (cell.isOccupied()) {
                hasMonster = true;
                break;
            }
        if (hasMonster) return respond(GameMenuResponsesEnum.CANT_ATTACK);
        decreaseRivalLP(game, ((Monster) game.getPlayerBoard().getMonsterZone()[cellNumber - 1].getCard()).getAttack());
        tempCells[cellNumber - 1].setCanAttack(false);
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse specialSummon(Game game, Card card) {
        if (!canBeSpecialSummoned(card.getFeatures())) return respond(GameMenuResponsesEnum.CANT_SPECIAL_SUMMON);
        if (game.isMonsterZoneFull()) return respond(GameMenuResponsesEnum.MONSTER_ZONE_IS_FULL);
        return respond(GameMenuResponsesEnum.SUCCESSFUL);
    }

    private static boolean canBeSpecialSummoned(ArrayList<CardFeatures> cardFeatures) {
        for (CardFeatures cardFeature : cardFeatures)
            if (cardFeature == CardFeatures.SPECIAL_SUMMON
                    || cardFeature == CardFeatures.HAS_SPECIAL_NORMAL_SUMMON) return true;
        return false;
    }

    private static String trimName(String name) {
        return name.replace(" ", "").replace(",", "").replace("-", "");
    }

    public static GameMenuResponse showPlayerGraveYard(Game game) {
        return respondWithObj(game.getPlayerBoard().getGraveyard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse showRivalGraveYard(Game game) {
        return respondWithObj(game.getRivalBoard().getGraveyard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse showCardFromPlayerMonsterZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showPlayerCard(game.getPlayerBoard().getMonsterZone(cellNumber - 1));
    }

    public static GameMenuResponse showCardFromPlayerSpellZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showPlayerCard(game.getPlayerBoard().getSpellZone()[cellNumber - 1]);
    }

    // for easier application of showCardFromPlayerMonsterZone & showCardFromPlayerSpellZone
    private static GameMenuResponse showPlayerCard(Cell cell) {
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse showCardFromRivalMonsterZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showRivalCard(game.getRivalBoard().getMonsterZone(cellNumber - 1));
    }

    public static GameMenuResponse showCardFromRivalSpellZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showRivalCard(game.getRivalBoard().getSpellZone()[cellNumber - 1]);
    }

    // for easier application of showCardFromRivalSpellZone and showCardFromRivalMonsterZone
    private static GameMenuResponse showRivalCard(Cell cell) {
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        State temp = cell.getState();
        if (temp == State.FACE_DOWN_DEFENCE || temp == State.FACE_DOWN_SPELL)
            return respond(GameMenuResponsesEnum.CARD_IS_HIDDEN);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse showCardFromPlayerFieldZone(Game game) {
        Cell cell = game.getPlayerBoard().getFieldZone();
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse showCardFromRivalFieldZone(Game game) {
        Cell cell = game.getRivalBoard().getFieldZone();
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static GameMenuResponse showCardFromHand(Game game, int cardNumberInHand) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cards.size() < cardNumberInHand) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return respondWithObj(game.getPlayerHandCards().get(cardNumberInHand - 1).toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public static void cashOut(int maxLP, boolean is3RoundGame, User winner, User loser) {
        if (is3RoundGame) {
            winner.setScore(winner.getScore() + 3000);
            winner.increaseBalance(3000 + 3 * maxLP);
            loser.increaseBalance(300);
        } else {
            winner.increaseBalance(1000 + maxLP);
            winner.setScore(winner.getScore() + 1000);
        }
    }

    private static GameMenuResponse respond(GameMenuResponsesEnum gameMenuResponsesEnum) {
        return new GameMenuResponse(gameMenuResponsesEnum);
    }

    private static GameMenuResponse respondWithObj(Object obj, GameMenuResponsesEnum gameMenuResponsesEnum) {
        return new GameMenuResponse(obj, gameMenuResponsesEnum);
    }

    public static void moveToPlayerGraveyard(Game game, int cellNumber, boolean fromMonsterZone) {
        Graveyard graveyard = game.getPlayerBoard().getGraveyard();
        Cell[] cells;
        Card tempCard;
        if (fromMonsterZone) {
            cells = game.getPlayerBoard().getMonsterZone();
        } else {
            cells = game.getPlayerBoard().getSpellZone();
        }
        graveyard.addCard(tempCard = cells[cellNumber - 1].removeCard());
        tempCard.destroy(game);
    }

    public static void moveToRivalGraveyard(Game game, int cellNumber, boolean fromMonsterZone) {
        Graveyard graveyard = game.getRivalBoard().getGraveyard();
        Cell[] cells;
        Card tempCard;
        if (fromMonsterZone) {
            cells = game.getRivalBoard().getMonsterZone();
        } else {
            cells = game.getRivalBoard().getSpellZone();
        }
        graveyard.addCard(tempCard = cells[cellNumber - 1].removeCard());
        tempCard.destroy(game);
    }

    public static void moveToGraveYardFromPlayerHand(Game game, Card card) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) == card) {
                moveToGraveYardFromPlayerHand(game, i + 1);
                return;
            }
        }
    }

    public static void moveToGraveYardFromPlayerHand(Game game, int cardNumberInHand) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cardNumberInHand > cards.size() || cardNumberInHand < 1) return;
        Card tempCard;
        game.getPlayerBoard().getGraveyard().addCard(tempCard = cards.remove(cardNumberInHand - 1));
        tempCard.destroy(game);
    }

    public static void activeEffect(Game game, Card card, User player, int speed) {

    }

    private User getOtherUser(Game game, User user) {
        if (game.getPlayer().getUsername().equals(user.getUsername())) return game.getRival();
        return game.getPlayer();
    }

    public static void sendToGraveYard(Game game, Card card) {
        Cell[] cells;
        Graveyard gy;
        Card tempCard;
        if (card.isMonster()) {
            cells = game.getPlayerBoard().getMonsterZone();
            gy = game.getPlayerBoard().getGraveyard();
            for (Cell cell : cells) {
                if (cell.getCard().equals(card)) {
                    gy.addCard(tempCard = cell.removeCard());
                    tempCard.destroy(game);
                    return;
                }
            }
            cells = game.getPlayerBoard().getSpellZone();
            for (Cell cell : cells) {
                if (cell.getCard().equals(card)) {
                    gy.addCard(tempCard = cell.removeCard());
                    tempCard.destroy(game);
                    return;
                }
            }
        } else {
            cells = game.getRivalBoard().getMonsterZone();
            gy = game.getRivalBoard().getGraveyard();
            for (Cell cell : cells) {
                if (cell.getCard().equals(card)) {
                    gy.addCard(tempCard = cell.removeCard());
                    tempCard.destroy(game);
                    return;
                }
            }
            cells = game.getRivalBoard().getSpellZone();
            for (Cell cell : cells) {
                if (cell.getCard().equals(card)) {
                    gy.addCard(tempCard = cell.removeCard());
                    tempCard.destroy(game);
                    return;
                }
            }
        }
    }
}
