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
    private int spacing = 5;
    private int width = 155;

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
        double newWidth = width * resultBoxes.size() - spacing;
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

    protected void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected abstract VBox getChoiceBox(String result);

    protected void addOptionToDecisionBox(Button button,Button... args){
        decisionBox.getChildren().add(button);
        if (args.length != 0) decisionBox.getChildren().addAll(args);
    }
    protected void emptyDecisionBox() {
        decisionBox.getChildren().removeAll(decisionBox.getChildren());
    }

    protected void updateChoiceBox() {
        if (searchField.getText().length() == 0) resetChoiceBox();
        else search(searchField.getText());
    }
}
