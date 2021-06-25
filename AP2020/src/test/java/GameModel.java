import com.google.gson.Gson;
import controller.database.CSVInfoGetter;
import controller.database.ReadAndWriteDataBase;
import model.User;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.exceptions.WinnerException;
import model.game.Board;
import model.game.Cell;
import model.game.Game;
import model.game.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

public class GameModel {
    private static Card card[] = new Card[13];
    private static Board board;
    private User mmd = ReadAndWriteDataBase.getUser("mmd.json");
    private User mir = ReadAndWriteDataBase.getUser("mir.json");
    private Game game;

    {
        try {
            game = new Game(mmd, mir);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach
    private void init() {
        for (int i = 0; i < 5; i++) card[i] = CSVInfoGetter.getCardByName("Battle OX");
        for (int i = 5; i < 10; i++) card[i] = CSVInfoGetter.getCardByName("Mind Crush");
        card[10] = CSVInfoGetter.getCardByName("Command Knight");
        card[11] = CSVInfoGetter.getCardByName("Closed Forest");
        card[12] = CSVInfoGetter.getCardByName("Closed Forest");
        board = new Board();
        board.addCardToSpellZone(card[5]);
        board.addCardToSpellZone(card[6]);
        board.addCardToSpellZone(card[7]);
        board.addCardToSpellZone(card[8]);
        board.addCardToSpellZone(card[9]);

        board.addCardToMonsterZone(card[0]);
        board.addCardToMonsterZone(card[1]);
        board.addCardToMonsterZone(card[2]);
        board.addCardToMonsterZone(card[3]);
        board.addCardToMonsterZone(card[4]);
    }

    @Test
    @Order(1)
    public void boardTest() {
        int[] cells = {1, 2, 3, 4, 5};
        Assertions.assertNotNull(card[5]);
        Assertions.assertNotNull(card[6]);
        Assertions.assertNotNull(card[7]);
        Assertions.assertNotNull(card[8]);
        Assertions.assertNotNull(card[9]);

        board.addCardToFieldZone(card[11]);
        Assertions.assertEquals(card[11], board.getFieldZone().getCard());
        board.addCardToFieldZone(card[12]);
        Assertions.assertEquals(card[12], board.getFieldZone().getCard());
        Assertions.assertTrue(board.getGraveyard().getCards().contains(card[11]));


        Assertions.assertEquals(5, board.getNumberOfMonstersInMonsterZone());
        Assertions.assertTrue(board.isMonsterZoneFull());
        Assertions.assertTrue(board.isSpellZoneFull());
        Assertions.assertEquals(board.getMonsterZone(0).getCard(), card[0]);
        Assertions.assertEquals(board.getSpellZone(0).getCard(), card[5]);
        Assertions.assertEquals(((Monster) card[0]).getLevel() * 5, board.getSumLevel(cells));

    }

    @Test
    @Order(2)
    public void cloneTest() {
        Board board1 = board.clone();

        Assertions.assertEquals(board.getSpellZone(0).getCard().getCardName(), board1.getSpellZone(0).getCard().getCardName());
        Assertions.assertEquals(board.getSpellZone(1).getCard().getCardName(), board1.getSpellZone(1).getCard().getCardName());
        Assertions.assertEquals(board.getSpellZone(2).getCard().getCardName(), board1.getSpellZone(2).getCard().getCardName());
        Assertions.assertEquals(board.getSpellZone(3).getCard().getCardName(), board1.getSpellZone(3).getCard().getCardName());
        Assertions.assertEquals(board.getSpellZone(4).getCard().getCardName(), board1.getSpellZone(4).getCard().getCardName());
        Assertions.assertEquals(board.getMonsterZone(4).getCard().getCardName(), board1.getMonsterZone(4).getCard().getCardName());
        Assertions.assertEquals(board.getMonsterZone(3).getCard().getCardName(), board1.getMonsterZone(3).getCard().getCardName());
        Assertions.assertEquals(board.getMonsterZone(2).getCard().getCardName(), board1.getMonsterZone(2).getCard().getCardName());
        Assertions.assertEquals(board.getMonsterZone(1).getCard().getCardName(), board1.getMonsterZone(1).getCard().getCardName());
        Assertions.assertEquals(board.getMonsterZone(0).getCard().getCardName(), board1.getMonsterZone(0).getCard().getCardName());
    }

    @Test
    @Order(3)
    public void changeTurnTest() {
        game.changeTurn();
        Assertions.assertEquals(game.getRival(), mmd);
        Assertions.assertEquals(game.getPlayer(), mir);
        Assertions.assertDoesNotThrow(() -> {
            Cell[][] cells = new Cell[2][];
            cells[0] = game.getPlayerBoard().getMonsterZone();
            cells[1] = game.getRivalBoard().getMonsterZone();
            for (int i = 0; i < 2; i++) {
                for (Cell cell : cells[i]) {
                    if (cell.isOccupied()) {
                        ArrayList<CardFeatures> features = cell.getCard().getFeatures();
                        if (features.contains(CardFeatures.ONE_EFFECT_PER_ROUND) && features.contains(CardFeatures.USED_EFFECT))
                            throw new Exception();
                        if (!cell.canAttack() && cell.isChangedPosition()) throw new Exception();
                    }
                }
            }
        });
    }

    @Test
    @Order(4)
    public void drawCardTest() {
        Card card = game.getPlayerDeck().getMainDeck().getCards().get(0);
        game.playerDrawCard();
        Assertions.assertTrue(game.getPlayerHandCards().contains(card));
        Assertions.assertFalse(game.getPlayerDeck().getMainDeck().getCards().contains(card));
        card = game.getRivalDeck().getMainDeck().getCards().get(0);
        game.rivalDrawCard();
        Assertions.assertTrue(game.getRivalHandCards().contains(card));
        Assertions.assertFalse(game.getRivalDeck().getMainDeck().getCards().contains(card));
    }

    @Test
    @Order(5)
    public void changeOfHealthTest() {
        Game testGame;
        try {
            testGame = new Game(mir, mmd);
        } catch (CloneNotSupportedException e) {
            testGame = null;
            Assertions.fail();
        }
        testGame.increaseHealth(500);
        testGame.increaseRivalHealth(500);
        Assertions.assertEquals(8500,testGame.getPlayerLP());
        Assertions.assertEquals(8500,testGame.getPlayerLP());
        Game finalTestGame = testGame;
        Assertions.assertThrows(WinnerException.class, () -> {
            for (int i = 0; i < 20; i++) {
                finalTestGame.decreaseHealth(500);
            }
        });
        Assertions.assertThrows(WinnerException.class, () -> {
            for (int i = 0; i < 20; i++) {
                finalTestGame.decreaseRivalHealth(500);
            }
        });
    }
    @Test
    @Order(6)
    public void directAttackTest() {
        Monster monster = (Monster) CSVInfoGetter.getCardByName("Battle OX");
        game.getPlayerBoard().getMonsterZone(0).addCard(monster);
        game.getPlayerBoard().getMonsterZone(0).setState(State.FACE_UP_ATTACK);
        game.setCanSummonCard(false);
        Executable executable = () -> game.directAttack(0);
        Assertions.assertDoesNotThrow(executable);
        Assertions.assertFalse(game.canSummon());
        Assertions.assertEquals(8000 - monster.getAttack(),game.getRivalLP());
    }
}