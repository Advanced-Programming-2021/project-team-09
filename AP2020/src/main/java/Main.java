import controller.database.CSVInfoGetter;
import controller.database.ReadAndWriteDataBase;
import model.User;
import model.card.Card;
import model.deck.Deck;
import model.game.Game;
import view.LoginMenu;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        LoginMenu.getInstance().run();
    }
}