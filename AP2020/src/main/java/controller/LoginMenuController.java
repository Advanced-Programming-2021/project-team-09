package controller;

import model.User;
import view.responses.LoginMenuResponses;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginMenuController {
    static User currentUser;

    public static boolean doesUsernameExists(String username) {
        FileReader fileReader = ReadAndWriteDataBase.getUserFileByUserAddr(username + ".json");
        if (fileReader != null) {
            try {
                fileReader.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else return false;

    }

    public static boolean doesNicknameExists(String nickname) {
        ArrayList<User> users = ReadAndWriteDataBase.getAllUsers();
        for (User user : users) {
            if (user.getNickname().equals(nickname)) return true;
        }
        return false;
    }

    public static boolean isPasswordCorrect(String username, String password) {
        if (doesUsernameExists(username)){
            User user = ReadAndWriteDataBase.getUser(username + ".json");
            return user.getPassword().equals(password);
        } else return false;
    }


    public static LoginMenuResponses createUser(String username , String nickname, String password) {
        if (doesUsernameExists(username)) return LoginMenuResponses.USER_WITH_THIS_USERNAME_EXITS;
        else if (doesNicknameExists(nickname)) return LoginMenuResponses.USER_WITH_THIS_NICKNAME_EXITS;
        else {
            User user = new User(username,password,nickname);
            ReadAndWriteDataBase.writeUserToUsersDirectory(user);
            return LoginMenuResponses.USER_CREATED_SUCCESSFULLY;
        }
    }

    public static LoginMenuResponses login(String username , String password){
        if (doesUsernameExists(username)) {
            if (isPasswordCorrect(username, password)) {
                setCurrentUser(username);
                return LoginMenuResponses.USER_LOGIN_SUCCESSFUL;
            } else return LoginMenuResponses.PASSWORD_AND_USERNAME_DIDNT_MATCH;
        } else return LoginMenuResponses.THERE_IS_NOT_A_USER_WITH_THIS_USERNAME;
    }




    public static void logout(){
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String username){
        currentUser = ReadAndWriteDataBase.getUser(username+".json");
    }
    public CurrentMenu showCurrentMenu(){
        return CurrentMenu.LOGIN_MENU;
    }
}
