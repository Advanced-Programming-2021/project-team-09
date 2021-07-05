package view.graphics.duelgraphics;


import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import main.Main;
import model.User;
import model.enums.Cursor;
import view.graphics.Menu;

public class DuelMenu extends Menu {
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
    public DuelMenu(int alaki){
        AnchorPane anchorPane = (AnchorPane) Menu.getNode("DuelMenu");
        assert anchorPane != null;
        Main.stage.setScene(new Scene(anchorPane, 600, 400));
    }

    public void initialize(){
        justifyButton(SINGLE_PLAYER_1_ROUND, Cursor.SWORD);
        justifyButton(SINGLE_PLAYER_3_ROUND, Cursor.SWORD);
        justifyButton(MULTIPLAYER_1_ROUND, Cursor.SWORD);
        justifyButton(MULTIPLAYER_3_ROUND, Cursor.SWORD);
        back.setCursor(javafx.scene.Cursor.HAND);
    }

    public void singlePlayerOneRound(){
        if (checkPlayerDeck()){

        }
    }
    public void singlePlayerThreeRound(){
        if (checkPlayerDeck()){

        }
    }
    public void multiplayerOneRound(){
        if (checkPlayerDeck()){
            goToChooseRival();
        }
    }
    public void multiplayerThreeRound(){
        if (checkPlayerDeck()){
            goToChooseRival();
        }
    }

    public void goToChooseRival(){
        new ChooseRival(0);
    }

    public void goToMainMenu() {
        AnchorPane anchorPane = (AnchorPane) Menu.getNode("MainMenu");
        assert anchorPane != null;
        Main.stage.setScene(new Scene(anchorPane,600,400));
    }

    public boolean checkPlayerDeck(){
        User player = LoginMenuController.getCurrentUser();
        if (player.getActiveDeck() == null){
            Popup noActiveDeckPopup = new Popup();
            Label label = new Label("you have no active deck");
            label.setStyle(" -fx-background-color: red;" +
                    " -fx-font-family: Chalkboard;" +
                    " -fx-text-fill: white;" +
                    " -fx-border-radius: 15");
            label.setMinWidth(80);
            label.setMinHeight(45);
            noActiveDeckPopup.getContent().add(label);
            noActiveDeckPopup.show(Main.stage);
            return false;
        }
        else if (!player.getActiveDeck().isValid()){
            Popup noValidDeckPopUp = new Popup();
            Label label = new Label("your deck is invalid");
            label.setStyle(" -fx-background-color: red;" +
                    " -fx-font-family: Chalkboard;" +
                    " -fx-border-radius: 15;" +
                    " -fx-text-fill: white");
            label.setMinWidth(80);
            label.setMinHeight(45);
            noValidDeckPopUp.getContent().add(label);
            noValidDeckPopUp.show(Main.stage);
            return false;
        }
        else
            return true;
    }
}
