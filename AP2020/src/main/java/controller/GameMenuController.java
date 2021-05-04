package controller;

import model.*;
import model.card.Card;
import model.deck.Deck;
import view.responses.DeckMenuResponses;
import view.responses.GameMenuResponses;

import java.util.ArrayList;

public class GameMenuController {
    private Game game;

    public GameMenuController(Game game) {
        this.game = game;
    }

    public String showTable () {
        String string =   game.getRival().getNickname() + ":" + game.getRivalLP() + "\n";
        ArrayList<Card> temp = game.getRivalHandCards();
        for (Card card : temp ) string += "    c";
        string += "\n" + game.getRivalDeck().getMainDeck().getNumberOfAllCards() + "\n" ;
        Cell[] tempCellArray = game.getRivalBoard().getSpellZone();
        string += tempCellArray[3].isOccupied() ? (tempCellArray[4].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[1].isOccupied() ? (tempCellArray[2].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[0].isOccupied() ? (tempCellArray[0].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[2].isOccupied() ? (tempCellArray[1].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[4].isOccupied() ? (tempCellArray[3].isFaceUp() ? "    O\n    ": "    H\n    ") : "    E\n    ";
        tempCellArray = game.getRivalBoard().getMonsterZone();
        string += monsterStateToString(tempCellArray[4]);
        string += monsterStateToString(tempCellArray[2]);
        string += monsterStateToString(tempCellArray[0]);
        string += monsterStateToString(tempCellArray[1]);
        string += monsterStateToString(tempCellArray[3]);
        string += "\n";
        string += game.getRivalBoard().getGraveyard().getNumberOfAllCards() + "\\t\\t\\t\\t\\t\\t" + (game.getRivalBoard().getFieldZone().isOccupied() ? "O\n" : "E\n");
        string += "\n------------------------------------------\n\n";
        string += game.getPlayerBoard().getFieldZone().isOccupied() ? "O" : "E" + "\\t\\t\\t\\t\\t\\t" + game.getPlayerBoard().getGraveyard().getNumberOfAllCards() + "\n";
        tempCellArray = game.getPlayerBoard().getMonsterZone();
        string += "    ";
        string += monsterStateToString(tempCellArray[3]);
        string += monsterStateToString(tempCellArray[1]);
        string += monsterStateToString(tempCellArray[0]);
        string += monsterStateToString(tempCellArray[2]);
        string += monsterStateToString(tempCellArray[4]);
        string += "\n";
        tempCellArray = game.getPlayerBoard().getSpellZone();
        string += tempCellArray[3].isOccupied() ? (tempCellArray[3].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[1].isOccupied() ? (tempCellArray[1].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[0].isOccupied() ? (tempCellArray[0].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[2].isOccupied() ? (tempCellArray[2].isFaceUp() ? "    O": "    H") : "    E";
        string += tempCellArray[4].isOccupied() ? (tempCellArray[4].isFaceUp() ? "    O\n    ": "    H\n    ") : "    E\n    ";
        string += "\\t\\t\\t\\t\\t\\t" + game.getPlayerDeck().getMainDeck().getNumberOfAllCards() + "\n";
        temp = game.getPlayerHandCards();
        for (Card card : temp) string += "    c";
        string += "\n";
        string += game.getPlayer().getNickname() + ":" + game.getPlayerLP();
        return string;
    }

    private String monsterStateToString(Cell cell) {
        if (!cell.isOccupied()) return "E   ";
        State state = cell.getState();
        if (state == State.FACE_UP_ATTACK) return "OO  ";
        else if (state == State.FACE_UP_DEFENCE) return "DO  ";
        return "DH  ";
    }

    public Cell selectMonsterFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        game.getPlayerBoard().getMonsterZone()[cellNumber - 1].select();
        return game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
    } // todo deselect cells

    public void deselectAllCells() {
        Cell[] temp = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : temp) cell.deselect();
        temp = game.getPlayerBoard().getSpellZone();
        for (Cell cell : temp) cell.deselect();
        game.getPlayerBoard().getFieldZone().deselect();
        temp = game.getRivalBoard().getMonsterZone();
        for (Cell cell : temp) cell.deselect();
        temp = game.getRivalBoard().getSpellZone();
        for (Cell cell : temp) cell.deselect();
        game.getRivalBoard().getFieldZone().deselect();
    }

    public GameMenuResponses canSelectMonsterFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public GameMenuResponses canSelectMonsterFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getRivalBoard().getMonsterZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (game.getRivalBoard().getMonsterZone()[cellNumber - 1].getState() == State.FACE_DOWN_DEFENCE) return GameMenuResponses.CARD_IS_HIDDEN;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectMonsterFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        deselectAllCells();
        Cell temp = game.getRivalBoard().getMonsterZone()[cellNumber - 1];
        temp.select();
        return temp;
    }

    public GameMenuResponses canSelectSpellAndTrapFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public GameMenuResponses canSelectSpellAndTrapFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getRivalBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (game.getRivalBoard().getSpellZone()[cellNumber - 1].getState() == State.FACE_DOWN_DEFENCE) return GameMenuResponses.CARD_IS_HIDDEN;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectSpellAndTrapFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        deselectAllCells();
        Cell temp = game.getPlayerBoard().getSpellZone()[cellNumber - 1];
        temp.select();
        return temp;
    }

    public Cell selectSpellAndTrapFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        deselectAllCells();
        Cell temp = game.getRivalBoard().getSpellZone()[cellNumber - 1];
        temp.select();
        return temp;
    }

    public GameMenuResponses canSelectPlayerFieldZone() {
        if (!game.getPlayerBoard().getFieldZone().isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectPlayerFieldZone() {
        deselectAllCells();
        Cell temp = game.getPlayerBoard().getFieldZone();
        temp.select();
        return temp;
    }

    public GameMenuResponses canSelectRivalFieldZone() {
        if (!game.getRivalBoard().getFieldZone().isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    public Cell selectRivalFieldZone() {
        deselectAllCells();
        Cell temp = game.getRivalBoard().getFieldZone();
        temp.select();
        return temp;
    }

    public GameMenuResponses canDeselect() {
        Cell[] temp = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : temp) if (cell.isSelected()) return GameMenuResponses.SUCCESSFUL;
        temp = game.getPlayerBoard().getSpellZone();
        for (Cell cell : temp) if (cell.isSelected()) return GameMenuResponses.SUCCESSFUL;
        if (game.getPlayerBoard().getFieldZone().isSelected()) return GameMenuResponses.SUCCESSFUL;
        temp = game.getRivalBoard().getMonsterZone();
        for (Cell cell : temp) if (cell.isSelected()) return GameMenuResponses.SUCCESSFUL;
        temp = game.getRivalBoard().getSpellZone();
        for (Cell cell : temp) if (cell.isSelected()) return GameMenuResponses.SUCCESSFUL;
        if (game.getRivalBoard().getFieldZone().isSelected()) return GameMenuResponses.SUCCESSFUL;
        return GameMenuResponses.NO_CARD_IS_SELECTED;
    }

    public Boolean canDraw() {
        return game.playerHasCapacityToDraw() && game.getPlayerDeck().getMainDeck().getNumberOfAllCards() > 0;
    } // todo what if 6 ta bud va resid be draw phase ?

    private Cell getSelectedCell() {
        Cell[] temp = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : temp) if (cell.isSelected()) return cell;
        temp = game.getPlayerBoard().getSpellZone();
        for (Cell cell : temp) if (cell.isSelected()) return cell;
        Cell tempCell;
        if ((tempCell = game.getPlayerBoard().getFieldZone()).isSelected()) return tempCell;
        temp = game.getRivalBoard().getMonsterZone();
        for (Cell cell : temp) if (cell.isSelected()) return cell;
        temp = game.getRivalBoard().getSpellZone();
        for (Cell cell : temp) if (cell.isSelected()) return cell;
        if ((tempCell = game.getRivalBoard().getFieldZone()).isSelected()) return tempCell;
        return null;
    }

    // returns the name of card which was added to hand
    public String draw() {
        Deck tempDeck = game.getPlayerDeck();
        ArrayList<Card> tempHand = game.getPlayerHandCards();
        Card tempCard;
        tempHand.add(tempCard = tempHand.remove(0));
        return  tempCard.getCardName();
    } // todo phase haro enum konim

    public GameMenuResponses canSelectCardFromHand(int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size()) return GameMenuResponses.INVALID_SELECTION;
        return null;
    }

    public Card selectCardFromHand(int cardNumber) {
        return null;
    }

    public GameMenuResponses canSummon() {
        if (getSelectedCell() == null) return GameMenuResponses.NO_CARD_IS_SELECTED;
        return null;
    }

    public void summon (Card card) {

    }

    public void setMonsterCard (Card card) {

    }

    public void setPosition(Cell cell, State state) {

    }

    public void setSpellCard (Card card) {

    }

    public void flipSummon(Cell cell) {

    }

    public void ritualSummon(Card card) {

    }

    public void directAttack(Card attackerCard, Card defenderCard) {

    }

    public void setTrapCard(Card card) {

    }

    public void specialSummon (Card card) {

    }

    public void showGraveYard () {

    }

    public void showCard (Card card) {

    }

    public void cashOut() {

    }
}
