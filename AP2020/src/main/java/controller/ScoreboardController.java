package controller;

import controller.database.ReadAndWriteDataBase;
import de.vandermeer.asciitable.AsciiTable;
import model.User;

import java.util.ArrayList;

public class ScoreboardController {

    private static ArrayList<User> sortUsers(){
        //TODO
        ArrayList<User> users = ReadAndWriteDataBase.getAllUsers();
        return users;
    }

    public static String getScoreBoard(){
        ArrayList<User> users = sortUsers();
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Rank","Username","Nickname","Score");
        asciiTable.addRule();
        int i = 1;
        for (User user : users){
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
