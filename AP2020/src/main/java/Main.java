import controller.database.CSVInfoGetter;
import controller.database.ReadAndWriteDataBase;
import model.User;
import model.exceptions.WinnerException;
import model.game.Board;
import model.game.Game;
import model.game.State;
import view.LoginMenu;
import view.duelMenu.OneRoundGame;

public class Main {

    public static void main(String[] args) {
        LoginMenu.getInstance().run();
//        User user1 = ReadAndWriteDataBase.getUser("mir.json");
//        User user2 = ReadAndWriteDataBase.getUser("mmd.json");
//        try {
//            Game game = new Game(user1, user2);
//            Board playerBoard = game.getPlayerBoard();
//            Board rivalBoard = game.getRivalBoard();
//            rivalBoard.getSpellZone()[0].addCard(CSVInfoGetter.getCardByName("Dark Hole"));
//            rivalBoard.getSpellZone()[0].setState(State.FACE_UP_SPELL);
//            playerBoard.getSpellZone()[0].addCard(CSVInfoGetter.getCardByName("Twin Twisters"));
//            playerBoard.getSpellZone()[0].setState(State.FACE_UP_SPELL);
//            OneRoundGame roundGame = new OneRoundGame(game, LoginMenu.getInstance().getScanner());
//            roundGame.run();
//        } catch (CloneNotSupportedException | WinnerException e) {
//            e.printStackTrace();
//        }
    }
}