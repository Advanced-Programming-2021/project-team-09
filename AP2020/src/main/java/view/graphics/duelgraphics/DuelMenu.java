package view.graphics.duelgraphics;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import view.graphics.Menu;
import view.responses.StartingGameResponses;

import java.io.IOException;

public class DuelMenu {
    private AnchorPane anchorPane;

    public DuelMenu(){
        anchorPane = (AnchorPane) Menu.getNode("DuelMenu");
        Main.stage.setScene(new Scene(anchorPane, 600, 400));
    }

    public void singlePlayerOneRound(){

    }
    public void singlePlayerThreeRound(){

    }
    public void multiplayerOneRound(){

    }
    public void multiplayerThreeRound(){

    }

}
