package controller.EffectController;

import model.card.Card;
import model.card.monster.MonsterType;
import model.game.Game;
import model.game.Limits;

import java.util.Calendar;

public class SpellEffectController extends EffectController {
    public void SpellAbsorption(Game game, Card card) {

    }

    public void Yami(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.FAIRY, -200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.FIEND, +200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.SPELLCASTER, +200);
    }

    private void addPowerNumbersToType(Limits playerLimits, Limits rivalLimits, MonsterType type, int amount) {
        playerLimits.addFieldZoneATK(type, amount);
        playerLimits.addFieldZoneDEF(type, amount);
        rivalLimits.addFieldZoneATK(type, amount);
        rivalLimits.addFieldZoneDEF(type, amount);
    }

    public void Forest(Game game, Card card) {

    }

}
