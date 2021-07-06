package view.graphics.duelgraphics;


import controller.LoginMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
        //TODO SIA ADD MAIN MENU
    }

    public boolean checkPlayerDeck(){
        User player = LoginMenuController.getCurrentUser();
        if (player.getActiveDeck() == null){
            Popup noActiveDeckPopup = new Popup();
            HBox hBox = new HBox(10);

            Label label = new Label("you don't have any active deck!");
            return makePopUp(noActiveDeckPopup, hBox, label);
        }
        else if (!player.getActiveDeck().isValid()){
            Popup noValidDeckPopUp = new Popup();
            HBox hBox = new HBox(10);
            Label label = new Label("your deck is invalid");

            return makePopUp(noValidDeckPopUp, hBox, label);
        }
        else
            return true;
    }

    private boolean makePopUp(Popup noValidDeckPopUp, HBox hBox, Label label) {
        hBox.setStyle(" -fx-background-color: white;" +
                " -fx-border-color: grey;" +
                " -fx-border-radius: 13;");
        label.setStyle(" -fx-background-color: transparent;" +
                " -fx-font-family: Chalkboard;" +
                " -fx-text-fill: black;" +
                " -fx-border-radius: 15");
        label.setMinWidth(80);
        label.setMinHeight(45);

        Button hide = new Button("hide");
        hide.setCursor(javafx.scene.Cursor.HAND);
        hide.setStyle(" -fx-border-radius: 50;" +
                " -fx-font-family: Chalkboard;" +
                " -fx-text-fill: white;" +
                " -fx-background-color: black;");

        Button goToDeck = new Button("go to deck");
        goToDeck.setCursor(javafx.scene.Cursor.HAND);
        goToDeck.setStyle(" -fx-border-radius: 50;" +
                " -fx-font-family: Chalkboard;" +
                " -fx-text-fill: white;" +
                " -fx-background-color: black;" +
                " -fx-wrap-text: true");

        hide.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                noValidDeckPopUp.hide();
            }
        });
        goToDeck.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                noValidDeckPopUp.hide();
                goToDeck();
            }
        });


        hBox.getChildren().addAll(goToDeck, label, hide);
        noValidDeckPopUp.getContent().add(hBox);
        noValidDeckPopUp.show(Main.stage);
        return false;
    }
    public void goToDeck(){
        //TODO SIA ADD DECK
    }
}
