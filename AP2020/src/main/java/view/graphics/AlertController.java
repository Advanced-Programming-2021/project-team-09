package view.graphics;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.enums.Media;

public class AlertController {
    @FXML
    private Label text;
    @FXML
    private Button button;
    private Stage popup;

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setStage(Stage popup) {
        this.popup = popup;
        button.setOnMouseClicked(mouseEvent -> popup.close());
    }

    public void showPopUp() {
        if (popup != null) {
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.show();
        }
    }

}
