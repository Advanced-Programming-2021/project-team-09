
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

    public Game(User player, User rival){
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
        Cell[] temp = playerBoard.getMonsterZone();
        for (Cell cell : temp) {
            cell.setChangedPosition(false);
            cell.increaseRoundCounter();
            cell.setCanAttack(false);
        }
        temp = playerBoard.getSpellZone();
        for (Cell cell : temp) cell.increaseRoundCounter();
        temp = rivalBoard.getMonsterZone();
        for (Cell cell : temp) {
            cell.setChangedPosition(false);
            cell.increaseRoundCounter();
            cell.setCanAttack(false);
        }
        temp = rivalBoard.getSpellZone();
        for (Cell cell : temp) cell.increaseRoundCounter();
    }
    //todo board bayad User begire be nazaram!

    public void playerDrawCard(){
        if (playerHasCapacityToDraw())
            playerHandCards.add(playerBoard.drawCardFromMainDeck());
    }

    public void rivalDrawCard(){
        if (rivalHasCapacityToDraw())
            rivalHandCards.add(rivalBoard.drawCardFromMainDeck());
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
    //todo methods!
    public void activeEffect(int cellNumber){

    }
    public void activeEffectRival(int cellNumber){

    }
    public boolean canRivalActiveSpell(){
        return false;
    }
    public boolean canRitualSummon(){
        return false;
    }
    public void attack(int numberOfAttackersCell, int numberOfDefendersCell){

    }
    public String showTable(){
        return null;
    }

    public Graveyard getGraveyard() {
        return null;
    }

    public boolean canSummon(){
        return canSummonCard;
    }
    public void setCanSummonCard(boolean canSummonCard) {
        this.canSummonCard = canSummonCard;
    }
    public void summonWithTribute(Card card){

    }
    public void ritualSummon(Card card) {

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

    public Limits getPlayerLimits() {
        return playerLimits;
    }

    public Limits getRivalLimits() {
        return rivalLimits;
    }
}
