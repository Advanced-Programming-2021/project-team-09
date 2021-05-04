
package model;

import model.card.Card;
import model.card.monster.Monster;
import model.deck.Deck;
import model.deck.Graveyard;

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
        winner = null;
        this.player = player;
        this.rival = rival;
        //playerDeck = (Deck)player.getActiveDeck().clone();
        //rivalDeck = (Deck)rival.getActiveDeck().clone();
        playerLimits = new Limits();
        rivalLimits = new Limits();
        playerBoard = new Board();
        rivalBoard = new Board();
    }

    public void changeTurn(){
        phaseCounter = 0;

        User tempUser;
        tempUser = player;
        player = rival;
        rival = tempUser;
        //increasing roundCounter in occupied cells
        for (int i = 0; i < 5; i++) {
            if (playerBoard.getMonsterZone(i).isOccupied())
                playerBoard.getMonsterZone(i).increaseRoundCounter();
            if (playerBoard.getSpellZone(i).isOccupied())
                playerBoard.getSpellZone(i).increaseRoundCounter();
        }

        for (int i = 0; i < 5; i++) {
            if (rivalBoard.getMonsterZone(i).isOccupied())
                rivalBoard.getMonsterZone(i).increaseRoundCounter();
            if (rivalBoard.getSpellZone(i).isOccupied())
                rivalBoard.getSpellZone(i).increaseRoundCounter();
        }

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
        roundCounter++;

    }
    //todo board bayad User begire be nazaram!

    public void playerDrawCard(){
        if (playerHasCapacityToDraw())
            playerHandCards.add(playerDeck.getMainDeck().getCards().get(0));
    }

    public void rivalDrawCard(){
        if (rivalHasCapacityToDraw())
            rivalHandCards.add(rivalDeck.getMainDeck().getCards().get(0));
    }

    public boolean playerHasCapacityToDraw(){
        return getNumberOfCardsInHand() < 5;
    }

    public boolean rivalHasCapacityToDraw(){
        return getNumberOfCardsInHandFromRival() < 5;
    }
    public int getNumberOfCardsInHand(){
        return playerHandCards.size();
    }
    public int getNumberOfCardsInHandFromRival(){
        return rivalHandCards.size();
    }
    public boolean isMonsterZoneFull(){
        return playerBoard.isMonsterZoneFull();
    }
    public boolean isSpellZoneFull(){
        return playerBoard.isSpellZoneFull();
    }
    public void changePosition(State state, int cellNumber){
        playerBoard.getMonsterZone(cellNumber).setState(state);
    }
    public boolean isThereEnoughMonstersToTribute(int amount){
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if(playerBoard.getMonsterZone(i).isOccupied()) counter++;
        }
        return amount <= counter;
    }
    public void increaseHealth(int amount){
        playerLP +=amount;
    }
    public void decreaseHealth(int amount){
        if(playerLP - amount <= 0) {
            playerLP = 0;
            setWinner(rival);
        }
        else playerLP -= amount;
    }
    public void increaseRivalHealth(int amount){
        rivalLP += amount;
    }
    public void decreaseRivalHealth(int amount){
        if (rivalLP - amount <= 0) {
            rivalLP = 0;
            setWinner(player);
        }
        else rivalLP -= amount;
    }
    public String getGraveyardPlayer(){
        return playerBoard.getGraveyard().toString();
    }
    public String getGraveyardRival(){
        return rivalBoard.getGraveyard().toString();
    }
    public void directAttack(int cellNumber){
        Monster tempMonster = (Monster) playerBoard.getMonsterZone(cellNumber).getCard();
        decreaseRivalHealth(tempMonster.getAttack());
    }
    public void changePhase(){
        phaseCounter++;
    }
    public void removeCardFromPlayerHand(Card card){
        for (int i = 0; i < 5; i++) {
            if(card.equals(playerHandCards.get(i)))
                playerHandCards.remove(i);
        }
    }
    public void summonMonster(Card card){
        if(!isMonsterZoneFull()){
            playerBoard.addCardToMonsterZone(card);
        }
    }
    public void summonSpell(Card card){
        if (!isSpellZoneFull()){
            playerBoard.addCardToSpellZone(card);
        }

    }
    public void setWinner(User user){
        this.winner =user;
    }
    public boolean hasWinner(){
        return winner!= null;
    }
    public User getWinner(){
        if(hasWinner()) return winner;
        else return null;
    }
    public String showTable(){
        StringBuilder table = new StringBuilder();
        table.append(rival.getNickname()).append(":").append(rivalLP).append("\n");
        ArrayList<Card> temp = rivalHandCards;
        for (Card card : temp ) table.append("    c");
        table.append("\n").append(rivalDeck.getMainDeck().getNumberOfAllCards()).append("\n");
        Cell[] tempCellArray = rivalBoard.getSpellZone();
        table.append(tempCellArray[3].isOccupied() ? (tempCellArray[4].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[1].isOccupied() ? (tempCellArray[2].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[0].isOccupied() ? (tempCellArray[0].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[2].isOccupied() ? (tempCellArray[1].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[4].isOccupied() ? (tempCellArray[3].isFaceUp() ? "    O\n    ": "    H\n    ") : "    E\n    ");
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
        table.append(tempCellArray[3].isOccupied() ? (tempCellArray[3].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[1].isOccupied() ? (tempCellArray[1].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[0].isOccupied() ? (tempCellArray[0].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[2].isOccupied() ? (tempCellArray[2].isFaceUp() ? "    O": "    H") : "    E");
        table.append(tempCellArray[4].isOccupied() ? (tempCellArray[4].isFaceUp() ? "    O\n    ": "    H\n    ") : "    E\n    ");
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

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getRivalBoard() {
        return rivalBoard;
    }

    public User getPlayer() {
        return player;
    }

    public User getRival () {
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

    public void ritualSummon(int handNumber, int spellZoneNumber) {
        if(canRitualSummon(handNumber, spellZoneNumber)){
        summonMonster(playerHandCards.get(handNumber));
        playerBoard.removeCardFromSpellZone(playerBoard.getSpellZone(spellZoneNumber).getCard());
        }
    }

    public boolean canRitualSummon(int handNumber, int spellZoneNumber){
        return playerHandCards.get(handNumber) != null && playerBoard.getSpellZone(spellZoneNumber).isOccupied();
    }
    public void attack(int numberOfAttackersCell, int numberOfDefendersCell){
        Monster playerMonster = (Monster) playerBoard.getMonsterZone(numberOfAttackersCell).getCard();
        Monster rivalMonster = (Monster) rivalBoard.getMonsterZone(numberOfDefendersCell).getCard();
        State rivalCardState = rivalBoard.getMonsterZone(numberOfDefendersCell).getState();
        int playerMonsterAttack = playerMonster.getAttack();
        int rivalMonsterAttack = rivalMonster.getAttack();
        int rivalMonsterDefence = rivalMonster.getDefense();
        if(rivalCardState.equals(State.FACE_UP_ATTACK)){
            if(playerMonsterAttack > rivalMonsterAttack){
                decreaseRivalHealth(playerMonsterAttack - rivalMonsterAttack);
                rivalBoard.removeCardFromMonsterZone(rivalMonster);
            }
            else if(playerMonsterAttack == rivalMonsterAttack){
                //nothing happens
            }
            else {
                decreaseHealth(rivalMonsterAttack - playerMonsterAttack);
                playerBoard.removeCardFromMonsterZone(playerMonster);
            }
        }
        else if(rivalCardState.equals(State.FACE_UP_DEFENCE)){
            if(playerMonsterAttack > rivalMonsterDefence){
                //destroy happens, we dont have any method for that yet!!
            }
            else if(playerMonsterAttack == rivalMonsterDefence){
                //nothing happens
            }
            else{
                decreaseHealth(rivalMonsterDefence - playerMonsterAttack);
            }
        }
        else if (rivalCardState.equals(State.FACE_DOWN_DEFENCE)){
            //same as DO just different RESPONSES!!
            if(playerMonsterAttack > rivalMonsterDefence){
                //destroy happens, we dont have any method for that yet!!
            }
            else if(playerMonsterAttack == rivalMonsterDefence){
                //nothing happens
            }
            else{
                decreaseHealth(rivalMonsterDefence - playerMonsterAttack);
            }
        }
    }
    //todo methods!

    public void activeEffect(int cellNumber){

    }
    public void activeEffectRival(int cellNumber){

    }
    public boolean canRivalActiveSpell(){
        return false;
    }

    public Graveyard getGraveyard() {
        return null;
    }
    public boolean canSummon(){
        return false;
    }

    public void summonWithTribute(Card card){

    }

    public Limits getPlayerLimits() {
        return playerLimits;
    }

    public Limits getRivalLimits() {
        return rivalLimits;
    }
}
