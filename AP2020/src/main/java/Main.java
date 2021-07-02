//import controller.database.CSVInfoGetter;
//import controller.database.ReadAndWriteDataBase;
//import model.User;
//import model.exceptions.WinnerException;
//import model.game.Board;
//import model.game.Game;
//import model.game.State;
//import view.LoginMenu;
//import view.duelMenu.OneRoundGame;
//
//public class Main {
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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.graphicalModels.CardHolder;
import view.graphics.Menu;

import java.io.IOException;

public class Main extends Application {
    public static Stage stage;
    public static void main(String[] args) {
        System.out.println("MIReBOZORG was here");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuController.login("sia","1234");
        Main.stage = stage;
//        Pane pane = new CardHolder();
        BorderPane pane = (BorderPane) Menu.getNode("ShopMenu");
//        BorderPane pane = (BorderPane) Menu.getNode("ProfileMenu");
//        pane.setBackground(new Background(new BackgroundImage(WelcomeMenu.BG, BackgroundRepeat.NO_REPEAT,null, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        stage.setScene(new Scene(pane));
        Menu.setCurrentScene(stage.getScene());
        pane.requestFocus();
        stage.show();
    }
}
