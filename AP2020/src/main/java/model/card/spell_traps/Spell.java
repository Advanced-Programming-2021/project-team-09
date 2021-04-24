package model.card.spell_traps;

import controller.*;
import model.card.Card;

import java.util.ArrayList;


public class Spell extends Card {
    private SpellType spellType;
    private Limitation limit;

    public Spell(String spellName) {
        ArrayList<String> temp = csvInfoGetter.trapAndSpellReadFromCSV(spellName);
        if (temp == null) return;
        spellType = csvInfoGetter.getSpellType(temp.get(1));
        cardName = spellName;
        description = temp.get(2);
        limit = csvInfoGetter.getLimitation(temp.get(3));
    }

    public Limitation getLimit(){
        return this.limit;
    }

    public SpellType getSpellType(){
        return this.spellType;
    }

    public String toString() {
        StringBuilder temp = new StringBuilder();
        temp.append("Name: " + cardName + "\n");
        temp.append("Spell\n");
        temp.append("Type: " + spellType + "\n");
        temp.append("Description: " + description);
        return temp.toString();
    }
}
