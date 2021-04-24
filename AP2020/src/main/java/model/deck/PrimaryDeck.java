package model.deck;

import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public abstract class PrimaryDeck {
    protected ArrayList<Card> cards;
    protected int maxCapacity;
    protected int minCapacity;
    protected String deckName;

    public boolean hasCapacity(){
        return cards.size() < maxCapacity;
    }

    public int getCardCount(String cardName){
        int temp = 0;
        for (Card tempCard : cards) if (tempCard.getCardName().equals(cardName)) temp++;
        return temp;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card removeCard(String cardName){
        for (int i = cards.size() - 1; i >= 0 ; i--){
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

    public abstract String toString();

    public boolean isValid() {
        if (cards.size() <= maxCapacity && cards.size() >= minCapacity) return true;
        return false;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public static void shuffle(ArrayList<Card> cards) {
        int elementsCount = cards.size();
        if (elementsCount <= 1) return;
        int index1 = 0, index2 = 0;
        Random rand = new Random();
        for (int i = 0; i < elementsCount*elementsCount; i ++){
            while(index1 == index2){
                index1 = rand.nextInt(elementsCount);
                index2 = rand.nextInt(elementsCount);
            }
            swap(index1, index2, cards);
        }
    }

    public static void swap(int index1, int index2, ArrayList<Card> cards){
        int min = Math.min(index1, index2);
        int max = Math.max(index1, index2);
        Card tempMin = cards.get(min);
        Card tempMax = cards.get(max);
        cards.remove(max);
        cards.remove(min);
        cards.add(min, tempMax);
        cards.add(max, tempMin);
    }



}
