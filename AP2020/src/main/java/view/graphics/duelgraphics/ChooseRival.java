package view.graphics.duelgraphics;

import controller.LoginMenuController;
import controller.database.ReadAndWriteDataBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import main.Main;
import model.User;
import view.graphics.Menu;
import view.graphics.SearchMenu;

import java.util.ArrayList;

public class ChooseRival extends SearchMenu {
    @FXML
    private TextField profileName;
    @FXML
    private ImageView imageView;

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

    public void getInput(){
        String command = profileName.getText();
        search(command);
        imageView.setImage(Menu.getImage("animatedDragon", "GIF"));
    }

    @Override
    protected void search(String searchText) {
        ArrayList<String> users = new ArrayList<>();
        for (User username: usernames) {
            String userName = username.getUsername();
            if (userName.contains(searchText)){
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
        button.onMouseEnteredProperty().set(mouseEvent -> button.setStyle(" -fx-background-color: black;" +
                " -fx-border-radius: 15;" +
                " -fx-border-color: black;" +
                " -fx-cursor: hand;" +
                " -fx-font-family: Chalkboard;" +
                " -fx-text-fill: white;"));
        button.onMouseExitedProperty().set(mouseEvent -> button.setStyle(" -fx-background-color: white;" +
                " -fx-border-radius: 13;" +
                " -fx-border-color: black;" +
                " -fx-cursor: hand;" +
                " -fx-font-family: Chalkboard;"));
        button.setOnAction(actionEvent -> {
            User user = ReadAndWriteDataBase.getUser(searchResult+".json");
            assert user != null;
            buttonFunctions(user);
        });
        return button;
    }
    public void buttonFunctions(User rival){
        if (rival.getActiveDeck() == null){
            Popup noActiveDeckPopup = new Popup();
            HBox hBox = new HBox(10);
            hBox.setStyle(" -fx-background-color: white;" +
                    " -fx-border-color: grey;" +
                    " -fx-border-radius: 13;");
            Label label = new Label(rival.getUsername() + " has no active deck");
            makePopUp(noActiveDeckPopup, hBox, label);
        }
        else if (!rival.getActiveDeck().isValid()){
            Popup noValidDeckPopUp = new Popup();
            HBox hBox = new HBox(10);
            hBox.setStyle(" -fx-background-color: white;" +
                    " -fx-border-color: grey;" +
                    " -fx-border-radius: 13;");
            Label label = new Label(rival.getUsername() + "'s deck is invalid");
            makePopUp(noValidDeckPopUp, hBox, label);
        }
        else {
            new ChooseMiniGame(LoginMenuController.getCurrentUser(), rival);
        }
    }

    private void makePopUp(Popup noActiveDeckPopup, HBox hBox, Label label) {
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
        hide.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                noActiveDeckPopup.hide();
            }
        });
        hBox.getChildren().addAll(label, hide);
        noActiveDeckPopup.getContent().add(hBox);
        noActiveDeckPopup.show(Main.stage);
    }
}
