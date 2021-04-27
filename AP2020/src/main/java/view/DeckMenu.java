package view;

import controller.DeckMenuController;
import view.regexes.DeckMenuRegex;
import view.regexes.RegexFunctions;
import view.responses.DeckMenuResponses;

import java.util.Scanner;
import java.util.regex.Matcher;

public class DeckMenu {
    private static DeckMenu deckMenu;
    private final Scanner scanner;


    private DeckMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public static DeckMenu getDeckMenu(Scanner scanner) {
        if (deckMenu == null) deckMenu = new DeckMenu(scanner);
        return deckMenu;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    private void showCard(String command) {

    }

    public void run() {
        String command;
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            if (command.matches(DeckMenuRegex.creatDeckRegex))
                creatDeck(command);
            else if (command.matches(DeckMenuRegex.deleteDeckRegex))
                deleteDeck(command);
            else if (command.matches(DeckMenuRegex.showMainDeckRegex))
                showMainDeck(command);
            else if (command.matches(DeckMenuRegex.showSideDeckRegex))
                showSideDeck(command);
            else if (command.matches(DeckMenuRegex.showAllDecksRegex))
                showAllDecks();
            else if (command.matches(DeckMenuRegex.showCardRegex))
                showCard(command);
            else if (command.matches(DeckMenuRegex.showAllCardsRegex))
                showAllCards();
            else if (command.matches(DeckMenuRegex.addCardToMainDeckRegex))
                addCardToMainDeck(command);
            else if (command.matches(DeckMenuRegex.addCardToSideDeckRegex))
                addCardToSideDeck(command);
            else if (command.matches(DeckMenuRegex.removeCardFromMainDeckRegex))
                removeCardFromMainDeck(command);
            else if (command.matches(DeckMenuRegex.removeCardFromSideDeckRegex))
                removeCardFromSideDeck(command);
            else if (command.matches(DeckMenuRegex.activeDeckRegex))
                activeDeck(command);
            else if (command.matches("back"))
                return;
            else respond(DeckMenuResponses.InvalidCommand);
        }
    }

    private void creatDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.creatDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            DeckMenuResponses response = DeckMenuController.createDeck(deckName);
            respond(response);
        }
    }

    private void deleteDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.deleteDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            DeckMenuResponses response = DeckMenuController.deleteDeck(deckName);
            respond(response);
        }
    }

    private void activeDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.activeDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            DeckMenuResponses response = DeckMenuController.activateDeck(deckName);
            respond(response);
        }
    }

    private void addCardToMainDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.addCardToMainDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String cardName = matcher.group("cardName");
            DeckMenuResponses response = DeckMenuController.addCardToMainDeck(cardName, deckName);
            respond(response);
        }
    }

    private void addCardToSideDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.addCardToSideDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String cardName = matcher.group("cardName");
            DeckMenuResponses response = DeckMenuController.addCardToSideDeck(cardName, deckName);
            respond(response);
        }
    }

    private void removeCardFromMainDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.removeCardFromMainDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String cardName = matcher.group("cardName");
            DeckMenuResponses response = DeckMenuController.removeCardFromMainDeck(cardName, deckName);
            respond(response);
        }
    }

    private void removeCardFromSideDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.removeCardFromSideDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String cardName = matcher.group("cardName");
            DeckMenuResponses response = DeckMenuController.removeCardFromSideDeck(cardName, deckName);
            respond(response);
        }
    }

    private void showAllCards() {
        String response = DeckMenuController.showAllCards();
        showMessage(response);
    }

    private void showAllDecks() {
        String response = DeckMenuController.showAllDecks();
        showMessage(response);
    }

    private void showMainDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.showMainDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            DeckMenuResponses response = DeckMenuController.showMainDeck(deckName);
            respond(response);
        }
    }

    private void showSideDeck(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, DeckMenuRegex.showSideDeckRegex);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            DeckMenuResponses response = DeckMenuController.showSideDeck(deckName);
            respond(response);
        }
    }

    private void respond(DeckMenuResponses response) {
        if (response.equals(DeckMenuResponses.SUCCESSFUL))
            System.out.println("successful!"); //ToDo
        else if (response.equals(DeckMenuResponses.CANT_ADD_MORE_OF_THIS_CARD))
            System.out.println("there are already three cards with this name in the selected deck!");
        else if (response.equals(DeckMenuResponses.DECK_DOESNT_EXIST))
            System.out.println("there is no such deck with this name!");
        else if (response.equals(DeckMenuResponses.CARD_DOESNT_EXIST))
            System.out.println("there isn't such a card with this name");
        else if (response.equals(DeckMenuResponses.DECK_ALREADY_EXISTS))
            System.out.println("deck with this name already exists!");
        else if (response.equals(DeckMenuResponses.MAIN_DECK_IS_FULL))
            System.out.println("main deck is full!");
        else if (response.equals(DeckMenuResponses.SIDE_DECK_IS_FULL))
            System.out.println("side deck is full!");
        else if (response.equals(DeckMenuResponses.InvalidCommand))
            System.out.println("invalid command!");
    }


}
