package model;

import model.card.Card;
import model.card.monster.Monster;
import model.deck.Deck;

import java.util.ArrayList;

public class Game {
    private User player;
    private User rival;
    private Deck playerDeck;
    private Deck rivalDeck;
    private ArrayList<Card> playerHandCards;
    private ArrayList<Card> rivalHandCards;
    private int playerLifePoint;
    private int rivalLifePoint;
    private int phaseCounter;
    private boolean canSummonCard;
    private Board playerBoard;
    private Board rivalBoard;
    private boolean canRivalActiveSpell;

    public Game(User player, User rival){
        this.player = player;
        this.rival = rival;
        playerDeck = (Deck)player.getActiveDeck().clone();
        rivalDeck = (Deck)rival.getActiveDeck().clone();
        playerBoard = new Board();
        rivalBoard = new Board();
    }
    public void changeTurn(){
        //age ba temp moshkel dare mitonim ba wrapperClass swap konim;
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
        if(playerLifePoint + amount >= 8000) playerLifePoint = 8000;
        else playerLifePoint+=amount;
    }
    public void decreaseHealth(int amount){
        //todo nabayad winner moshakhas konim??
        if(playerLifePoint - amount <= 0) playerLifePoint = 0;
        else playerLifePoint -= amount;
    }
    public void increaseRivalHealth(int amount){
        if (rivalLifePoint + amount >= 8000) rivalLifePoint = 8000;
        else rivalLifePoint += amount;
    }
    public void decreaseRivalHealth(int amount){
        if (rivalLifePoint - amount <= 0) rivalLifePoint = 0;
        else rivalLifePoint -= amount;
    }
    public String getGraveyardPlayer(){
        return playerBoard.getGraveyard().toString();
    }
    public String getGraveyardRival(){
        return rivalBoard.getGraveyard().toString();
    }
    public void directAttack(int[] cellNumbers){
        for (int i = 0; i < cellNumbers.length; i++) {
            Monster tempMonster = (Monster) playerBoard.getMonsterZone(cellNumbers[i]).getCard();
            decreaseRivalHealth(tempMonster.getAttack());
        }
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
    public void attack(int numberOfAttackers, int numberOfDefenders){

    }
    public String showTable(){
        return null;
    }
    public void summonMonster(Card card){

    }
    public void summonSpell(Card card){

    }
    public boolean canSummon(){
        return false;
    }
    public void summonWithTribute(Card card){

    }
    public void ritualSummon(Card card){

    }
}
