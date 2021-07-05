package view.graphics.deckmenu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.deck.Deck;
import model.enums.Cursor;
import view.graphics.ChoiceMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDeckMenu extends ChoiceMenu implements Initializable {
    private Deck deck;

    @FXML private VBox mainDeckOptionBar;
    @FXML private Label nameLabel;
    @FXML private ToggleButton sideToggle;
    @FXML private ToggleButton mainToggle;
    @FXML private VBox sideDeckOptionBar;

    @Override
    protected VBox getChoiceBox(String result) {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(mainToggle,sideToggle);
        mainToggle.setSelected(true);
        mainToggle.setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(138, 138, 138, 1), 0.5, 0.0, 1, 0));
        mainToggle.setOnAction(actionEvent -> onSelectToggle(mainToggle,group));
        sideToggle.setOnAction(actionEvent -> onSelectToggle(sideToggle,group));
    }



    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
