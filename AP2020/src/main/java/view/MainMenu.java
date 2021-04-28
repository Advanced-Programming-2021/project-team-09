package view;

import view.regexes.RegexFunctions;
import view.responses.MainMenuResponses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private final Scanner scanner;
    private static MainMenu mainMenu;

    private MainMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public static MainMenu getInstance(Scanner scanner){
        if (mainMenu == null) mainMenu = new MainMenu(scanner);
        return mainMenu;
    }

    public  void run(){
        String command;
        while (true){
            command = scanner.nextLine().trim().toLowerCase();
            if (command.matches("^menu enter (?<menuName>\\w+)$"))
                gotoMenu(command);
            else if (command.matches("logout"))
                logout();
            else respond(MainMenuResponses.INVALID_COMMAND);
        }
    }
    private void gotoMenu(String command){
        Matcher matcher = RegexFunctions.getCommandMatcher(command,"^menu enter (?<menuName>\\w+)$");
        if (matcher.find()) {
            String menuName = matcher.group("menuName");
            System.out.println(menuName);
            try {
                Method method = MainMenu.class.getMethod(menuName);
                method.invoke(MainMenu.class);
            } catch (NoSuchMethodException e) {
                respond(MainMenuResponses.INVALID_MENU);
                System.out.println("f");
            } catch (InvocationTargetException e){
                System.out.println("ff");
            } catch (IllegalAccessException e){
                System.out.println("ee");
            }
        }

    }
    private void respond(MainMenuResponses response){
        System.out.println("e");
    }

    private void shopMenu(){

    }
    private void scoreboard(){
        ScoreboardMenu scoreboardMenu = ScoreboardMenu.getInstance(scanner);
        scoreboardMenu.run();
    }
    private void profile(){

    }
    private void deck(){

    }
    private void importexport(){

    }
    private void duel(){

    }
    private void logout(){

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = MainMenu.getInstance(scanner);
        mainMenu.run();

    }
}
