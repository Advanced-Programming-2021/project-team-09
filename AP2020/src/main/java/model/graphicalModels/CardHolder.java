package model.graphicalModels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.graphics.Menu;

public class CardHolder extends Pane {
    private final static Image border = Menu.getImage("CardPlace","png");
    private final ImageView cardPlace;
    public ImageView cardBox;

    public void setCard(Image card) {
        cardPlace.setImage(card);
    }

    {
        cardPlace = new ImageView();
        cardPlace.setLayoutX(6);
        cardPlace.setLayoutY(6);
        cardPlace.setFitHeight(188.0);
        cardPlace.setFitWidth(138.0);
        cardPlace.setImage(Menu.getCard("BACK"));
    }

    {
        cardBox = new ImageView();
        cardBox.setImage(border);
        cardBox.setFitWidth(150.0);
        cardBox.setFitHeight(200.0);
    }

    {
        this.setHeight(250.0);
        this.setWidth(150);
        this.getChildren().add(cardPlace);
        this.getChildren().add(cardBox);
    }
}
