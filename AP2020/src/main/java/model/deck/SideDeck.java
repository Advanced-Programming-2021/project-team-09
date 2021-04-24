package model.deck;

import model.card.Card;

import java.util.ArrayList;

public class SideDeck extends PrimaryDeck {
    public SideDeck(String deckName){
        maxCapacity = 15;
        minCapacity = 0;
        cards = new ArrayList<Card>();
        this.deckName = deckName;
    }

    public String toString(){
        return "Deck: " + deckName + "\nSide Deck: \n" + PrimaryDeck.sortCardsInDecks(cards);
    }


}