package view;

import java.util.Scanner;

import view.responses.ScoreboardMenuResponses;
public class ScoreboardMenu {
    private static ScoreboardMenu scoreboardMenu;
    private final Scanner scanner;
    private String scoreboard;
    private ScoreboardMenu(Scanner scanner){
        this.scanner = scanner;
    }
    public static ScoreboardMenu getScoreboardMenu(Scanner scanner){
        if(scoreboardMenu == null) scoreboardMenu = new ScoreboardMenu(scanner);
        return scoreboardMenu;
    }

    public void run(){
        String command;
        while (true){
            command = scanner.nextLine().trim();
            if(command.matches("scoreboard show")) showScoreBoard();
            else if(command.matches("back")) return;
            else respond(ScoreboardMenuResponses.INVALID_COMMAND);
        }
    }
    public void showScoreBoard(){
        //what the fuck is wrong with you???
        respond(ScoreboardMenuResponses.SHOWING_SCOREBOARD);
    }

    public void respond(ScoreboardMenuResponses response){
        if(response.equals(ScoreboardMenuResponses.INVALID_COMMAND)) System.out.println("invalid command!");
        else if(response.equals(ScoreboardMenuResponses.SHOWING_SCOREBOARD))
            System.out.println(scoreboard);
    }
}
