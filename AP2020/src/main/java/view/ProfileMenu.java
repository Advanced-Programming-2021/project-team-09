package view;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private final Scanner scanner =new Scanner(System.in);
    private static ProfileMenu profileMenu = new ProfileMenu();
    private Matcher matcher;
    private ProfileMenu(){

    }
    public static ProfileMenu getProfileMenu(){
        return profileMenu;
    }

    public void changePassword(String command){

    }
    public void changeNickname(String command){

    }
    public void run(){
        while (true){
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("menu exit")) return;
            if(ProfileMenuRegex.getCommandMatcher(command, ProfileMenuRegex.changeNicknameRegex).matches())
                changeNickname(command);
            if(ProfileMenuRegex.getCommandMatcher(command, ProfileMenuRegex.changeNicknameRegexShort).matches())
                changeNickname(command);
            if(ProfileMenuRegex.getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType1).matches())
                changePassword(command);
            if(ProfileMenuRegex.getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType1Short).matches())
                changePassword(command);
            if (ProfileMenuRegex.getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType2).matches())
                changePassword(command);
            if(ProfileMenuRegex.getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType2Short).matches())
                changePassword(command);
        }
    }
    public void response(){

    }
    enum Responses{
        NICKNAME_CHANGED_SUCCESSFULLY,
        USER_WITH_NICKNAME_ALREADY_EXISTS,
        PASSWORD_CHANGED_SUCCESSFULLY,
        CURRENT_PASSWORD_IS_INVALID,
        PLEASE_ENTER_A_NEW_PASSWORD
    }
}
