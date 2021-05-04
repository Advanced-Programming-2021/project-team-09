package model.card;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import model.Game;
import model.card.monster.Monster;
import model.card.spell_traps.Spell;
import model.card.spell_traps.Trap;

@JsonTypeInfo(use = Id.NAME)
@JsonSubTypes({
        @Type(name = "Monster",value = Monster.class),
        @Type(name = "Spell",value = Spell.class),
        @Type(name = "Trap",value = Trap.class),
})

public abstract class Card {
    protected String cardName;
    protected String description;
    protected CardType cardType;
    protected String cardID;

    public String getDescription(){
        return this.description;
    }

    public String getCardName(){
        return this.cardName;
    }

    public boolean isMonster(){
        return cardType == CardType.MONSTER;
    }

    public boolean isSpell(){
        return cardType == CardType.SPELL;
    }

    public boolean isTrap(){
        return cardType == CardType.TRAP;
    }
    public void destroy(Game game){

    }
    public abstract String toString();

}
