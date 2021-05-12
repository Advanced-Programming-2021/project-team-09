import controller.DeckMenuController;
import controller.EffectController.MonsterEffectController;
import controller.GameMenuController;
import controller.LoginMenuController;
import controller.database.*;
import model.User;
import model.card.Card;
import model.game.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashSet;

public class MonsterEffectsTest {
    static User user1,user2;
    static Game game ;

    @BeforeEach
    public void createTestArea() {
        user1 = ReadAndWriteDataBase.getUser("sia.json");
        user2 = ReadAndWriteDataBase.getUser("ali.json");
        LoginMenuController.login("sia","1234");
        Assertions.assertNotNull(LoginMenuController.getCurrentUser());
        Assertions.assertNotNull(user1);
        Assertions.assertNotNull(user2);
        Assertions.assertNotNull(user1.getActiveDeck().getMainDeck());
        Assertions.assertNotNull(user1.getActiveDeck().getMainDeck().getCards());
        Assertions.assertNotNull(user1.getActiveDeck().getMainDeck().getCards().get(5));
        try {
            game = new Game(user1,user2);
        } catch (CloneNotSupportedException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testCommandKnight() {
        Card card = csvInfoGetter.getCardByName("Command Knight");
        HashSet<Integer> bannedCell = new HashSet<>();
        bannedCell.add(1);
        Assertions.assertNotNull(card);
        Assertions.assertNotNull(game.getPlayerBoard());
        Assertions.assertNotNull(game.getPlayerBoard().getMonsterZone(1));
        game.getPlayerBoard().getMonsterZone(1).addCard(card);
        MonsterEffectController.CommandKnight(game, card);
        Assertions.assertNotNull(game.getPlayerLimits());
        Assertions.assertNotNull(game.getRivalLimits().getCantAttackCells());
        Assertions.assertArrayEquals(game.getRivalLimits().getCantAttackCells().toArray(),bannedCell.toArray());

    }
    @Test
    public void testMarshmallon(){
        Card card = csvInfoGetter.getCardByName("Marshmallon");
        Assertions.assertNotNull(card);
        Assertions.assertNotNull(game.getPlayerBoard());
        Assertions.assertNotNull(game.getPlayerBoard().getMonsterZone(1));
        game.getPlayerBoard().getMonsterZone(1).addCard(card);

    }
}
