package controller;

import model.User;
import model.card.Card;
import model.card.monster.Monster;
import model.exceptions.WinnerException;
import model.game.Game;
import view.duelMenu.SelectState;
import view.responses.GameMenuResponsesEnum;

import java.util.ArrayList;
import java.util.Collections;

public class AI {
    private final static User AI = new User("AI_MADE_BY_MIREBOZORG_AND_KASMAL_BEJOZ_SIA"
            , "NOONEWILLKNOW", "AI_MADE_BY_MIREBOZORG_AND_KASMAL_BEJOZ_SIA");
    private Game game;

    public AI(){

    }

    public void run(Game game) throws WinnerException{
        this.game = game;
        summon(game);
    }

    public User getAI() {
        return AI;
    }

    public void summon(Game game) throws WinnerException {
        ArrayList<Card> cardsInAiHand = game.getPlayerHandCards();
        int[] cardsNumbersRankedByAttAndDef = rankMonstersAttAndDef(cardsInAiHand);
        GameMenuController.setSelectState(SelectState.HAND);
        for (int i : cardsNumbersRankedByAttAndDef) {
            GameMenuController.setCellNumber(i);
            if (GameMenuController.summon(game, i).getGameMenuResponseEnum() == GameMenuResponsesEnum.SUCCESSFUL) break;
        }
        int[] cardNumbersOfSpells = getSpellAndTrapNumbers(cardsInAiHand);
        int played = 0;
        for (int i : cardNumbersOfSpells) {
            GameMenuController.setCellNumber(i);
            if (GameMenuController.summon(game, i).getGameMenuResponseEnum() == GameMenuResponsesEnum.SUCCESSFUL) played++;
            if (played == 2) break;
        }
    }


    // returns the card numbers of monsters in hand
    // ranked by their att + def
    public int[] rankMonstersAttAndDef(ArrayList<Card> cards) {
        ArrayList<Monster> tempMonsters = new ArrayList<>();
        for (Card card : cards) {
            if (card.isMonster()) tempMonsters.add((Monster) card);
        }
        Collections.sort(tempMonsters, (o1, o2) -> {
            int a1 = o1.getAttack() + o1.getDefense();
            int a2 = o2.getAttack() + o2.getDefense();
            if (a1 > a2) return -1;
            return 1;
        });
        ArrayList<Card> tempCards = new ArrayList<>(tempMonsters);
        return fill(tempCards, cards);
    }

    public int[] getSpellAndTrapNumbers(ArrayList<Card> cards) {
        ArrayList<Card> tempCards = new ArrayList<>();
        for (Card card : cards) {
            if (!card.isMonster()) tempCards.add(card);
        }
        return fill(tempCards, cards);
    }

    public int[] fill(ArrayList<Card> cards1, ArrayList<Card> cards2) {
        int[] ret = new int[cards1.size()];
        for (int i = 0; i < cards1.size(); i++) {
            for (int j = 0; j < cards2.size(); j++) {
                if (cards1.get(i) == cards2.get(j)) {
                    ret[i] = j + 1;
                    break;
                }
            }
        }
        return ret;
    }
}
