package view.graphics.duelgraphics;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import model.card.Card;
import model.game.Cell;
import view.graphics.Menu;
import view.responses.CardEffectsResponses;

import java.util.ArrayList;
import java.util.Random;

public class CardEffectsViewGraphical {

    public static  boolean doYouWantTo(String message) {
        return false;
        // TODO: 7/10/2021
    }

     public static void respond(CardEffectsResponses response) {
        if (response == CardEffectsResponses.CANT_RITUAL_SUMMON)
            Menu.showAlert("You cant ritual summon !");
        else if (response == CardEffectsResponses.HAVE_NO_CARDS)
            Menu.showAlert("You have no cards !");
        else if (response == CardEffectsResponses.INVALID_CELL_NUMBER)
            Menu.showAlert("Invalid Cell number !");
        else if (response == CardEffectsResponses.MONSTER_ZONE_IS_FULL)
            Menu.showAlert("Monster zone is full !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL)
            Menu.showAlert("Please select a field spell !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_AN_SPELL)
            Menu.showAlert("Please select a spell !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_VALID_TYPE)
            Menu.showAlert("Please select a valid type ! ");
        else if (response == CardEffectsResponses.YOU_DONT_HAVE_ANY_FIELD_SPELL)
            Menu.showAlert("You dont have any field spell !");
        else if (response == CardEffectsResponses.NO_MONSTERS)
            Menu.showAlert("You have no monsters !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_MONSTER)
            Menu.showAlert("Please select monster !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_VALID_NUMBER)
            Menu.showAlert("Please select a valid number !");
        else if (response == CardEffectsResponses.SPECIAL_SUMMON_NOW)
            Menu.showAlert("You have to special summon now !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_LEVEL_7_OR_MORE)
            Menu.showAlert("Please select level 7 or more monster !");
        else if (response == CardEffectsResponses.PLEASE_SELECT_A_VALID_MONSTER)
            Menu.showAlert("Please select a valid monster !");
        else if (response == CardEffectsResponses.CANT_ACTIVATE_TRAP)
            Menu.showAlert("You cant activate trap !");
    }

    private static Card chooseCardFromList(ArrayList<Card> cards, String message) {
        if (cards.size() == 0) return null;
        Cell chosenCell = new Cell();
        ArrayList<Node> nodes = Menu.getRectangleAndButtonForGameMenus("Choose!");
        Pane pane = (Pane) Main.stage.getScene().getRoot();
        pane.getChildren().add(nodes.get(0));
        Pane newPane = Menu.copyPane(pane);
        ImageView iv = new ImageView(Menu.getImage("yu-gi-ohs-best-and-worst-girl-role-models", "png"));
        iv.setY(0);
        iv.setX(0);
        iv.setFitHeight(700);
        iv.setFitWidth(700);
        iv.setOpacity(0.18);
        Label label = new Label(message);
        newPane.getChildren().add(nodes.get(1));
        label.setStyle("-fx-background-color: transparent;-fx-font: 19px Chalkboard;-fx-text-fill: green;");
        label.setWrapText(true);
        label.setLayoutX(10);
        label.setLayoutY(100);
        newPane.getChildren().add(label);
        Stage newStage = Menu.copyStage(Main.stage);
        newStage.setScene(new Scene(newPane));
        Label labelForNumCards = new Label("1/" + cards.size());
        newPane.getChildren().add(nodes.get(1));
        labelForNumCards.setStyle("-fx-background-color: transparent;-fx-font: 19px Chalkboard;-fx-text-fill: green;");
        labelForNumCards.setWrapText(true);
        labelForNumCards.setLayoutX(10);
        labelForNumCards.setLayoutY(100);
        newPane.getChildren().add(label);
        ImageView cardPlace = new ImageView(Menu.getCard(cards.get(0).getCardName()));
        cardPlace.setFitHeight(300);
        cardPlace.setFitWidth(210);
        cardPlace.setX(245);
        cardPlace.setY(300);
        cardPlace.setOnMouseClicked(mouseEvent -> {
            cardPlace.setImage(Menu.getCard(cards.get(Integer.parseInt(labelForNumCards.getText().split("/")[0]) % cards.size()).getCardName()));
            labelForNumCards.setText(((Integer.parseInt(labelForNumCards.getText().split("/")[0]) % cards.size()) + 1) + "/" + cards.size());
        });
        Button button = (Button) nodes.get(1);
        button.setOnMouseClicked(mouseEvent -> {
            chosenCell.addCard(cards.get(Integer.parseInt(labelForNumCards.getText().split("/")[0]) - 1));
            newStage.close();
        });
        newStage.showAndWait();
        pane.getChildren().remove(nodes.get(0));
        if (chosenCell.getCard() == null) return cards.get(new Random().nextInt(cards.size()));
        return chosenCell.getCard();
    }
}
