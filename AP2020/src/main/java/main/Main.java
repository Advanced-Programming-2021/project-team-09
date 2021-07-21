package main;

import controller.SocketWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import view.graphics.Menu;
import view.graphics.SettingController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        try {
            SocketWrapper.socket = new Socket("localhost", 8569);
            SocketWrapper.objectInputStream = new ObjectInputStream(SocketWrapper.socket.getInputStream());
            SocketWrapper.objectOutputStream = new ObjectOutputStream(SocketWrapper.socket.getOutputStream());
        } catch (IOException ignored) { }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        Menu.setStage(stage);
        stage.setTitle("Fanta-Oh!");
        stage.getIcons().add(Menu.getImage("icon", "png"));
        stage.setResizable(false);
        Menu.goToMenu("Welcome");
        SettingController.playBG();
        stage.show();
    }
}
