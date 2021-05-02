package model.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.card.monster.Monster;
import model.card.spell_traps.Spell;
import model.card.spell_traps.Trap;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @Type(value = Monster.class, name = "Monster"),
        @Type(value = Spell.class, name = "Spell"),
        @Type(value = Trap.class, name = "Trap")
})

@JsonIgnoreProperties ({"description","cardType","cardID"})
public abstract class Card {
    protected String cardName;
    protected String description;
    protected CardType cardType;
    protected String cardID;

    public String getDescription() {
        return this.description;
    }

    public String getCardName() {
        return this.cardName;
    }

    @JsonIgnore
    public boolean isMonster() {
        return cardType == CardType.MONSTER;
    }
    @JsonIgnore
    public boolean isSpell() {
        return cardType == CardType.SPELL;
    }
    @JsonIgnore
    public boolean isTrap() {
        return cardType == CardType.TRAP;
    }

    public abstract String toString();

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
