package view.graphics.deckmenu;

import controller.database.CSVInfoGetter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.card.Card;
import model.deck.Deck;
import model.deck.MainDeck;
import model.deck.PrimaryDeck;
import model.deck.SideDeck;
import model.enums.Cursor;
import model.graphicalModels.CardHolder;
import view.graphics.ChoiceMenu;

import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class EditDeckMenu extends ChoiceMenu implements Initializable {
    private final HashMap<String,VBox> cardBoxes = new HashMap<>();
    {
        for (String cardName : Card.getCardNames()) {
            VBox choiceBox = new VBox();
            choiceBox.setSpacing(10);
            CardHolder holder = new CardHolder(getCard(cardName));
            holder.scale(0.65);
            double width = holder.getWidth();
            double height = holder.getHeight();
            choiceBox = (VBox) setDimension(choiceBox, width, height + 50);
            Label name = getLabel(cardName, width, 15, 10);
            choiceBox.getChildren().add(name);
            choiceBox.getChildren().add(holder);
            cardBoxes.put(cardName,choiceBox);
        }

    }

    private Deck deck;
    private SideDeck sideDeck;
    private MainDeck mainDeck;
    @FXML
    private VBox mainDeckOptionBar;
    @FXML
    private Label nameLabel;
    @FXML
    private ToggleButton sideToggle;
    @FXML
    private ToggleButton mainToggle;
    @FXML
    private VBox sideDeckOptionBar;
    @FXML
    private HBox mainChoiceBox;
    @FXML
    private HBox sideChoiceBox;

    @Override
    protected VBox getChoiceBox(String result) {
        return cardBoxes.get(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceNames = new HashSet<>(Card.getCardNames());
        setSpacing(5);
        setWidth(150 * 0.65 + 5);
        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(mainToggle, sideToggle);
        mainChoiceBox.setSpacing(5);
        sideChoiceBox.setSpacing(5);
        mainToggle.setSelected(true);
        mainToggle.setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(138, 138, 138, 1), 0.5, 0.0, 1, 0));
        mainToggle.setOnAction(actionEvent -> onSelectToggle(mainToggle, group));
        sideToggle.setOnAction(actionEvent -> onSelectToggle(sideToggle, group));
        searchField.textProperty().addListener((observableValue, s, t1) -> search(t1));
    }


    @Override
    protected void onSelectToggle(ToggleButton button,ToggleGroup group) {
        super.onSelectToggle(button,group);
        if (button == mainToggle && !mainToggle.isSelected()) {
            resetBox(mainChoiceBox);
        }
        if (button == sideToggle && !sideToggle.isSelected()) resetBox(sideChoiceBox);
    }

    private void resetBox(HBox box) {
        HBox hBox = choiceBox;
        choiceBox = box;
        resetChoiceBox();
        choiceBox = hBox;
    }

    @Override
    protected void search(String searchText) {
        if (mainToggle.isSelected()) choiceBox = mainChoiceBox;
        else if (sideToggle.isSelected()) choiceBox = sideChoiceBox;
        else return;
        super.search(searchText);
    }

    private HBox getChoiceBox() {
        if (mainToggle.isSelected()) return mainChoiceBox;
        if (sideToggle.isSelected()) return sideChoiceBox;
        return null;
    }

    private HashSet<String> getChoiceNames() {
        if (mainToggle.isSelected()) return mainDeck.getCardNames();
        if (sideToggle.isSelected()) return sideDeck.getCardNames();
        return null;
    }

    private PrimaryDeck getDeck() {
        if (mainToggle.isSelected()) return mainDeck;
        if (sideToggle.isSelected()) return sideDeck;
        return null;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        mainDeck = deck.getMainDeck();
        sideDeck = deck.getSideDeck();
        nameLabel.setText(deck.getDeckName());
        choiceBox = mainChoiceBox;
        addToChoiceBox(choiceNames);
        System.out.println(choiceNames.isEmpty());
        choiceBox = sideChoiceBox;
        addToChoiceBox(choiceNames);
    }

}
