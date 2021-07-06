package controller;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.card.Card;
import model.game.Game;
import view.duelMenu.Phase;
import view.graphics.Menu;

import java.util.ArrayList;
import java.util.Arrays;


public class GraphicalGameController {

    private final Button SELECT = new Button("Select !");
    private final Button ATTACK = new Button("Attack Monster !");
    private final Button DIRECT_ATTACK = new Button("Direct Attack !");
    // TODO: 7/4/2021

    private final Pane pane;
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
    private Phase currentPhase = Phase.DRAW_PHASE;
    //private final boolean[][] isSelected = new boolean[4][5]; // 1: playerMonster, 2 : playerSpells, 3 : rivalmonster, 4 : rivalSpells

    private class MoveTransition extends Transition{
        final double xDes, yDes, xStart, yStart;
        final ImageView imageView;

        public MoveTransition(double xDes, double yDes, double xStart, double yStart, ImageView imageView) {
            this.xDes = xDes;
            this.yDes = yDes;
            this.xStart = xStart;
            this.yStart = yStart;
            this.imageView = imageView;
            this.setInterpolator(Interpolator.EASE_OUT);
            this.setCycleDuration(Duration.millis(2000));
            this.setCycleCount(1);
        }

        @Override
        protected void interpolate(double v) {
            imageView.setX(xStart + (xDes - xStart)*v);
            imageView.setY(yStart + (yDes - yStart)*v);
        }
    }



    public GraphicalGameController(ImageView[] playerMonsters, ImageView[] playerSpells,
                                   ImageView[] rivalMonsters, ImageView[] rivalSpells,
                                   HBox playerCards, HBox rivalCards, VBox options,
                                   ImageView playerFieldSpell, ImageView playerGraveYard,
                                   ImageView rivalFieldSpell, ImageView rivalGraveYard, Game game, Pane pane) {
        this.pane = pane;
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
        setImageFunctionality();
        loadCardHands();
    }

    private void loadCardHands() {
        ArrayList<Card> cards = game.getPlayerHandCards();
        for (Card card : cards) {
            playerCards.getChildren().add(Menu.getImageWithSizeForGame(GameMenuController.trimName(card.getCardName()), 0, 0));
        }
        for (Node child : playerCards.getChildren()) {
            VBox.setMargin(child, new Insets(0, 0, 0, 5));
        }
        cards = game.getRivalHandCards();
        for (Card card : cards) {
            rivalCards.getChildren().add(Menu.getImageWithSizeForGame(GameMenuController.trimName(card.getCardName()), 0, 0));
        }
        for (Node child : rivalCards.getChildren()) {
            VBox.setMargin(child, new Insets(0, 0, 0, 5));
        }
    }

    private void setImageFunctionality() {
        ArrayList<ImageView> images = new ArrayList<>(Arrays.asList(playerMonsters));
        images.addAll(Arrays.asList(playerSpells));
        images.addAll(Arrays.asList(rivalMonsters));
        images.addAll(Arrays.asList(rivalSpells));
        images.add(playerFieldSpell);
        images.add(rivalFieldSpell);
        images.add(playerGraveYard);
        images.add(rivalGraveYard);
        for (ImageView image : images) setImageSelectable(image);
    }

    private void setImageSelectable(ImageView image) {
        image.setOnMouseClicked(mouseEvent -> {
            resetImages();
            if (((ImageView)mouseEvent.getSource()).getImage() != null)
                ((ImageView) mouseEvent.getSource()).setEffect(new DropShadow(40, Color.RED));
        });
    }

    private void resetImages() {
        for (ImageView playerMonster : playerMonsters) {
            playerMonster.setEffect(null);
        }
        for (ImageView playerSpell : playerSpells) {
            playerSpell.setEffect(null);
        }
        for (ImageView rivalMonster : rivalMonsters) {
            rivalMonster.setEffect(null);
        }
        for (ImageView rivalSpell : rivalSpells) {
            rivalSpell.setEffect(null);
        }
        rivalFieldSpell.setEffect(null);
        rivalGraveYard.setEffect(null);
        playerFieldSpell.setEffect(null);
        playerGraveYard.setEffect(null);
    }

    public void movePlayerMonsterToGraveYard(int num) {
        ImageView imageView = Menu.getImageWithSizeForGame(playerMonsters[num].getImage(), playerMonsters[num].getLayoutX(),
                playerMonsters[num].getLayoutY());
        pane.getChildren().add(imageView);
        MoveTransition moveTransition = new MoveTransition(playerGraveYard.getLayoutX(),
                playerGraveYard.getLayoutY(), playerMonsters[num].getLayoutX(), playerMonsters[num].getLayoutY(), imageView);
        moveTransition.play();
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
