package view.duelMenu;

import model.User;

import java.util.Scanner;

public class MiniGamesMenu {
    private User firstUser;
    private User secondUser;
    private final Scanner scanner;
    private static MiniGamesMenu miniGameMenu;

    private MiniGamesMenu(User firstUser, User secondUser, Scanner scanner){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.scanner = scanner;
    }
    public static MiniGamesMenu getInstance(User firstUser, User secondUser ,Scanner scanner){
        if (miniGameMenu == null) miniGameMenu = new MiniGamesMenu(firstUser, secondUser ,scanner);
        return miniGameMenu;
    }
    public void run(){
        String command;
        System.out.println("chose a game to declare first player:\n" +
                "rock paper scissors\n"+
                "dice\n"+
                "throw coin");
        while (true){
            command = scanner.nextLine().trim();
            if (command.matches("rock paper scissors"))
                playRockPaperScissor(firstUser, secondUser);
            else if (command.matches("dice"))
                playDice(firstUser, secondUser);
            else if (command.matches("throw coin"))
                playCoin(firstUser, secondUser);
            else if (command.matches("help"))
                showHelp();
            else if (command.matches("menu show-current"))
                System.out.println("mini games menu");
            else System.out.println("invalid command!");
        }

    }
    public User playRockPaperScissor(User firstUser, User secondUser){
        //todo better to make it a class?!
    }
    public User playDice(User firstUser, User secondUser){
        int firstUserDice = dice();
        int secondUserDice = dice();
        User winner;
        if (firstUserDice > secondUserDice)
            return winner = firstUser;
        else if (firstUserDice == secondUserDice)
            playDice(firstUser, secondUser);
        else return winner = secondUser;
        //todo dare bikhod irad migire!
    }
    public User playCoin(User firstUser, User secondUser){
        System.out.println("Head or Tale?");
        String playerChoice = scanner.nextLine().trim();
        String coin = throwCoin();
        User winner;
        if(playerChoice.equals(throwCoin()))
            return winner = firstUser;
        else
            return winner = secondUser;
    }
    public int dice(){
        int randomNumber;
        randomNumber = (int) (Math.random()*1_000_000);
        int diceNumber;
        return diceNumber = (randomNumber % 6) + 1;
    }

    public String throwCoin(){
        int randomNumber;
        randomNumber = (int) (Math.random()*1_000_000);
        int headOrTale = randomNumber % 2;
        if (headOrTale == 0)
            return "Head";
        else
            return "Tale";
    }
    public void showHelp(){
        StringBuilder help = new StringBuilder();
        help.append("rock paper scissors\n");
        help.append("dice\n");
        help.append("throw coin\n");
        help.append("menu show-current");
        System.out.println(help.toString());
    }
}
