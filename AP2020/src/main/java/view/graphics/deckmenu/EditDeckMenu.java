package view.graphics.deckmenu;

import controller.LoginMenuController;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.card.Card;
import model.graphicalModels.CardHolder;
import org.jetbrains.annotations.NotNull;
import view.graphics.ChoiceMenu;
import view.graphics.SearchMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class EditDeckMenu extends ChoiceMenu implements Initializable {
    @FXML
    public ScrollPane cardsPlace;
    @FXML
    public HBox choiceBox;
    @FXML
    public VBox decisionBox;

    private ArrayList<Card> cards;

    private int getNumberOfCards(String cardName) {
        ArrayList<Card> allCards = (ArrayList<Card>) cards.clone();
        return (int) allCards.stream().filter(card -> card.getCardName().equals(cardName)).count();

    }

    @NotNull
    protected VBox getChoiceBox(String cardName) {
        VBox box = new VBox();
        box.setSpacing(10);
        CardHolder holder = new CardHolder(getCard(cardName));
        Label name = getLabel(cardName, 150, 20);
        Label count = getLabel("#" + getNumberOfCards(cardName), 150, 20);
        box.setPrefWidth(150);
        box.setMaxWidth(150);
        box.setPrefHeight(260);
        box.setMaxWidth(260);
        box.getChildren().add(name);
        box.getChildren().add(holder);
        box.getChildren().add(count);
        setOnCardClicked(box);
        return box;
    }

    private void setOnCardClicked(VBox box) {
        box.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            TranslateTransition translate = new TranslateTransition();
            translate.setNode(box);
            translate.setByY(-20);
            translate.setDuration(Duration.millis(250));
            translate.setAutoReverse(true);
            translate.setCycleCount(2);
            translate.play();
//            RotateTransition rotate = new RotateTransition();
//            rotate.setAxis(new Point3D(0,1,0));
//            rotate.setByAngle(180);
//            rotate.setDuration(Duration.millis(1000));
//            rotate.setNode(box.getChildren().get(1));
//            rotate.play();
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setSpacing(5);
        resetChoiceBox();
        cards = LoginMenuController.getCurrentUser().getCards();
        for (Card card : cards) choiceNames.add(card.getCardName());
        searchField.textProperty().addListener((observableValue, s, t1) -> search(t1));
        resetChoiceBox();
    }

    @Override
    protected Button getOptionButton(String searchResult) {
        return null;
    }
}
