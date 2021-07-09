package view.graphics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.enums.Cursor;
import model.enums.VoiceEffects;

import java.net.URL;
import java.util.ResourceBundle;



public class SettingController extends Menu implements Initializable {
    private static boolean isMute = false;
    private static double sfxVolume = 1;
    private static double volume = 1;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button exitButton;
    @FXML
    private Button importExport;
    @FXML
    private Slider sfxSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ToggleButton muteToggle;

    public static double getSFX() {
        return sfxVolume;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initToggle();
        exitButton.setOnMouseEntered(mouseEvent -> changeCursor(Cursor.CANCEL,mouseEvent));
        exitButton.setOnMouseExited(mouseEvent -> changeCursor(Cursor.DEFAULT,mouseEvent));
        justifyButton(importExport,Cursor.TRASH);
        exitButton.setOnMouseClicked(mouseEvent -> {
            playMedia(VoiceEffects.EXPLODE);
            close();
        });
        sfxSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sfxVolume = t1.floatValue() / 100;
                playMedia(VoiceEffects.SHOOW_2);
            }
        });
    }

    private void initToggle() {
        ToggleGroup group = new ToggleGroup();
        group.getToggles().add(muteToggle);
        onSelectToggle(muteToggle,group);
    }

    private void close() {
        ((Stage)mainPane.getScene().getWindow()).close();
    }

}
