package view.graphics.duelgraphics;

import controller.LoginMenuController;
import controller.database.CSVInfoGetter;
import controller.database.ReadAndWriteDataBase;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import main.Main;
import model.User;
import model.card.Card;
import model.enums.Cursor;
import view.graphics.Menu;
import view.graphics.SearchMenu;

import java.util.ArrayList;

public class ChooseRival extends SearchMenu {
    @FXML
    private TextField profileName;
    @FXML
    private Label userScore;

    public static ArrayList<User> usernames = ReadAndWriteDataBase.getAllUsers();

    public ChooseRival(){

    }
    public ChooseRival(int alaki){
        AnchorPane anchorPane = (AnchorPane) Menu.getNode("ChooseRival");
        assert anchorPane != null;
        Main.stage.setScene(new Scene(anchorPane,340,400));
    }

    public void goToDuelMenu() {
        new DuelMenu(0);
    }

    public void getInput() {
        String command = profileName.getText();
        search(command);
    }

    @Override
    protected void search(String searchText) {
        ArrayList<String> users = new ArrayList<>();
        for (User username: usernames) {
            if (username.getUsername().contains(searchText)){
                users.add(username.getUsername());
            }
        }
        ArrayList<VBox> resultBoxes = getSearchResults(users);
        searchResults = new ArrayList<>();
        searchResults.addAll(resultBoxes);
        if (searchResults.size() > 0) showVBox(0);
        else {
            emptySearchBox();
            stageCounter.setText("-/-");
        }
    }

    @Override
    protected ArrayList<VBox> getSearchResults(ArrayList<String> searchResults) {
        ArrayList<VBox> resultBoxes = new ArrayList<>();
        VBox currentBox = new VBox(2);
        for (String result : searchResults) {
            if (currentBox.getChildren().size() == 9) {
                resultBoxes.add(currentBox);
                currentBox = new VBox(2);
            }
            currentBox.getChildren().add(getOptionButton(result));
        }
        if (currentBox.getChildren().size() != 0) resultBoxes.add(currentBox);
        return resultBoxes;
    }
    protected Button getOptionButton(String searchResult) {
        Button button = new Button(searchResult + "-" + ReadAndWriteDataBase.getUser(searchResult+".json").getScore());
        button.setPrefHeight(28);
        button.setPrefWidth(120);
        button.setStyle(" -fx-background-color: white;" +
                " -fx-border-radius: 13;" +
                " -fx-border-color: black;" +
                " -fx-cursor: hand;" +
                " -fx-font-family: Chalkboard;");
        button.setOnAction(actionEvent -> {
            User user = ReadAndWriteDataBase.getUser(searchResult+".json");
            assert user != null;
            buttonFunctions(user);
        });
        justifyButton(button, Cursor.SEARCH);
        return button;
    }
    public void buttonFunctions(User rival){
        if (rival.getActiveDeck() == null){
            Popup noActiveDeckPopup = new Popup();
            Label label = new Label(rival.getUsername() + " has no active deck");
            label.setStyle(" -fx-background-color: black;" +
                    " -fx-font-family: Chalkboard;" +
                    " -fx-text-fill: white;" +
                    " -fx-border-radius: 15");
            label.setMinWidth(80);
            label.setMinHeight(45);
            noActiveDeckPopup.getContent().add(label);
            noActiveDeckPopup.show(Main.stage);
        }
        else if (!rival.getActiveDeck().isValid()){
            Popup noValidDeckPopUp = new Popup();
            Label label = new Label(rival.getUsername() + "'s deck is invalid");
            label.setStyle(" -fx-background-color: black;" +
                    " -fx-font-family: Chalkboard;" +
                    " -fx-border-radius: 15;" +
                    " -fx-text-fill: white");
            label.setMinWidth(80);
            label.setMinHeight(45);
            noValidDeckPopUp.getContent().add(label);
            noValidDeckPopUp.show(Main.stage);
        }
        else {
            new ChooseMiniGame(LoginMenuController.getCurrentUser(), rival);
        }
    }
}
