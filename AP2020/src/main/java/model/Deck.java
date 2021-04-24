package model;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private MainDeck mainDeck;
    private SideDeck sideDeck;
    private String deckName;


    public Deck(String deckName) {
        this.deckName = deckName;
        mainDeck = new MainDeck(deckName);
        sideDeck = new SideDeck(deckName);
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.addCard(card);
    }

    public void addCardToSideDeck(Card card) {
        sideDeck.addCard(card);
    }

    public int getNumberOfCardsByName(String cardName) {
        return mainDeck.getCardCount(cardName) + sideDeck.getCardCount(cardName);
    }

    public boolean canAddCardByName(String cardName) { // todo in male controllere bayad pak she
        return getNumberOfCardsByName(cardName) < 3;
    }

    public boolean doesMainDeckHasCard(String cardName) {
        return mainDeck.getCardCount(cardName) > 0;
    }

    public boolean doesSideDeckHasCard(String cardName) {
        return sideDeck.getCardCount(cardName) > 0;
    }


    public String allCardsToString() { // todo bayad bere too deck
        ArrayList<Card> cards = getAllCards();
        StringBuilder temp = new StringBuilder();
        HashMap<String, String> cardNameToDescription = new HashMap<>();
        for (Card card : cards) cardNameToDescription.put(card.getCardName(), card.getDescription());
        ArrayList<String> sortedCardNames = PrimaryDeck.sortCardsByName(cards);
        for (String tempString : sortedCardNames)
            temp.append(tempString + ":" + cardNameToDescription.get(tempString) + "\n");
        return temp.toString();
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> temp ;
        ArrayList<Card> output = new ArrayList<>();
        temp = mainDeck.getCards();
        for (Card card : temp) output.add(card);
        temp = sideDeck.getCards();
        for (Card card : temp) output.add(card);
        return output;
    }

    public String getDeckName() {
        return deckName;
    }

    public String showMainDeck() {
        return mainDeck.toString();
    }

    public String showSideDeck() {
        return sideDeck.toString();
    }
}
