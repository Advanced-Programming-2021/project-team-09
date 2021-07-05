package view.graphics.deckmenu;

import controller.DeckMenuController;
import controller.LoginMenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.card.Card;
import model.deck.Deck;
import model.enums.Cursor;
import model.graphicalModels.CardHolder;
import org.jetbrains.annotations.NotNull;
import view.graphics.ChoiceMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class AllDecksMenu extends ChoiceMenu implements Initializable {
    private final static Image DECK_PIC1 = getImage("DeckPicture1", "png");
    private final static Image DECK_PIC2 = getImage("DeckPicture2", "png");

    @FXML
    private HBox previewBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        previewBox.setPrefWidth(0);
        choiceBox.setSpacing(5);
        setWidth(107);
        for (Deck deck : LoginMenuController.getCurrentUser().getDecks()) choiceNames.add(deck.getDeckName());
        addToChoiceBox(choiceNames);
        searchField.textProperty().addListener((observableValue, s, t1) -> search(t1));
        decisionBox.setSpacing(10);

    }
    @Override
    protected void search(String searchText) {
        resetBoxProperties(previewBox);
        super.search(searchText);
    }

    @Override
    protected void addToChoiceBox(HashSet<String> matchingCards) {
        super.addToChoiceBox(matchingCards);
        VBox activeBox = getActiveDeckBox();
        if (activeBox == null) return;
        activeBox.setStyle("-fx-background-color: #fcf58c; -fx-background-radius: 10;-fx-border-radius: 10; -fx-border-style: Dashed; -fx-border-color: #404040");
    }

    @Override
    protected VBox getChoiceBox(String result) {
        Deck deck = LoginMenuController.getCurrentUser().getDeckByName(result);
        VBox box = new VBox();
        box.setSpacing(5);
        box.setPrefHeight(200);
        box.setPrefWidth(100);
        Label deckName = getLabel(result);
        Label side = getLabel("Side Deck:");
        Label sideSize = getLabel("#" + deck.getSideDeck().getCards().size());
        Label main = getLabel("Main Deck:");
        Label mainSize = getLabel("#" + deck.getMainDeck().getCards().size());
        Node[] array = {deckName, getImageView(100, 100, DECK_PIC1), main, mainSize, side, sideSize};
        box.getChildren().addAll(Arrays.asList(array));
        setOnClickAction(box);
        box.setStyle("-fx-border-radius: 10; -fx-border-style: Dashed; -fx-border-color: #404040");
        return box;
    }

    private void setOnClickAction(VBox box) {
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ImageView view = (ImageView) box.getChildren().get(1);
                if (view.getImage().equals(DECK_PIC1)) {
                    resetSelectOthers();
                    view.setImage(DECK_PIC2);
                    setOptionsInDecisionBox(box);
                    addPreviewBox(getDeckName(box));
                } else {
                    view.setImage(DECK_PIC1);
                    emptyDecisionBox();
                    resetBoxProperties(previewBox);
                }
            }
        });
    }

    private void setOptionsInDecisionBox(VBox box) {
        emptyDecisionBox();
        String deckName = ((Label) box.getChildren().get(0)).getText();
        Deck deck = LoginMenuController.getCurrentUser().getActiveDeck();
        String activeDeck = deck != null ? deck.getDeckName() : "";
        boolean isDeckActive = deckName.equals(activeDeck);
        Button delete = getButton("Delete");
        Button edit = getButton("Edit");
        Button activate = getButton(isDeckActive ? "DeActivate" : "Activate");
        if (!isDeckActive) activate.setOnAction(actionEvent -> activateDeck(box));
        delete.setOnAction(actionEvent -> deleteDeck(deckName));
        if (!isDeckActive) addOptionToDecisionBox(activate);
        addOptionToDecisionBox(edit, delete);
    }

    private void activateDeck(VBox box) {
        DeckMenuController.activateDeck(((Label) box.getChildren().get(0)).getText());
        updateChoiceBox();
    }


    private void deleteDeck(String deckName) {
        DeckMenuController.deleteDeck(deckName);
        choiceNames.remove(deckName);
        updateChoiceBox();
        emptyDecisionBox();
        resetBoxProperties(previewBox);
    }

    private VBox getActiveDeckBox() {
        Deck deck = LoginMenuController.getCurrentUser().getActiveDeck();
        if (deck == null) {
            return null;
        }
        String activeDeck = deck.getDeckName();
        if (choiceBox.getChildren().size() == 0) return null;
        for (Node box : choiceBox.getChildren())
            if (((Label) ((VBox) box).getChildren().get(0)).getText().equals(activeDeck)) return (VBox) box;
        return null;
    }

    private Button getButton(String text) {
        Button button = new Button(text);
        button.setPrefHeight(25);
        button.setPrefWidth(100);
        button.setStyle("-fx-border-style: solid none solid; -fx-border-radius: 10;");
        if (text.equals("Edit")) justifyButton(button, Cursor.EDIT);
        else if (text.equals("Delete")) justifyButton(button, Cursor.TRASH);
        else if (text.equals("Activate")) justifyButton(button, Cursor.ACCEPT);
        else justifyButton(button, Cursor.CANCEL);
        return button;
    }

    private void resetSelectOthers() {
        for (Node box : choiceBox.getChildren()) {
            ((ImageView) ((VBox) box).getChildren().get(1)).setImage(DECK_PIC1);
        }
    }

    @NotNull
    private Label getLabel(String text) {
        Label label = new Label(text);
        label.setPrefHeight(15);
        label.setPrefWidth(100);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private ImageView getImageView(double height, double width, Image image) {
        ImageView view = new ImageView();
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setImage(image);
        return view;
    }

    private void addPreviewBox(String deckName) {
        resetBoxProperties(previewBox);
        ArrayList<CardHolder> holders = new ArrayList<>();
        ArrayList<Card> mainCards = LoginMenuController.getCurrentUser().getDeckByName(deckName).getMainDeck().getSortedCards();
        ArrayList<Card> sideCards = LoginMenuController.getCurrentUser().getDeckByName(deckName).getSideDeck().getSortedCards();
        mainCards.addAll(sideCards);
        for (Card card : mainCards) {
            CardHolder holder = new CardHolder(card);
            holder.scale(0.4);
            holders.add(holder);
        }
        previewBox.setSpacing(5);
        previewBox.setPrefWidth((holders.get(0).getWidth() + 5) * holders.size() - 5);
        previewBox.setPrefHeight(holders.get(0).getHeight() + 12);
        previewBox.getChildren().addAll(holders);
    }

    private String getDeckName(VBox box) {
        return ((Label) box.getChildren().get(0)).getText();
    }

    private void resetBoxProperties(Pane box) {
        box.getChildren().removeAll(box.getChildren());
        box.setPrefWidth(0);
        box.setPrefHeight(0);
    }
}
