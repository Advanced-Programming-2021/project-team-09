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
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.enums.Cursor;
import model.enums.VoiceEffects;

import java.io.File;
import java.io.IOException;

public class Menu {
    private static final DropShadow effect = new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(138, 138, 138, 1), 0.5, 0.0, 1, 0);
    private static final File ALERT_FILE = new File("src/main/resources/Scenes/Alert.fxml");
    private static Scene currentScene;
    private static Stage mainStage;
    private static final String ROOT_STYLE = "-fx-border-color: #000000; -fx-border-width: 3;";

    public static void goToMenu(String menuName) {
        playMedia(VoiceEffects.CLICK);
        Parent root = getNode(menuName + "Menu");
        root.setStyle(ROOT_STYLE);
        Scene scene = new Scene(root,-1,-1,true);
        Menu.setCurrentScene(scene);
        mainStage.setScene(scene);
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
        button.setEffect(effect);
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

    public static Label getLabel(String text, double width, double height, double fontSize) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setMaxWidth(width);
        label.setPrefHeight(height);
        label.setMaxHeight(height);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("chalkboard", fontSize));
        return label;
    }

    public static Image getProfilePhoto(int number) {
        return getImage("ProfilePhotos/Profile" + number, "png");
    }


    protected void onSelectToggle(ToggleButton mainToggle, ToggleGroup group) {
        for (Toggle button : group.getToggles()) ((ToggleButton) button).setEffect(null);
        if (!mainToggle.isSelected()) mainToggle.setEffect(null);
        else mainToggle.setEffect(effect);
    }

    protected Pane setDimension(Pane parent, double width, double height) {
        parent.setPrefHeight(height);
        parent.setMinHeight(height);
        parent.setMaxHeight(height);
        parent.setPrefWidth(width);
        parent.setMinWidth(width);
        parent.setMaxHeight(width);
        return parent;
    }

    protected void mio() {
        playMedia(VoiceEffects.MIO);
    }

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void playMedia(VoiceEffects voiceEffects) {
        MediaPlayer player = new MediaPlayer(voiceEffects.getMedia());
        player.play();
        MediaErrorEvent event;
    }

    public static Media getVoice(String voiceName,String format) {
        return new Media(new File("src/main/resources/Scenes/Voices/" + voiceName + "." + format).toURI().toString());
    }

    public static void showAlert(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(ALERT_FILE.toURI().toURL());
            Parent alert = loader.load();
            AlertController controller = loader.getController();
            controller.setText(message);
            Stage stage = new Stage();
            Scene scene = new Scene(alert,-1,-1,true);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            controller.setStage(stage);
            controller.showPopUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToMainMenu() {
        goToMenu("Main");
    }
}
