package view.graphics;


import controller.LoginMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.responses.LoginMenuResponses;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WelcomeMenuController extends Menu implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;

    //FixMe
    private static final Image BG = new Image(new File("/Users/siasor88/Documents/GitHub/project-team-09/AP2020/src/main/resources/Scenes/Images/backgroundMolaee2.jpg").toURI().toString(),600,370,true,true);
   // private static final Image BG = getImage("Profile","png");
    public void goToSignInMenu(ActionEvent actionEvent) {
    }

    public void login(ActionEvent actionEvent) {
        String password = passwordField.getText();
        String username = usernameField.getText();
        if (password.equals("") || username.equals("")) {
            showMessage("Please Fill all the Fields!");
        } else{
            LoginMenuResponses respond = LoginMenuController.login(username,password);
            showMessage(respond.toString().replace("_", " ") + "!");
            if (respond == LoginMenuResponses.USER_LOGIN_SUCCESSFUL) goToMainMenu();
        }
    }

    public static void  goToMainMenu() {
    }

    public void shadowEffectButtonEnter(MouseEvent mouseEvent) {
        loginButton.setEffect(new DropShadow(1, Color.rgb(80,80,80)));
    }

    public void shadowEffectButtonExits(MouseEvent mouseEvent) {
        loginButton.setEffect(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //menuBar.setOpacity(2);
        Background background = new Background(new BackgroundImage(BG, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(100,100,true,true,true,true)));
        mainPane.setBackground(background);
    }

    public void setFocuse(MouseEvent mouseEvent) {
        loginButton.getScene().getWindow().requestFocus();
    }
}
