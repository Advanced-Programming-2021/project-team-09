package model;

import model.card.Card;
import model.deck.Deck;
import model.deck.Graveyard;

public class Board {
    private Cell[] monsterZone = new Cell[5];
    private Cell[] spellZone = new Cell[5];
    private Cell fieldZone = new Cell();
    private Graveyard graveyard = new Graveyard();
    private Deck deck;
    public void addCardToMonsterZone(Card card){
        for (int i = 0; i < 5; i++) {
            if(!monsterZone[i].isOccupied()) monsterZone[i].addCard(card);
        }
    }
    public void addCardToSpellZone(Card card){
        for (int i = 0; i < 5; i++) {
            if(!spellZone[i].isOccupied()) spellZone[i].addCard(card);
        }
    }
    public void sendToGraveYard(Card card){
        //TODO
    }
    public Card getCardFromDeck(){
        //TODO
        return null;
    }
    public Card removeCardFromMonsterZone(Card card){
        for (int i = 0; i < 5; i++) {
            if (monsterZone[i].getCard() != card) {
                continue;
            }
            return monsterZone[i].removeCard();
        }
        return null;
    }
    public Card removeCardFromSpellZone(Card card){
        for (int i = 0; i < 5; i++) {
            if (spellZone[i].getCard().equals(card)) {
                return spellZone[i].removeCard();
            }
        }
        return null;
    }
    public Card removeCardFromGraveYard(Card card){
        //TODO
        return card;
    }
    public void addCardToFieldZone(Card card){
        if (!fieldZone.isOccupied()) fieldZone.addCard(card);
    }
    public Card removeCardFromFieldZone(Card card){
        if (fieldZone.getCard().equals(card)) return fieldZone.removeCard();
        return null;
    }
    public int getSumLevel(){
        //TODO
        return 1;
    }
}
