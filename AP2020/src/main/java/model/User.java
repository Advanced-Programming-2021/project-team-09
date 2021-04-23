package model;

import com.squareup.moshi.JsonWriter;

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
        activeDeck = null;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void activeDeck(String deckName) {
        if (getDeckByName(deckName) != null)
            activeDeck = getDeckByName(deckName);
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) return deck;
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

    public void removeDeck(String deckName) {
        Deck deck = getDeckByName(deckName);
        if (deck != null) {
            //ToDo decks be completed
            decks.remove(deck);
        }
    }

    public boolean hasEnoughBalance(int amount) {
        return balance >= amount;
    }

    public void addCardToDeck(Card card, String deckName) {
        Deck deck = getDeckByName(deckName);
    }

    public void removeCardFromDeck(Card card, String deckName) {

    }

    public boolean doesDeckExist(String deckName) {
        return getDeckByName(deckName) != null;
    }

    public ArrayList<Deck> sortDeck() {
        ArrayList<Deck> decks = (ArrayList<Deck>) this.decks.clone();
        decks.remove(activeDeck);
        Collections.sort(decks, new sortDeckBYName());
        return decks;
    }



    class sortDeckBYName implements Comparator<Deck> {
        @Override
        public int compare(Deck deck1, Deck deck2) {
            return deck1.getName().compareTo(deck2.getName());
        }
    }



}
