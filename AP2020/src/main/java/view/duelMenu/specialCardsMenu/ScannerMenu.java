package view.duelMenu.specialCardsMenu;

import model.card.Card;
import view.LoginMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class ScannerMenu {
    public static Card run(ArrayList<Card> cards) {
        String command;
        Scanner scanner = LoginMenu.getInstance().getScanner();
        while (true) {
            System.out.println("Please choose one of the following cards .. please type the number of the card");
            for (int i = 0; i < cards.size(); i++) {
                System.out.println((i + 1) + " : " + cards.get(i).getCardName());
            }
            command = scanner.nextLine().trim();
            if (command.matches("^[\\d]{1,2}$")) {
                int temp =  Integer.parseInt(command);
                if (!(temp == 0 || temp > cards.size())) {
                    return cards.get(temp - 1);
                }
            }
            System.out.println("Invalid Command");
        }
    }

    public static void pleaseSelectMonster() {
        System.out.println("Selected Card is not monster.. please try again ..");
    }
}
