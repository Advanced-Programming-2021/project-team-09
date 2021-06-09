import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import view.graphics.Menu;
import view.graphics.WelcomeMenu;

import java.io.IOException;

public class Main extends Application {
    public static Stage stage;
    public static void main(String[] args) throws IOException {
        System.out.println("MIReBOZORG was here");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        BorderPane pane = (BorderPane) Menu.getNode("WelcomeMenu");
      //  pane.setBackground(new Background(new BackgroundImage(WelcomeMenu.BG, BackgroundRepeat.NO_REPEAT,null, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));

        stage.setScene(new Scene(pane));
        pane.requestFocus();
        stage.show();
    }
}