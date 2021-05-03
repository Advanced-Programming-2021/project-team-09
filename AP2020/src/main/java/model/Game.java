package model;

import model.card.Card;
import model.deck.Deck;

import java.util.ArrayList;

public class Game {
    User player;
    User rival;
    Deck playerDeck;
    Deck rivalPlayer;
    ArrayList<Card> playersHandCards;
    ArrayList<Card> rivalsHandCards;
    int playerLP;
    int rivalLP;
    int phaseCounter;
    boolean canSummonCard;
    boolean canRivalActiveSpell;
    Board playerBoard;
    Board rivalBoard;
    public void playerDrawCard() {

    }

    public void rivalDrawCard() {

    }
    public void changeTurn() {

    }
    public int getNumberOfCardsInHand() {
        return 0;
    }
    public int getNumberOfCardsInHandRival() {
        return 0;
    }
    public boolean hasCapacityToDraw() {
        return true;
    }
   public boolean isMonsterZoneFull() {
        return true;
    }
   public boolean isSpellZoneFull() {
        return true;
    }
    public boolean isThereEnoughMonstersToTribute(int amount) {
        return true;
    }
    public void summonMonster(Card card) {

    }
    public void summonSpell(Card card) {

    }
    public boolean canSummon() {
        return false;
    }
    public void summonWithTribute(Card card) {

    }
    public void ritualSummon (Card card) {

    }

    public void changePosition (State state, int cellNumber) {

    }
    public void attack (int numberOfAttacker, int numberOfDefender) {

    }

    public void directAttack (int numberOfAttacker) {

    }
    public void increaseHealth (int amount) {

    }
    public void decreaseHealth (int amount) {

    }
    public void activeEffect(int cellNumber) {

    }
    public void activeEffectRival(int cellNumber) {

    }
    public void changePhase() {

    }
    public boolean canRitualSummon() {
        return true;
    }
    public Deck getPlayersGraveYard() {
        return null;
    }
    public Deck getRivalsGraveYard() {
        return null;
    }

}
