package view.graphics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;

public class AlertController {
    @FXML
    private Label text;
    @FXML
    private Button button;
    private Popup popup;
    public void setText(String text) {
        this.text.setText(text);
    }
    public void setPopup(Popup popup) {
        this.popup = popup;
        button.setOnMouseClicked(mouseEvent -> popup.hide());
    }

}
