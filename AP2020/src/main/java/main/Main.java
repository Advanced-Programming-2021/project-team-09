package main;//import controller.database.CSVInfoGetter;
//import controller.database.ReadAndWriteDataBase;
//import model.User;
//import model.exceptions.WinnerException;
//import model.game.Board;
//import model.game.Game;
//import model.game.State;
//import view.LoginMenu;
//import view.duelMenu.OneRoundGame;
//
//public class main.Main {
//
//    public static void main(String[] args) {
//        LoginMenu.getInstance().run();
////        User user1 = ReadAndWriteDataBase.getUser("mir.json");
////        User user2 = ReadAndWriteDataBase.getUser("mmd.json");
////        try {
////            Game game = new Game(user1, user2);
////            Board playerBoard = game.getPlayerBoard();
////            Board rivalBoard = game.getRivalBoard();
////            rivalBoard.getSpellZone()[0].addCard(CSVInfoGetter.getCardByName("Dark Hole"));
////            rivalBoard.getSpellZone()[0].setState(State.FACE_UP_SPELL);
////            playerBoard.getSpellZone()[0].addCard(CSVInfoGetter.getCardByName("Twin Twisters"));
////            playerBoard.getSpellZone()[0].setState(State.FACE_UP_SPELL);
////            OneRoundGame roundGame = new OneRoundGame(game, LoginMenu.getInstance().getScanner());
////            roundGame.run();
////        } catch (CloneNotSupportedException | WinnerException e) {
////            e.printStackTrace();
////        }
//    }
//}

import controller.LoginMenuController;
import controller.database.ReadAndWriteDataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;
import model.game.MiniGame;
import view.graphics.Menu;
import view.graphics.duelgraphics.ChooseMiniGame;
import view.graphics.duelgraphics.DuelMenu;
import view.graphics.duelgraphics.OneRoundGameGraphical;

public class Main extends Application {
    public static Stage stage;
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuController.login("sia","1234");
        Main.stage = stage;
        stage.setResizable(false);
        AnchorPane pane = (AnchorPane) Menu.getNode("DuelMenu");
        Scene scene = new Scene(pane,-1,-1,true);
        Menu.setCurrentScene(scene);
        stage.setScene(scene);
        stage.show();
//        Pane pane = new CardHolder();
//        new DuelMenu(0);
//        stage.show();
//        User user = ReadAndWriteDataBase.getUser("mir.json");
//        User user2 = ReadAndWriteDataBase.getUser("mmd.json");

//        new MiniGameCoin(new MiniGame(user, user2));
    }
}
