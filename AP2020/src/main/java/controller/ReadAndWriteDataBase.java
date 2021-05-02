package controller;

import com.google.gson.Gson;
import model.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;

public class ReadAndWriteDataBase {
    public static final String usersAddr = "src/resources/Users/";

    public static User getUser(String usersAddr) {
        FileReader fileReader = getUserFileByUserAddr(usersAddr);
        if (fileReader != null) {
            ObjectMapper mapper = new ObjectMapper();
            User user;
            try {
                user = mapper.readValue(fileReader,User.class);
                fileReader.close();
            } catch (IOException e) {
                return null;
            }
            return user;
        } else return null;
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
