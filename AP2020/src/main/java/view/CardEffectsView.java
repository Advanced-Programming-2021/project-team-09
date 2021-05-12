package view;

import model.deck.Deck;
import model.game.Board;
import model.game.Cell;

import model.game.Game;
import model.game.State;
import model.card.Card;
import model.deck.Graveyard;
import view.responses.CardEffectsResponses;
import view.responses.HowToSummon;

import java.util.ArrayList;
import java.util.Scanner;

public class CardEffectsView {

    static public int getCellNumber() {
        return getCellNumbers(1)[0];
    }

    static public void respond(CardEffectsResponses response) {
        if (response == CardEffectsResponses.CANT_RITUAL_SUMMON)
            System.out.println("You cant ritual summon !");
        else if (response == CardEffectsResponses.HAVE_NO_CARDS)
            System.out.println("You have no cards !");
        else if (response == CardEffectsResponses.INVALID_CELL_NUMBER)
            System.out.println("Invalid Cell number !");
        else if (response == CardEffectsResponses.MONSTER_ZONE_IS_FULL)
            System.out.println("Monster zone is full !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL)
            System.out.println("Please select a field spell !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_AN_SPELL)
            System.out.println("Please select a spell !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_VALID_TYPE)
            System.out.println("Please select a valid type ! ");
        else if (response == CardEffectsResponses.YOU_DONT_HAVE_ANY_FIELD_SPELL)
            System.out.println("You dont have any field spell !");
        else if (response == CardEffectsResponses.NO_MONSTERS)
            System.out.println("You have no monsters !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_MONSTER)
            System.out.println("Please select monster !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER)
            System.out.println("Please select a valid number !");
        else if (response == CardEffectsResponses.SPECIAL_SUMMON_NOW)
            System.out.println("You have to special summon now !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_LEVEL_7_OR_MORE)
            System.out.println("Please select level 7 or more monster !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_VALID_MONSTER)
            System.out.println("Please select a valid monster !");
        else if (response == CardEffectsResponses.CANT_ACTIVATE_TRAP)
            System.out.println("You cant activate trap !");
    }

    static public boolean doYouWantTo(String message) {
        Scanner scanner = LoginMenu.getInstance().getScanner();
        while (true) {
            System.out.println(message);
            String command = scanner.nextLine().trim().toLowerCase();
            if (command.matches("^no$")) return false;
            else if (command.matches("^yes$")) return true;
            System.out.println("Invalid response .. please say yes or no ..");
        }
    }

    static public Card getCardFrom(Board board, ArrayList<Card> cards) {
        ArrayList<Card> temp = new ArrayList<>();
        Cell[] cells = board.getMonsterZone();
        ArrayList<Card> output = new ArrayList<>(cards);
        output.addAll(board.getGraveyard().getCards());
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                temp.add(cells[i].getCard());
            }
        }
        output.addAll(temp);
        return selectCardFromArrayList(output);
    }

    private static Card selectCardFromArrayList(ArrayList<Card> cards) {
        Scanner scanner = LoginMenu.getInstance().getScanner();
        String command;
        while (true) {
            System.out.println("Please select one of the following cards : ");
            for (int i = 0; i < cards.size(); i++) {
                System.out.println((i + 1) + " : " + cards.get(i).getCardName());
            }
            command = scanner.nextLine().trim();
            if (command.matches("^[\\d]{1,3}$")) {
                int temp = Integer.parseInt(command);
                if (temp <= cards.size()) {
                    return cards.get(temp - 1);
                }
            }
            System.out.println("Invalid input .. ");
        }
    }

    static public boolean doSpecialSummon() {
        return true;
    }

    static public String getCardName(){
        System.out.println("Please enter a card name ..");
        return LoginMenu.getInstance().getScanner().nextLine();
    }

    static public int getNumberOfCardInHand() {
        System.out.println("please select a card from your hand");
        return 0;
    }

    static public int[] getCellNumbers(int count) {
        while (true) {
            int[] temp = getCellNumberExtracted(count);
            if (temp.length == count) return temp;
            System.out.println("Invalid Command .. please enter " + count + " cell number(s)");
        }
    }

    static public int[] getCellNumbers() {
        return getCellNumberExtracted(0);
    }

    private static int[] getCellNumberExtracted(int count) {
        String command;
        Scanner scanner = LoginMenu.getInstance().getScanner();
        while (true) {
            if (count == 0) {
                System.out.println("Please enter cell numbers .. ");
            } else {
                System.out.println("Please enter " + count + " cell numbers .. ");
            }
            command = scanner.nextLine().trim();
            if (command.matches("^([1-5])( [1-5])*$")) {
                ArrayList<Integer> temp = new ArrayList<>();
                String[] stringArray = command.split(" ");
                for (String s : stringArray) temp.add(Integer.parseInt(s));
                int[] output = new int[temp.size()];
                for (int i = 0 ;i < temp.size(); i++) {
                    output[i] = temp.get(i);
                }
                return output;
            }
            System.out.println("Invalid Command .. ");
        }
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
