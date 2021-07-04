package view.graphics.duelgraphics;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Main;
import model.enums.Cursor;
import view.graphics.Menu;

public class DuelMenu extends Menu {
    private AnchorPane anchorPane;
    @FXML
    private Button MULTIPLAYER_1_ROUND;
    @FXML
    private Button MULTIPLAYER_3_ROUND;
    @FXML
    private Button SINGLE_PLAYER_1_ROUND;
    @FXML
    private Button SINGLE_PLAYER_3_ROUND;
    @FXML
    private Button back;

    public DuelMenu(){

    }

    public void initialize(){
        justifyButton(SINGLE_PLAYER_1_ROUND, Cursor.SWORD);
        justifyButton(SINGLE_PLAYER_3_ROUND, Cursor.SWORD);
        justifyButton(MULTIPLAYER_1_ROUND, Cursor.SWORD);
        justifyButton(MULTIPLAYER_3_ROUND, Cursor.SWORD);
        back.setCursor(javafx.scene.Cursor.HAND);
    }

    public void singlePlayerOneRound(){

    }
    public void singlePlayerThreeRound(){

    }
    public void multiplayerOneRound(){
        goToChooseRival();

    }
    public void multiplayerThreeRound(){
        goToChooseRival();

    }

    public void goToChooseRival(){
        anchorPane = (AnchorPane) Menu.getNode("ChooseRival");
        Main.stage.setScene(new Scene(anchorPane,600,400));

    }

    public void goToMainMenu() {
        anchorPane = (AnchorPane) Menu.getNode("MainMenu");
        Main.stage.setScene(new Scene(anchorPane,600,400));
    }
}
