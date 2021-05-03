package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Board;
import model.User;
import model.card.Card;
import model.card.monster.Monster;
import model.card.spell_traps.Spell;
import model.card.spell_traps.Trap;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ReadAndWriteDataBase {
    public static final String usersAddr = "src/resources/Users/";

    public static User getUser(String usersAddr) {
        File file = getUserFileByUserAddr(usersAddr);
        ObjectMapper mapper = new ObjectMapper();
        User user;
        try {
            user = mapper.readValue(file,User.class);
        } catch (IOException e) {
            return null;
        }
        return user;
    }


    public static void writeUserToUsersDirectory(User user) {
        try {
            FileWriter fileWriter = new FileWriter(usersAddr + user.getUsername() + ".json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(fileWriter, user);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR 404"); // todo remove println in controller : mir
        }
    }

    public static File getUserFileByUserAddr(String userAddr) {
        return new File(usersAddr + userAddr);
    }

    public static ArrayList<User> getAllUsers() {
        String[] userAddrs;
        ArrayList<User> users = new ArrayList<>();
        File usersDirectory = new File(usersAddr);
        userAddrs = usersDirectory.list();
        if (userAddrs == null) return users;
        for (String userAddr : userAddrs) {
            users.add(ReadAndWriteDataBase.getUser(userAddr));
        }
        return users;
    }

    public static void updateUser(User user) {
        ReadAndWriteDataBase.writeUserToUsersDirectory(user);
    }



}
