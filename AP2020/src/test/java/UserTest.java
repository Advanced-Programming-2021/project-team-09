import controller.database.CSVInfoGetter;
import model.User;
import model.card.Card;
import model.card.monster.Monster;
import org.junit.jupiter.api.*;

public class UserTest extends TestEssentials {
    private static User user;

    @BeforeEach
    public void init() {
        user = new User("sia","sia","sia");
    }
    @Test
    @Order(1)
    public void userTest () {
        Card monster = CSVInfoGetter.getCardByName("Battle OX");
        user.addCard(monster);
        Assertions.assertTrue(user.getCards().contains(monster));
        Assertions.assertNotNull(user.getCardByName("Battle OX"));
        user.removeCard(monster.getCardName());
        Assertions.assertFalse(user.getCards().contains(monster));
        user.increaseBalance(500);
        Assertions.assertEquals(500,user.getBalance());
        Assertions.assertTrue(user.hasEnoughBalance(500));
        Assertions.assertFalse(user.hasEnoughBalance(5000));
        user.decreaseBalance(500);
        Assertions.assertEquals(0,user.getBalance());
        user.changeNickname("ali");
        Assertions.assertEquals("ali",user.getNickname());
        user.changePassword("1234");
        Assertions.assertEquals("1234",user.getPassword());
        Assertions.assertEquals("sia",user.getUsername());
    }
    @Test
    @Order(2)
    public void userDeckTests() {
        Card monster = CSVInfoGetter.getCardByName("Battle OX");
        Card monster1 = CSVInfoGetter.getCardByName("Battle OX");
        Assertions.assertNotNull(monster);
        Assertions.assertNotNull(monster1);

        user.createDeck("ali");
        Assertions.assertNotNull(user.getDeckByName("ali"));

        user.addCardToMainDeck(monster,"ali");
        Assertions.assertTrue(user.getDeckByName("ali").getMainDeck().getCards().contains(monster));

        user.addCardToSideDeck(monster1,"ali");
        Assertions.assertTrue(user.getDeckByName("ali").getSideDeck().getCards().contains(monster1));

        user.removeCardFromMainDeck(monster.getCardName(),"ali");
        user.removeCardFromSideDeck(monster1.getCardName(),"ali");
        Assertions.assertFalse(user.getDeckByName("ali").getMainDeck().getCards().contains(monster));
        Assertions.assertFalse(user.getDeckByName("ali").getSideDeck().getCards().contains(monster1));

        user.activeDeck("ali");
        Assertions.assertNotNull(user.getActiveDeck());

        user.removeDeck("ali");
        Assertions.assertNull(user.getDeckByName("ali"));
    }
}
