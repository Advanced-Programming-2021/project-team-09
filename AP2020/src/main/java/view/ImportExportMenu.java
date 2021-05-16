package view;

import controller.LoginMenuController;
import controller.database.ReadAndWriteDataBase;

import java.util.Scanner;

public class ImportExportMenu {
    public static void run(){
        String command;
        Scanner scanner = LoginMenu.getInstance().getScanner();
        System.out.println("Here you can update your account to save your changes ..");
        while (true) {
            command = scanner.nextLine().trim();
            if (command.matches("^help$")) showHelp();
            else if (command.matches("^menu exit$")) {
                System.out.println("Entering main menu");
                return;
            } else if (command.matches("^update$")) {
                ReadAndWriteDataBase.updateUser(LoginMenuController.getCurrentUser());
                System.out.println("User updated ..");
            } else if (command.matches("^show menu-current$")) System.out.println("Import/Export menu ..");
            else System.out.println("Invalid command ..");
        }
    }

    public static void showHelp() {
        System.out.println("update\nshow menu-current\nmenu exit\n");
    }
}
