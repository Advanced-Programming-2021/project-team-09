package controller;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;
import model.card.Card;
import model.game.Cell;
import model.game.Game;
import view.graphics.Menu;

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

    private class MoveToGraveYardTransition extends Transition{
        final double gyX, gyY, ivX, ivY;
        final ImageView imageView;

        public MoveToGraveYardTransition(double gyX, double gyY, double ivX, double ivY, ImageView imageView) {
            this.gyX = gyX;
            this.gyY = gyY;
            this.ivX = ivX;
            this.ivY = ivY;
            this.imageView = imageView;
            this.setInterpolator(Interpolator.EASE_OUT);
            this.setCycleDuration(Duration.millis(1000));
            this.setCycleCount(1);
        }

        @Override
        protected void interpolate(double v) {
            imageView.setLayoutX(ivX + (gyX - ivX)*v);
            imageView.setLayoutY(ivY + (gyY - ivY)*v);
        }
    }



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
        // TODO: 7/4/2021 delete thses :
        playerCards.getChildren().add(new ImageView(Menu.getCardImage("BattleOx")));
//        movePlayerMonsterToGraveYard(0);
    }

    public void movePlayerMonsterToGraveYard(int num) {
        ImageView imageView = new ImageView(playerMonsters[num].getImage());
        imageView.setLayoutX(playerMonsters[num].getLayoutX());
        imageView.setLayoutY(playerMonsters[num].getLayoutY());
        MoveToGraveYardTransition moveToGraveYardTransition = new MoveToGraveYardTransition(playerGraveYard.getX(),
                playerGraveYard.getY(), playerMonsters[num].getX(), playerMonsters[num].getY(), imageView);
        moveToGraveYardTransition.play();
    }

    public void updateRivalSpells() {

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
