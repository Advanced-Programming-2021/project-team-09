package view.duelMenu;

import model.User;

import java.util.Scanner;

public class MiniGamesMenu {
    private User firstUser;
    private User secondUser;
    private final Scanner scanner;
    private static MiniGamesMenu miniGameMenu;
    private User winner;

    private MiniGamesMenu(User firstUser, User secondUser, Scanner scanner) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.scanner = scanner;
    }

    public static MiniGamesMenu getInstance(User firstUser, User secondUser, Scanner scanner) {
        if (miniGameMenu == null) miniGameMenu = new MiniGamesMenu(firstUser, secondUser, scanner);
        return miniGameMenu;
    }

    public User run() {
        String command;
        System.out.println("chose a game to declare first player:\n" +
                "rock paper scissors\n" +
                "dice\n" +
                "throw coin");
        while (true) {
            command = scanner.nextLine().trim();
            if (command.matches("rock paper scissors")) {
                playRockPaperScissor(firstUser, secondUser);
                return getWinner();
            } else if (command.matches("dice")) {
                playDice(firstUser, secondUser);
                return getWinner();
            } else if (command.matches("throw coin")) {
                playCoin(firstUser, secondUser);
                return getWinner();
            } else if (command.matches("help"))
                showHelp();
            else if (command.matches("menu show-current"))
                System.out.println("mini games menu");
            else System.out.println("invalid command!");
        }
    }

    public void playRockPaperScissor(User firstUser, User secondUser) {
        setWinner(RockPaperScissors.getInstance(firstUser, secondUser, scanner).run());
    }

    public void playDice(User firstUser, User secondUser) {
        int firstUserDice = dice();
        int secondUserDice = dice();
        if (firstUserDice > secondUserDice)
            setWinner(firstUser);
        else if (firstUserDice == secondUserDice)
            playDice(firstUser, secondUser);
        else
            setWinner(secondUser);
    }

    public void playCoin(User firstUser, User secondUser) {
        System.out.println("Head or Tale?");
        String playerChoice = scanner.nextLine().trim();
        String coin = throwCoin();

        if (playerChoice.equals(throwCoin()))
            setWinner(firstUser);
        else
            setWinner(secondUser);
    }

    public int dice() {
        int randomNumber;
        randomNumber = (int) (Math.random() * 1_000_000);
        int diceNumber;
        return diceNumber = (randomNumber % 6) + 1;
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
        StringBuilder help = new StringBuilder();
        help.append("rock paper scissors\n");
        help.append("dice\n");
        help.append("throw coin\n");
        help.append("menu show-current");
        System.out.println(help);
    }

    public void setWinner(User user) {
        winner = user;
    }

    public User getWinner() {
        return winner;
    }
}
