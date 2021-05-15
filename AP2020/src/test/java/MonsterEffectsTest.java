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

import java.io.*;
import java.util.HashSet;

public class MonsterEffectsTest {
    static User user1, user2;
    static Game game;
    static PrintStream defaultStream = System.out;
    static InputStream defaultInputStream = System.in;

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
    public void testManEaterBug() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Card card = CSVInfoGetter.getCardByName("Man-Eater Bug");
        Card card1 = CSVInfoGetter.getCardByName("Battle OX");
        Card card2 = CSVInfoGetter.getCardByName("Raigeki");
        Assertions.assertNotNull(card);
        Assertions.assertNotNull(card1);
        Assertions.assertNotNull(card2);
        game.getPlayerBoard().getMonsterZone(1).addCard(card);
        game.getRivalBoard().getSpellZone(1).addCard(card2);
        Executable executable = () -> MonsterEffectController.ManEaterBug(game, card);

        {
            String command = "2\n66\n3";
            setCommandInInputStream(command);
            PrintStream printStream = new PrintStream(stream);
            System.setOut(printStream);
        }
        {
            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("You have no monsters !", stream.toString().trim());
            stream.reset();
        }
        {
            game.getRivalBoard().getMonsterZone(2).addCard(card1);
            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("Please enter 1 cell numbers .. \n" +
                            "Invalid Cell number !\n" +
                            "Please enter 1 cell numbers .. \n" +
                            "Invalid Command .. \n" +
                            "Please enter 1 cell numbers ..",
                    stream.toString().trim());
        }
        Assertions.assertNull(game.getRivalBoard().getMonsterZone(2).getCard());
        Assertions.assertTrue(game.getRivalBoard().getGraveyard().getCards().contains(card1));
        System.setOut(defaultStream);
        System.setIn(defaultInputStream);
    }

    @Test
    public void testGateGuardian() {
        ByteArrayOutputStream stream = getOutPutStream();

        Card card = CSVInfoGetter.getCardByName("Gate Guardian");
        Card card1 = CSVInfoGetter.getCardByName("Battle OX");
        Card card2 = CSVInfoGetter.getCardByName("Battle OX");
        Card card3 = CSVInfoGetter.getCardByName("Battle OX");
        Executable executable = () -> MonsterEffectController.GateGuardian(game, card);
        {
            Assertions.assertNotNull(card);
            Assertions.assertNotNull(card1);
            Assertions.assertNotNull(card2);
            Assertions.assertNotNull(card3);
        }
        {
            game.getPlayerBoard().getMonsterZone(0).addCard(card1);
            game.getPlayerHandCards().add(card);

            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("You have no monsters !", stream.toString().trim());
            stream.reset();
        }
        {
            setCommandInInputStream("1 2 2\n1 2\n3 4 5\n1 2 3");
            game.getPlayerBoard().getMonsterZone(1).addCard(card2);
            game.getPlayerBoard().getMonsterZone(2).addCard(card3);
            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("Please select 3 cards that you want to tribute.\n" +
                    "Card number must be between 1 and 5 and please select them in this format :1 2 3 ..\n" +
                    "Invalid Cell number !\n" +
                    "Please select 3 cards that you want to tribute.\n" +
                    "Card number must be between 1 and 5 and please select them in this format :1 2 3 ..\n" +
                    "Invalid Command\n" +
                    "Invalid Cell number !\n" +
                    "Please select 3 cards that you want to tribute.\n" +
                    "Card number must be between 1 and 5 and please select them in this format :1 2 3 ..",stream.toString().trim());
            Assertions.assertNotNull(game.getPlayerBoard().getMonsterZone(0).getCard());
            Assertions.assertNull(game.getPlayerBoard().getMonsterZone(1).getCard());
            Assertions.assertNull(game.getPlayerBoard().getMonsterZone(2).getCard());
            Assertions.assertFalse(game.getPlayerHandCards().contains(card));
            Assertions.assertTrue(game.getPlayerBoard().getGraveyard().getCards().contains(card1));
            Assertions.assertTrue(game.getPlayerBoard().getGraveyard().getCards().contains(card2));
            Assertions.assertTrue(game.getPlayerBoard().getGraveyard().getCards().contains(card3));
            Assertions.assertNotNull(game.getPlayerBoard().getMonsterZoneCellByCard(card));
        }



    }

    @Test
    public void testHeraldofCreation() {
        ByteArrayOutputStream stream = getOutPutStream();
        Card card = CSVInfoGetter.getCardByName("Herald of Creation");
        Card card1 = CSVInfoGetter.getCardByName("Gate Guardian");
        Card card2 = CSVInfoGetter.getCardByName("Battle OX");
        Card card3 = CSVInfoGetter.getCardByName("Battle OX");
        game.getPlayerBoard().addCardToMonsterZone(card);
        Executable executable = () -> MonsterEffectController.HeraldofCreation(game,card);

        {
            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("You have no cards !", stream.toString().trim());
            stream.reset();
        }

        {
            game.getPlayerHandCards().add(card2);
            game.getPlayerBoard().getGraveyard().getCards().add(card1);
            game.getPlayerBoard().getGraveyard().getCards().add(card3);
            setCommandInInputStream("2\n1\n2\n1");
            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("please select a card number from your hand\n" +
                    "Please enter a valid number ..\n" +
                    "Please select a card from this item(s) :\n" +
                    "1 : Gate Guardian\n" +
                    "2 : Battle OX\n" +
                    "Please select level 7 or more monster !\n" +
                    "Please select a card from this item(s) :\n" +
                    "1 : Gate Guardian\n" +
                    "2 : Battle OX",stream.toString().trim());

            Assertions.assertTrue(game.getPlayerHandCards().contains(card1));
            Assertions.assertFalse(game.getPlayerHandCards().contains(card2));
            Assertions.assertFalse(game.getPlayerBoard().getGraveyard().getCards().contains(card1));
            Assertions.assertTrue(game.getPlayerBoard().getGraveyard().getCards().contains(card2));
            Assertions.assertTrue(game.getPlayerBoard().getGraveyard().getCards().contains(card3));

        }

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
        Executable executable = () -> MonsterEffectController.MirageDragon(game, card);
        Assertions.assertDoesNotThrow(executable);
        Assertions.assertNotNull(game.getPlayerLimits().getLimitations());
        Assertions.assertTrue(game.getPlayerLimits().getLimitations().contains(EffectLimitations.CANT_ACTIVATE_TRAP));
    }

    private void testTheTricky() {
        ByteArrayOutputStream stream = getOutPutStream();
        Card card = CSVInfoGetter.getCardByName("The Tricky");
        Card card1 = CSVInfoGetter.getCardByName("Battle OX");
        Card card2 = CSVInfoGetter.getCardByName("Battle OX");
        Card card3 = CSVInfoGetter.getCardByName("Battle OX");
        Card card4 = CSVInfoGetter.getCardByName("Battle OX");
        Card card5 = CSVInfoGetter.getCardByName("Battle OX");
        Card card6 = CSVInfoGetter.getCardByName("Battle OX");
        Executable executable = () -> MonsterEffectController.TheTricky(game,card);
    }


    private ByteArrayOutputStream getOutPutStream() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(stream);
        System.setOut(printStream);
        return stream;
    }

    private void setCommandInInputStream(String command1) {
        ByteArrayInputStream stream1 = new ByteArrayInputStream(command1.getBytes());
        System.setIn(stream1);
    }


}
