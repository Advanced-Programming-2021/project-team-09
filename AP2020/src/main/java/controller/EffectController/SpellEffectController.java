package controller.EffectController;

import controller.EffectController.EffectController;
import model.card.monster.MonsterType;
import model.card.spell_traps.Spell;
import model.card.spell_traps.SpellType;
import model.game.Board;
import model.game.Cell;
import model.game.Game;
import model.card.Card;
import model.game.Limits;
import view.CardEffectsView;
import view.responses.CardEffectsResponses;


public class SpellEffectController extends EffectController {

    public void MonsterReborn(Game game, Card card) {
        Card chosenCard = CardEffectsView.getCardFromBothGraveyards(game.getPlayerBoard().getGraveyard(), game.getRivalBoard().getGraveyard());
        game.addCardToHand(card);
    }

    public void TerraForming(Game game, Card card) {
        boolean hasFieldZoneSpell = false;
        for (Card tempCard : game.getPlayerDeck().getAllCards()) {
            if (tempCard.isSpell()) {
                Spell spell = (Spell) tempCard;
                if (spell.getSpellType().equals(SpellType.FIELD)) hasFieldZoneSpell = true;
            }
        }
        while (hasFieldZoneSpell) {
            Card chosenCard = CardEffectsView.getCardFromDeck(game.getPlayerDeck());
            if (chosenCard.isSpell()) {
                Spell spell = (Spell) chosenCard;
                if (spell.getSpellType().equals(SpellType.FIELD)) {
                    game.addCardToHand(spell);
                    break;
                } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL);
            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_AN_SPELL);
        }
    }

    public void PotofGreed(Game game, Card card) {
        if (game.getPlayerDeck().getAllCards().size() >= 2) {
            game.addCardToHand(game.getPlayerDeck().getMainDeck().getCards().get(0));
            game.getPlayerDeck().getMainDeck().getCards().remove(0);
            game.addCardToHand(game.getPlayerDeck().getMainDeck().getCards().get(0));
            game.getPlayerDeck().getMainDeck().getCards().remove(0);
        } else game.setWinner(game.getRival());
    }

    public void Raigeki(Game game, Card card) {
        for (Cell tempCell : game.getPlayerBoard().getMonsterZone()) {
            if (tempCell.isOccupied()) game.getPlayerBoard().removeCardFromMonsterZone(tempCell.getCard());
        }
    }

    public void ChangeofHeart(Game game, Card card) {

    }

    public void harpiesFeatherDuster(Game game, Card card) {
        for (Cell tempCell : game.getPlayerBoard().getSpellZone()) {
            if (tempCell.isOccupied()) game.getPlayerBoard().removeCardFromSpellZone(tempCell.getCard());
        }
    }

    public void SwordsofRevealingLight(Game game, Card card) {

    }

    public void DarkHole(Game game, Card card) {
        for (Cell tempCell : game.getPlayerBoard().getMonsterZone()) {
            if (tempCell.isOccupied()) game.getPlayerBoard().removeCardFromMonsterZone(tempCell.getCard());
        }
        for (Cell tempCell : game.getRivalBoard().getMonsterZone()) {
            if (tempCell.isOccupied()) game.getRivalBoard().removeCardFromMonsterZone(tempCell.getCard());
        }
    }

    public void SupplySquad(Game game, Card card) {

    }

    public void SpellAbsorption(Game game, Card card) {

    }

    public void Messengerofpeace(Game game, Card card) {

    }

    public void TwinTwisters(Game game, Card card) {

    }

    public void Mysticalspacetyphoon(Game game, Card card) {
        Card chosenCard = CardEffectsView.getCardFromBothBoards(game.getPlayerBoard().getSpellZone(), game.getRivalBoard().getSpellZone());
        Board board;
        if (EffectController.doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        board = game.getRivalBoard();
        board.removeCardFromSpellZone(chosenCard);
    }

    public void RingofDefense(Game game, Card card) {

    }

    public void Yami(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.FAIRY, -200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.FIEND, +200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.SPELLCASTER, +200);
    }

    public void Forest(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.BEAST, 200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.BEAST_WARRIOR, 200);
        addPowerNumbersToType(playerLimits, rivalLimits, MonsterType.INSECT, 200);
    }

    public void ClosedForest(Game game, Card card) {
        Limits limits;
        if (doesCardBelongsToPlayer(game, card)) limits = game.getPlayerLimits();
        else limits = game.getRivalLimits();
        limits.addFieldZoneATK(MonsterType.BEAST,100);
    }

    public void UMIIRUKA(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        playerLimits.addFieldZoneATK(MonsterType.AQUA,500);
        rivalLimits.addFieldZoneATK(MonsterType.AQUA,500);
        playerLimits.addFieldZoneDEF(MonsterType.AQUA,-400);
        rivalLimits.addFieldZoneDEF(MonsterType.AQUA,-400);
    }


    public void SwordofDarkDestruction(Game game, Card card) {

    }

    public void blackPendant(Game game, Card card) {

    }

    public void unitedWeStand(Game game, Card card) {

    }

    public void magnumShield(Game game, Card card) {

    }

    public void advancedRitualArt(Game game, Card card) {

    }

    public void magicCylinder(Game game, Card card) {

    }

    public void mirrorForce(Game game, Card card) {

    }

    public void mindCrush(Game game, Card card) {

    }

    public void trapHole(Game game, Card card) {

    }

    public void torrentialTribute(Game game, Card card) {

    }

    public void timeSeal(Game game, Card card) {

    }

    public void negateAttack(Game game, Card card) {

    }

    public void solemnWarning(Game game, Card card) {

    }

    public void magicJammer(Game game, Card card) {

    }

    public void calloftheHaunted(Game game, Card card) {

    }


    private void addPowerNumbersToType(Limits playerLimits, Limits rivalLimits, MonsterType type, int amount) {
        playerLimits.addFieldZoneATK(type, amount);
        playerLimits.addFieldZoneDEF(type, amount);
        rivalLimits.addFieldZoneATK(type, amount);
        rivalLimits.addFieldZoneDEF(type, amount);
    }


}
