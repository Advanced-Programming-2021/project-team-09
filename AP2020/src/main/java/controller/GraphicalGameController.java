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
import model.game.Cell;
import model.game.Game;
import model.game.State;
import view.duelMenu.Phase;
import view.graphics.Menu;

import java.util.ArrayList;
import java.util.Arrays;


public class GraphicalGameController {

    private final Button ATTACK = new Button("Attack Monster !");
    private final Button DIRECT_ATTACK = new Button("Direct Attack !");
    private final Button NEXT_PHASE = new Button("Next phase!");
    private final Button ACTIVE_EFFECT = new Button("Active Effect!");
    private final Button SET = new Button("Set!");
    private final Button FLIP_SUMMON = new Button("Flip Summon!");
    private final Button SHOW_CARD = new Button("Show Card!");
    private final Button SHOW_GRAVEYARD = new Button("Show GraveYard!");
    private final Button SET_ATTACK_POSITION = new Button("Set Attack!");
    private final Button SET_DEFENSE_POSITION = new Button("Set Defense!");
    private final Button SURRENDER = new Button("Surrender!");

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

    // only in the beginning of the game
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
            resetImageEffects();
            if (((ImageView) mouseEvent.getSource()).getImage() != null)
                ((ImageView) mouseEvent.getSource()).setEffect(new DropShadow(40, Color.RED));
            updateButtons();
        });
    }

    private void updateButtons() {
        removeOptions();
        if (playerMonsterIsSelected()) {
            playerMonsterButtons();
        } else if (playerSpellIsSelected()) {
            playerSpellsButtons();
        } else if (rivalMonsterIsSelected()) {
            rivalMonstersButtons();
        } else if (rivalSpellIsSelected()) {
            rivalSpellsButtons();
        } else if (playerFieldSpellIsSelected()) {
            playerFieldSpellButtons();
        } else if (playerGraveYardIsSelected()) {
            playerGraveYardButtons();
        } else if (rivalFieldSpellIsSelected()) {
            rivalFieldSpellButtons();
        } else if (rivalGraveYardIsSelected()) {
            rivalGraveYardButtons();
        }
    }

    private void removeOptions() {
        for (Node child : options.getChildren()) {
            options.getChildren().remove(child);
        }
    }

    private boolean rivalGraveYardIsSelected() {
        return rivalGraveYard.getImage() != null && rivalGraveYard.getEffect() != null;
    }

    private boolean rivalFieldSpellIsSelected() {
        return rivalFieldSpell.getImage() != null && rivalFieldSpell.getEffect() != null;
    }

    private boolean playerGraveYardIsSelected() {
        return playerGraveYard.getImage() != null && playerGraveYard.getEffect() != null;
    }

    private boolean playerFieldSpellIsSelected() {
        return playerFieldSpell.getImage() != null && playerFieldSpell.getEffect() != null;
    }

    private boolean rivalSpellIsSelected() {
        for (ImageView imageView : rivalSpells) {
            if (imageView.getImage() != null && imageView.getEffect() != null) return true;
        }
        return false;
    }

    private boolean rivalMonsterIsSelected() {
        for (ImageView imageView : rivalMonsters) {
            if (imageView.getImage() != null && imageView.getEffect() != null) return true;
        }
        return false;
    }

    private boolean playerSpellIsSelected() {
        for (ImageView imageView : playerSpells) {
            if (imageView.getImage() != null && imageView.getEffect() != null) return true;
        }
        return false;
    }

    private boolean playerMonsterIsSelected() {
        for (ImageView imageView : playerMonsters) {
            if (imageView.getImage() != null && imageView.getEffect() != null) return true;
        }
        return false;
    }

    public void playerMonsterButtons() {
        int i = 0;
        for (int i1 = 0; i1 < playerMonsters.length; i1++) {
            if (playerMonsters[i1].getImage() != null && playerMonsters[i1].getEffect() != null) {
                i = i1;
                break;
            }
        }
        Cell cell = game.getPlayerBoard().getMonsterZone(i);
        if (!cell.isOccupied()) {
            options.getChildren().addAll(NEXT_PHASE, SURRENDER);
            return;
        }
        State cellState = cell.getState();
        if (currentPhase == Phase.BATTLE_PHASE && cellState == State.FACE_UP_ATTACK) {
            options.getChildren().addAll(ATTACK, DIRECT_ATTACK);
        }
        if (cellState != State.FACE_DOWN_DEFENCE && (currentPhase == Phase.MAIN_PHASE1 || currentPhase == Phase.MAIN_PHASE2)) {
            options.getChildren().addAll(SET_ATTACK_POSITION, SET_DEFENSE_POSITION);
        }
        options.getChildren().addAll(SHOW_CARD, NEXT_PHASE, SURRENDER);
    }

    private void resetImageEffects() {
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

    private class MoveTransition extends Transition {
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
            imageView.setX(xStart + (xDes - xStart) * v);
            imageView.setY(yStart + (yDes - yStart) * v);
        }
    }
}
