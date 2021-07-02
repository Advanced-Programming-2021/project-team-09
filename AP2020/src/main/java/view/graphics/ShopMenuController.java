package view.graphics;

import controller.LoginMenuController;
import controller.ShopController;
import controller.database.CSVInfoGetter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.card.Card;
import model.card.monster.Monster;
import model.enums.Cursor;
import model.graphicalModels.CardHolder;
import view.graphics.profile.SearchMenu;
import view.regexes.RegexFunctions;
import view.responses.ShopMenuResponses;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;

public class ShopMenuController extends SearchMenu implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;
    @FXML
    private Button shopButton;
    @FXML
    private CardHolder cardHolder;

    private static final String advanceSearchRegex = "^(?<type>type:\\s*[\\w /]+)?\\s*(?<price>price:\\s*\\d+(?:-?\\d+)?)?\\s*(?<attack>attack:\\s*\\d+(?:-?\\d+)?)?\\s*(?<defend>defend:\\s*\\d+(?:-?\\d*)?)?$";
    public static final ArrayList<String> cardNames = CSVInfoGetter.getCardNames();
    public static final ArrayList<Card> cards = new ArrayList<>();
    public static final HashMap<String, Integer> prices = new HashMap<>();

    {
        for (String cardName : cardNames) {
            cards.add(CSVInfoGetter.getCardByName(cardName));
            prices.put(cardName, CSVInfoGetter.getPriceByCardName(cardName));
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.textProperty().addListener((observableValue, s, t1) -> search(t1));
        justifyButton(plusButton, Cursor.RIGHT_ARROW);
        justifyButton(minusButton, Cursor.LEFT_ARROW);
        justifyButton(shopButton, Cursor.ACCEPT);
        nameLabel.setText("None");
        typeLabel.setText("None");
        priceLabel.setText("0000");
        updateBalanceLabel();
        stageCounter.setText("-/-");
        searchBox.setSpacing(5);

    }

    private void updateBalanceLabel() {
        balanceLabel.setText(Integer.toString(LoginMenuController.getCurrentUser().getBalance()));
    }

    public void buy(ActionEvent actionEvent) {
        String cardName = nameLabel.getText();
        if (cardName.equals("None")) return;
        ShopMenuResponses respond = ShopController.buyCard(cardName);
        System.out.println(respond);
        //ToDo
        updateBalanceLabel();

    }

    public void nextMenu(ActionEvent actionEvent) {
        int currentStage = getCurrentSearchStage();
        if (currentStage == -1) return;
        if (currentStage == searchResults.size()) return;
        showVBox(currentStage);
        setStageLabel(currentStage + 1);

    }

    public void previousMenu(ActionEvent actionEvent) {
        int currentStage = getCurrentSearchStage();
        if (currentStage == -1) return;
        if (currentStage == 1) return;
        showVBox(currentStage - 2);
        setStageLabel(currentStage - 1);
    }


    protected void search(String searchText) {
        searchText = searchText.trim().toLowerCase();
        if (searchText.equals("")) {
            emptySearchBox();
            stageCounter.setText("-/-");
            return;
        }
        if (searchText.charAt(0) == '#') {
            advancedSearch(searchText);
            return;
        }
        ArrayList<String> matchingCards = new ArrayList<>();
        for (String card : cardNames) if (card.contains(searchText)) matchingCards.add(card);
        ArrayList<VBox> resultBoxes = getSearchResults(matchingCards);
        searchResults = new ArrayList<>();
        searchResults.addAll(resultBoxes);
        if (searchResults.size() > 0) showVBox(0);
        else {
            emptySearchBox();
            stageCounter.setText("-/-");
        }
    }

    private void advancedSearch(String searchText) {
        emptySearchBox();
        if (searchText.equals("#")) return;
        searchText = searchText.substring(1);
        if (searchText.matches(advanceSearchRegex)) {
            Matcher matcher = RegexFunctions.getCommandMatcher(searchText, advanceSearchRegex);
            if (matcher.find()) {
                String type = matcher.group("type");
                String defend = matcher.group("defend");
                String attack = matcher.group("attack");
                String price = matcher.group("price");
                HashMap<String, String> searchTypes = new HashMap<>();
                if (type != null) searchTypes.put("type", type);
                if (price != null) searchTypes.put("price", price);
                if (defend != null) searchTypes.put("defend", defend);
                if (attack != null) searchTypes.put("attack", attack);
                ArrayList<String> filteredCards = filterCards(searchTypes);
                ArrayList<VBox> searchBoxes = getSearchResults(filteredCards);
                searchResults = new ArrayList<>(searchBoxes);
                if (searchResults.size() > 0) showVBox(0);
            }
        } else if (searchText.equalsIgnoreCase("all")) {
            ArrayList<VBox> searchBoxes = getSearchResults(cardNames);
            searchResults = new ArrayList<>(searchBoxes);
            if (searchResults.size() > 0) showVBox(0);
        }

    }

    private ArrayList<String> filterCards(HashMap<String, String> searchTypes) {
        ArrayList<String> cardNames = (ArrayList<String>) ShopMenuController.cardNames.clone();
        if (searchTypes.containsKey("price")) filterPrice(cardNames, searchTypes.get("price"));
        if (searchTypes.containsKey("type")) filterType(cardNames, searchTypes.get("type"));
        if (searchTypes.containsKey("attack")) filterAttack(cardNames, searchTypes.get("attack"));
        if (searchTypes.containsKey("defend")) filterDefend(cardNames, searchTypes.get("defend"));
        return cardNames;
    }

    private void filterDefend(ArrayList<String> cardNames, String defendLimits) {
        int[] ints = getMinMax(defendLimits);
        for (int i = 0; i < cardNames.size(); i++) {
            Card card = getCardByName(cardNames.get(i));
            if (card instanceof Monster) {
                int defense = ((Monster)card).getDefense();
                if (!(defense >= ints[0] && defense <= ints[1])) cardNames.remove(cardNames.get(i--));
            } else cardNames.remove(cardNames.get(i--));
        }
    }

    private void filterType(ArrayList<String> cardNames, String typeLimit) {
        typeLimit = typeLimit.split(":")[1].trim();
        String[] typesArray = typeLimit.split("/");
        HashSet<String> types = new HashSet<>();
        for (int i = 0; i < typesArray.length; i++) types.add(typesArray[i].trim().toLowerCase());
        for (int i = 0; i < cardNames.size(); i++) {
            if (!types.contains(getCardByName(cardNames.get(i)).getCardType().toString().toLowerCase())) cardNames.remove(cardNames.get(i--));
        }
    }

    private void filterPrice(ArrayList<String> cardNames, String priceLimit) {
        int[] ints = getMinMax(priceLimit);
        for (int i = 0; i < cardNames.size(); i++) {
            int cardPrice = prices.get(cardNames.get(i));
            if (!(cardPrice >= ints[0] && cardPrice <= ints[1])) cardNames.remove(cardNames.get(i--));
        }
    }

    private void filterAttack(ArrayList<String> cardNames, String attackLimit) {
        int[] ints = getMinMax(attackLimit);
        for (int i = 0; i < cardNames.size(); i++) {
            Card card = getCardByName(cardNames.get(i));
            if (card instanceof Monster) {
                int cardAttack = ((Monster)card).getAttack();
                if (!(cardAttack >= ints[0] && cardAttack <= ints[1])) cardNames.remove(cardNames.get(i--));
            } else cardNames.remove(cardNames.get(i--));
        }
    }


    private int[] getMinMax(String limit){
        limit =  limit.split(":")[1].trim();
        int[] numbers = new int[2];
        if (!limit.contains("-")) {
            numbers[0] = numbers[1] = Integer.parseInt(limit);
        } else {
            String[] attacks = limit.split("-");
            numbers[0] = Integer.parseInt(attacks[0]);
            numbers[1] = Integer.parseInt(attacks[1]);
        }
        return numbers;
    }

    protected Button getOptionButton(String searchResult) {
        Button button = new Button(searchResult);
        button.setPrefHeight(28);
        button.setPrefWidth(120);
        button.setStyle("-fx-border-style: solid none solid");
        button.setOnAction(actionEvent -> {
            Card card = CSVInfoGetter.getCardByName(searchResult);
            cardHolder.setCard(getCard(searchResult));
            priceLabel.setText(CSVInfoGetter.getPriceByCardName(searchResult) + "");
            nameLabel.setText(card.getCardName());
            typeLabel.setText(card.getCardType().toString().toLowerCase());

        });
        justifyButton(button, Cursor.SEARCH);
        return button;
    }

    private Card getCardByName(String name) {
        for (Card card : cards) if (card.getCardName().equals(name)) return card;
        return null;
    }

}

