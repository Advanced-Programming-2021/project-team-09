import controller.ScoreboardController;
import controller.database.ReadAndWriteDataBase;
import de.vandermeer.asciitable.AsciiTable;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.LoginMenu;

import java.io.*;

public class ViewTest {
    static PrintStream defaultStream = System.out;
    static InputStream defaultInputStream = System.in;

    @Test
    public void testCreateUserAndProfileMenu() {
        String command = "user create -u kasra -p kasra1380 -n kasi\n" +
                "user login --username kasra --password kasra1380\n" +
                "menu enter profile\n" +
                "profile change -p -c kasra1380 -n kasr\n" +
                "profile change -n kasram\n" +
                "menu exit\n" +
                "user logout\n" +
                "menu exit\n";
        setCommandInInputStream(command);
        LoginMenu.getInstance().run();
        User testUser = ReadAndWriteDataBase.getUser("kasra.json");
        Assertions.assertNotNull(testUser);
        Assertions.assertEquals("kasram", testUser.getNickname());
        Assertions.assertEquals("kasr", testUser.getPassword());
    }

    @Test
    public void testScoreBoard() {
        String testScoreBoard = ScoreboardController.getScoreBoard();
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Rank", "Username", "Nickname", "Score");
        asciiTable.addRule();
        asciiTable.addRow(1, "sia", "siasor88", 4000);
        asciiTable.addRule();
        asciiTable.addRow(2, "ali", "ali", 3000);
        asciiTable.addRule();
        asciiTable.addRow(3, "kasra", "kasram", 2000);
        asciiTable.addRule();
        asciiTable.addRow(3, "kk", "kkk", 2000);
        asciiTable.addRule();
        String expectedScoreBoard = asciiTable.render();
        Assertions.assertEquals(expectedScoreBoard, testScoreBoard);
    }

    @AfterEach
    public void afterEachTest() {
        resetStreams();
    }

    private ByteArrayOutputStream getOutPutStream() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(stream);
        System.setOut(printStream);
        return stream;
    }

    private void setCommandInInputStream(String command1) {
        try {
            System.in.reset();
        } catch (IOException ignored) {
        }
        ByteArrayInputStream stream1 = new ByteArrayInputStream(command1.getBytes());
        System.setIn(stream1);
    }

    private void resetStreams() {
        System.setIn(defaultInputStream);
        System.setOut(defaultStream);
    }

}
