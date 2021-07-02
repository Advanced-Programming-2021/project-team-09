package view.graphics.deckmenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import view.graphics.profile.SearchMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class MainDeckMenu extends SearchMenu implements Initializable {
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;

    @Override
    protected void search(String searchText) {

    }

    @Override
    protected Button getOptionButton(String searchResult) {
        return null;
    }

    public void previous(ActionEvent actionEvent) {
    }

    public void next(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
