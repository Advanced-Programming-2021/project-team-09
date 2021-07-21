package controller;

import controller.database.ReadAndWriteDataBase;
import model.User;
import view.responses.LoginMenuResponses;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginMenuController {
    static User currentUser;

    public static boolean doesUsernameExists(String username) {
        try {
            ObjectOutputStream objectOutputStream = SocketWrapper.objectOutputStream;
            objectOutputStream.writeObject("login~doesUsernameExists~" + username);
            String string = (String) SocketWrapper.objectInputStream.readObject();
            return string.equals("true");
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }

    }

    public static boolean doesNicknameExists(String nickname) {
        try {
            ObjectOutputStream objectOutputStream = SocketWrapper.objectOutputStream;
            objectOutputStream.writeObject("login~doesNicknameExists~" + nickname);
            String string = (String) SocketWrapper.objectInputStream.readObject();
            return string.equals("true");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPasswordCorrect(String username, String password) {
        try {
            ObjectOutputStream objectOutputStream = SocketWrapper.objectOutputStream;
            objectOutputStream.writeObject("login~isPasswordCorrect~" + username + "~" + password);
            String string = (String) SocketWrapper.objectInputStream.readObject();
            return string.equals("true");
        } catch (Exception e) {
            return false;
        }
    }


    public static LoginMenuResponses createUser(String username, String nickname, String password) {
        try {
            ObjectOutputStream objectOutputStream = SocketWrapper.objectOutputStream;
            objectOutputStream.writeObject("login~createUser~" + username + "~" + nickname + "~" + password);
            return (LoginMenuResponses)SocketWrapper.objectInputStream.readObject();
        } catch (Exception e) {
            return LoginMenuResponses.INVALID_COMMAND;
        }
    }

    public static LoginMenuResponses login(String username, String password) {
        try {
            ObjectOutputStream objectOutputStream = SocketWrapper.objectOutputStream;
            objectOutputStream.writeObject("login~login~" + username + "~" + password);
            User user = (User) SocketWrapper.objectInputStream.readObject();
            setCurrentUser(user);
            return (LoginMenuResponses)SocketWrapper.objectInputStream.readObject();
        } catch (Exception e) {
            return LoginMenuResponses.INVALID_COMMAND;
        }
    }


    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String username) {
        currentUser = ReadAndWriteDataBase.getUser(username + ".json");
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
