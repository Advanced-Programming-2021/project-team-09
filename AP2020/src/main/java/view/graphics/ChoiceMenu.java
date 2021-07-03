package view.graphics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;

abstract public class ChoiceMenu extends SearchMenu{
    @FXML
    public ScrollPane cardsPlace;
    @FXML
    public HBox choiceBox;
    @FXML
    public VBox decisionBox;
    protected HashSet<String> choiceNames = new HashSet<>();
    @Override
    protected void search(String searchText) {
        searchText = searchText.trim().toLowerCase();
        if (searchText.equals("")) {
            resetChoiceBox();
            return;
        }
        HashSet<String> matchingChoices = new HashSet<>();
        for (String choice : choiceNames) if (choice.toLowerCase().contains(searchText)) matchingChoices.add(choice);

        addToChoiceBox(matchingChoices);
    }

    protected void resetChoiceBox() {
        choiceBox.getChildren().removeAll(choiceBox.getChildren());
        choiceBox.setPrefWidth(0);
        choiceBox.setMinWidth(0);
        choiceBox.setMaxWidth(0);
        addToChoiceBox(new HashSet<>(choiceNames));
    }

    protected void addToChoiceBox(HashSet<String> matchingCards) {
        ArrayList<VBox> resultBoxes = getSearchResults(new ArrayList<>(matchingCards));
        choiceBox.getChildren().removeAll(choiceBox.getChildren());
        choiceBox.setPrefWidth(0);
        choiceBox.setMinWidth(0);
        choiceBox.setMaxWidth(0);
        double newWidth = 155 * resultBoxes.size() - 5;
        choiceBox.setMaxWidth(newWidth);
        choiceBox.setMinWidth(newWidth);
        choiceBox.setPrefWidth(newWidth);
        for (VBox box : resultBoxes) {
            choiceBox.getChildren().add(box);
        }
    }

    @Override
    protected ArrayList<VBox> getSearchResults(ArrayList<String> searchResults) {
        ArrayList<VBox> resultBoxes = new ArrayList<>();
        for (String result : searchResults) resultBoxes.add(getChoiceBox(result));
        return resultBoxes;
    }

    

    protected abstract VBox getChoiceBox(String result);

}
