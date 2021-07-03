package view.graphics;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    public static void enterButton(Button button, Cursor cursor, MouseEvent mouseEvent) {
        button.setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(138, 138, 138, 1), 0.5, 0.0, 1, 0));
        changeCursor(cursor, mouseEvent);
    }

    public static void exitButton(Button button ,MouseEvent mouseEvent) {
        button.setEffect(null);
        changeCursor(Cursor.DEFAULT, mouseEvent);
    }

    public static void justifyButton(Button button, Cursor enterCursor) {
        button.onMouseEnteredProperty().set(mouseEvent -> enterButton(button,enterCursor,mouseEvent));
        button.onMouseExitedProperty().set(mouseEvent -> exitButton(button, mouseEvent));
    }

    public static Image getCard(String cardName) {
        return getImage("Cards/" + cardName.trim().replace(" ",""),"jpg");
    }

    public static Label getLabel(String text, double width, double height) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setMaxWidth(width);
        label.setPrefHeight(height);
        label.setMaxHeight(height);
        label.getStylesheets().add("src/main/resources/Scenes/StyleSheets/Label.css");
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }
}
