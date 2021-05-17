package model.game;

import model.card.Card;
import org.jetbrains.annotations.NotNull;

public class Cell {
    private Card card;
    private State state;
    private int roundCounter;
    private boolean changedPosition;
    private boolean canAttack;// for monster cards

    public Cell() {
        card = null;
        state = null;
        roundCounter = 0;
        canAttack = false;
        changedPosition = false;
    }

    public boolean isOccupied() {
        return this.card != null;
    }

    public Card removeCard() {
        Card returningCard = this.card;
        this.card = null;
        this.roundCounter = 0;
        this.state = null;
        changedPosition = false;
        canAttack = false;
        return returningCard;
    }

    public void addCard(@NotNull Card card) {
        this.card = card;
        changedPosition = false;
        roundCounter = 0;
        if (card.isMonster()) canAttack = true;
    }

    public Card getCard() {
        return this.card;
    }

    public boolean isFaceUp() {
        return state.equals(State.FACE_UP_ATTACK) || state.equals(State.FACE_UP_DEFENCE);
    }

    public boolean isFaceDown() {
        return state.equals(State.FACE_DOWN_DEFENCE);
    }

    public boolean isAttack() {
        return state.equals(State.FACE_UP_ATTACK);
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isChangedPosition() {
        return changedPosition;
    }

    public void setChangedPosition(boolean changedPosition) {
        this.changedPosition = changedPosition;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean canAttack() {
        return canAttack;
    }



}
