import controller.database.ReadAndWriteDataBase;
import model.User;
import model.card.Card;
import model.deck.Deck;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        User user = ReadAndWriteDataBase.getAllUsers().get(2);
        ArrayList<Deck> decks = user.getDecks();
        Deck temp = decks.get(0);
        for(Card card : temp.getMainDeck().getCards()) {
            System.out.println(card.getCardName());
        }
        System.out.println("___________________________");
        temp.getMainDeck().shuffle();
        for (Card card : temp.getMainDeck().getCards()) {
            System.out.println(card.getCardName());
        }
    }
}