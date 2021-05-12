
package model.game;

import controller.DeckMenuController;
import model.User;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.deck.Deck;
import model.deck.Graveyard;
import model.deck.MainDeck;
import model.exceptions.WinnerException;

import java.util.ArrayList;

public class Game {
    private User player;
    private User rival;
    private User winner;
    private Deck playerDeck;
    private Deck rivalDeck;
    private ArrayList<Card> playerHandCards;
    private ArrayList<Card> rivalHandCards;
    private int playerLP;
    private int rivalLP;
    private int phaseCounter;
    private int roundCounter;
    private boolean canSummonCard;
    private Board playerBoard;
    private Board rivalBoard;
    private boolean canRivalActiveSpell;
    private Limits playerLimits;
    private Limits rivalLimits;

    public Game(User player, User rival) throws CloneNotSupportedException {
        rivalLP = 8000;
        playerLP = 8000;
        winner = null;
        this.player = player;
        this.rival = rival;
        playerDeck = (Deck) player.getActiveDeck().clone();
        playerDeck.getMainDeck().shuffle();
        rivalDeck = (Deck) rival.getActiveDeck().clone();
        rivalDeck.getMainDeck().shuffle();
        playerHandCards = new ArrayList<>();
        rivalHandCards = new ArrayList<>();
        playerLimits = new Limits();
        rivalLimits = new Limits();
        playerBoard = new Board();
        rivalBoard = new Board();
    }

    public void changeTurn() {
        canSummonCard = true;
        phaseCounter = 0;
        roundCounter++;
        switchReferences();
        updateCellData(playerBoard);
        updateCellData(rivalBoard);
        deleteUsedEnumsFromGame();
    }

    private void updateCellData(Board board) {
        Cell[] temp = board.getMonsterZone();

        for (Cell cell : temp) {
            cell.setChangedPosition(false);
            if (cell.isOccupied()) cell.increaseRoundCounter();
            cell.setCanAttack(false);
        }

        temp = board.getSpellZone();
        for (Cell cell : temp) {
            if (cell.isOccupied()) cell.increaseRoundCounter();
        }
    }

    private void deleteUsedEnumsFromGame() {
        deleteUsedEnumsForBoard(getPlayerBoard());
        deleteUsedEnumsForBoard(getRivalBoard());
    }

    private void deleteUsedEnumsForBoard(Board board) {
        for (Cell cell : board.getMonsterZone()) {
            deleteUsedEnums(cell.getCard());
        }
        for (Cell cell : board.getSpellZone()) {
            deleteUsedEnums(cell.getCard());
        }
    }

    private void deleteUsedEnums(Card card) {
        if (!card.getFeatures().contains(CardFeatures.ONE_USE_ONLY)) {
            while (card.getFeatures().contains(CardFeatures.USED_EFFECT)) {
                card.getFeatures().remove(CardFeatures.USED_EFFECT);
            }
        }
    }

    private void switchReferences() {
        User tempUser;
        tempUser = player;
        player = rival;
        rival = tempUser;
        Board tempBoard;
        tempBoard = playerBoard;
        playerBoard = rivalBoard;
        rivalBoard = tempBoard;
        ArrayList<Card> tempCard;
        tempCard = playerHandCards;
        playerHandCards = rivalHandCards;
        rivalHandCards = tempCard;
        Deck tempDeck;
        tempDeck = playerDeck;
        playerDeck = rivalDeck;
        rivalDeck = tempDeck;
        Limits tempLimits;
        tempLimits = playerLimits;
        playerLimits = rivalLimits;
        rivalLimits = tempLimits;
    }
    //todo board bayad User begire be nazaram!

    public void playerDrawCard() {
        MainDeck tempDeck = playerDeck.getMainDeck();
        if (playerHasCapacityToDraw() && tempDeck.getNumberOfAllCards() != 0)
            playerHandCards.add(tempDeck.removeCard(tempDeck.getCards().get(0).getCardName()));
    }

    public void addCardToHand(Card card) {
        playerHandCards.add(card);
    }

    public void rivalDrawCard() {
        if (rivalHasCapacityToDraw()) {
            rivalHandCards.add(rivalDeck.getMainDeck().getCards().get(0));
            rivalDeck.getMainDeck().getCards().remove(0);
        }

    }

    public boolean playerHasCapacityToDraw() {
        return getNumberOfCardsInHand() < 5;
    }

    public boolean rivalHasCapacityToDraw() {
        return getNumberOfCardsInHandFromRival() < 5;
    }

    public int getNumberOfCardsInHand() {
        return playerHandCards.size();
    }

    public int getNumberOfCardsInHandFromRival() {
        return rivalHandCards.size();
    }

    public boolean isMonsterZoneFull() {
        return playerBoard.isMonsterZoneFull();
    }

    public boolean isSpellZoneFull() {
        return playerBoard.isSpellZoneFull();
    }

    public void changePosition(State state, int cellNumber) {
        playerBoard.getMonsterZone(cellNumber).setState(state);
    }

    public boolean isThereEnoughMonstersToTribute(int amount) {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (playerBoard.getMonsterZone(i).isOccupied()) counter++;
        }
        return amount <= counter;
    }

    public void increaseHealth(int amount) {
        playerLP += amount;
    }

    public void decreaseHealth(int amount) throws WinnerException {
        if (playerLP - amount <= 0) {
            playerLP = 0;
            setWinner(rival);
        } else playerLP -= amount;
    }

    public void increaseRivalHealth(int amount) {
        rivalLP += amount;
    }

    public void decreaseRivalHealth(int amount) throws WinnerException {
        if (rivalLP - amount <= 0) {
            rivalLP = 0;
            setWinner(player);
        } else rivalLP -= amount;
    }

    public String getGraveyardPlayer() {
        return playerBoard.getGraveyard().toString();
    }

    public String getGraveyardRival() {
        return rivalBoard.getGraveyard().toString();
    }

    public void directAttack(int cellNumber) throws WinnerException {
        Monster tempMonster = (Monster) playerBoard.getMonsterZone(cellNumber).getCard();
        decreaseRivalHealth(tempMonster.getAttack());
    }

    public void changePhase() {
        phaseCounter++;
    }

    public void removeCardFromPlayerHand(Card card) {
        for (int i = 0; i < 5; i++) {
            if (card.equals(playerHandCards.get(i)))
                playerHandCards.remove(i);
        }
    }


    public void summonMonster(Card card) {
        if (!isMonsterZoneFull()) {
            playerBoard.addCardToMonsterZone(card);
            canSummonCard = false;
        }
    }

    public void summonSpell(Card card) {
        if (!isSpellZoneFull()) {
            playerBoard.addCardToSpellZone(card);
            canSummonCard = false;
        }

    }

    public void setWinner(User user) throws WinnerException {
        if (this.winner == null)
            this.winner = user;
        User loser;
        int winnerLP, loserLP;
        if (winner == player) {
            loser = rival;
            winnerLP = playerLP;
            loserLP = rivalLP;
        } else {
            loser = player;
            loserLP = playerLP;
            winnerLP = rivalLP;
        }
        throw new WinnerException(winner, loser, winnerLP, loserLP);
    }

    public boolean hasWinner() {
        return winner != null;
    }

    public User getWinner() {
        if (hasWinner()) return winner;
        else return null;
    }

    //todo methods!
    public void activeEffect(int cellNumber) {

    }

    public void activeEffectRival(int cellNumber) {

    }

    public boolean canRivalActiveSpell() {
        return false;
    }

    public boolean canRitualSummon() {
        return false;
    }

    public void attack(int numberOfAttackersCell, int numberOfDefendersCell) {

    }

    public String showTable() {
        StringBuilder table = new StringBuilder();
        table.append(rival.getNickname()).append(":").append(rivalLP).append("\n");
        ArrayList<Card> temp = rivalHandCards;
        for (Card card : temp) table.append("    c");
        table.append("\n").append(rivalDeck.getMainDeck().getNumberOfAllCards()).append("\n");
        Cell[] tempCellArray = rivalBoard.getSpellZone();
        table.append(tempCellArray[3].isOccupied() ? (tempCellArray[4].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[1].isOccupied() ? (tempCellArray[2].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[0].isOccupied() ? (tempCellArray[0].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[2].isOccupied() ? (tempCellArray[1].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[4].isOccupied() ? (tempCellArray[3].isFaceUp() ? "    O\n    " : "    H\n    ") : "    E\n    ");
        tempCellArray = rivalBoard.getMonsterZone();
        table.append(monsterStateToString(tempCellArray[4]));
        table.append(monsterStateToString(tempCellArray[2]));
        table.append(monsterStateToString(tempCellArray[0]));
        table.append(monsterStateToString(tempCellArray[1]));
        table.append(monsterStateToString(tempCellArray[3]));
        table.append("\n");
        table.append(rivalBoard.getGraveyard().getNumberOfAllCards()).append("\\t\\t\\t\\t\\t\\t").append(rivalBoard.getFieldZone().isOccupied() ? "O\n" : "E\n");
        table.append("\n------------------------------------------\n\n");
        table.append(playerBoard.getFieldZone().isOccupied() ? "O" : "E" + "\\t\\t\\t\\t\\t\\t" + playerBoard.getGraveyard().getNumberOfAllCards() + "\n");
        tempCellArray = playerBoard.getMonsterZone();
        table.append("    ");
        table.append(monsterStateToString(tempCellArray[3]));
        table.append(monsterStateToString(tempCellArray[1]));
        table.append(monsterStateToString(tempCellArray[0]));
        table.append(monsterStateToString(tempCellArray[2]));
        table.append(monsterStateToString(tempCellArray[4]));
        table.append("\n");
        tempCellArray = playerBoard.getSpellZone();
        table.append(tempCellArray[3].isOccupied() ? (tempCellArray[3].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[1].isOccupied() ? (tempCellArray[1].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[0].isOccupied() ? (tempCellArray[0].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[2].isOccupied() ? (tempCellArray[2].isFaceUp() ? "    O" : "    H") : "    E");
        table.append(tempCellArray[4].isOccupied() ? (tempCellArray[4].isFaceUp() ? "    O\n    " : "    H\n    ") : "    E\n    ");
        table.append("\\t\\t\\t\\t\\t\\t").append(playerDeck.getMainDeck().getNumberOfAllCards()).append("\n");
        temp = playerHandCards;
        for (Card card : temp) table.append("    c");
        table.append("\n");
        table.append(player.getNickname()).append(":").append(playerLP);
        return table.toString();
    }

    private String monsterStateToString(Cell cell) {
        if (!cell.isOccupied()) return "E   ";
        State state = cell.getState();
        if (state == State.FACE_UP_ATTACK) return "OO  ";
        else if (state == State.FACE_UP_DEFENCE) return "DO  ";
        return "DH  ";
    }

    public Graveyard getGraveyard() {
        return null; //ToDo
    }


    public boolean canSummon() {
        return canSummonCard;
    }

    public void setCanSummonCard(boolean canSummonCard) {
        this.canSummonCard = canSummonCard;
    }

    public void summonWithTribute(Card card) {

    }

    public void ritualSummon(int handNumber, int spellZoneNumber) {
        if (canRitualSummon(handNumber, spellZoneNumber)) {
            summonMonster(playerHandCards.get(handNumber));
            playerBoard.removeCardFromSpellZone(playerBoard.getSpellZone(spellZoneNumber).getCard());
            canSummonCard = false;
        }
    }

    public boolean canRitualSummon(int handNumber, int spellZoneNumber) {
        return playerHandCards.get(handNumber) != null && playerBoard.getSpellZone(spellZoneNumber).isOccupied();
    }


    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getRivalBoard() {
        return rivalBoard;
    }

    public User getPlayer() {
        return player;
    }

    public User getRival() {
        return rival;
    }

    public int getRivalLP() {
        return rivalLP;
    }

    public int getPlayerLP() {
        return playerLP;
    }

    public ArrayList<Card> getPlayerHandCards() {
        return playerHandCards;
    }

    public ArrayList<Card> getRivalHandCards() {
        return rivalHandCards;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public Deck getRivalDeck() {
        return rivalDeck;

    }

    public Limits getPlayerLimits() {
        return playerLimits;
    }

    public Limits getRivalLimits() {
        return rivalLimits;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

}
