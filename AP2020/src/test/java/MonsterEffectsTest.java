import controller.EffectController.MonsterEffectController;
import controller.LoginMenuController;
import controller.database.*;
import model.User;
import model.card.Card;
import model.card.monster.Monster;
import model.game.EffectLimitations;
import model.game.Game;
import model.game.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashSet;

public class MonsterEffectsTest {
    static User user1, user2;
    static Game game;

    @BeforeEach
    public void createTestArea() {
        user1 = ReadAndWriteDataBase.getUser("sia.json");
        user2 = ReadAndWriteDataBase.getUser("ali.json");
        LoginMenuController.login("sia", "1234");
        Assertions.assertNotNull(LoginMenuController.getCurrentUser());
        Assertions.assertNotNull(user1);
        Assertions.assertNotNull(user2);
        Assertions.assertNotNull(user1.getActiveDeck().getMainDeck());
        Assertions.assertNotNull(user1.getActiveDeck().getMainDeck().getCards());
        Assertions.assertNotNull(user1.getActiveDeck().getMainDeck().getCards().get(5));
        try {
            game = new Game(user1, user2);
        } catch (CloneNotSupportedException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testCommandKnight() {
        Card card = CSVInfoGetter.getCardByName("Command Knight");
        HashSet<Integer> bannedCell = new HashSet<>();
        bannedCell.add(1);
        Assertions.assertNotNull(card);
        Assertions.assertNotNull(game.getPlayerBoard());
        Assertions.assertNotNull(game.getPlayerBoard().getMonsterZone(1));
        game.getPlayerBoard().getMonsterZone(1).addCard(card);
        MonsterEffectController.CommandKnight(game, card);
        Assertions.assertNotNull(game.getPlayerLimits());
        Assertions.assertNotNull(game.getRivalLimits().getCantAttackCells());
        Assertions.assertArrayEquals(game.getRivalLimits().getCantAttackCells().toArray(), bannedCell.toArray());

    }

    @Test
    public void testMarshmallon() {
        Card card = CSVInfoGetter.getCardByName("Marshmallon");
        Assertions.assertNotNull(card);
        Assertions.assertNotNull(game.getPlayerBoard());
        Assertions.assertNotNull(game.getPlayerBoard().getMonsterZone(1));
        int health = game.getRivalLP();
        Executable executable = () -> MonsterEffectController.Marshmallon(game, card);
        game.getPlayerBoard().getMonsterZone(1).addCard(card);
        Assertions.assertDoesNotThrow(executable);
        Assertions.assertNotEquals(game.getRivalLP(), health);
        Assertions.assertEquals(1000, health - game.getRivalLP());
    }

    @Test
    public void testTheCalculator() {
        Card card = CSVInfoGetter.getCardByName("The Calculator");
        Card card1 = CSVInfoGetter.getCardByName("Battle OX");
        Card card2 = CSVInfoGetter.getCardByName("Battle OX");
        Card card3 = CSVInfoGetter.getCardByName("Battle OX");

        Assertions.assertNotNull(card);
        Assertions.assertNotNull(card1);
        Assertions.assertNotNull(card2);
        Assertions.assertNotNull(card3);

        game.getPlayerBoard().getMonsterZone(1).addCard(card);
        game.getPlayerBoard().getMonsterZone(1).setState(State.FACE_UP_ATTACK);
        game.getPlayerBoard().getMonsterZone(2).addCard(card1);
        game.getPlayerBoard().getMonsterZone(2).setState(State.FACE_DOWN_DEFENCE);
        game.getPlayerBoard().getMonsterZone(3).addCard(card2);
        game.getPlayerBoard().getMonsterZone(3).setState(State.FACE_UP_DEFENCE);
        game.getPlayerBoard().getMonsterZone(4).addCard(card3);
        game.getPlayerBoard().getMonsterZone(4).setState(State.FACE_UP_ATTACK);

        Executable executable = () -> MonsterEffectController.TheCalculator(game, card);
        Assertions.assertDoesNotThrow(executable);
        Monster monster = (Monster) card;
        Assertions.assertEquals(3000, monster.getAttack());
        Assertions.assertEquals(0, monster.getDefense());

        game.getPlayerBoard().getMonsterZone(2).setState(State.FACE_UP_ATTACK);

        Assertions.assertDoesNotThrow(executable);
        Assertions.assertEquals(4200, monster.getAttack());
        Assertions.assertEquals(0, monster.getDefense());

        game.getPlayerBoard().getMonsterZone(2).removeCard();
        Assertions.assertNull(game.getPlayerBoard().getMonsterZone(2).getCard());

        Assertions.assertDoesNotThrow(executable);
        Assertions.assertEquals(3000, monster.getAttack());
        Assertions.assertEquals(0, monster.getDefense());
    }

    @Test
    public void testMirageDragon() {
        Card card = CSVInfoGetter.getCardByName("Mirage Dragon");
        game.getRivalBoard().addCardToMonsterZone(card);
        game.getRivalBoard().getMonsterZoneCellByCard(card).setState(State.FACE_UP_ATTACK);
        Executable executable = () -> MonsterEffectController.MirageDragon(game,card);
        Assertions.assertDoesNotThrow(executable);
        Assertions.assertNotNull(game.getPlayerLimits().getLimitations());
        Assertions.assertTrue(game.getPlayerLimits().getLimitations().contains(EffectLimitations.CANT_ACTIVATE_TRAP));
    }
}
