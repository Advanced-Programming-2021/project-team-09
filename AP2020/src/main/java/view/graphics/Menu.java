package view.graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;

public class Menu {
    public static Parent getNode(String nodeName) {
        try {
            return FXMLLoader.load(new File("/Users/siasor88/Documents/GitHub/project-team-09/AP2020/src/main/resources/Scenes/"+ nodeName + ".fxml").toURI().toURL());
        } catch (IOException e) {
            return null;
        }
    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();
    }
}
