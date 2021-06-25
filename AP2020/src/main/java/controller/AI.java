package controller;

import controller.database.CSVInfoGetter;
import model.User;
import model.card.Card;
import model.card.monster.Monster;
import model.deck.Deck;
import model.deck.MainDeck;
import model.exceptions.GameException;
import model.exceptions.WinnerException;
import model.game.Cell;
import model.game.Game;
import model.game.State;
import view.duelMenu.SelectState;
import view.responses.GameMenuResponsesEnum;
import java.util.ArrayList;
import java.util.Collections;

public class AI {
    private final static User AI = new User("AI_MADE_BY_MIREBOZORG_AND_KASMAL_BEJOZ_SIA"
            , "NOONEWILLKNOW", "AI_MADE_BY_MIREBOZORG_AND_KASMAL_BEJOZ_SIA");
    private Game game;
    public enum AIState {EASY, NORMAL, HARD}
    private AIState aiState;

    public AI(AIState aiState){
        this.aiState = aiState;
        loadAI();
    }

    private void loadAI() {
        switch (aiState) {
            case EASY:
                loadEasy();
                break;
            case NORMAL:
                loadNormal();
                break;
            case HARD:
                loadHard();
                break;
        }
    }

    public void run(Game game) throws WinnerException{
        if (game == null) return;
        this.game = game;
        GameMenuController.draw(game);
        summon();
        attack();
    }

    public User getAI() {
        return AI;
    }

    public void summon() throws WinnerException {
        ArrayList<Card> cardsInAiHand = game.getPlayerHandCards();
        int[] cardsNumbersRankedByAttAndDef = rankMonstersAttAndDef(cardsInAiHand);
        GameMenuController.setSelectState(SelectState.HAND);
        for (int i : cardsNumbersRankedByAttAndDef) {
            GameMenuController.setCellNumber(i);
            if (GameMenuController.summon(game, i, true).getGameMenuResponseEnum() == GameMenuResponsesEnum.SUCCESSFUL) break;
        }
        int[] cardNumbersOfSpells = getSpellAndTrapNumbers(cardsInAiHand);
        int played = 0;
        for (int i : cardNumbersOfSpells) {
            GameMenuController.setCellNumber(i);
            if (GameMenuController.summon(game, i, true).getGameMenuResponseEnum() == GameMenuResponsesEnum.SUCCESSFUL) played++;
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

    private int[] fill(ArrayList<Card> cards1, ArrayList<Card> cards2) {
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

    public void attack() throws WinnerException{
        Cell[] aiMonsters = game.getPlayerBoard().getMonsterZone();
        Cell[] rivalMonsters = game.getRivalBoard().getMonsterZone();
        boolean[] attacked = new boolean[5];
        GameMenuController.setSelectState(SelectState.PLAYER_MONSTER);
        for (int i = 0; i < 5; i++) {
            if (!aiMonsters[i].isOccupied()) attacked[i] = true;
        }
        for (int i = 0; i < 5; i++) {
            if (!attacked[i]) attackWithAIMonster(i + 1, aiMonsters, rivalMonsters);
        }
    }

    private void attackWithAIMonster(int cellNumber, Cell[] aiMonsters, Cell[] rivalMonsters) throws WinnerException{
        int monsters = 0;
        for (Cell rivalMonster : rivalMonsters) if (rivalMonster.isOccupied()) monsters++;
        if (monsters == 0) {
            GameMenuController.setCellNumber(cellNumber);
            GameMenuController.directAttack(game, cellNumber);
        } else {
            for (int i = 0; i < 5; i++) {
                if (rivalMonsters[i].isOccupied()) {
                    if ((rivalMonsters[i].getState() == State.FACE_UP_ATTACK
                            && ((Monster)rivalMonsters[i].getCard()).getAttack() < ((Monster)aiMonsters[cellNumber - 1].getCard()).getAttack())
                            ||(rivalMonsters[i].getState() == State.FACE_UP_DEFENCE
                            && ((Monster)rivalMonsters[i].getCard()).getDefense() < ((Monster)aiMonsters[cellNumber - 1].getCard()).getAttack())) {
                        try {
                            GameMenuController.attack(game, cellNumber, i + 1, true);
                        } catch (GameException e) {
                            if (e instanceof WinnerException) {
                                throw (WinnerException) e;
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadEasy() {
        if (AI.getActiveDeck() == null) AI.setActiveDeck(new Deck(AI.getNickname()));
        MainDeck deck = AI.getActiveDeck().getMainDeck();
        removeAllCards();
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Battle OX"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Axe Raider"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Silver Fang"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Baby dragon"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Hero of the east"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Battle warrior"));
        }
    }

    private void loadNormal() {
        if (AI.getActiveDeck() == null) AI.setActiveDeck(new Deck(AI.getNickname()));
        MainDeck deck = AI.getActiveDeck().getMainDeck();
        removeAllCards();
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Haniwa"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Bitron"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Feral Imp"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Warrior Dai Grepher"));
        }
        for (int i = 0; i < 10; i++) {
            deck.addCard(CSVInfoGetter.getCardByName("Dark Blade"));
        }
    }

    private void loadHard() {
        if (AI.getActiveDeck() == null) AI.setActiveDeck(new Deck(AI.getNickname()));
        MainDeck deck = AI.getActiveDeck().getMainDeck();
        removeAllCards();
        for (int i = 0; i < 10; i++) {
            Card card = CSVInfoGetter.getCardByName("Spiral Serpent");
            ((Monster) card).setLevel(1);
            deck.addCard(card);
        }
        for (int i = 0; i < 10; i++) {
            Card card = CSVInfoGetter.getCardByName("Wattaildragon");
            ((Monster) card).setLevel(1);
            deck.addCard(card);
        }
        for (int i = 0; i < 10; i++) {
            Card card = CSVInfoGetter.getCardByName("Slot Machine");
            ((Monster) card).setLevel(1);
            deck.addCard(card);
        }
        for (int i = 0; i < 20; i++) {
            Card card = CSVInfoGetter.getCardByName("Blue-Eyes white dragon");
            ((Monster) card).setLevel(1);
            deck.addCard(card);
        }
        for (int i = 0; i < 10; i++) {
            Card card = CSVInfoGetter.getCardByName("Dark magician");
            ((Monster) card).setLevel(1);
            deck.addCard(card);
        }
    }

    private void removeAllCards() {
        MainDeck deck = AI.getActiveDeck().getMainDeck();
        for (int i = deck.getCards().size() - 1; i >= 0; i--) {
            deck.getCards().remove(i);
        }
    }
}
