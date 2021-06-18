package view.graphics.profile;

import controller.LoginMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.enums.Cursor;
import view.LoginMenu;
import view.graphics.MainMenuController;
import view.graphics.Menu;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenu extends Menu implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Button changePassButton;
    @FXML
    private Button changeNickButton;
    @FXML
    private Button changeProfButton;
    @FXML
    private Label AKALabel;
    @FXML
    private Circle imagePlace;
    @FXML
    private Label username;
    @FXML
    private Label nickname;

    public static Image getProfilePhoto(int number) {
        return getImage("ProfilePhotos/Profile" + number, "png");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AKALabel.setStyle("-fx-font: Bold 12px Chalkboard;");
        setImageInCircle(LoginMenuController.getCurrentUser().getProfilePhoto());
        username.setText(LoginMenuController.getCurrentUser().getUsername());
        nickname.setText(LoginMenuController.getCurrentUser().getNickname());
        ImageView imageView = new ImageView(MainMenuController.SAD_COMPUTER_IMG);
        imageView.setFitHeight(370);
        imageView.setFitWidth(340);
        imageView.setPreserveRatio(true);
        mainPane.setCenter(imageView);
    }

    private void setImageInCircle(int number) {
        imagePlace.setFill(new ImagePattern(getProfilePhoto(number)));
        DropShadow shadow = new DropShadow();
        shadow.offsetXProperty().setValue(0.5);
        shadow.offsetYProperty().setValue(0.5);
        imagePlace.setEffect(shadow);
    }

    public void pressChangeNick(ActionEvent actionEvent) {
        setCenter("ChangeNick");
    }

    public void pressChangeProf(ActionEvent actionEvent) {
        setCenter("ChangeProf");
    }

    public void pressChangePass(ActionEvent actionEvent) {
        setCenter("ChangePass");
    }

    private void setCenter(String nodeName) {
        try {
            VBox center = (VBox) getNode(nodeName);
            mainPane.setCenter(center);
        } catch (Exception ignored) {
        }
    }

    public void enterChangePass(MouseEvent mouseEvent) {
        enterButton(changePassButton,Cursor.CHANGE_PASS,mouseEvent);
    }

    public void enterChangeNick(MouseEvent mouseEvent) {
        enterButton(changeNickButton,Cursor.HASHTAG,mouseEvent);
    }

    public void enterChangeProf(MouseEvent mouseEvent) {
        enterButton(changeProfButton,Cursor.PROFILE,mouseEvent);
    }

    public void exitChangePass(MouseEvent mouseEvent) {
        exitButton(changePassButton,mouseEvent);
    }

    public void exitChangeNick(MouseEvent mouseEvent) {
        exitButton(changeNickButton,mouseEvent);
    }

    public void exitChangeProf(MouseEvent mouseEvent) {
        exitButton(changeProfButton,mouseEvent);
    }

}
