package model;

import model.card.Card;
import model.deck.Deck;
import controller.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class User {
    private String username;
    private String nickname;
    private String password;
    private int balance;
    private int score;
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks;
    private Deck activeDeck;
    
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.score = 0;
        this.nickname = nickname;
        this.cards = new ArrayList<>();
        this.decks = new ArrayList<>();
        activeDeck  = null;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void activeDeck(String deckName) {
        if (getDeckByName(deckName) != null)
            activeDeck = getDeckByName(deckName);
        else activeDeck = null;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName)) return deck;
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void createDeck(String deckName) {
        decks.add(new Deck(deckName));
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public void changeNickname(String newNickname) {
        nickname = newNickname;
    }

    public void increaseBalance(int amount) {
        balance += amount;
    }

    public void decreaseBalance(int amount) {
        balance -= amount;
    }

    public int getScore() {
        return score;
    }

    public int getBalance() {
        return balance;
    }

    public boolean hasEnoughBalance(int amount) {
        return balance >= amount;
    }

    public void addCardToMainDeck(Card card, String deckName) {
        Deck deck = getDeckByName(deckName);
        if (deck.canAddCardByName(card.getCardName())) {
           deck.addCardToSideDeck(card);
        }
    }

    public void addCardToSideDeck(Card card, String deckName) {
        Deck deck = getDeckByName(deckName);
        if (deck.canAddCardByName(card.getCardName()))
            deck.addCardToSideDeck(card);
    }

    public void removeCardFromMainDeck(String cardName,String deckName){
        if (doesDeckExist(deckName)){
            Deck deck = getDeckByName(deckName);
            if (deck.doesMainDeckHasCard(cardName)) {
                Card card = deck.removeCardFromMainDeck(cardName);
                cards.add(card);
            }
        }
    }

    public void removeCardFromSideDeck(String cardName,String deckName){
        if (doesDeckExist(deckName)){
            Deck deck = getDeckByName(deckName);
            if (deck.doesSideDeckHasCard(cardName)) {
                Card card = deck.removeCardFromSideDeck(cardName);
                cards.add(card);
            }
        }
    }

    public void removeDeck(String deckName) {
        Deck deck = getDeckByName(deckName);
        if (deck != null) {
            ArrayList<Card> cardsOdThisDeck = deck.getAllCards();
            cards.addAll(cardsOdThisDeck);
            decks.remove(deck);
            if (deck == activeDeck) activeDeck = null;
        }
    }

    public boolean doesDeckExist(String deckName) {
        return getDeckByName(deckName) != null;
    }

    public ArrayList<Deck> getSortedDecks() {
        ArrayList<Deck> decks = (ArrayList<Deck>) this.decks.clone();
        decks.remove(activeDeck);
        Collections.sort(decks, new sortDeckByName());
        return decks;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    class sortDeckByName implements Comparator<Deck> {
        @Override
        public int compare(Deck deck1, Deck deck2) {
            String tempName1 = deck1.getDeckName(), tempName2 = deck2.getDeckName();
            return DeckMenuController.compareStrings(tempName1, tempName2);
        }
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }
}
