package controller;

import com.google.gson.Gson;
import model.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LoginMenuController {
    static User currentUser;
    static final String usersAddr = "src/resources/Users/";

    public static boolean doesUsernameExists(String username) {
        FileReader fileReader = getUserFileByUserAddr(username + ".json");
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
        ArrayList<User> users = getAllUsers();
        for (User user : users) {
            if (user.getNickname().equals(nickname)) return true;
        }
        return false;
    }

    public static boolean isPasswordCorrect(String username, String password) {
        if (doesUsernameExists(username)){
            User user = getUser(username + ".json");
            return user.getPassword().equals(password);
        } else return false;
    }
    

    public static String createUser(String username , String nickname, String password) {
        if (doesUsernameExists(username)) return "user with username " + username + " already Exists";
        else if (doesNicknameExists(nickname)) return "user with nickname " + nickname + " already Exists";
        else {
            User user = new User(username,password,nickname);
            writeUserToUsersDirectory(user);
            return "user created successfully!";
        }
    }

    public static String login(String username , String password){
        if (doesUsernameExists(username)) {
            if (isPasswordCorrect(username, password)) {
                currentUser = getUser(username + ".json");
                return "user logged in successfully!";
            } else return "Username and password didn't match";
        } else return "there isn't a user with this username!";
    }


    public static FileReader getUserFileByUserAddr(String userAddr) {
        try {
            return new FileReader(usersAddr + userAddr);
        } catch (IOException e) {
            return null;
        }
    }

    public static ArrayList<User> getAllUsers() {
        String[] userAddrs;
        ArrayList<User> users = new ArrayList<>();
        File usersDirectory = new File(usersAddr);
        userAddrs = usersDirectory.list();
        for (String userAddr : userAddrs) {
            users.add(getUser(userAddr));
        }
        return users;
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static User getUser(String usersAddr) {
        FileReader fileReader = getUserFileByUserAddr(usersAddr);
        if (fileReader != null) {
            Gson gson = new Gson();
            User user = gson.fromJson(fileReader, User.class);
            try {
                fileReader.close();
            } catch (IOException e) {
                return null;
            }
            return user;
        } else return null;
    }


    public static void writeUserToUsersDirectory(User user){
        try {
            FileWriter fileWriter = new FileWriter(usersAddr + user.getUsername() + ".json");
            new Gson().toJson(user,fileWriter);
            fileWriter.close();
        } catch (IOException e){
            System.out.println("ERROR 404");
        }
    }

    private static void setCurrentUser(String username){
        currentUser = getUser(username+".json");
    }

}
