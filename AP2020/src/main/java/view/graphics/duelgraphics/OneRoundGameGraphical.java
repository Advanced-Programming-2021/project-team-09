package view.graphics.duelgraphics;

import controller.GameMenuController;
import controller.GraphicalGameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    @FXML
    private ImageView playerMonster0;
    @FXML
    private ImageView playerMonster1;
    @FXML
    private ImageView playerMonster2;
    @FXML
    private ImageView playerMonster3;
    @FXML
    private ImageView playerMonster4;
    private ImageView[] playerMonsters = new ImageView[5];
    {
        playerMonsters[0] = playerMonster0;
        playerMonsters[1] = playerMonster1;
        playerMonsters[2] = playerMonster2;
        playerMonsters[3] = playerMonster3;
        playerMonsters[4] = playerMonster4;
    }

    @FXML
    private ImageView playerSpell0;
    @FXML
    private ImageView playerSpell1;
    @FXML
    private ImageView playerSpell2;
    @FXML
    private ImageView playerSpell3;
    @FXML
    private ImageView playerSpell4;
    private ImageView[] playerSpells = new ImageView[5];
    {
        playerSpells[0] = playerSpell0;
        playerSpells[1] = playerSpell1;
        playerSpells[2] = playerSpell2;
        playerSpells[3] = playerSpell3;
        playerSpells[4] = playerSpell4;
    }

    @FXML
    private ImageView rivalSpell0;
    @FXML
    private ImageView rivalSpell1;
    @FXML
    private ImageView rivalSpell2;
    @FXML
    private ImageView rivalSpell3;
    @FXML
    private ImageView rivalSpell4;
    private ImageView[] rivalSpells = new ImageView[5];
    {
        rivalSpells[0] = rivalSpell0;
        rivalSpells[1] = rivalSpell1;
        rivalSpells[2] = rivalSpell2;
        rivalSpells[3] = rivalSpell3;
        rivalSpells[4] = rivalSpell4;
    }

    @FXML
    private ImageView rivalMonster0;
    @FXML
    private ImageView rivalMonster1;
    @FXML
    private ImageView rivalMonster2;
    @FXML
    private ImageView rivalMonster3;
    @FXML
    private ImageView rivalMonster4;
    private ImageView[] rivalMonsters = new ImageView[5];
    {
        rivalMonsters[0] = rivalMonster0;
        rivalMonsters[1] = rivalMonster1;
        rivalMonsters[2] = rivalMonster2;
        rivalMonsters[3] = rivalMonster3;
        rivalMonsters[4] = rivalMonster4;
    }

    @FXML
    private HBox playerCardBox;
    @FXML
    private HBox rivalCardBox;
    @FXML
    private VBox buttonsMenu;
    @FXML
    private ImageView playerFieldSpell;
    @FXML
    private ImageView playerGraveYard;
    @FXML
    private ImageView rivalFieldSpell;
    @FXML
    private ImageView rivalGraveYard;

    public OneRoundGameGraphical(User player, User rival) {
        try {
            OneRoundGameGraphical.game = new Game(player, rival);
            GameMenuController.firstDraw(game);
        } catch (CloneNotSupportedException ignored) {}
        pane = (BorderPane) Menu.getNode("OneRoundGameGraphical");
        Main.stage.setScene(new Scene(pane));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicalGameController = new GraphicalGameController(playerMonsters, playerSpells, rivalMonsters, rivalSpells,
                playerCardBox, rivalCardBox, buttonsMenu, playerFieldSpell, playerGraveYard, rivalFieldSpell,
                rivalGraveYard, game);
    }
}
