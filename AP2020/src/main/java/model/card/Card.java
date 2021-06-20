package model.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import controller.EffectController.Destroy;
import controller.EffectController.MonsterEffectController;
import controller.EffectController.SpellEffectController;
import controller.GameMenuController;
import model.exceptions.GameException;
import model.game.Game;
import model.card.monster.Monster;
import model.card.spell_traps.Spell;
import model.card.spell_traps.Trap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@JsonTypeInfo(
        use = Id.NAME,
        include = As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @Type(name = "Monster", value = Monster.class),
        @Type(name = "Spell", value = Spell.class),
        @Type(name = "Trap", value = Trap.class),
})

public abstract class Card {
    protected String cardName;
    protected String description;
    protected CardType cardType;
    protected String cardID;
    @JsonIgnore
    protected ArrayList<CardFeatures> features;


    @JsonIgnore
    public ArrayList<CardFeatures> getFeatures() {
        return features;
    }

    @JsonIgnore
    public void addFeature(CardFeatures cardFeature) {
        this.features.add(cardFeature);
    }

    @JsonIgnore
    public void setFeatures(ArrayList<CardFeatures> features) {
        this.features = features;
    }

    @JsonIgnore
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

    @JsonIgnore
    public CardType getCardType() {
        return cardType;
    }

    @JsonIgnore
    public void setDescription(String description) {
        this.description = description;
    }

    public void destroy(Game game) {
        try {
            Method method = Destroy.class.getDeclaredMethod(GameMenuController.trimName(this.getCardName()), Game.class, Card.class);
            method.invoke(null, game, this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) { }
    }

    public void activeEffect(Game game) throws GameException {
        if (isMonster()) {
            try {
                Method method = MonsterEffectController.class.getDeclaredMethod(GameMenuController.trimName(this.getCardName()), Game.class, Card.class);
                method.invoke(null, game, this);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) { }
        } else {
            try {
                Method method = SpellEffectController.class.getDeclaredMethod(GameMenuController.trimName(this.getCardName()), Game.class, Card.class);
                method.invoke(null, game, this);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) { }
        }
    }

    public abstract String toString();

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
