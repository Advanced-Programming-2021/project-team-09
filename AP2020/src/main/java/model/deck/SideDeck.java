package model.deck;

import model.card.Card;

import java.util.ArrayList;

public class SideDeck extends PrimaryDeck {
    public SideDeck(String deckName){
        maxCapacity = 15;
        minCapacity = 0;
        cards = new ArrayList<>();
        this.deckName = deckName;
    }
    public SideDeck() {
        maxCapacity = 15;
        minCapacity = 0;
    }

    public String toString(String deckName){
        return "Deck: " + deckName + "\nSide Deck: \n" + PrimaryDeck.sortCardsInDecks(cards);
    }


}
