package view.graphics.deckmenu;

import controller.LoginMenuController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.deck.Deck;
import org.jetbrains.annotations.NotNull;
import view.graphics.ChoiceMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class AllDecksMenu extends ChoiceMenu implements Initializable {
    private final static Image DECK_PIC1 = getImage("DeckPicture1","png");
    private final static Image DECK_PIC2 = getImage("DeckPicture2","png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Deck deck : LoginMenuController.getCurrentUser().getDecks()) choiceNames.add(deck.getDeckName());
        addToChoiceBox(choiceNames);
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
        Node[] array = {deckName,getImageView(100,100,DECK_PIC1),main,mainSize,side,sideSize};
        box.getChildren().addAll(Arrays.asList(array));
        setOnClickAction(box);
        return box;
    }

    private void setOnClickAction(VBox box) {
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ImageView view = (ImageView)box.getChildren().get(1);
                if (view.getImage().equals(DECK_PIC1)) {
                    resetSelectOthers();
                    view.setImage(DECK_PIC2);
                }
                else view.setImage(DECK_PIC1);

            }
        });
    }

    private void resetSelectOthers() {
        for (Node box : choiceBox.getChildren()) {
            ((ImageView)((VBox) box).getChildren().get(1)).setImage(DECK_PIC1);
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

    private ImageView getImageView(double height,double width,Image image) {
        ImageView view = new ImageView();
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setImage(image);
        return view;
    }


}
