package controller;

import model.Cell;
import model.Game;
import model.State;
import model.card.Card;
import model.card.monster.Monster;
import model.deck.MainDeck;
import view.responses.GameMenuResponses;

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
        MainDeck tempDeck = game.getPlayerDeck().getMainDeck();
        ArrayList<Card> tempHand = game.getPlayerHandCards();
        Card tempCard = tempDeck.getCards().get(0);
        tempDeck.removeCard(tempCard.getCardName());
        tempHand.add(tempCard);
        return tempCard.getCardName();
    } // todo phase haro enum konim

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

    public GameMenuResponses canSummon(Card card) {
        if (!card.isMonster())
            return game.isSpellZoneFull() ? GameMenuResponses.SPELL_ZONE_IS_FULL : GameMenuResponses.SUCCESSFUL;
        Monster tempMonster = (Monster) card;
        if (tempMonster.getLevel() > 4) return canTributeSummon(tempMonster);
        return game.isMonsterZoneFull() ? GameMenuResponses.MONSTER_ZONE_IS_FULL : GameMenuResponses.SUCCESSFUL;
        // todo more conditions
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

    public void tribute(int[] cellNumbers) {

    }

    public void summon(Card card) {

    }

    public void setMonsterCard(Card card) {

    }

    public void setPosition(Cell cell, State state) {

    }

    public void setSpellCard(Card card) {

    }

    public void flipSummon(Cell cell) {

    }

    public void ritualSummon(Card card) {

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
