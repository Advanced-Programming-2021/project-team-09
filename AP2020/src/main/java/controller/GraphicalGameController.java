package controller;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.card.Card;
import model.game.Cell;
import model.game.Game;

import java.util.ArrayList;


public class GraphicalGameController {

    private final Button SELECT = new Button("Select !");
    private final Button ATTACK = new Button("Attack Monster !");
    private final Button DIRECT_ATTACK = new Button("Direct Attack !");
    // TODO: 7/4/2021

    private final ImageView[] playerMonsters;
    private final ImageView[] playerSpells;
    private final ImageView[] rivalMonsters;
    private final ImageView[] rivalSpells;
    private final HBox playerCards;
    private final HBox rivalCards;
    private final VBox options;
    private final ImageView playerFieldSpell;
    private final ImageView playerGraveYard;
    private final ImageView rivalFieldSpell;
    private final ImageView rivalGraveYard;
    private final Game game;



    public GraphicalGameController(ImageView[] playerMonsters, ImageView[] playerSpells,
                                   ImageView[] rivalMonsters, ImageView[] rivalSpells,
                                   HBox playerCards, HBox rivalCards, VBox options,
                                   ImageView playerFieldSpell, ImageView playerGraveYard,
                                   ImageView rivalFieldSpell, ImageView rivalGraveYard, Game game) {
        this.playerMonsters = playerMonsters;
        this.playerSpells = playerSpells;
        this.options = options;
        this.rivalMonsters = rivalMonsters;
        this.rivalCards = rivalCards;
        this.rivalSpells = rivalSpells;
        this.playerCards = playerCards;
        this.playerFieldSpell = playerFieldSpell;
        this.playerGraveYard = playerGraveYard;
        this.rivalFieldSpell = rivalFieldSpell;
        this.rivalGraveYard = rivalGraveYard;
        this.game = game;
        updateRivalHand();
        updatePlayerHand();
        updatePlayerMonsters();
        updatePlayerSpells();
        updateRivalMonsters();
        updateRivalSpells();
    }

    public void updateRivalSpells() {
        Cell[] cells = game.getRivalBoard().getSpellZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                rivalSpells[i].getImage().getUrl();
            }
        }
    }

    public void updatePlayerSpells() {

    }

    public void updateRivalMonsters() {

    }

    public void updatePlayerMonsters() {

    }

    public void updatePlayerHand() {

    }

    public void updateRivalHand() {
    }
}
