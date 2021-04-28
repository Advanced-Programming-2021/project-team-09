package view;

import controller.ProfileController;
import view.regexes.ProfileMenuRegex;
import view.responses.ProfileMenuResponses;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private final Scanner scanner;
    private static ProfileMenu profileMenu;

    private ProfileMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public static ProfileMenu getProfileMenu(Scanner scanner) {
        if (profileMenu == null) {
            profileMenu = new ProfileMenu(scanner);
        }
        return profileMenu;
    }


    public void changePassword(String command) {
        HashMap<String,String> passwords = getOldAndNewPassword(command);
        ProfileMenuResponses response = ProfileController.changePassword(passwords.get("currentPassword"), passwords.get("newPassword"));
        respond(response);
    }

    public void changeNickname(String command) {
        Matcher matcher = ProfileMenuRegex.getRightMatcherForChangeNickname(command);
        if (matcher.find()) {
            String newNickname = matcher.group("nickName");
            ProfileMenuResponses response = ProfileController.changeNickname(newNickname);
            respond(response);
        }
    }

    public void run() {
        String command;
        while (true) {
            command = scanner.nextLine().trim().toLowerCase();
            if (command.matches(ProfileMenuRegex.changeNicknameRegex) ||
               command.matches(ProfileMenuRegex.changeNicknameRegexShort))
                changeNickname(command);
            else if (command.matches(ProfileMenuRegex.changePasswordRegexType1) ||
                    command.matches(ProfileMenuRegex.changePasswordRegexType2) ||
                    command.matches(ProfileMenuRegex.changePasswordRegexType1Short) ||
                    command.matches(ProfileMenuRegex.changePasswordRegexType2Short))
                changePassword(command);
            else if (command.matches("exit menu"))
                return;
            else respond(ProfileMenuResponses.INVALID_COMMAND);
        }
    }

    public void respond(ProfileMenuResponses response) {
        if (response.equals(ProfileMenuResponses.CURRENT_PASSWORD_IS_INVALID))
            System.out.println("current password is incorrect!");
        else if (response.equals(ProfileMenuResponses.PLEASE_ENTER_A_NEW_PASSWORD))
            System.out.println("please enter a new password!");
        else if (response.equals(ProfileMenuResponses.USER_WITH_NICKNAME_ALREADY_EXISTS))
            System.out.println("user with this nickname already exists");
        else if (response.equals(ProfileMenuResponses.PASSWORD_CHANGED_SUCCESSFULLY))
            System.out.println("password changed successfully!");
        else if (response.equals(ProfileMenuResponses.NICKNAME_CHANGED_SUCCESSFULLY))
            System.out.println("nickname changed successfully!");
        else if (response.equals(ProfileMenuResponses.INVALID_COMMAND))
            System.out.println("invalid command!");
    }

    public HashMap<String,String> getOldAndNewPassword(String command) {
        HashMap<String,String> passwords = new HashMap<>();
        Matcher matcher = ProfileMenuRegex.getRightMatcherForChangePassword(command);
        if (matcher.find()) {
            passwords.put("newPassword",matcher.group("newPassword"));
            passwords.put("currentPassword",matcher.group("currentPassword"));
            return passwords;
        }
        return null;
    }
}
