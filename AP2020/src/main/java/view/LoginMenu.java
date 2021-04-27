package view;

import controller.LoginMenuController;
import view.regexes.LoginMenuRegex;
import view.responses.LoginMenuResponses;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    private Scanner scanner;
    private LoginMenu loginMenu;

    private LoginMenu() {
        scanner = new Scanner(System.in);
    }

    public LoginMenu getInstance() {
        if (loginMenu == null)
            loginMenu = new LoginMenu();
        return loginMenu;
    }
    
    public void run() {
        String command;
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            if (LoginMenuRegex.doesItLoginCommand(command))
                login(command);
            else if (LoginMenuRegex.doesItCreateUserCommand(command))
                createUser(command);
            else if (command.matches("exit"))
                return;
            else respond(LoginMenuResponses.INVALID_COMMAND);
        }

    }

    private void login(String command) {
        HashMap<String, String> data = parseLoginData(command);
        String username = data.get("username");
        String password = data.get("password");
        LoginMenuResponses response = LoginMenuController.login(username, password);
        respond(response);
        MainMenu.run();
    }

    private void createUser(String command) {
        HashMap<String, String> data = parseCreateUserData(command);
        String username = data.get("username");
        String password = data.get("password");
        String nickname = data.get("nickname");
        LoginMenuResponses response = LoginMenuController.createUser(username, nickname, password);
        respond(response);
    }

    private void respond(LoginMenuResponses response) {

    }

    private HashMap<String, String> parseLoginData(String command) {
        HashMap<String, String> parsedInfo = new HashMap<>();
        Matcher matcher = LoginMenuRegex.getRightMatcherForLogin(command);
        if (matcher.find()) {
            parsedInfo.put("username", matcher.group("username"));
            parsedInfo.put("password", matcher.group("password"));
        }
        return parsedInfo;
    }

    private HashMap<String, String> parseCreateUserData(String command) {
        HashMap<String, String> parsedInfo = new HashMap<>();
        Matcher matcher = LoginMenuRegex.getRightMatcherForCreateUser(command);
        if (matcher.find()) {
            parsedInfo.put("username", matcher.group("username"));
            parsedInfo.put("password", matcher.group("password"));
            parsedInfo.put("nickname", matcher.group("nickname"));
        }
        return parsedInfo;
    }

}
