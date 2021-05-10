package view.duelMenu;

import model.User;

import java.util.Scanner;

public class RockPaperScissors {
    private int firstUsersScore;
    private int secondUsersScore;
    private final Scanner scanner;
    private static RockPaperScissors rockPaperScissors;
    private User firstUser;
    private User secondUser;
    private String firstUsersChoice;
    private String secondUsersChoice;

    private RockPaperScissors(User firstUser , User secondUser , Scanner scanner){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.scanner = scanner;
        firstUsersScore = 0;
        secondUsersScore = 0;
    }
    public static RockPaperScissors getInstance(User firstUser, User secondUser, Scanner scanner){
        if (rockPaperScissors == null) rockPaperScissors = new RockPaperScissors(firstUser, secondUser, scanner);
                return rockPaperScissors;
    }

    public User run(){
        while (firstUsersScore < 3 && secondUsersScore < 3){
            while (true){
                firstUsersChoice =scanner.nextLine().trim();
                if(firstUsersChoice.matches("rock"))
                    break;
                else if (firstUsersChoice.matches("paper"))
                    break;
                else if (firstUsersChoice.matches("scissors"))
                    break;
                else System.out.println("invalid command!\n"+"please chose rock,paper or scissors");
            }
            while (true){
                secondUsersChoice =scanner.nextLine().trim();
                if(secondUsersChoice.matches("rock"))
                    break;
                else if (secondUsersChoice.matches("paper"))
                    break;
                else if (secondUsersChoice.matches("scissors"))
                    break;
                else System.out.println("invalid command!\n"+"please chose rock,paper or scissors");
            }
            checkThisRoundWinner(firstUsersChoice, secondUsersChoice);
            System.out.println(firstUser.getNickname()+":"+firstUsersChoice+"\t"
                    +secondUser.getNickname()+":"+secondUsersChoice+"\n"
                    +firstUsersScore+":"+secondUsersScore);
        }
        if (firstUsersScore == 3)
            return firstUser;
        else
            return secondUser;
    }
    public void checkThisRoundWinner(String firstUsersChoice, String secondUsersChoice){
        if (firstUsersChoice.equals("rock") && secondUsersChoice.equals("scissors"))
            firstUsersScore++;
        else if (firstUsersChoice.equals("rock") && secondUsersChoice.equals("paper"))
            secondUsersScore++;
        else if (firstUsersChoice.equals("scissors") && secondUsersChoice.equals("paper"))
            firstUsersScore++;
        else if (firstUsersChoice.equals("scissors") && secondUsersChoice.equals("rock"))
            secondUsersScore++;
        else if (firstUsersChoice.equals("paper") && secondUsersChoice.equals("rock"))
            firstUsersScore++;
        else if (firstUsersChoice.equals("paper") && secondUsersChoice.equals("scissors"))
            secondUsersScore++;
    }
}
