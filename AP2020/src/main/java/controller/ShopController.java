package controller;

import controller.database.ReadAndWriteDataBase;
import controller.database.csvInfoGetter;
import de.vandermeer.asciitable.AsciiTable;
import model.User;
import model.card.Card;
import view.responses.ShopMenuResponses;

import java.util.ArrayList;

public class ShopController {

    public static String showAllCards() {
        ArrayList<String> cardNames = csvInfoGetter.getCardNames();
        ArrayList<Card> cards = new ArrayList<>();
        for (String cardName : cardNames)
            cards.add(csvInfoGetter.getCardByName(cardName));
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("No.","Name","Description","Price");
        int counter = 1;
        for (Card card : cards) {
            asciiTable.addRow(counter,
                    card.getCardName(),
                    card.getDescription(),
                    csvInfoGetter.getPriceByCardName(card.getCardName()));
            asciiTable.addRule();
            counter++;
        }
        return asciiTable.render();
    }

    public static ShopMenuResponses BuyCard(String cardName) {
        User user = LoginMenuController.getCurrentUser();
        if (csvInfoGetter.cardNameExists(cardName)) {
            if (user.hasEnoughBalance(csvInfoGetter.getPriceByCardName(cardName))) {
                user.addCard(csvInfoGetter.getCardByName(cardName));
                user.decreaseBalance(csvInfoGetter.getPriceByCardName(cardName));
                ReadAndWriteDataBase.updateUser(user);
                return ShopMenuResponses.SUCCESSFUL;
            } else return ShopMenuResponses.USER_HAS_NOT_ENOUGH_BALANCE;
        } else return ShopMenuResponses.INVALID_CARD_NAME;
    }

/*    private static ArrayList<Card> sortCards (ArrayList<Card> cards){
        //ToDo
        return null;
    }*/

}
