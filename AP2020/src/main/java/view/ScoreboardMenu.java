package view;

import java.util.Scanner;

public class ScoreboardMenu {
    private final Scanner scanner;
    private static ScoreboardMenu scoreboardMenu;
    public void run(){
        System.out.println("hehe");
    }
    public static ScoreboardMenu getInstance(Scanner scanner){
        if (scoreboardMenu == null) scoreboardMenu = new ScoreboardMenu(scanner);
        return scoreboardMenu;
    }
    private ScoreboardMenu (Scanner scanner){
        this.scanner = scanner;
    }
}
