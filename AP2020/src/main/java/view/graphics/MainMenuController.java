package view.graphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Menu implements Initializable {

    @FXML
    private Label Title;
    @FXML
    private ImageView imageOfMenu;
    @FXML
    private ImageView gameMenu;
    @FXML
    private ImageView profileMenu;
    @FXML
    private ImageView scoreBoard;
    @FXML
    private ImageView deckMenu;
    @FXML
    private ImageView setting;
    @FXML
    private ImageView BabeFace;
    boolean isHappy = false;

    private final static Image SETTING_MENU_IMG = getImage("SettingIcon","png");
    private final static Image DECK_MENU_IMG = getImage("DeckMenuIcon","png");
    private final static Image PROFILE_MENU_IMG = getImage("ProfileMenuIcon","png");
    private final static Image SCOREBOARD_MENU_IMG = getImage("ScoreboardMenuIcon","png");
    private final static Image HAPPY_FACE_IMG = getImage("HappyFace","png");
    private final static Image NORMAL_FACE_IMG = getImage("NormalFace","png");
    private final static Image GAME_MENU_IMG = getImage("StartGameMenu","png");
    private final static Image GAME_IMG = getImage("Game","png");
    private final static Image SETTING_IMG = getImage("Setting","png");
    private final static Image SCOREBOARD_IMG = getImage("Setting","png");
    private final static Image PROFILE_IMG = getImage("Profile","png");
    private final static Image DECK_IMG = getImage("Deck","png");
    private final static Image SAD_COMPUTER_IMG = getImage("SadComputer","png");


    public void changeFace(MouseEvent mouseEvent) {
        if (isHappy) BabeFace.setImage(NORMAL_FACE_IMG);
        else BabeFace.setImage(HAPPY_FACE_IMG);
        isHappy = ! isHappy;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckMenu.setImage(DECK_MENU_IMG);
        setting.setImage(SETTING_MENU_IMG);
        scoreBoard.setImage(SCOREBOARD_MENU_IMG);
        profileMenu.setImage(PROFILE_MENU_IMG);
        BabeFace.setImage(NORMAL_FACE_IMG);
        gameMenu.setImage(GAME_MENU_IMG);
        imageOfMenu.setImage(SAD_COMPUTER_IMG);
    }

    public void changeGameMenu(MouseEvent mouseEvent) {
        changeFace(null);
        if (isHappy) {
            gameMenu.setEffect(new DropShadow());
            changeMainPicture("Game");
            changeLabel("Game Menu");
        } else {
            resetIcons(gameMenu);
        }
    }

    public void changeProfileMenu(MouseEvent mouseEvent) {
        changeFace(null);
        if (isHappy) {
            profileMenu.setEffect(new DropShadow());
            changeMainPicture("Profile");
            changeLabel("Profile Menu");
        } else {
            resetIcons(profileMenu);
        }
    }

    public void changeScoreboard(MouseEvent mouseEvent) {
        changeFace(null);
        if (isHappy) {
            scoreBoard.setEffect(new DropShadow());
            changeMainPicture("Scoreboard");
            changeLabel("Scoreboard Menu");
        } else {
            resetIcons(scoreBoard);
        }
    }

    public void changeDeckMenu(MouseEvent mouseEvent) {
        changeFace(null);
        if (isHappy) {
            deckMenu.setEffect(new DropShadow());
            changeMainPicture("Deck");
            changeLabel("Deck Menu");
        } else {
            resetIcons(deckMenu);
        }
    }

    public void changeSetting(MouseEvent mouseEvent) {
        changeFace(null);
        if (isHappy) {
            setting.setEffect(new DropShadow());
            changeMainPicture("Setting");
            changeLabel("Setting Menu");
        } else {
            resetIcons(setting);
        }
    }

    public void changeLabel(String message) {
        if (Title.getText().equals("")) Title.setText(message);
        else Title.setText("");
    }

    private void resetIcons(ImageView currentIcon) {
        currentIcon.setEffect(null);
        changeMainPicture("");
        changeLabel("");
    }


    public void changeMainPicture(String nameOfMenu) {
        switch (nameOfMenu) {
            case "Setting": imageOfMenu.setImage(SETTING_IMG); break;
            case "Profile": imageOfMenu.setImage(PROFILE_IMG); break;
            case "Deck": imageOfMenu.setImage(DECK_IMG); break;
            case "Game": imageOfMenu.setImage(GAME_IMG); break;
            case "Scoreboard": imageOfMenu.setImage(SCOREBOARD_IMG); break;
            default: imageOfMenu.setImage(SAD_COMPUTER_IMG);
        }

    }

    public void goToGameMenu(MouseEvent mouseEvent) {
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
    }

    public void goToDeckMenu(MouseEvent mouseEvent) {
    }

    public void goToSetting(MouseEvent mouseEvent) {
    }
}
