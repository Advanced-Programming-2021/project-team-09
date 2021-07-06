package view.graphics;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.enums.Cursor;

import java.io.File;
import java.io.IOException;

public class Menu {
    private static Scene currentScene;

    public static void goToMenu(String menuName) {

    }

    public static Scene getScene(Parent root) {
        return new Scene(root);
    }

    public static void changeCursor(Cursor cursor, MouseEvent mouseEvent) {
        if (cursor.getImage() == null) currentScene.setCursor(javafx.scene.Cursor.DEFAULT);
        else {
            ImageCursor cursor1 = new ImageCursor(cursor.getImage(), mouseEvent.getX(), mouseEvent.getY());
            currentScene.setCursor(cursor1);
        }
    }

    public static Parent getNode(String nodeName) {
        try {
            return FXMLLoader.load(new File("src/main/resources/Scenes/" + nodeName + ".fxml").toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image getImage(String imageName, String format) {
        return new Image(new File("src/main/resources/Scenes/Images/" + imageName + "." + format).toURI().toString());
    }

    public static Background getBackGround(String imageName, String format, double width, double height) {
        return new Background(new BackgroundImage(getImage(imageName, format), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(width, height, false, false, true, true)));
    }

    public static Image getImage(String imageName, String format, double width, double height) {
        return new Image(new File("Scenes/Images/" + imageName + format).toURI().toString(), width, height, false, false);
    }

    public static Image getCardImage(String cardName) {
        return getImage("Cards/" + cardName, "jpg");
    }

    public static ImageView getImageWithSizeForGame(String cardName, double x, double y) {
        ImageView ret = new ImageView(getCardImage(cardName));
        ret.setFitHeight(100);
        ret.setFitWidth(70);
        ret.setX(x);
        ret.setY(y);
        return ret;
    }

    public static ImageView getImageWithSizeForGame(Image image, double x, double y) {
        ImageView ret = new ImageView(image);
        ret.setFitHeight(100);
        ret.setFitWidth(70);
        ret.setX(x);
        ret.setY(y);
        return ret;
    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        Menu.currentScene = currentScene;
    }

    public static void enterButton(ButtonBase button, Cursor cursor, MouseEvent mouseEvent) {
        button.setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(138, 138, 138, 1), 0.5, 0.0, 1, 0));
        changeCursor(cursor, mouseEvent);
    }

    public static void exitButton(ButtonBase button, MouseEvent mouseEvent) {
        button.setEffect(null);
        changeCursor(Cursor.DEFAULT, mouseEvent);
    }

    public static void justifyButton(Button button, Cursor enterCursor) {
        button.onMouseEnteredProperty().set(mouseEvent -> enterButton(button, enterCursor, mouseEvent));
        button.onMouseExitedProperty().set(mouseEvent -> exitButton(button, mouseEvent));
    }

    public static Image getCard(String cardName) {
        return getImage("Cards/" + cardName.trim().replace(" ", ""), "jpg");
    }

    public static Label getLabel(String text, double width, double height) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setMaxWidth(width);
        label.setPrefHeight(height);
        label.setMaxHeight(height);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public static Label getLabel(String text, double width, double height,double fontSize) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setMaxWidth(width);
        label.setPrefHeight(height);
        label.setMaxHeight(height);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("chalkboard",fontSize));
        return label;
    }


    protected void onSelectToggle(ToggleButton mainToggle, ToggleGroup group) {
        for (Toggle button : group.getToggles()) ((ToggleButton) button).setEffect(null);
        if (!mainToggle.isSelected()) mainToggle.setEffect(null);
        else mainToggle.setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(138, 138, 138, 1), 0.5, 0.0, 1, 0));
    }

    protected Pane setDimension(Pane parent,double width, double height) {
        parent.setPrefHeight(height);
        parent.setMinHeight(height);
        parent.setMaxHeight(height);
        parent.setPrefWidth(width);
        parent.setMinWidth(width);
        parent.setMaxHeight(width);
        return parent;
    }
}
