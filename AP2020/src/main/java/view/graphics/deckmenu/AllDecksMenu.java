package view.graphics.deckmenu;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.graphics.ChoiceMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class AllDecksMenu extends ChoiceMenu implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    protected VBox getChoiceBox(String result) {
        return null;
    }

    @Override
    protected Button getOptionButton(String searchResult) {
        return null;
    }
}
