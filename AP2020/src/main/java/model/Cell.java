package model;

import model.card.Card;

public class Cell {
    private Card card;
    private State state;
    private int roundCounter;

    public boolean isOccupied() {
        return this.card != null;
    }

    public Card removeCard() {
        Card returningCard = this.card;
        this.card = null;
        this.roundCounter = 0;
        this.state = null;
        return returningCard;

    }

    public void addCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }

    public boolean isFaceUp() {
        return state.equals(State.FACE_UP_ATTACK) || state.equals(State.FACE_UP_DEFENCE);
    }

    public boolean isFaceDown() {
        return state.equals(State.FACE_DOWN_ATTACK) || state.equals(State.FACE_DOWN_DEFENCE);
    }

    public boolean isAttack() {
        return state.equals(State.FACE_UP_ATTACK) || state.equals(State.FACE_DOWN_ATTACK);
    }

    public boolean isDefence() {
        return state.equals(State.FACE_UP_DEFENCE) || state.equals(State.FACE_DOWN_DEFENCE);
    }

    public int getRoundCounter() {
        return this.roundCounter;
    }

    public void increaseRoundCounter() {
        this.roundCounter++;
    }
}
