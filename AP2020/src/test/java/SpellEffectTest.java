import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controller.EffectController.SpellEffectController;
import controller.database.CSVInfoGetter;
import model.card.Card;
import model.exceptions.StopSpell;
import model.exceptions.WinnerException;
import model.game.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SpellEffectTest extends TestEssentials {
    @Test
    public void testMagicJammer() {
        ByteArrayOutputStream stream = getOutPutStream();
        ArrayList<Card> cards = game.getRivalHandCards();
        Board board = game.getRivalBoard();
        Card card1 = CSVInfoGetter.getCardByName("Battle OX");
        Card card = CSVInfoGetter.getCardByName("Magic Jamamer");
        Assertions.assertNotNull(card);
        Executable executable = () -> SpellEffectController.MagicJammer(game, card);
        board.addCardToSpellZone(card);
        {
            Assertions.assertDoesNotThrow(executable);
            Assertions.assertEquals("You have no cards !", stream.toString().trim());
        }
        {
            setCommandInInputStream("1");
            cards.add(card1);
            Assertions.assertThrows(StopSpell.class, executable);
            Assertions.assertFalse(cards.contains(card1));
            Assertions.assertTrue(board.getGraveyard().getCards().contains(card1));
        }
    }

    @Test
    public void testSolemanWarning() {
        Board board = game.getRivalBoard();
        Card card = CSVInfoGetter.getCardByName("Solemn Warning");
        Assertions.assertNotNull(card);
        Executable executable = () -> SpellEffectController.SolemnWarning(game, card);
        board.addCardToSpellZone(card);

        {
            Assertions.assertThrows(StopSpell.class, executable);
            Assertions.assertEquals(6000, game.getRivalLP());
        }
        {
            Assertions.assertThrows(StopSpell.class, executable);
            Assertions.assertEquals(4000, game.getRivalLP());
        }
        {
            Assertions.assertThrows(StopSpell.class, executable);
            Assertions.assertEquals(2000, game.getRivalLP());
        }
        {
            Assertions.assertThrows(WinnerException.class, executable);
            Assertions.assertEquals(0, game.getRivalLP());

        }
    }

    @Test
    public void testCalloftheHaunted() {
        Board board = game.getRivalBoard();
        Card card = CSVInfoGetter.getCardByName("Call of the Haunted");
        Card card1 = CSVInfoGetter.getCardByName("Battle OX");
        Card card2 = CSVInfoGetter.getCardByName("Battle OX");
        Assertions.assertNotNull(card);
        Executable executable = () -> SpellEffectController.SolemnWarning(game, card);
        board.addCardToSpellZone(card);
        
    }
}