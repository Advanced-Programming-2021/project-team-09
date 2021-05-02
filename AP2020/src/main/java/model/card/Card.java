package model.card;

import model.card.monster.Monster;
import model.card.spell_traps.Spell;
import model.card.spell_traps.Trap;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

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

    public abstract String toString();

}
