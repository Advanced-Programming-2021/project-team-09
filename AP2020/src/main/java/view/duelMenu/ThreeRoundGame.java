package view.duelMenu;

import controller.GameMenuController;
import model.User;
import model.deck.Deck;
import model.exceptions.WinnerException;

import java.util.Scanner;

public class ThreeRoundGame {
    private User firstUser;
    private User secondUser;
    private User winner;
    private User loser;
    private final Scanner scanner;
    private OneRoundGame firstRound;
    private OneRoundGame secondRound;
    private OneRoundGame thirdRound;
    private WinnerException firstRoundException;
    private WinnerException secondRoundException;
    private WinnerException thirdRoundException;

    public ThreeRoundGame(User firstUser, User secondUser, Scanner scanner) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.scanner = scanner;
    }

    public void run() {
        firstRound = new OneRoundGame(firstUser, secondUser, scanner);
        try {
            firstRound.run();
        } catch (WinnerException firstRoundException) {
            this.firstRoundException = firstRoundException;
        }
        askPlayersIfTheyWantToChangeDeck();
        askPlayersIfTheyWantToBringCardsFromMainToSide();
        secondRound = new OneRoundGame(firstRoundException.getWinner(), firstRoundException.getLoser(), scanner);
        try {
            secondRound.run();
        } catch (WinnerException secondRoundException) {
            this.firstRoundException = secondRoundException;
        }
        if (checkIfThirdRoundIsNeededOrNot()){
            askPlayersIfTheyWantToChangeDeck();
            askPlayersIfTheyWantToBringCardsFromMainToSide();
            thirdRound = new OneRoundGame(secondRoundException.getWinner(), secondRoundException.getLoser(), scanner);
            try {
                thirdRound.run();
            } catch (WinnerException thirdRoundException) {
                this.thirdRoundException = thirdRoundException;
            }
            declareWinnerAndLoser(true);
            GameMenuController.cashOut(calculateMaxLP(true),true,winner,loser);
        }
        else {
            declareWinnerAndLoser(false);
            GameMenuController.cashOut(calculateMaxLP(false),true,winner,loser);
        }
    }
    public void askPlayersIfTheyWantToBringCardsFromMainToSide(){
        askPlayerToBringCardsFromMainToSide(firstUser);
        askPlayerToBringCardsFromMainToSide(secondUser);
    }
    public void askPlayerToBringCardsFromMainToSide(User user){
        String command;
        while (true) {
            System.out.println("do you want to swap cards "+ user.getNickname()+"?");
            command = scanner.nextLine();
            if (command.matches("yes")){
                swapCard(user);
            }
            else if (command.matches("no"))
                break;
            else System.out.println("invalid command!");
        }
    }
    public void swapCard(User user){
        //todo
    }

    public void askPlayersIfTheyWantToChangeDeck() {
        askPlayerToChangeDeck(firstUser);
        askPlayerToChangeDeck(secondUser);
    }

    private void askPlayerToChangeDeck(User user) {
        String command;
        while (true) {
            System.out.println("do you want to change your deck " + user.getNickname() + "?");
            command = scanner.nextLine().trim();
            if (command.matches("yes"))
                changeDeck(user);
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
    public boolean checkIfThirdRoundIsNeededOrNot(){
        return !firstRoundException.getWinner().equals(secondRoundException.getWinner());
    }
    public void declareWinnerAndLoser(boolean hasThirdRound){
        if (hasThirdRound){
            winner = thirdRoundException.getWinner();
            loser = thirdRoundException.getLoser();
        }
        else {
            winner = firstRoundException.getWinner();
            loser = firstRoundException.getLoser();
        }
    }
    public int calculateMaxLP(boolean hasThirdRound){
        if (hasThirdRound){
            if (thirdRoundException.getWinner().equals(secondRoundException.getWinner()))
                return Math.max(secondRoundException.getWinnerLP(), thirdRoundException.getWinnerLP());
            return Math.max(firstRoundException.getWinnerLP(), thirdRoundException.getWinnerLP());
        }
        else {
            return Math.max(firstRoundException.getWinnerLP(), secondRoundException.getWinnerLP());
        }
    }
}
