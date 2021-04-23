package model;

import java.util.ArrayList;
import java.util.HashMap;

public class MainDeck extends PrimaryDeck{
    public MainDeck(String deckName){
        maxCapacity = 60;
        minCapacity = 40;
        cards = new ArrayList<Card>();
        this.deckName = deckName;
    }

    public String toString(){
        return "Deck: " + deckName + "\nMain Deck: \n" + PrimaryDeck.sortCardsInDecks(cards);
    }

    public boolean isValid() {
        if (cards.size() <= maxCapacity && cards.size() >= minCapacity) return true;
        return false;
    }

}
