package model.card.spell_traps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import controller.*;
import model.card.Card;
import model.card.MonsterCardType;

import java.util.ArrayList;
@JsonIgnoreProperties({"description","cardType","cardID","trapType","limit"})
public class Trap extends Card {
    private TrapType trapType;
    private Limitation limit;

    public Trap(String cardName) {
        ArrayList<String> temp = csvInfoGetter.trapAndSpellReadFromCSV(cardName);
        if (temp == null || temp.size() != 4) return;
        trapType = csvInfoGetter.getTrapType(temp.get(1));
        this.cardName = cardName;
        description = temp.get(2);
        limit = csvInfoGetter.getLimitation(temp.get(3));
        cardType = MonsterCardType.TRAP;
    }

    public Trap(){

    }

    public Limitation getLimit(){
        return this.limit;
    }

    public TrapType getTrapType(){
        return this.trapType;
    }

    public String toString(){
        StringBuilder temp = new StringBuilder();
        temp.append("Name: " + cardName + "\n");
        temp.append("Trap\n");
        temp.append("Type: " + trapType + "\n");
        temp.append("Description: " + description);
        return temp.toString();
    }
}
