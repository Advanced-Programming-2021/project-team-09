package model.card.spell_traps;

import controller.*;
import model.card.Card;

import java.util.ArrayList;

public class Trap extends Card {
    private TrapType trapType;
    private Limitation limit;

    public Trap(String trapName) {
        ArrayList<String> temp = csvInfoGetter.trapAndSpellReadFromCSV(trapName);
        if (temp == null || temp.size() != 4) return;
        trapType = csvInfoGetter.getTrapType(temp.get(1));
        cardName = trapName;
        description = temp.get(2);
        limit = csvInfoGetter.getLimitation(temp.get(3));
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
