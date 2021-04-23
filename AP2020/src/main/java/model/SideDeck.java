package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SideDeck extends PrimaryDeck{
    public SideDeck(String deckName){
        maxCapacity = 15;
        minCapacity = 0;
        cards = new ArrayList<Card>();
        this.deckName = deckName;
    }

    public String toString(){
        return "Deck: " + deckName + "\nSide Deck: \n" + PrimaryDeck.sortCardsInDecks(cards);
    }

    public boolean isValid() {
        if (cards.size() <= maxCapacity && cards.size() >= minCapacity) return true;
        return false;
    }
}
