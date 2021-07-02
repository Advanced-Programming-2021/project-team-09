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
    private VBox rightBox;
    @FXML
    private Button allCardsButton;
    @FXML
    private Button editDeck;
    @FXML
    private Button deleteDeckButton;
    @FXML
    private Button createDeckButton;
    @FXML
    private VBox leftBox;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    private ListView<VBox> listView;


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

    }


    private VBox getCardBox(String cardName) {
        VBox box = new VBox();
        box.setSpacing(10);
        CardHolder holder = new CardHolder(getCard(cardName));
        Label name = getLabel(cardName,150,20);
        Label count = getLabel("#" + getNumberOfCards(cardName),150,20);
        box.setPrefWidth(150);
        box.setMaxWidth(150);
        box.setPrefHeight(260);
        box.setMaxWidth(260);
        box.getChildren().add(name);
        box.getChildren().add(holder);
        box.getChildren().add(count);
        return box;
    }



    private int getNumberOfCards (String cardName) {
        ArrayList<Card> allCards = LoginMenuController.getCurrentUser().getCards();
        return (int) allCards.stream().filter(card -> card.getCardName().equals(cardName)).count();

    }


}
