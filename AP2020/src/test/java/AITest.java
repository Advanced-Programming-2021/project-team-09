import controller.AI;
import controller.database.ReadAndWriteDataBase;
import model.User;
import model.exceptions.WinnerException;
import model.game.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AITest {

    private User userTest1 = ReadAndWriteDataBase.getUser("mir.json");

    @Test
    public void testAIRunWithEasyLoad() throws WinnerException, CloneNotSupportedException {
        AI aiTest = new AI(AI.AIState.EASY);
        Game gameTest = new Game(userTest1,aiTest.getAI());
        aiTest.run(gameTest);
        Assertions.assertNotNull(gameTest.getPlayerBoard().getMonsterZone(1));
        Assertions.assertNotEquals(gameTest.getRivalLP(),8000);
    }
    @Test
    public void testAIRunWithNormalLoad() throws WinnerException, CloneNotSupportedException {
        AI aiTest = new AI(AI.AIState.NORMAL);
        Game gameTest = new Game(userTest1,aiTest.getAI());
        aiTest.run(gameTest);
        Assertions.assertNotNull(gameTest.getPlayerBoard().getMonsterZone(1));
        Assertions.assertNotEquals(gameTest.getRivalLP(),8000);
    }
    @Test
    public void testAIRunWithHardLoad() throws WinnerException, CloneNotSupportedException {
        AI aiTest = new AI(AI.AIState.HARD);
        Game gameTest = new Game(userTest1,aiTest.getAI());
        aiTest.run(gameTest);
        Assertions.assertNotNull(gameTest.getPlayerBoard().getMonsterZone(1));
        Assertions.assertNotEquals(gameTest.getRivalLP(),8000);
    }
}
