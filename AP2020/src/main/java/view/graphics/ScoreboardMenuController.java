package view.graphics;

import controller.LoginMenuController;
import controller.ScoreboardController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ScoreboardMenuController extends SearchMenu implements Initializable {
    private final ArrayList<User> users = ScoreboardController.getSortedUsers();
    private final ArrayList<String> usernames = new ArrayList<>();
    private final HashMap<String, Parent> userOptions = new HashMap<>();
    private final HashMap<String,User> userHashMap = new HashMap<>();

    @FXML private Button minusButton;
    @FXML private Button plusButton;
    @FXML private Circle imagePlace;
    @FXML private Label usernameLabel;
    @FXML private ImageView babeFace;
    @FXML private Label nicknameLabel;
    @FXML private Label scoreLabel;

    {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            usernames.add(user.getUsername());
            userHashMap.put(usernames.get(i),user);
        }
    }

    {
        for (int i = users.size() - 1; i >= 0; i--) {
            User user = users.get(i);
            userOptions.put(user.getUsername(), getScoreboardOption(user, users.size() - i));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMinSizeSearchBox(0);
        searchResults = getSearchResults(usernames);
        showVBox(0);
        searchField.textProperty().addListener((observableValue, s, t1) -> search(t1));
        plusButton.setOnAction(actionEvent -> next());
        minusButton.setOnAction(actionEvent -> previous());

    }


    @Override
    protected void search(String searchText) {
        if (searchText.equals("")) {
            emptySearchBox();
            stageCounter.setText("-/-");
            return;
        }
        ArrayList<String> matchingUsers = new ArrayList<>();
        for (String card : usernames) if (card.toLowerCase().contains(searchText)) matchingUsers.add(card);
        ArrayList<VBox> resultBoxes = getSearchResults(matchingUsers);
        searchResults = new ArrayList<>();
        searchResults.addAll(resultBoxes);
        if (searchResults.size() > 0) showVBox(0);
        else {
            emptySearchBox();
            stageCounter.setText("-/-");
        }
    }

    @Override
    protected ArrayList<VBox> getSearchResults(ArrayList<String> searchResults) {
        ArrayList<String> usernames = new ArrayList<>(this.usernames);
        ArrayList<VBox> searchBoxes = new ArrayList<>();
        usernames.retainAll(searchResults);
        VBox currentBox = new VBox(2);
        for (int i = usernames.size() - 1; i >= 0; i--) {
            if (currentBox.getChildren().size() == 11) {
                searchBoxes.add(currentBox);
                currentBox = new VBox(3);
            }
            currentBox.getChildren().add(userOptions.get(usernames.get(i)));
        }
        if (currentBox.getChildren().size() != 0) searchBoxes.add(currentBox);
        return searchBoxes;
    }


    public Parent getScoreboardOption(User user, int rank) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/main/resources/Scenes/ScoreboardOption.fxml").toURI().toURL());
            HBox parent = loader.load();
            ScoreboardOptionCreator controller = loader.getController();
            controller.setProfileCircle(user.getProfilePhoto());
            controller.setRank(rank);
            controller.setUsername(user.getUsername());
            controller.setScore(user.getScore());
            if (LoginMenuController.getCurrentUser().getUsername().equals(user.getUsername())) parent.getChildren().get(0).setStyle("-fx-background-color: #f8ea9d; -fx-background-radius: 10; -fx-border-color: #000000; -fx-border-radius: 10;");
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setProfileBox(Pane option) {
        List<Node> list = option.getChildren();
        String username = ((Label)list.get(1)).getText();
        String score = ((Label)list.get(3)).getText();
        String nickName = userHashMap.get(username).getNickname();
        scoreLabel.setText(score);
        imagePlace.setFill(((Circle)list.get(0)).getFill());
        nicknameLabel.setText(nickName);
        usernameLabel.setText(username);
    }

    public void previous() {

    }

    public void next() {
    }
}
