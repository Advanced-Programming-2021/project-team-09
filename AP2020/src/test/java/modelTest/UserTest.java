package modelTest;

import controller.database.CSVInfoGetter;
import model.User;
import model.card.Card;
import model.card.monster.Monster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static User user;
    @BeforeAll
    static void init() {
        user = new User("sia","sia","sia");
    }
    @Test
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
    public void userDeckTests() {
        user.createDeck("ali");
        Assertions.assertNotNull(user.getDeckByName("ali"));
        user.activeDeck("ali");
        Assertions.assertNotNull(user.getActiveDeck());
        user.removeDeck("ali");
        Assertions.assertNull(user.getDeckByName("ali"));
    }
}
