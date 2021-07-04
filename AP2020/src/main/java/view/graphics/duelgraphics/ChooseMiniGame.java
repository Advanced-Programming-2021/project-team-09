package view.graphics.duelgraphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.Main;
import model.User;
import model.game.MiniGame;
import view.graphics.Menu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseMiniGame implements Initializable {
    private static User player;
    private static User rival;
    private static MiniGame miniGame;
    @FXML
    private Label playerName;
    @FXML
    private Label rivalName;
    private AnchorPane anchorPane;
    @FXML
    private Button go;
    @FXML
    private Label mode;
    @FXML
    private RadioButton radio;
    @FXML
    private Button chooseCoin;
    @FXML
    private Button chooseRock;
    @FXML
    private Button chooseDice;

    public ChooseMiniGame() {

    }

    public ChooseMiniGame(User player, User rival) {
        miniGame = new MiniGame(player, rival);
        anchorPane = (AnchorPane) Menu.getNode("ChooseMiniGame");
        Main.stage.setScene(new Scene(anchorPane));
        // TODO: 7/3/2021 go back buttons for mini games
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        go.setDisable(true);
        playerName.setText(miniGame.getFirstUser().getNickname());
        rivalName.setText(miniGame.getSecondUser().getNickname());
        ArrayList<Button> menuButtons = new ArrayList<>();
        menuButtons.add(chooseRock);
        menuButtons.add(chooseCoin);
        menuButtons.add(chooseDice);
        for (Button menuButton : menuButtons) menuButton.setCursor(Cursor.HAND);
        chooseCoin.setOnMouseClicked(mouseEvent -> {
            boldSource(menuButtons, mouseEvent);
            go.setDisable(false);
            radio.setDisable(true);
            radio.setVisible(false);
            mode.setText("Coin");
        });
        chooseRock.setOnMouseClicked(mouseEvent -> {
            boldSource(menuButtons, mouseEvent);
            go.setDisable(false);
            radio.setDisable(true);
            radio.setVisible(false);
            mode.setText("Rock Paper Scissors");
        });
        chooseDice.setOnMouseClicked(mouseEvent -> {
            boldSource(menuButtons, mouseEvent);
            go.setDisable(false);
            radio.setDisable(false);
            radio.setVisible(true);
            mode.setText("Dice");
        });
        go.setOnMouseClicked(mouseEvent -> goToMode());
    }

    private void boldSource(ArrayList<Button> menuButtons, MouseEvent mouseEvent) {
        Button source = (Button) mouseEvent.getSource();
        for (Button menuButton : menuButtons) {
            menuButton.setStyle("-fx-background-color: transparent");
        }
        source.setStyle("-fx-background-color: #001f7f77");
    }

    private void goToMode() {
        go.setDisable(true);
        if (mode.getText().equals("Coin")) {
            new MiniGameCoin(miniGame);
        } else if (mode.getText().equals("Dice")) {
            new MiniGameDice(miniGame, radio.isSelected());
        } else {
            new MiniGameRockPaperScissors(miniGame);
        }
    }
}
