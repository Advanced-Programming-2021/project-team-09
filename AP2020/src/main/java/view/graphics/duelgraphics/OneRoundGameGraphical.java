package view.graphics.duelgraphics;

import controller.GraphicalGameController;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import main.Main;
import model.User;
import model.game.Game;
import view.graphics.Menu;

import java.net.URL;
import java.util.ResourceBundle;

public class OneRoundGameGraphical implements Initializable {
    private static Game game;
    private static GraphicalGameController graphicalGameController;
    private BorderPane pane;

    public OneRoundGameGraphical(User player, User rival) {
        try {
            OneRoundGameGraphical.game = new Game(player, rival);
        } catch (CloneNotSupportedException ignored) {}
        pane = (BorderPane) Menu.getNode("OneRoundGameGraphical");
        Main.stage.setScene(new Scene(pane));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
