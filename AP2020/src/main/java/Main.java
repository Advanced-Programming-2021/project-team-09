
import com.google.gson.Gson;
import controller.ReadAndWriteDataBase;
import controller.csvInfoGetter;
import model.User;
import model.card.Card;
import model.card.CardFeatures;
import model.card.FeatureWrapper;
import model.deck.Deck;
import model.deck.MainDeck;
import view.LoginMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
       // LoginMenu.getInstance().run();
        Gson gson = new Gson();
        ArrayList<String> names = csvInfoGetter.getCardNames();
        FileWriter fileWriter = new FileWriter("src/resources/card-infos/features/"+"Skull"+".json");
        FeatureWrapper wrapper = new FeatureWrapper();
        //wrapper.features.add(CardFeatures.CANT_ATTACK_WHEN_FACE_UP);
        //wrapper.features.add(CardFeatures.CHANGING_ATK_DEF_POINTS_OF_CARDS);
        //wrapper.features.add(CardFeatures.DESTROY_ATTACKER);
        //wrapper.features.add(CardFeatures.ONE_EFFECT_PER_ROUND);
        wrapper.features.add(CardFeatures.RITUAL_SUMMON);
        wrapper.features.add(CardFeatures.NORMAL_SUMMON);
        gson.toJson(wrapper,fileWriter);
        fileWriter.close();
        User user = ReadAndWriteDataBase.getUser("sia.json");
        Deck deck = user.getDeckByName("fave");

    }
}