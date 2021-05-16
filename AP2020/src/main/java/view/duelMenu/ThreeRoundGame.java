package view.duelMenu;

import model.User;
import model.deck.Deck;

import java.util.Scanner;

public class ThreeRoundGame {
    private User firstUser;
    private User secondUser;
    private final Scanner scanner;
    private OneRoundGame firstRound;
    private OneRoundGame secondRound;
    private OneRoundGame thirdRound;

    public ThreeRoundGame(User firstUser, User secondUser, Scanner scanner) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.scanner = scanner;
    }

    public void run() {
        firstRound = new OneRoundGame(firstUser, secondUser, scanner);
        //todo inja bayad winner ro begirim va first va second ro avaz konim!
        askPlayersIfTheyWantToChangeDeck();
        secondRound = new OneRoundGame(firstUser, secondUser, scanner);
        askPlayersIfTheyWantToChangeDeck();
        thirdRound = new OneRoundGame(firstUser, secondUser, scanner);
    }

    public void askPlayersIfTheyWantToChangeDeck() {
        String command;
        while (true) {
            System.out.println("do you want to change your deck " + firstUser.getNickname() + "?");
            command = scanner.nextLine().trim();
            if (command.matches("yes"))
                changeDeck(firstUser);
            else if (command.matches("no"))
                break;
            else if (command.matches("next"))
                break;
            else
                System.out.println("invalid command!");
        }
        while (true) {
            System.out.println("do you want to change your deck " + secondUser.getNickname() + "?");
            command = scanner.nextLine().trim();
            if (command.matches("yes"))
                changeDeck(secondUser);
            else if (command.matches("no"))
                break;
            else if (command.matches("next"))
                break;
            else
                System.out.println("invalid command!");
        }
    }

    public void changeDeck(User user) {
        System.out.println("which deck do you want to use for this round " + user.getNickname() + "?");
        for (Deck deck : user.getDecks()) {
            if (deck.isValid()) {
                System.out.println(deck.getDeckName() + ":");
                System.out.println();
                System.out.println(deck.allCardsToString());
            }
        }
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            if (command.matches("back"))
                return;
            else if (user.getDeckByName(command) == null)
                System.out.println("you don't have deck with this name!");
            else if (user.getDeckByName(command).isValid()) {
                user.setActiveDeck(user.getDeckByName(command));
                return;
            } else
                System.out.println("chose a valid deck!");
        }
    }
}
