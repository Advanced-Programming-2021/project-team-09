package view;

import controller.ShopController;
import view.regexes.RegexFunctions;
import view.regexes.ShopMenuRegexes;
import view.responses.ShopMenuResponses;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private static ShopMenu shopMenu;
    private final Scanner scanner;

    private ShopMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public static ShopMenu getInstance(Scanner scanner) {
        if (shopMenu == null)  shopMenu = new ShopMenu(scanner);
        return shopMenu;
    }

    public void run(){
        String command;
        while (true){
            command = scanner.nextLine().trim().toLowerCase();
            if (ShopMenuRegexes.isItShowAllCards(command))
                showAllCards();
            else if (command.matches(ShopMenuRegexes.shopCardRegex))
                buyCard(command);
            else respond(ShopMenuResponses.INVALID_COMMAND);

        }
    }

    private void respond(ShopMenuResponses response) {
        if (response.equals(ShopMenuResponses.INVALID_CARD_NAME))
            System.out.println("invalid card name!");
        else if (response.equals(ShopMenuResponses.USER_HAS_NOT_ENOUGH_BALANCE))
            System.out.println("user has not enough balance to buy this card");
        else if (response.equals(ShopMenuResponses.SUCCESSFUL))
            System.out.println("card had been bought successfully!");
        else if (response.equals(ShopMenuResponses.INVALID_COMMAND))
            System.out.println("invalid command!");
    }

    private void buyCard (String command) {
        Matcher matcher = RegexFunctions.getCommandMatcher(command, ShopMenuRegexes.shopCardRegex);
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            ShopMenuResponses response = ShopController.BuyCard(cardName);
            respond(response);
        }
    }

    private void showAllCards () {
        String allCardsInfo = ShopController.showAllCards();
        System.out.println(allCardsInfo);
    }
}
