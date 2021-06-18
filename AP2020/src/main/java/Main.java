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
    public static void main(String[] args) throws IOException {
        System.out.println("MIReBOZORG was here");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuController.login("sia","1234");
        Main.stage = stage;
//        Pane pane = new CardHolder();
        BorderPane pane = (BorderPane) Menu.getNode("DeckMenu");
//        BorderPane pane = (BorderPane) Menu.getNode("ProfileMenu");
//        pane.setBackground(new Background(new BackgroundImage(WelcomeMenu.BG, BackgroundRepeat.NO_REPEAT,null, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        stage.setScene(new Scene(pane));
        Menu.setCurrentScene(stage.getScene());
        pane.requestFocus();
        stage.show();
    }
}

