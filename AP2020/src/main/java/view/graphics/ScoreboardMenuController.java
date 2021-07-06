package view.graphics;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import model.game.Board;
import model.game.Cell;

import java.net.URL;
import java.util.*;

public class ScoreboardMenuController extends SearchMenu implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    protected void search(String searchText) {

    }

    @Override
    protected ArrayList<VBox> getSearchResults(ArrayList<String> searchResults) {
        return null;
    }

    public static void main(String[] args) {
        int m = 10, n = 10, w = 0, y = 0;
        if (w == 0 && y == 0) {
//0
        } else if (w == 0 && y == n - 1) {
//1
        } else if (w == m-1 && y ==0) {
        } else if (w == m-1 && y == n-1) {
        } else if (w ==0) {
        } else if (w == m-1) {
        } else if (y == 0) {
        } else if (y == n-1) {
        } else {

        }
     }
}
