package model;

public abstract class Card {
    protected String cardName;
    protected String description;
    protected CardType cardType;
    protected String cardID;

    public String getDescription(){
        return this.description;
    }

    public String getCardName(){
        return this.cardName;
    }

    public boolean isMonster(){
        return cardType == CardType.MONSTER;
    }

    public boolean isSpell(){
        return cardType == CardType.SPELL;
    }

    public boolean isTrap(){
        return cardType == CardType.TRAP;
    }

    public abstract String toString();
}
