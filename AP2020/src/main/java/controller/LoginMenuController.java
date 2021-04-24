package controller;

import model.User;

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


    public static String createUser(String username , String nickname, String password) {
        if (doesUsernameExists(username)) return "user with username " + username + " already Exists";
        else if (doesNicknameExists(nickname)) return "user with nickname " + nickname + " already Exists";
        else {
            User user = new User(username,password,nickname);
            ReadAndWriteDataBase.writeUserToUsersDirectory(user);
            return "user created successfully!";
        }
    }

    public static String login(String username , String password){
        if (doesUsernameExists(username)) {
            if (isPasswordCorrect(username, password)) {
                setCurrentUser(username);
                return "user logged in successfully!";
            } else return "Username and password didn't match";
        } else return "there isn't a user with this username!";
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

}
