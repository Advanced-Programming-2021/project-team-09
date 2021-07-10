package view.graphics.duelgraphics;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import model.game.Game;
import view.graphics.Menu;

import java.util.ArrayList;

public class TributeMenuGraphical {
    private final Game game;

    public TributeMenuGraphical(Game game) {
        this.game = game;
    }

    public int[] run(int numberOfTributes) {
        ImageView[] images = new ImageView[5];
        int[] tributes = new int[numberOfTributes];
        for (int i = 0; i < 5; i++) {
            images[i] = new ImageView();
        }
        Pane pane = (Pane) Main.stage.getScene().getRoot();
        ArrayList<Node> nodes = Menu.getRectangleAndButtonForGameMenus("Tribute!");
        pane.getChildren().add(nodes.get(0));
        Pane newPane = Menu.copyPane(pane);
        Stage newStage = Menu.copyStage(Main.stage);
        ImageView iv = new ImageView(Menu.getImage("tribute menu", "png"));
        iv.setY(0);
        iv.setX(0);
        iv.setFitHeight(700);
        iv.setFitWidth(700);
        iv.setOpacity(0.18);
        newPane.getChildren().add(iv);

    }
}