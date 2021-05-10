package view;

import model.deck.Deck;
import model.game.Board;
import model.game.Cell;

import model.game.State;
import model.card.Card;
import model.deck.Graveyard;
import view.responses.CardEffectsResponses;
import view.responses.HowToSummon;

public class CardEffectsView {
    static public int getCellNumber() {
        return 0;
    }

    static public void respond(CardEffectsResponses response) {
    }

    static public boolean doYouWantTo(String message) {
        return true;
    }

    static public Card getCardFrom(Board board) {
        return null;
    }

    static public boolean doSpecialSummon() {
        return true;
    }

    static public int getNumberOfCardInHand() {
        return 0;
    }

    static public int[] getCellNumbers(int count) {
        return null;
    }

    static public int[] getCellNumbers() {
        return null;
    }

    static public Card getCardFromGraveyard(Graveyard graveyard) {
        return null;
    }

    static public Card getCardFromBothGraveyards(Graveyard playerGraveyard, Graveyard rivalGraveyard) {
        return null;
    }

    static public Card getCardFromDeck(Deck deck) {
        return null;
    }

    static public Card getCardFromBothBoards(Cell[] playerSpellZone, Cell[] rivalSpellZone) {
        return null;
    }

    static public HowToSummon howToSpecialNormalSummon() {
        return null;
    }

    static public State getStateOfSummon() {
        return null;
    }

}
