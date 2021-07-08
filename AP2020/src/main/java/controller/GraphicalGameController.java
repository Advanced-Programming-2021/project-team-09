package controller;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.EventHandler;
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
import model.exceptions.GameException;
import model.exceptions.WinnerException;
import model.game.Cell;
import model.game.Game;
import model.game.State;
import view.duelMenu.EndPhaseMenu;
import view.duelMenu.Phase;
import view.graphics.Menu;
import view.responses.GameMenuResponse;
import view.responses.GameMenuResponsesEnum;

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
    private final Label phase;
    private final Game game;
    private Phase currentPhase = Phase.DRAW_PHASE;
    //private final boolean[][] isSelected = new boolean[4][5]; // 1: playerMonster, 2 : playerSpells, 3 : rivalmonster, 4 : rivalSpells

    public GraphicalGameController(ImageView[] playerMonsters, ImageView[] playerSpells,
                                   ImageView[] rivalMonsters, ImageView[] rivalSpells,
                                   HBox playerCards, HBox rivalCards, VBox options,
                                   ImageView playerFieldSpell, ImageView playerGraveYard,
                                   ImageView rivalFieldSpell, ImageView rivalGraveYard, Game game, Pane pane,
                                   Label playerName, Label playerHealth, Label rivalName, Label rivalHealth, Label phase) {
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
        this.phase = phase;
        this.game = game;
        setImageFunctionality();
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
        } else if (playerHandCardIsSelected()) {
            playerHandCardsButtons();
        }
    }

    private boolean playerHandCardIsSelected() {
        for (Node child : playerCards.getChildren()) {
            if (child.getEffect() != null) return true;
        }
        return false;
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

    public void playerHandCardsButtons() {

        options.getChildren().addAll(SUMMON, SET, SHOW_CARD, NEXT_PHASE, SURRENDER);
    }

    public void summon(MouseEvent event) {
        int i = -1;
        for (int i1 = 0; i1 < playerCards.getChildren().size(); i1++) {
            if (playerCards.getChildren().get(i1).getEffect() != null) {
                i = i1;
                break;
            }
        }
        if (i == -1) return;
        Card card = game.getPlayerHandCards().get(i);
        GameMenuResponse gameMenuResponse;
        try {
            gameMenuResponse = GameMenuController.summon(game, i + 1, false);
        } catch (WinnerException winnerException) {
            // TODO: 7/8/2021
            return;
        }
        if (gameMenuResponse.getGameMenuResponseEnum() == GameMenuResponsesEnum.SUCCESSFUL)
            updateSummonWithTransition(card, i);
    }

    private void updateSummonWithTransition(Card card, int index) {
        int x, y;
        if (card.isMonster()) {
            y = 360;
            int i = -1;
            Cell[] cells = game.getPlayerBoard().getMonsterZone();
            for (int i1 = cells.length - 1; i1 >= 0; i1--) {
                if (cells[i1].getCard() == card){
                    i = i1;
                    break;
                }
            }
            if (i == -1 ) return;
            if (i == 0) x = 165 + 75*2;
            else if (i == 1) x = 165 + 75;
            else if (i == 2) x = 165 + 75*3;
            else if (i == 3) x = 165;
            else x = 165 + 75*4;
        } else {
            y = 460;
            int i = -1;
            Cell[] cells = game.getPlayerBoard().getSpellZone();
            for (int i1 = cells.length - 1; i1 >= 0; i1--) {
                if (cells[i1].getCard() == card){
                    i = i1;
                    break;
                }
            }
            if (i == -1 ) return;
            if (i == 0) x = 165 + 75*2;
            else if (i == 1) x = 165 + 75;
            else if (i == 2) x = 165 + 75*3;
            else if (i == 3) x = 165;
            else x = 165 + 75*4;
        }
        ImageView imageView = Menu.getImageWithSizeForGame(GameMenuController.trimName(card.getCardName()), 100 + index * 75, 590);
        pane.getChildren().add(imageView);
        MoveTransition moveTransition = new MoveTransition(x, y, 100 + index * 75, 590, imageView, 1000);
        moveTransition.setOnFinished(actionEvent -> {
            pane.getChildren().remove(imageView);
            updateWithoutTransition();
        });
        moveTransition.play();
    }
    
    public void attack(MouseEvent mouseEvent) {
        int attacker = -1;
        for (int i = 0; i < 5; i++) {
            if (playerMonsters[i].getEffect() != null) {
                attacker = i;
            }
        }
        if (attacker == -1) return;
        if (game.getRivalBoard().getNumberOfMonstersInMonsterZone() == 0) {
            // TODO: 7/8/2021
            return;
        }
        final int attackFinal = attacker;
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Paint.valueOf("WHITE"));
        rectangle.setOpacity(0.4);
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
        try {
            if (currentPhase.equals(Phase.STANDBY_PHASE))
                goToMainPhase1();
            else if (currentPhase.equals(Phase.END_PHASE)) {
                goToDrawPhase();
                GameMenuResponse gameMenuResponse;
                if ((gameMenuResponse = GameMenuController.draw(game)).getGameMenuResponseEnum() == GameMenuResponsesEnum.SUCCESSFUL) {
                    // TODO: 7/8/2021
                }
                updateWithoutTransition();
            } else if (currentPhase.equals(Phase.DRAW_PHASE))
                goToStandByPhase();
            else if (currentPhase.equals(Phase.MAIN_PHASE2))
                goToEndPhase();
            else if (currentPhase.equals(Phase.BATTLE_PHASE))
                goToMainPhase2();
            else if (currentPhase.equals(Phase.MAIN_PHASE1))
                goToBattlePhase();
        } catch (WinnerException e) {
            // TODO: 7/8/2021
        }
    }

    public void goToStandByPhase() throws WinnerException{
        GameMenuController.goToStandByPhase(game);
        setCurrentPhase(Phase.STANDBY_PHASE);
        updatePhase();
    }

    public void goToMainPhase1() {
        setCurrentPhase(Phase.MAIN_PHASE1);
        updatePhase();
    }

    public void goToDrawPhase() throws WinnerException {
        resetImageEffects();
        if (game.getPlayerHandCards().size() > 6) new EndPhaseMenu(game).run(); // TODO: 7/8/2021
        game.changeTurn();
//        if (ai != null){
//            ai.run(game);
//            System.out.println("It's " + game.getRival().getNickname() + "'s turn ..");
//            game.changeTurn();
//        } // TODO: 7/8/2021
        resetImageEffects();
        setCurrentPhase(Phase.DRAW_PHASE);
        updatePhase();
    }

    public void goToMainPhase2() {
        setCurrentPhase(Phase.MAIN_PHASE2);
        updatePhase();
    }

    public void goToBattlePhase() {
        setCurrentPhase(Phase.BATTLE_PHASE);
        updatePhase();
    }

    public void goToEndPhase() {
        setCurrentPhase(Phase.END_PHASE);
        updatePhase();
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void updatePhase() {
        if (currentPhase.equals(Phase.STANDBY_PHASE))
            phase.setText("StandBy");
        else if (currentPhase.equals(Phase.END_PHASE))
            phase.setText("End phase");
        else if (currentPhase.equals(Phase.DRAW_PHASE))
            phase.setText("Draw");
        else if (currentPhase.equals(Phase.MAIN_PHASE2))
            phase.setText("Main 2");
        else if (currentPhase.equals(Phase.BATTLE_PHASE))
            phase.setText("Battle");
        else if (currentPhase.equals(Phase.MAIN_PHASE1))
            phase.setText("Main 1");
    }
    
    public void activeEffect(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void flipSummon(MouseEvent mouseEvent) {
        // TODO: 7/7/2021  
    }
    
    public void showCard(MouseEvent mouseEvent) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Paint.valueOf("WHITE"));
        rectangle.setOpacity(0.4);
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
        for (Node child : playerCards.getChildren()) {
            if (child.getEffect() != null) return (ImageView) child;
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
        gameFinished(new WinnerException(game.getRival(), game.getPlayer(), game.getRivalLP(), game.getPlayerLP()));
    }

    public void gameFinished(WinnerException winnerException) {
        // TODO: 7/8/2021
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
        for (Node child : playerCards.getChildren()) {
            child.setEffect(null);
        }
        rivalFieldSpell.setEffect(null);
        rivalGraveYard.setEffect(null);
        playerFieldSpell.setEffect(null);
        playerGraveYard.setEffect(null);
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
        playerName.setText(game.getPlayer().getNickname());
        rivalName.setText(game.getRival().getNickname());
        rivalHealth.setText(String.valueOf(game.getRivalLP()));
        ArrayList<Card> cards = game.getPlayerHandCards();
        for (Card card : cards) {
            ImageView imageView;
            playerCards.getChildren().add(imageView = Menu.getImageWithSizeForGame(GameMenuController.trimName(card.getCardName()), 0, 0));
            imageView.setOnMouseClicked(mouseEvent -> {
                resetImageEffects();
                if (((ImageView) mouseEvent.getSource()).getImage() != null)
                    ((ImageView) mouseEvent.getSource()).setEffect(new DropShadow(40, Color.RED));
                updateButtons();
            });
        }
        cards = game.getRivalHandCards();
        for (Card card : cards) {
            rivalCards.getChildren().add(Menu.getImageWithSizeForGame("back", 0, 0));
        }
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                State cellState = cells[i].getState();
                if (cellState == State.FACE_UP_ATTACK) {
                    playerMonsters[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                    playerMonsters[i].setRotate(0);
                } else if (cellState == State.FACE_UP_DEFENCE) {
                    playerMonsters[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                    playerMonsters[i].setRotate(90);
                } else if (cellState == State.FACE_DOWN_DEFENCE) {
                    playerMonsters[i].setImage(Menu.getCardImage("back"));
                    playerMonsters[i].setRotate(0);
                }
            } else {
                playerMonsters[i].setImage(null);
            }
        }
        cells = game.getPlayerBoard().getSpellZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                State cellState = cells[i].getState();
                if (cellState == State.FACE_UP_SPELL) {
                    playerSpells[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                } else if (cellState == State.FACE_DOWN_SPELL) {
                    playerSpells[i].setImage(Menu.getCardImage("back"));
                }
            } else {
                playerSpells[i].setImage(null);
            }
        }
        cells = game.getRivalBoard().getMonsterZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                State cellState = cells[i].getState();
                if (cellState == State.FACE_UP_ATTACK) {
                    rivalMonsters[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                    rivalMonsters[i].setRotate(0);
                } else if (cellState == State.FACE_UP_DEFENCE) {
                    rivalMonsters[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                    rivalMonsters[i].setRotate(90);
                } else if (cellState == State.FACE_DOWN_DEFENCE) {
                    rivalMonsters[i].setImage(Menu.getCardImage("back"));
                    rivalMonsters[i].setRotate(0);
                }
            } else {
                rivalMonsters[i].setImage(null);
            }
        }
        cells = game.getRivalBoard().getSpellZone();
        for (int i = 0; i < 5; i++) {
            if (cells[i].isOccupied()) {
                State cellState = cells[i].getState();
                if (cellState == State.FACE_UP_SPELL) {
                    rivalSpells[i].setImage(Menu.getCardImage(GameMenuController.trimName(cells[i].getCard().getCardName())));
                } else if (cellState == State.FACE_DOWN_SPELL) {
                    rivalSpells[i].setImage(Menu.getCardImage("back"));
                }
            } else {
                rivalSpells[i].setImage(null);
            }
        }
        if (game.getPlayerBoard().getFieldZone().isOccupied())
            playerFieldSpell.setImage(Menu.getCardImage(GameMenuController.trimName(game.getPlayerBoard().getFieldZone().getCard().getCardName())));
        else playerFieldSpell.setImage(Menu.getCardImage("back"));
        if (game.getRivalBoard().getFieldZone().isOccupied())
            rivalFieldSpell.setImage(Menu.getCardImage(GameMenuController.trimName(game.getRivalBoard().getFieldZone().getCard().getCardName())));
        else rivalFieldSpell.setImage(Menu.getCardImage("back"));
        if (game.getPlayerBoard().getGraveyard().getCards().size() == 0)
            playerGraveYard.setImage(Menu.getCardImage("back"));
        else playerGraveYard.setImage(Menu.getCardImage(GameMenuController.trimName(game.getPlayerBoard().getGraveyard().getCards().get(game.getPlayerBoard().getGraveyard().getCards().size() - 1).getCardName())));
        if (game.getRivalBoard().getGraveyard().getCards().size() == 0)
            rivalGraveYard.setImage(Menu.getCardImage("back"));
        else rivalGraveYard.setImage(Menu.getCardImage(GameMenuController.trimName(game.getRivalBoard().getGraveyard().getCards().get(game.getRivalBoard().getGraveyard().getCards().size() - 1).getCardName())));
        updatePhase();
    }

    private class MoveTransition extends Transition {
        final double xDes, yDes, xStart, yStart;
        final ImageView imageView;

        public MoveTransition(double xDes, double yDes, double xStart, double yStart, ImageView imageView , int time) {
            this.xDes = xDes;
            this.yDes = yDes;
            this.xStart = xStart;
            this.yStart = yStart;
            this.imageView = imageView;
            this.setInterpolator(Interpolator.EASE_OUT);
            this.setCycleDuration(Duration.millis(time));
            this.setCycleCount(1);
        }

        @Override
        protected void interpolate(double v) {
            imageView.setX(xStart + (xDes - xStart) * v);
            imageView.setY(yStart + (yDes - yStart) * v);
        }
    }
}
