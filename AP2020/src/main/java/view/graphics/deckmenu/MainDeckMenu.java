package view.graphics.deckmenu;

import controller.DeckMenuController;
import controller.LoginMenuController;
import controller.ShopController;
import controller.database.CSVInfoGetter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.card.Card;
import model.enums.Cursor;
import model.graphicalModels.CardHolder;
import view.graphics.Menu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainDeckMenu extends Menu implements Initializable {
    public BorderPane mainPane;
    @FXML
    private Button allCardsButton;
    @FXML
    private Button editDeck;
    @FXML
    private Button deleteDeckButton;
    @FXML
    private Button createDeckButton;


    public void previous(ActionEvent actionEvent) {

    }

    public void next(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        justifyButton(createDeckButton, Cursor.CREATE_DECK);
        justifyButton(allCardsButton,Cursor.ALL_CARDS);
        justifyButton(deleteDeckButton,Cursor.TRASH);
        justifyButton(editDeck,Cursor.EDIT);
        editDeck.setOnAction(actionEvent->setEditDeck());

    }



    private void setEditDeck() {
        mainPane.setCenter(getNode("EditDeck"));
    }


}
