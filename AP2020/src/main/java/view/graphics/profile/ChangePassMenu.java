package view.graphics.profile;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Cursor;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassMenu extends ChangeMenu{

    @Override
    public void change() {

    }

    public void enterChangeButton(MouseEvent mouseEvent) {
        enterButton(changeButton, Cursor.ACCEPT,mouseEvent);
    }

    public void exitChangeButton(MouseEvent mouseEvent) {
        exitButton(changeButton,mouseEvent);
    }
}
