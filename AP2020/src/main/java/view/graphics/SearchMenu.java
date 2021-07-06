package view.graphics;

import controller.database.CSVInfoGetter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.card.Card;
import model.enums.Cursor;
import view.graphics.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SearchMenu extends Menu {

    @FXML
    protected VBox searchBox;
    @FXML
    protected TextField searchField;
    @FXML
    protected Label stageCounter;

    protected ArrayList<VBox> searchResults = new ArrayList<>();

    protected abstract void search(String searchText);

    protected abstract ArrayList<VBox> getSearchResults(ArrayList<String> searchResults);


    protected int getCurrentSearchStage() {
        String currentSearchStage = stageCounter.getText().split("/")[0];
        try {
            return Integer.parseInt(currentSearchStage);
        } catch (Exception e) {
            return -1;
        }
    }

    protected void showVBox(int index) {
        if (index >= searchResults.size() || index < 0) return;
        ObservableList<Node> list = searchBox.getChildren();
        emptySearchBox();
        list.add(searchResults.get(index));
        setStageLabel(index + 1);
    }

    protected void emptySearchBox() {
        ObservableList<Node> list = searchBox.getChildren();
        if (list.size() != 1) list.remove(list.get(1));
    }

    protected void setStageLabel(int i) {
        stageCounter.setText(i + "/" + searchResults.size());
    }
}
