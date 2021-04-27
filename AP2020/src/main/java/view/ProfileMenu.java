package view;

import controller.ProfileController;
import view.regexes.ProfileMenuRegex;
import view.regexes.RegexFunctions;
import view.responses.ProfileMenuResponses;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private final Scanner scanner;
    private static ProfileMenu profileMenu;

    private ProfileMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public static ProfileMenu getProfileMenu(Scanner scanner){
        if (profileMenu == null) {
            profileMenu = new ProfileMenu(scanner);
        }
        return profileMenu;
    }


    public void changePassword(String command){
       String[] passwords = getOldAndNewPassword(command);
       ProfileMenuResponses response = ProfileController.changePassword(passwords[0],passwords[1]);
       respond(response);

    }
    public void changeNickname(String command){
       Matcher matcher = RegexFunctions.getRightMatcherForChangeNickname(command);
    }
    public void run(){

    }
    public void respond(ProfileMenuResponses response){
    }

    public String[] getOldAndNewPassword(String command){
        String[] passwords = new String[2];
        Matcher matcher = RegexFunctions.getRightMatcherForChangePassword(command);
        if (matcher.find()) {
            passwords[1] = matcher.group("newPassword");
            passwords[0] = matcher.group("currentPassword");
            return passwords;
        }
        return null;
    }
}
