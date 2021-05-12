package view.duelMenu;

import model.User;
import model.game.MiniGame;

import java.util.Scanner;

public class MiniGamesMenu {
    private final User firstUser;
    private final User secondUser;
    private final Scanner scanner;
    private static MiniGamesMenu miniGameMenu;
    private final MiniGame miniGame;

    private MiniGamesMenu(Scanner scanner, MiniGame miniGame) {
        this.miniGame = miniGame;
        this.firstUser = miniGame.getFirstUser();
        this.secondUser = miniGame.getSecondUser();
        this.scanner = scanner;
    }

    public static MiniGamesMenu getInstance(Scanner scanner, MiniGame miniGame) {
        if (miniGameMenu == null) miniGameMenu = new MiniGamesMenu(scanner , miniGame);
        return miniGameMenu;
    }

    public void run() {
        String command;
        System.out.println("chose a game to declare first player:\n" +
                "rock paper scissors\n" +
                "dice\n" +
                "throw coin");
        while (true) {
            command = scanner.nextLine().trim();
            if (command.matches("rock paper scissors")) {
                playRockPaperScissor();
                return;
            } else if (command.matches("dice")) {
                playDice();
                return;
            } else if (command.matches("throw coin")) {
                playCoin();
                return;
            } else if (command.matches("help"))
                showHelp();
            else if (command.matches("menu show-current"))
                System.out.println("mini games menu");
            else System.out.println("invalid command!");
        }
    }

    public void playRockPaperScissor() {
        RockPaperScissors.getInstance(scanner, miniGame).run();
    }

    public void playDice() {
        int firstUserDice = dice();
        int secondUserDice = dice();
        if (firstUserDice > secondUserDice)
            miniGame.setWinner(firstUser);
        else if (firstUserDice == secondUserDice)
            playDice();
        else
            miniGame.setWinner(secondUser);
    }

    public void playCoin() {
        System.out.println("Head or Tale?");
        String playerChoice = scanner.nextLine().trim();
        String coin = throwCoin();

        if (playerChoice.equals(coin))
            miniGame.setWinner(firstUser);
        else
            miniGame.setWinner(secondUser);
    }

    public int dice() {
        int randomNumber;
        randomNumber = (int) (Math.random() * 1_000_000);
        return (randomNumber % 6) + 1;
    }

    public String throwCoin() {
        int randomNumber;
        randomNumber = (int) (Math.random() * 1_000_000);
        int headOrTale = randomNumber % 2;
        if (headOrTale == 0)
            return "Head";
        else
            return "Tale";
    }

    public void showHelp() {
        String help = "rock paper scissors\n" +
                "dice\n" +
                "throw coin\n" +
                "menu show-current";
        System.out.println(help);
    }
}
