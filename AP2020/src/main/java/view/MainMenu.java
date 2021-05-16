package view;

import controller.LoginMenuController;
import view.duelMenu.DuelMenu;
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
            else if(command.matches("menu show-current"))
                respond(MainMenuResponses.CURRENT_MENU_MAIN_MENU);
            else if (command.matches("logout")){
                logout();
                return;
            }
            else if (command.matches("help")) showHelp();
            else respond(MainMenuResponses.INVALID_COMMAND);
        }
    }

    private void gotoMenu(String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, "^menu enter (?<menuName>\\w+)$");
        if (matcher.find()) {
            String menuName = matcher.group("menuName");
            try {
                Method method = MainMenu.class.getDeclaredMethod(menuName);
                method.invoke(this);
            } catch (Exception e) {
                respond(MainMenuResponses.INVALID_MENU);
            }
        }
    }

    private void respond(MainMenuResponses response){
        if(response.equals(MainMenuResponses.CURRENT_MENU_MAIN_MENU))
            System.out.println("you are in main menu");
        else if(response.equals(MainMenuResponses.INVALID_COMMAND))
            System.out.println("invalid command!");
        else if(response.equals(MainMenuResponses.INVALID_MENU))
            System.out.println("menu navigation is not possible");
        else if(response.equals(MainMenuResponses.LOGOUT_SUCCESSFUL))
            System.out.println("user logged out successfully!");
    }

    private void shop(){
        ShopMenu shopMenu = ShopMenu.getInstance(scanner);
        shopMenu.run();
    }

    public void scoreboard(){
        ScoreboardMenu scoreboardMenu = ScoreboardMenu.getInstance(scanner);
        scoreboardMenu.run();
    }

    private void profile(){
        ProfileMenu profileMenu = ProfileMenu.getInstance(scanner);
        profileMenu.run();
    }

    private void deck(){
        DeckMenu deckMenu = DeckMenu.getInstance(scanner);
        deckMenu.run();
    }

    private void importexport(){

    }

    private void duel(){
        System.out.println("Now Entering duel menu  ..");
        DuelMenu.getInstance(LoginMenu.getInstance().getScanner()).run();
    }

    private void logout(){
        LoginMenuController.logout();
        respond(MainMenuResponses.LOGOUT_SUCCESSFUL);
    }

    public void showHelp() {
        String help = "user logout\n";
        help += "menu show-current\n";
        help += "menu enter <menu name>\n";
        help += "menu exit";
        System.out.println(help);
    }

}
