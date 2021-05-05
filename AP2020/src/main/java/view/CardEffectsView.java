package view;

import model.Board;
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

    static public boolean doSpecialSummon(){
        return true;
    }
    static public int getNumberOfCardInHand(){
        return 0;
    }

    static public int[] getCellNumbers(int count) {
        return null;
    }

    static public Card getCardFromGraveyard(Graveyard graveyard) {
        return null;
    }

    static public HowToSummon howToSummon() {
        return null;
    }

}
