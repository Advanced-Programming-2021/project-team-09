package model;

import model.card.Card;
import model.card.monster.Monster;
import model.deck.Deck;
import model.deck.Graveyard;

public class Board {
    private Cell[] monsterZone = new Cell[5];
    private Cell[] spellZone = new Cell[5];
    private Cell fieldZone = new Cell();
    private Graveyard graveyard = new Graveyard();
    private Deck deck;

    public boolean isMonsterZoneFull(){
        for (int i = 0; i < 5; i++) {
            if(!monsterZone[i].isOccupied()) return false;
        }
        return true;
    }
    public boolean isSpellZoneFull(){
        for (int i = 0; i < 5; i++) {
            if(!spellZone[i].isOccupied()) return false;
        }
        return true;
    }
    public Cell getMonsterZone(int cellNumber){
        return monsterZone[cellNumber];
    }

    public void addCardToMonsterZone(Card card){
        for (int i = 0; i < 5; i++) {
            if(!monsterZone[i].isOccupied() && monsterZone[i].getCard().isMonster()) monsterZone[i].addCard(card);
        }
    }
    public void addCardToSpellZone(Card card){
        for (int i = 0; i < 5; i++) {
            if(!spellZone[i].isOccupied() && monsterZone[i].getCard().isSpell()) spellZone[i].addCard(card);
        }
    }
    public void sendToGraveYard(Card card){
        graveyard.addCard(card);
    }
    public Card drawCardFromMainDeck(){
        return  deck.getMainDeck().getCards().get(0);
    }
    public Card removeCardFromMonsterZone(Card card){
        for (int i = 0; i < 5; i++) {
            if (monsterZone[i].getCard() != card) {
                continue;
            }
            sendToGraveYard(monsterZone[i].getCard());
            return monsterZone[i].removeCard();
        }
        return null;
    }
    public Card removeCardFromSpellZone(Card card){
        for (int i = 0; i < 5; i++) {
            if (spellZone[i].getCard() != card) {
                continue;
            }
            sendToGraveYard(spellZone[i].getCard());
            return spellZone[i].removeCard();
        }
        return null;
    }
    public Card removeCardFromGraveYard(Card card){
        return graveyard.removeCard(card.getCardName());
    }

    public void addCardToFieldZone(Card card){
        if (!fieldZone.isOccupied())
            fieldZone.addCard(card);
        else {
            removeCardFromFieldZone(fieldZone.getCard());
            fieldZone.addCard(card);
        }
    }
    public Card removeCardFromFieldZone(Card card){
        if (fieldZone.getCard() == card) {
            sendToGraveYard(fieldZone.getCard());
            return fieldZone.removeCard();
        }
        return null;
    }
    public int getSumLevel(Integer[] cellNumbers){
        int sumLevel = 0;
        for (int i = 0; i < cellNumbers.length; i++) {
            Monster monster =(Monster)monsterZone[cellNumbers[i]].getCard();
            sumLevel += monster.getLevel();
        }
        return sumLevel;
    }

    public Cell[] getMonsterZone() {
        return monsterZone;
    }

    public Cell[] getSpellZone() {
        return spellZone;
    }

    public Graveyard getGraveyard() {
        return this.graveyard;
    }

    public Cell getFieldZone() {
        return fieldZone;
    }
}
