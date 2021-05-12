package view.duelMenu;

import controller.MiniGameController;
import model.game.MiniGame;

import java.util.Scanner;

public class MiniGamesMenu {
    private final Scanner scanner;
    private static MiniGamesMenu miniGameMenu;
    private final MiniGame miniGame;

    private MiniGamesMenu(Scanner scanner, MiniGame miniGame) {
        this.miniGame = miniGame;
        this.scanner = scanner;
    }

    public static MiniGamesMenu getInstance(Scanner scanner, MiniGame miniGame) {
        if (miniGameMenu == null) miniGameMenu = new MiniGamesMenu(scanner, miniGame);
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

    private void playDice() {
        MiniGameController.playDice(miniGame);
    }

    private void playCoin() {
        System.out.println("head or tale");
        String userChoice;
        while (true) {
            userChoice = scanner.nextLine().trim();
            if (userChoice.equals("head"))
                break;
            else if (userChoice.equals("tale"))
                break;
            else
                System.out.println("please chose head or tail!");
        }
        MiniGameController.playCoin(miniGame, userChoice);
    }

    public void playRockPaperScissor() {
        RockPaperScissors.getInstance(scanner, miniGame).run();
    }

    public void showHelp() {
        String help = "rock paper scissors\n" +
                "dice\n" +
                "throw coin\n" +
                "menu show-current";
        System.out.println(help);
    }
}
