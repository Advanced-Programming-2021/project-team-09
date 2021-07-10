package view.graphics;

import controller.CardCreator;
import controller.database.CSVInfoGetter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.card.Card;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.card.monster.MonsterType;
import model.enums.Cursor;
import model.enums.VoiceEffects;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MonsterCreatorController extends Menu implements Initializable {
    private static ArrayList<String> effectMonsters = new ArrayList<>();
    {
        for (Card card : Card.getAllCards()) {
            if (card.isMonster() && ((Monster)card).hasEffect()) effectMonsters.add(card.getCardName());
        }
    }
    public Label nameLabel;
    public Label attackLabel;
    public Label defenceLabel;
    public Label priceLabel;
    public Button exitButton;
    public TextField nameField;
    public TextArea descriptionField;
    public TextField attackField;
    public TextField defenceField;
    public ChoiceBox effects;
    public Button createButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTextFields();
        initButtons();
        effects.getItems().addAll(effectMonsters);
    }

    private void initButtons() {
        exitButton.setOnMouseEntered(mouseEvent -> changeCursor(Cursor.CANCEL,mouseEvent));
        exitButton.setOnMouseExited(mouseEvent -> changeCursor(Cursor.DEFAULT,mouseEvent));//FIXME
        exitButton.setOnMouseClicked(mouseEvent -> close());
        justifyButton(createButton,Cursor.SWORD);
        createButton.setOnMouseClicked(mouseEvent -> {
            if (isDataValid()) {
                createMonster();
            } else playMedia(VoiceEffects.ERROR);
        });
    }

    private void close() {
        playMedia(VoiceEffects.EXPLODE);
        ((Stage)createButton.getScene().getWindow()).close();
    }
    private void createMonster() {
        int atk = Integer.parseInt(attackField.getText());
        int def = Integer.parseInt(defenceField.getText());
        MonsterCardType type = MonsterCardType.NORMAL;
        String typeEffect = (String) effects.getSelectionModel().getSelectedItem();
        System.out.println(typeEffect);
        //CardCreator.getMonsterPrice(atk,def,null);
        //ToDo
    }

    private boolean isDataValid() {
        if (Card.getCardNames().contains(nameField.getText())) {
            showAlert("ESM ET KHARABE!");
            return false;
        }
        if (!attackField.getText().matches("^\\d+$")) {
            showAlert("ATTACK ET KHARABE!!!");
            return false;
        }

        if (!defenceField.getText().matches("^\\d+$")) {
            showAlert("DEFENCE ET KHARABE!!!");
            return false;
        }

        return true;

    }

    private void initTextFields() {
        nameField.textProperty().addListener(((observableValue, s, t1) -> {
            playMedia(VoiceEffects.KEYBOARD_HIT);
            changeNameField(t1);
        }));
        attackField.textProperty().addListener(((observableValue, s, t1) -> {
            playMedia(VoiceEffects.KEYBOARD_HIT);
            changeAttackField(t1);
        }));
        defenceField.textProperty().addListener(((observableValue, s, t1) -> {
            playMedia(VoiceEffects.KEYBOARD_HIT);
            changeDefField(t1);
        }));
        descriptionField.textProperty().addListener(((observableValue, s, t1) -> playMedia(VoiceEffects.KEYBOARD_HIT)));
    }

    private void changeDefField(String t1) {
        if (t1.matches("^[12]?\\d{1,4}$")) defenceLabel.setText(t1);
        else defenceLabel.setText("?!?");
    }

    private void changeAttackField(String t1) {
        if (t1.matches("^[12]?\\d{1,4}$")) attackField.setText(t1);
        else attackLabel.setText("?!?");
    }

    private void changeNameField(String t1) {
        if (!Card.getCardNames().contains(t1)) nameLabel.setText(t1);
        else nameLabel.setText("MIO!");
    }


}
