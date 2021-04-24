package model.deck;

import model.card.Card;

import java.util.ArrayList;

public class MainDeck extends PrimaryDeck {
    public MainDeck(String deckName){
        maxCapacity = 60;
        minCapacity = 40;
        cards = new ArrayList<Card>();
        this.deckName = deckName;
    }

    public String toString(){
        return "Deck: " + deckName + "\nMain Deck: \n" + PrimaryDeck.sortCardsInDecks(cards);
    }



}