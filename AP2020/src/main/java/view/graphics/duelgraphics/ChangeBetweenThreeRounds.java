package view.graphics.duelgraphics;

import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.User;
import model.deck.Deck;
import model.deck.MainDeck;
import model.deck.SideDeck;
import view.graphics.ChoiceMenu;

public class ChangeBetweenThreeRounds {
    @FXML
    private Label username;
    @FXML
    private Button swap;
    @FXML
    private HBox mainChoiceBox;
    @FXML
    private HBox sideChoiceBox;
    private User user;
    private Deck deck;
    private SideDeck sideDeck;
    private MainDeck mainDeck;
    private ChoiceMenu mainChoiceMenu;
    private ChoiceMenu sideChoiceMenu;
    public ChangeBetweenThreeRounds(){

    }

    public void initialize(){
        setUsername();
        swap.setDisable(true);

    }



    public void setUser(User user) {
        this.user = user;
    }
    public void setUsername(){
        user = LoginMenuController.getCurrentUser();
        username.setText(user.getUsername());
    }

    public void setDeck() {
        deck = user.getActiveDeck();
        mainDeck = deck.getMainDeck();
        sideDeck = deck.getSideDeck();
    }

    public void done() {
    }

    public void swap() {

    }
}
