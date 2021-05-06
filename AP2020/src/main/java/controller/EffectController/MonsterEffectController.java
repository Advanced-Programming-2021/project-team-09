package controller.EffectController;

import controller.GameMenuController;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.card.monster.MonsterType;
import model.deck.Graveyard;
import model.game.*;
import view.CardEffectsView;
import view.responses.CardEffectsResponses;
import view.responses.HowToSummon;

import java.util.ArrayList;

public class MonsterEffectController extends EffectController {

    public void CommandKnight(Game game, Card card) {
        Limits limits;
        if (doesCardBelongsToPlayer(game, card)) limits = game.getPlayerLimits();
        else limits = game.getRivalLimits();
        limits.increaseATKAddition(400);
        int cellNumber = getCellNumberOfMonster(game, card);
        limits.banAttackingToCell(cellNumber);
    }

    public void YomiShip() {

    }

    public void HornImp() {

    }

    public void SilverFang() {

    }

    public void Suijin() {

    }

    public void Fireyarou() {

    }

    public void Curtainofthedarkones() {

    }

    public void FeralImp() {

    }

    public void Darkmagician() {

    }

    public void Wattkid() {

    }

    public void Babydragon() {

    }

    public void Herooftheeast() {

    }

    public void Battlewarrior() {

    }

    public void Crawlingdragon() {

    }

    public void Flamemanipulator() {

    }

    public void BlueEyeswhitedragon() {

    }

    public void CrabTurtle() {

    }

    public void SkullGuardian() {

    }

    public void SlotMachine() {

    }

    public void Haniwa() {

    }

    public void ManEaterBug(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getRivalBoard();
        else board = game.getPlayerBoard();
        while (true) {
            int cellNumber = CardEffectsView.getCellNumber() - 1;
            if (!isCellNumberValid(cellNumber)) CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            else {
                Cell cell = board.getMonsterZone(cellNumber);
                if (cell.isOccupied()) {
                    board.removeCardFromMonsterZone(cell.getCard());
                    return;
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            }
        }
    }

    public void GateGuardian(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        while (true) {
            int[] cellNumbers = CardEffectsView.getCellNumbers(3);
            if (isCellNumberValid(cellNumbers[0]-1) &&
                    isCellNumberValid(cellNumbers[1]-1) &&
                    isCellNumberValid(cellNumbers[2]-1)) {
                Cell[] cells = new Cell[3];
                cells[0] = board.getMonsterZone(cellNumbers[0]-1);
                cells[1] = board.getMonsterZone(cellNumbers[1]-1);
                cells[2] = board.getMonsterZone(cellNumbers[2]-1);
                if (cells[0].isOccupied() && cells[1].isOccupied() && cells[2].isOccupied()) {
                    for (int i = 0; i < cells.length; ++i) {
                        board.removeCardFromMonsterZone(cells[i].getCard());
                        board.addCardToMonsterZone(card);
                    }
                    return;
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        }
    }

    public void Scanner(Game game, Card card) {
        Board board;

        if (!card.getCardName().equals("Scanner")) {
            card.destroy(game);
            for (CardFeatures feature : card.getFeatures()) {
                if (feature == CardFeatures.SCANNER || feature == CardFeatures.NORMAL_SUMMON) ;
                else card.getFeatures().remove(feature);
            }
        }

        if (doesCardBelongsToPlayer(game, card)) board = game.getRivalBoard();
        else board = game.getPlayerBoard();

        Graveyard graveyard = board.getGraveyard();
        Card card1;

        while (true) {
            card1 = CardEffectsView.getCardFromGraveyard(graveyard);
            if (card1 == null) return;
            else if (!(card1 instanceof Monster)) CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
            else break;
        }

        Monster monster = (Monster) card;
        Monster monster1 = (Monster) card1;
        DuplicateMonster(monster, monster1);
    }


    public void Marshmallon(Game game, Card card) {
        if (doesCardBelongsToPlayer(game, card)) game.decreaseRivalHealth(1000);
        else game.decreaseHealth(1000);
    }

    public void BeastKingBarbaros(Game game, Card card) {
        ArrayList<Card> hand;
        if (doesCardBelongsToPlayer(game, card)) hand = game.getPlayerHandCards();
        else hand = game.getRivalHandCards();

        while (true) {
            HowToSummon howToSummon = CardEffectsView.howToSpecialNormalSummon();
            if (howToSummon == HowToSummon.SPECIAL_NORMAL_TYPE1) {
                ((Monster) card).setAttack(1900);
                State state = CardEffectsView.getStateOfSummon();
                for (int i = 0; i < hand.size(); ++i) {
                    if (hand.get(i).equals(card)) {
                        if (state.equals(State.SUMMON)) {
                            GameMenuController.summonMonster(game, i);
                            return;
                        } else if (state.equals(State.SET)) {
                            GameMenuController.setMonsterCard(game, i);
                            return;
                        }
                    }
                }
            } else if (howToSummon == HowToSummon.SPECIAL_NORMAL_TYPE2) {
                main:
                while (true) {
                    int[] cellNumbers = CardEffectsView.getCellNumbers(3);
                    Cell[] cell = game.getPlayerBoard().getMonsterZone();

                    for (int i = 0; i < 3; i++) {
                        if (!isCellNumberValid(cellNumbers[i])) {
                            CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
                            continue main;
                        }
                    }
                    if (cellNumbers[0] == cellNumbers[1] || cellNumbers[1] == cellNumbers[2] || cellNumbers[0] == cellNumbers[2]) {
                        CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
                        continue;
                    }
                    for (int i = 0; i < 3; i++) {
                        if (!cell[cellNumbers[i]].isOccupied()) {
                            CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
                            continue main;
                        }
                    }

                    GameMenuController.tribute(game, cellNumbers);
                    game.summonMonster(card);
                    int cellNumber = getCellNumberOfMonster(game, card);
                    game.getPlayerBoard().getMonsterZone(cellNumber).setState(State.FACE_UP_ATTACK);
                }
            } else if (howToSummon == HowToSummon.BACK) {
                return;
            }
        }
    }

    public void Texchanger(Game game, Card card) {
        if (CardEffectsView.doYouWantTo("do you want to summon a normal cyberse card?")) {
            Board board;
            if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
            else board = game.getRivalBoard();
            if (board.isMonsterZoneFull()) CardEffectsView.respond(CardEffectsResponses.MONSTER_ZONE_IS_FULL);
            else {
                while (true) {
                    Card card1 = CardEffectsView.getCardFrom(board);
                    if (card1 == null) return;
                    if (card1.isMonster()) {
                        if (((Monster) card1).getMonsterType().equals(MonsterType.CYBERSE)) {
                            Monster monster = (Monster) card1;
                            if (monster.hasEffect() || monster.isMonsterRitual())
                                CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_MONSTER);
                            else {
                                board.addCardToMonsterZone(monster);
                                int cellNumber = getCellNumberOfMonster(game,monster);
                                board.getMonsterZone(cellNumber).setState(State.FACE_UP_ATTACK);
                            }
                        } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_TYPE);
                    } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
                }
            }

        } else return;
    }


    public void TheCalculator(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        board = game.getRivalBoard();
        int sumLevel = 0;
        for (int i = 0; i < 5; i++) {
            if (board.getMonsterZone(i).isOccupied() && board.getMonsterZone(i).isFaceUp()) {
                Monster monster = (Monster) board.getMonsterZone(i).getCard();
                sumLevel += monster.getLevel();
            }
        }
        Monster monster = (Monster) card;
        monster.setAttack(sumLevel * 300);
        card = monster;
    }


    public void MirageDragon(Game game, Card card) {
        Limits limits;
        Board board;
        if (doesCardBelongsToPlayer(game, card)) {
            limits = game.getRivalLimits();
            board = game.getPlayerBoard();
        } else {
            limits = game.getPlayerLimits();
            board = game.getRivalBoard();
        }
        if (board.getMonsterZoneCellByCard(card).isFaceUp()) {
            limits.addLimit(EffectLimitations.CANT_ACTIVATE_TRAP);
        }
    }

    public void HeraldofCreation(Game game, Card card) {
        mainLoop:
        while (true) {
            int numberOfCardInHand = CardEffectsView.getNumberOfCardInHand();
            if (game.getPlayerHandCards().get(numberOfCardInHand - 1) == null)
                CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
            else {
                Card removingCard = game.getPlayerHandCards().get(numberOfCardInHand - 1);
                while (true) {
                    Card givenCardFromGraveYard = CardEffectsView.getCardFromGraveyard(game.getGraveyard());
                    if (givenCardFromGraveYard.isMonster()) {
                        Monster monster = (Monster) givenCardFromGraveYard;
                        if (monster.getLevel() >= 7) {
                            game.removeCardFromPlayerHand(removingCard);
                            game.addCardToHand(monster);
                            break mainLoop;
                        } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_LEVEL_7_OR_MORE);
                    } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
                }
            }
        }
    }


    public void TerratigertheEmpoweredWarrior(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        board = game.getRivalBoard();
        //todo be halat adi ehzar shode yani chi?? bedon in mizanam felan!
        //todo nabayad moqe summon kardan state ro ham moshakhas konim??!
        while (true) {
            int numberOfCardInHand = CardEffectsView.getNumberOfCardInHand();
            Card chosenCard = game.getPlayerHandCards().get(numberOfCardInHand - 1);
            if (chosenCard.isMonster()) {
                Monster monster = (Monster) chosenCard;
                if (monster.getLevel() <= 4) {
                    game.summonMonster(monster);
                    board.getMonsterZoneCellByCard(monster).setState(State.FACE_DOWN_DEFENCE);
                    break;
                } else {
                    CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_LEVEL_4_OR_LESS);
                }
            } else {
                CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
            }
        }
    }

    // baraye ehzar vizhe hast in
    public void TheTricky(Game game, Card card) {
        for (int i = 0; i < 5; i++) {
            if (game.getPlayerHandCards().get(i).equals(card)) {
                CardEffectsView.respond(CardEffectsResponses.DO_YOU_WANT_TO_SPECIAL_SUMMON);
                boolean wantsToSpecialSummon = CardEffectsView.doSpecialSummon();
                while (wantsToSpecialSummon) {
                    int numberOfCardInHand = CardEffectsView.getNumberOfCardInHand();
                    if (game.getPlayerHandCards().get(numberOfCardInHand - 1) == null)
                        CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER);
                    else {
                        Card toBeRemovedCard = game.getPlayerHandCards().get(numberOfCardInHand - 1);
                        game.removeCardFromPlayerHand(toBeRemovedCard);
                        game.summonMonster(card);
                        break;
                    }
                }
            }
        }
    }


    public int getCellNumberOfMonster(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        Cell[] cells = board.getMonsterZone();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getCard().equals(card)) return i;
        }
        return 0;
    }


    private void DuplicateMonster(Monster monster, Monster originalMonster) {
        monster.setMonsterEffectType(originalMonster.getMonsterEffectType());
        monster.setMonsterType(originalMonster.getMonsterType());
        monster.setAttack(originalMonster.getAttack());
        monster.setDefense(originalMonster.getDefense());
        monster.setLevel(originalMonster.getLevel());
        monster.setMonsterCardType(originalMonster.getMonsterCardType());
        monster.setCardName(originalMonster.getCardName());
    }

}
