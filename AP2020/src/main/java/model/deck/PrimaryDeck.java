package model.deck;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import model.User;
import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public abstract class PrimaryDeck {
    protected ArrayList<Card> cards;
    @JsonIgnore
    protected int maxCapacity;
    @JsonIgnore
    protected int minCapacity;
    @JsonIgnore
    protected String deckName;

    public boolean hasCapacity(){
        if (cards == null) System.out.println("fik");
        return cards.size() < maxCapacity;
    }

    @JsonIgnore
    public int getCardCount(String cardName){
        int temp = 0;
        for (Card tempCard : cards) if (tempCard.getCardName().equals(cardName)) temp++;
        return temp;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card removeCard(String cardName) {
        for (int i = 0; i < cards.size() ; i++){
            if (cards.get(i).getCardName().equals(cardName)) {
                Card card = cards.get(i);
                cards.remove(i);
                return card;
            }
        }
        return null;
    }

    public static ArrayList<String> sortCardsByName(ArrayList<Card> cards){
        ArrayList<String> cardNames = new ArrayList<>();
        for (Card card : cards) cardNames.add(card.getCardName());
        Collections.sort(cardNames);
        return  cardNames;
    }

    protected static String sortCardsInDecks(ArrayList<Card> cards){
        StringBuilder temp = new StringBuilder();
        temp.append("Monsters:\n");
        HashMap<String, String> cardNameToDescription = new HashMap<>();
        for (Card card : cards) cardNameToDescription.put(card.getCardName(), card.getDescription());
        ArrayList<Card> tempArraylist = new ArrayList<>();
        for (Card card : cards) if (card.isMonster()) tempArraylist.add(card);
        ArrayList<String> sortedArraylist = sortCardsByName(tempArraylist);
        for (String string : sortedArraylist) temp.append(string + ":" + cardNameToDescription.get(string) + "\n");
        temp.append("Spell and Traps:\n");
        tempArraylist = new ArrayList<>();
        for (Card card : cards) if (!card.isMonster()) tempArraylist.add(card);
        sortedArraylist = sortCardsByName(tempArraylist);
        for (String string : sortedArraylist) temp.append(string + ":" + cardNameToDescription.get(string) + "\n");
        return temp.toString();
    }

    @Override
    public abstract String toString();

    @JsonIgnore
    public boolean isValid() {
        if (cards.size() <= maxCapacity && cards.size() >= minCapacity) return true;
        return false;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void shuffle() {
        ArrayList<Card> newCards = new ArrayList<>();
        Random random = new Random();
        while (cards.size() != 0) {
            int rand = random.nextInt(cards.size());
            newCards.add(cards.remove(rand));
        }
        cards.addAll(newCards);
    }

    @JsonIgnore
    public int getNumberOfAllCards() {
        return cards.size();
    }

    @Override
    public abstract PrimaryDeck clone();

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getDeckName() {
        return deckName;
    }
}
