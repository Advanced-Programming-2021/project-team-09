package model;

import model.card.Card;
import model.deck.Deck;

import javax.management.loading.PrivateClassLoader;
import java.util.ArrayList;

public class Game {

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
