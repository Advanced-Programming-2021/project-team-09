package controller;

import controller.database.ReadAndWriteDataBase;
import de.vandermeer.asciitable.AsciiTable;
import model.User;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {

    private static ArrayList<User> sortUsers() {
        ArrayList<User> users = ReadAndWriteDataBase.getAllUsers();
        int numberOfUsers = users.size();
        for (int i = 0; i < numberOfUsers - 1; i++) {
            for (int j = 0; j < numberOfUsers - i - 1; j++) {
                if (users.get(j).getScore() > users.get(j + 1).getScore()) {
                    Collections.swap(users, j, j + 1);
                }
                if (users.get(j).getScore() == users.get(j + 1).getScore()) {
                    if (users.get(j).getNickname().compareToIgnoreCase(users.get(j + 1).getNickname()) > 0) {
                        Collections.swap(users, j, j + 1);
                    }
                }
            }
        }

        return users;
    }

    public static String getScoreBoard() {
        //todo those who have same score have same rank, you are not handling it!
        ArrayList<User> users = sortUsers();
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Rank", "Username", "Nickname", "Score");
        asciiTable.addRule();
        int i = 1;
        for (User user : users) {
            asciiTable.addRow(i,
                    user.getUsername(),
                    user.getNickname(),
                    user.getScore());
            asciiTable.addRule();
            ++i;
        }
        return asciiTable.render();
    }
}