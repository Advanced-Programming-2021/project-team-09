package model;

import model.card.Card;
import model.deck.Deck;

import javax.management.loading.PrivateClassLoader;
import java.util.ArrayList;

public class Game {
    private User player;
    private User rival;
    private Deck playerDeck;
    private Deck rivalDeck;
    private ArrayList<Card> playerHandCards;
    private ArrayList<Card> rivalHandCards;
    private int playerLP;
    private int rivalLP;
    private int phaseCounter;
    private boolean canSummonCard;
    private Board playerBoard;
    private Board rivalBoard;
    private boolean canRivalActiveSpell;


    public void playerDrawCard() {

    }

    public void rivalDrawCard() {

    }

    public void changeTurn() {

    }

    public String showTable() {
        return null;
    }

    public int getNumberOfCardsInHand() {
        return -1;
    }

    public int getNumberOfCardsInHandFromRival() {
        return -1;
    }

    public boolean hasCapacityToDraw() {
        return false;
    }

    public boolean isMonsterZoneFull() {
        return false;
    }

    public boolean isSpellZoneFull() {
        return false;
    }

    public boolean isThereEnoughMonstersToTribute(int amount) {
        return false;
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

    public void ritualSummon(Card card) {

    }

    public void changePosition(State state, int cellNumber) {

    }

    public void attack(int numberOfAttackers, int numberOfDefenders) {

    }

    public void directAttack(int numberOfAttackers) {

    }

    public void increaseHealth(int amount) {

    }

    public void decreaseHealth(int amount) {

    }

    public void increaseRivalHealth(int amount) {

    }

    public void decreaseRivalHealth(int amount) {

    }

    public void activeEffect(int cellNumber) {

    }

    public void activeEffectRival(int cellNumber) {

    }

    public boolean canRivalActiveSpell() {
        return false;
    }

    public void changePhase() {

    }

    public boolean canRitualSummon() {
        return false;
    }

    public String getGraveyard() {
        return null;
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
}
