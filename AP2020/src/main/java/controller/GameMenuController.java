package controller;

import model.Cell;
import model.Game;
import model.Limits;
import model.State;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.deck.MainDeck;
import view.responses.GameMenuResponses;

import java.awt.*;
import java.util.ArrayList;

public class GameMenuController {
    private Game game;

    public GameMenuController(Game game) {
        this.game = game;
    }

    public String showTable() {
        return game.showTable();
    }

    public Cell selectMonsterFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
    } // todo deselect cells

    public GameMenuResponses canSelectMonsterFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public GameMenuResponses canSelectMonsterFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getRivalBoard().getMonsterZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (game.getRivalBoard().getMonsterZone()[cellNumber - 1].isFaceDown()) return GameMenuResponses.CARD_IS_HIDDEN;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectMonsterFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getRivalBoard().getMonsterZone()[cellNumber - 1];
    }

    public GameMenuResponses canSelectSpellAndTrapFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public GameMenuResponses canSelectSpellAndTrapFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getRivalBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (game.getRivalBoard().getSpellZone()[cellNumber - 1].isFaceDown()) return GameMenuResponses.CARD_IS_HIDDEN;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectSpellAndTrapFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getPlayerBoard().getSpellZone()[cellNumber - 1];
    }

    public Cell selectSpellAndTrapFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getRivalBoard().getSpellZone()[cellNumber - 1];
    }

    public GameMenuResponses canSelectPlayerFieldZone() {
        if (!game.getPlayerBoard().getFieldZone().isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectPlayerFieldZone() {
        return game.getPlayerBoard().getFieldZone();
    }

    public GameMenuResponses canSelectRivalFieldZone() {
        if (!game.getRivalBoard().getFieldZone().isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectRivalFieldZone() {
        return game.getRivalBoard().getFieldZone();
    }

    public Boolean canDraw() {
        return game.playerHasCapacityToDraw() && game.getPlayerDeck().getMainDeck().getNumberOfAllCards() > 0;
    }

    // returns the name of card which was added to hand
    public String draw() {
        // todo use draw from game
        MainDeck tempDeck = game.getPlayerDeck().getMainDeck();
        ArrayList<Card> tempHand = game.getPlayerHandCards();
        Card tempCard = tempDeck.getCards().get(0);
        tempDeck.removeCard(tempCard.getCardName());
        tempHand.add(tempCard);
        return tempCard.getCardName();
    }

    public GameMenuResponses canSelectCardFromHand(int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size()) return GameMenuResponses.INVALID_SELECTION;
        if (cardNumber < 1) return GameMenuResponses.INVALID_SELECTION;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Card selectCardFromHand(int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size() || cardNumber < 1) return null;
        return tempCards.get(cardNumber - 1);
    }


    public GameMenuResponses summonStatus(int cardNumberInHand) {
        ArrayList<Card> cardsInHand = game.getPlayerHandCards();
        if (cardNumberInHand > cardsInHand.size() || cardNumberInHand < 1) return GameMenuResponses.INVALID_SELECTION;
        Card card = cardsInHand.get(cardNumberInHand - 1);
        if (!game.canSummon()) return GameMenuResponses.ALREADY_SUMMONED;
        if (!canNormalSummon(card.getFeatures())) return GameMenuResponses.THIS_CARD_CANT_NORMAL_SUMMON;
        if (!card.isMonster())
            return game.isSpellZoneFull() ? GameMenuResponses.SPELL_ZONE_IS_FULL : GameMenuResponses.SUCCESSFUL;
        Monster tempMonster = (Monster) card;
        if (tempMonster.getLevel() > 4) return canTributeSummon(tempMonster);
        return game.isMonsterZoneFull() ? GameMenuResponses.MONSTER_ZONE_IS_FULL : GameMenuResponses.SUCCESSFUL;
        // todo more conditions
    }

    private boolean canNormalSummon(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.NORMAL_SUMMON) return true;
        return false;
    }

    private GameMenuResponses canTributeSummon(Monster monster) {
        Cell[] monsterCells = game.getPlayerBoard().getMonsterZone();
        int numMonsters = 0;
        for (Cell cell : monsterCells) if (cell.isOccupied()) numMonsters++;
        if (monster.getLevel() > 6)
            return numMonsters >= 2 ? GameMenuResponses.SUCCESSFUL : GameMenuResponses.NOT_ENOUGH_MONSTERS;
        else
            return numMonsters >= 1 ? GameMenuResponses.SUCCESSFUL : GameMenuResponses.NOT_ENOUGH_MONSTERS;
    }



    public GameMenuResponses attackStatus(int cellNumberAttacker, int cellNumberDefender) {
        if (cellNumberAttacker > 5 || cellNumberAttacker < 1 || cellNumberDefender > 5 || cellNumberDefender < 1)
            return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        if (!game.getRivalBoard().getMonsterZone()[cellNumberAttacker - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].canAttack()) return GameMenuResponses.ALREADY_ATTACKED;
        Monster attackerMonster = (Monster)game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].getCard(),
        defenderMonster = (Monster)game.getRivalBoard().getMonsterZone()[cellNumberDefender - 1].getCard();
        if (!game.getPlayerLimits().canAttackByThisLimitations(attackerMonster)) return GameMenuResponses.CANT_ATTACK;
        if (game.getRivalLimits().canAttackCell(cellNumberDefender)) return GameMenuResponses.CANT_ATTACK;
        return GameMenuResponses.SUCCESSFUL;
    }

    public GameMenuResponses selectedCardsCanBeTributed(int[] cellNumbers) {
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells) if (!cell.isOccupied()) return GameMenuResponses.NO_MONSTER_ON_THIS_ADDRESS;
        return GameMenuResponses.SUCCESSFUL;

    }

    public void tribute(int[] cellNumbers) {
        for (int i : cellNumbers) game.getGraveyard().addCard(game.getPlayerBoard().getMonsterZone(i - 1).removeCard());
    }

    public void summonMonster(int numberOFCardInHand) {
        Card card = game.getPlayerHandCards().get(numberOFCardInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_UP_ATTACK);
            break;
        }
        if (monsterHasSummonEffect(card.getFeatures())) ;// todo card.effect();
    }

    public void summonSpell(int cardNumberInHand) {
        Card card = game.getPlayerHandCards().get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_UP_SPELL);
            break;
        }
        // todo card.effect();
    }

    private boolean monsterHasSummonEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures cardFeatures : features) if (cardFeatures == CardFeatures.SUMMON_EFFECT) return true;
        return false;
    }

    public GameMenuResponses canSetMonster() {
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        int occupied = 0;
        for (Cell cell : cells) if (cell.isOccupied()) occupied ++;
        if (occupied == 5) return GameMenuResponses.MONSTER_ZONE_IS_FULL;
        if (!game.canSummon()) return GameMenuResponses.ALREADY_SUMMONED;
        return GameMenuResponses.SUCCESSFUL;
    }

    public void setMonsterCard(int numberOFCardInHand) {
        Card card = game.getPlayerHandCards().get(numberOFCardInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_DEFENCE);
            break;
        }
    }

    public GameMenuResponses canSetPositionMonster(int cellNumber, String attackOrDefence) {
        if (cellNumber > 5 || cellNumber < 1) return GameMenuResponses.INVALID_SELECTION;
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        if (!tempCells[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        Card tempCard = tempCells[cellNumber - 1].getCard();
        if (tempCells[cellNumber - 1].getState() == State.FACE_DOWN_DEFENCE) return GameMenuResponses.YOU_HAVENT_SUMMONED_YET;
        State tempState = attackOrDefence.equals("attack") ? State.FACE_UP_ATTACK : State.FACE_UP_DEFENCE;
        if (tempCells[cellNumber - 1].getState() == tempState) return GameMenuResponses.ALREADY_IN_THIS_POSITION;
        return GameMenuResponses.SUCCESSFUL;
    }

    public void setPositionMonster(int cellNumber, State state) {
        game.getPlayerBoard().getMonsterZone(cellNumber - 1).setState(state);
    }


    public GameMenuResponses canSetSpellCard() {
        // todo
    }

    public void setSpellCard(int cardNumberInHand) {
        Card card = game.getPlayerHandCards().get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_SPELL);
            break;
        }
    }

    public GameMenuResponses canFlipSummon(int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return GameMenuResponses.INVALID_SELECTION;
        Cell tempCell = game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
        if (!tempCell.isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (tempCell.getRoundCounter() == 0 || tempCell.getState() != State.FACE_DOWN_DEFENCE) return GameMenuResponses.CANT_FLIP_SUMMON;
        return GameMenuResponses.SUCCESSFUL;

    }

    public void flipSummon(int cellNumber) {
        Cell tempCell = game.getPlayerBoard().getMonsterZone(cellNumber - 1);
        tempCell.setState(State.FACE_UP_ATTACK);
        if (monsterHasSummonEffect(tempCell.getCard().getFeatures())) ;// todo tempCell.getCard().effect();
    }

    private boolean monsterHasFlipEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.FLIP_EFFECT) return true;
        return false;
    }

    public void ritualSummon(int[] tributeCellNumber, int numberOfCardInHand) {

    }

    public void directAttack(Card attackerCard, Card defenderCard) {

    }

    public void setTrapCard(Card card) {

    }

    public void specialSummon(Card card) {

    }

    public void showGraveYard() {

    }

    public void showCard(Card card) {

    }

    public void cashOut() {

    }
}
