package controller;

import controller.database.CSVInfoGetter;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.card.Card;
import model.deck.Graveyard;
import model.exceptions.GameException;
import model.exceptions.WinnerException;
import model.game.Cell;
import model.game.Game;
import model.game.State;
import view.duelMenu.Phase;
import view.graphics.Menu;
import view.responses.GameMenuResponse;

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
    private final Button SUMMON = new Button("Summon!");
    {
        ATTACK.setOnMouseClicked(this::attack);
        DIRECT_ATTACK.setOnMouseClicked(this::directAttack);
        NEXT_PHASE.setOnMouseClicked(this::nextPhase);
        ACTIVE_EFFECT.setOnMouseClicked(this::activeEffect);
        SET.setOnMouseClicked(this::set);
        FLIP_SUMMON.setOnMouseClicked(this::flipSummon);
        SHOW_CARD.setOnMouseClicked(this::showCard);
        SHOW_GRAVEYARD.setOnMouseClicked(this::showGraveYard);
        SET_ATTACK_POSITION.setOnMouseClicked(this::setAttackPosition);
        SET_DEFENSE_POSITION.setOnMouseClicked(this::setDefensePosition);
        SURRENDER.setOnMouseClicked(this::surrender);
        SUMMON.setOnMouseClicked(this::summon);
    }

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
    private final Label playerName;
    private final Label playerHealth;
    private final Label rivalName;
    private final Label rivalHealth;
    private final Game game;
    private Phase currentPhase = Phase.DRAW_PHASE;
    //private final boolean[][] isSelected = new boolean[4][5]; // 1: playerMonster, 2 : playerSpells, 3 : rivalmonster, 4 : rivalSpells

    public GraphicalGameController(ImageView[] playerMonsters, ImageView[] playerSpells,
                                   ImageView[] rivalMonsters, ImageView[] rivalSpells,
                                   HBox playerCards, HBox rivalCards, VBox options,
                                   ImageView playerFieldSpell, ImageView playerGraveYard,
                                   ImageView rivalFieldSpell, ImageView rivalGraveYard, Game game, Pane pane,
                                   Label playerName, Label playerHealth, Label rivalName, Label rivalHealth) {
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
        this.playerName = playerName;
        this.playerHealth = playerHealth;
        this.rivalName = rivalName;
        this.rivalHealth = rivalHealth;
        this.game = game;
        setImageFunctionality();
        loadCardHands();
        loadNames();
        updateWithoutTransition();
    }

    // only in the beginning of the game
    private void loadNames() {
        playerName.setText(game.getPlayer().getNickname());
        playerHealth.setText(game.getPlayerLP() + "");
        rivalName.setText(game.getRival().getNickname());
        rivalHealth.setText(game.getRivalLP() + "");
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

    // doesnt work for hand cards ..
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
        // todo hand cards
    }

    private void removeOptions() {
        for (int i = options.getChildren().size() - 1; i >= 0; i--) {
            options.getChildren().remove(i);
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

    public void playerSpellsButtons() {
        int i = 0;
        for (int i1 = 0; i1 < playerSpells.length; i1++) {
            if (playerSpells[i1].getImage() != null && playerSpells[i1].getEffect() != null) {
                i = i1;
                break;
            }
        }
        Cell cell = game.getPlayerBoard().getSpellZone(i);
        if (!cell.isOccupied()) {
            options.getChildren().addAll(NEXT_PHASE, SURRENDER);
            return;
        }
        State cellState = cell.getState();
        if (currentPhase == Phase.MAIN_PHASE1 || currentPhase == Phase.MAIN_PHASE2) {
            options.getChildren().add(ACTIVE_EFFECT);
        }
        options.getChildren().addAll(SHOW_CARD, NEXT_PHASE, SURRENDER);
    }

    public void rivalMonstersButtons() {
        int i = 0;
        for (int i1 = 0; i1 < rivalMonsters.length; i1++) {
            if (rivalMonsters[i1].getImage() != null && rivalMonsters[i1].getEffect() != null) {
                i = i1;
                break;
            }
        }
        Cell cell = game.getRivalBoard().getMonsterZone(i);
        if (!cell.isOccupied()) {
            options.getChildren().addAll(NEXT_PHASE, SURRENDER);
            return;
        }
        State cellState = cell.getState();
        if (cellState != State.FACE_DOWN_DEFENCE) options.getChildren().add(SHOW_CARD);
        options.getChildren().addAll(NEXT_PHASE, SURRENDER);
    }

    public void rivalSpellsButtons() {
        int i = 0;
        for (int i1 = 0; i1 < rivalSpells.length; i1++) {
            if (rivalSpells[i1].getImage() != null && rivalSpells[i1].getEffect() != null) {
                i = i1;
                break;
            }
        }
        Cell cell = game.getRivalBoard().getSpellZone(i);
        if (!cell.isOccupied()) {
            options.getChildren().addAll(NEXT_PHASE, SURRENDER);
            return;
        }
        State cellState = cell.getState();
        if (cellState == State.FACE_UP_SPELL) options.getChildren().add(SHOW_CARD);
        options.getChildren().addAll(NEXT_PHASE, SURRENDER);
    }

    public void playerFieldSpellButtons() {
        if (game.getPlayerBoard().getFieldZone().isOccupied()) options.getChildren().add(SHOW_CARD);
        options.getChildren().addAll(NEXT_PHASE, SURRENDER);
    }

    public void playerGraveYardButtons() {
        options.getChildren().addAll(SHOW_GRAVEYARD, NEXT_PHASE, SURRENDER);
    }

    public void rivalGraveYardButtons() {
        playerGraveYardButtons();
    }

    public void rivalFieldSpellButtons() {
        if (game.getRivalBoard().getFieldZone().isOccupied()) options.getChildren().add(SHOW_CARD);
        options.getChildren().addAll(NEXT_PHASE, SURRENDER);
    }

    public void summon() {
        // TODO: 7/7/2021
    }
    
    public void attack(MouseEvent mouseEvent) {
        int attacker = -1;
        for (int i = 0; i < 5; i++) {
            if (playerMonsters[i].getEffect() != null) {
                attacker = i;
            }
        }
        if (attacker == -1) return;
        final int attackFinal = attacker;
        Rectangle rectangle = new Rectangle();
        rectangle.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
        rectangle.setHeight(700);
        rectangle.setWidth(700);
        rectangle.setX(0);
        rectangle.setY(0);
        pane.getChildren().add(rectangle);
        ImageView[] images = new ImageView[5];
        for (int i = 0; i <5; i++) {
            images[i] = Menu.getImageWithSizeForGame(rivalMonsters[i].getImage(), 75*i + 200, 200);
            pane.getChildren().add(images[i]);
        }
        Button attackBtn = new Button("Attack");
        pane.getChildren().add(attackBtn);
        rectangle.setOnMouseClicked(mouseEvent1 -> {
            pane.getChildren().removeAll(Arrays.asList(images));
            pane.getChildren().removeAll(rectangle, attackBtn);
        });
        attackBtn.setDisable(true);
        for (ImageView image : images) {
            image.setOnMouseClicked(mouseEvent1 -> {
                attackBtn.setDisable(false);
                for (ImageView imageView : images) {
                    imageView.setEffect(null);
                }
                if (((ImageView)mouseEvent1.getSource()).getImage() != null)
                    ((ImageView)mouseEvent1.getSource()).setEffect(new DropShadow(40, Color.GREEN));
            });
        }
        attackBtn.setOnMouseClicked(mouseEvent1 -> {
            int defender = -1;
            for (int i = 0; i < 5; i++) {
                if (images[i].getEffect() != null) defender = i;
            }
            if (defender == -1) return;
            if (!game.getRivalBoard().getMonsterZone(defender).isOccupied()) return;
            pane.getChildren().removeAll(Arrays.asList(images));
            pane.getChildren().removeAll(rectangle, attackBtn);
            getSelectedImageView().setEffect(null);
            removeOptions();
            try {
                GameMenuResponse gameMenuResponse = GameMenuController.attack(game, attackFinal + 1, defender + 1, false);
                // TODO: 7/7/2021
            } catch (GameException gameException) {
                if (gameException instanceof WinnerException) ; // TODO: 7/7/2021
            }
        });
    }
    
    public void directAttack(MouseEvent mouseEvent) {
        int attacker = -1;
        for (int i = 0; i < 5; i++) {
            if (playerMonsters[i].getEffect() != null) {
                attacker = i;
            }
        }
        if (attacker == -1) return;
        try {
            GameMenuResponse gameMenuResponse = GameMenuController.directAttack(game, attacker + 1);
        } catch (WinnerException winnerException) {
            // TODO: 7/7/2021
        }
    }
    
    public void set(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void nextPhase(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void activeEffect(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void flipSummon(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void showCard(MouseEvent mouseEvent) {
        Rectangle rectangle = new Rectangle();
        rectangle.setStyle("-fx-background-color: rgba(255,255,255,0.4)");
        rectangle.setHeight(700);
        rectangle.setWidth(700);
        rectangle.setX(0);
        rectangle.setY(0);
        ImageView imageView = new ImageView(getSelectedImageView().getImage());
        imageView.setX(280);
        imageView.setY(200);
        imageView.setFitWidth(140);
        imageView.setFitHeight(200);
        pane.getChildren().addAll(rectangle, imageView);
        EventHandler<MouseEvent> eventHandler = mouseEvent1 -> pane.getChildren().removeAll(imageView, rectangle);
        rectangle.setOnMouseClicked(eventHandler);
        imageView.setOnMouseClicked(eventHandler);
    }
    
    public ImageView getSelectedImageView() {
        for (ImageView playerMonster : playerMonsters) {
            if (playerMonster.getEffect() != null) return playerMonster;
        }
        for (ImageView playerSpell : playerSpells) {
            if (playerSpell.getEffect() != null) return playerSpell;
        }
        for (ImageView rivalMonster : rivalMonsters) {
            if (rivalMonster.getEffect() != null) return rivalMonster;
        }
        for (ImageView rivalSpell : rivalSpells) {
            if (rivalSpell.getEffect() != null) return rivalSpell;
        }
        if (rivalFieldSpell.getEffect() != null) return rivalFieldSpell;
        if (rivalGraveYard.getEffect() != null) return rivalGraveYard;
        if (playerFieldSpell.getEffect() != null) return playerFieldSpell;
        return playerGraveYard;
    }
    
    public void showGraveYard(MouseEvent mouseEvent) {
        if (playerGraveYard.getEffect() != null) {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Paint.valueOf("WHITE"));
            rectangle.setOpacity(0.36);
            rectangle.setHeight(700);
            rectangle.setWidth(700);
            rectangle.setX(0);
            rectangle.setY(0);
            if (game.getPlayerBoard().getGraveyard().getCards().size() == 0) {
                ImageView imageView = new ImageView(getSelectedImageView().getImage());
                imageView.setX(280);
                imageView.setY(200);
                imageView.setFitWidth(140);
                imageView.setFitHeight(200);
                pane.getChildren().addAll(rectangle, imageView);
                EventHandler<MouseEvent> eventHandler = mouseEvent1 -> pane.getChildren().removeAll(imageView, rectangle);
                rectangle.setOnMouseClicked(eventHandler);
                imageView.setOnMouseClicked(eventHandler);
            } else {
                Label label = new Label("1/" + game.getPlayerBoard().getGraveyard().getCards().size());
                label.setStyle("-fx-text-fill: green; -fx-font-size: 20px; -fx-background-color: rgba(255,255,255,0.44)");
                label.setLayoutX(340);
                label.setLayoutY(130);
                ImageView imageView = new ImageView(Menu.getCard(GameMenuController.trimName(game.getPlayerBoard().getGraveyard().getCards().get(0).getCardName())));
                imageView.setX(280);
                imageView.setY(200);
                imageView.setFitWidth(140);
                imageView.setFitHeight(200);
                pane.getChildren().addAll(rectangle, imageView, label);
                EventHandler<MouseEvent> eventHandler = mouseEvent1 -> pane.getChildren().removeAll(imageView, rectangle, label);
                rectangle.setOnMouseClicked(eventHandler);
                imageView.setOnMouseClicked(event -> {
                    imageView.setImage(Menu.getCard(GameMenuController.trimName(game.getPlayerBoard().getGraveyard().getCards().get(Integer.parseInt(label.getText().split("/")[0])%game.getPlayerBoard().getGraveyard().getCards().size()).getCardName())));
                    label.setText(((Integer.parseInt(label.getText().split("/")[0]) % game.getPlayerBoard().getGraveyard().getCards().size()) + 1) + "/" + game.getPlayerBoard().getGraveyard().getCards().size());
                });
            }
        } else if (rivalGraveYard.getEffect() != null) {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Paint.valueOf("WHITE"));
            rectangle.setOpacity(0.36);
            rectangle.setHeight(700);
            rectangle.setWidth(700);
            rectangle.setX(0);
            rectangle.setY(0);
            if (game.getRivalBoard().getGraveyard().getCards().size() == 0) {
                ImageView imageView = new ImageView(getSelectedImageView().getImage());
                imageView.setX(280);
                imageView.setY(200);
                imageView.setFitWidth(140);
                imageView.setFitHeight(200);
                pane.getChildren().addAll(rectangle, imageView);
                EventHandler<MouseEvent> eventHandler = mouseEvent1 -> pane.getChildren().removeAll(imageView, rectangle);
                rectangle.setOnMouseClicked(eventHandler);
                imageView.setOnMouseClicked(eventHandler);
            } else {
                Label label = new Label("1/" + game.getRivalBoard().getGraveyard().getCards().size());
                label.setStyle("-fx-text-fill: green; -fx-font-size: 20px; -fx-background-color: rgba(255,255,255,0.44)");
                label.setLayoutX(340);
                label.setLayoutY(130);
                ImageView imageView = new ImageView(Menu.getCard(GameMenuController.trimName(game.getRivalBoard().getGraveyard().getCards().get(0).getCardName())));
                imageView.setX(280);
                imageView.setY(200);
                imageView.setFitWidth(140);
                imageView.setFitHeight(200);
                pane.getChildren().addAll(rectangle, imageView, label);
                EventHandler<MouseEvent> eventHandler = mouseEvent1 -> pane.getChildren().removeAll(imageView, rectangle, label);
                rectangle.setOnMouseClicked(eventHandler);
                imageView.setOnMouseClicked(event -> {
                    imageView.setImage(Menu.getCard(GameMenuController.trimName(game.getRivalBoard().getGraveyard().getCards().get(Integer.parseInt(label.getText().split("/")[0])%game.getRivalBoard().getGraveyard().getCards().size()).getCardName())));
                    label.setText(((Integer.parseInt(label.getText().split("/")[0]) % game.getRivalBoard().getGraveyard().getCards().size()) + 1) + "/" + game.getRivalBoard().getGraveyard().getCards().size());
                });
            }
        }
    }
    
    public void setAttackPosition(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void setDefensePosition(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void surrender(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
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

    public void updateWithoutTransition() {
        for (int i = rivalCards.getChildren().size() - 1; i >= 0; i--) {
            rivalCards.getChildren().remove(i);
        }
        for (int i = playerCards.getChildren().size() - 1; i >= 0; i--) {
            playerCards.getChildren().remove(i);
        }
        playerHealth.setText(String.valueOf(game.getPlayerLP()));
        rivalHealth.setText(String.valueOf(game.getRivalLP()));
        ArrayList<Card> cards = game.getPlayerHandCards();
        for (Card card : cards) {
            playerCards.getChildren().add(Menu.getImageWithSizeForGame(GameMenuController.trimName(card.getCardName()), 0, 0));
        }
        cards = game.getRivalHandCards();
        for (Card card : cards) {
            rivalCards.getChildren().add(Menu.getImageWithSizeForGame(GameMenuController.trimName(card.getCardName()), 0, 0));
        }
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                if (!(playerMonsters[i].getImage().getUrl().toLowerCase().contains(GameMenuController.trimName(cells[i].getCard().getCardName()).toLowerCase()))) {
                    playerMonsters[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                }
            } else {
                playerMonsters[i].setImage(null);
            }
        }
        cells = game.getPlayerBoard().getSpellZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                if (!(playerSpells[i].getImage().getUrl().toLowerCase().contains(GameMenuController.trimName(cells[i].getCard().getCardName()).toLowerCase()))) {
                    playerSpells[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                }
            } else {
                playerSpells[i].setImage(null);
            }
        }
        cells = game.getRivalBoard().getMonsterZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                if (!(rivalMonsters[i].getImage().getUrl().toLowerCase().contains(GameMenuController.trimName(cells[i].getCard().getCardName()).toLowerCase()))) {
                    rivalMonsters[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                }
            } else {
                rivalMonsters[i].setImage(null);
            }
        }
        cells = game.getRivalBoard().getSpellZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                if (!(rivalSpells[i].getImage().getUrl().toLowerCase().contains(GameMenuController.trimName(cells[i].getCard().getCardName()).toLowerCase()))) {
                    rivalSpells[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                }
            } else {
                rivalSpells[i].setImage(null);
            }
        }
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
