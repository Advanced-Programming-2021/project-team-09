package model.card.spell_traps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import controller.*;
import model.card.Card;
import model.card.CardType;

import java.util.ArrayList;

@JsonIgnoreProperties({"description","cardType","cardID","spellType","limit"})
public class Spell extends Card {
    private SpellType spellType;
    private Limitation limit;

    public Spell(String cardName) {
        ArrayList<String> temp = csvInfoGetter.trapAndSpellReadFromCSV(cardName);
        if (temp == null || temp.size() != 4) return;
        spellType = csvInfoGetter.getSpellType(temp.get(1));
        this.cardName = cardName;
        description = temp.get(2);
        limit = csvInfoGetter.getLimitation(temp.get(3));
        cardType = CardType.SPELL;
    }
    public Spell(){

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
