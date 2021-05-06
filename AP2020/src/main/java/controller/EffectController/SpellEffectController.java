package controller.EffectController;

import controller.EffectController.EffectController;
import model.card.monster.MonsterType;
import model.card.spell_traps.Spell;
import model.card.spell_traps.SpellType;
import model.deck.Deck;
import model.game.*;
import model.card.Card;
import view.CardEffectsView;
import view.responses.CardEffectsResponses;

import java.util.ArrayList;


public class SpellEffectController extends EffectController {

    public void MonsterReborn(Game game, Card card) {
        Board board = getBoard(game, card);
        if (board.isMonsterZoneFull()) {
            CardEffectsView.respond(CardEffectsResponses.MONSTER_ZONE_IS_FULL);
            return;
        }
        while (true) {
            Card chosenCard = CardEffectsView.getCardFromBothGraveyards(game.getPlayerBoard().getGraveyard(), game.getRivalBoard().getGraveyard());
            if (chosenCard == null) return;
            if (!chosenCard.isMonster()) {
                board.addCardToMonsterZone(chosenCard);
                int cellNumber = MonsterEffectController.getCellNumberOfMonster(game, chosenCard);
                board.getMonsterZone(cellNumber).setState(State.FACE_UP_ATTACK);
                return;
            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
        }

    }

    public void TerraForming(Game game, Card card) {
        boolean hasFieldZoneSpell = false;
        for (Card tempCard : game.getPlayerDeck().getAllCards()) {
            if (tempCard.isSpell()) {
                Spell spell = (Spell) tempCard;
                if (spell.getSpellType().equals(SpellType.FIELD)) hasFieldZoneSpell = true;
            }
        }
        if (!hasFieldZoneSpell) CardEffectsView.respond(CardEffectsResponses.YOU_DONT_HAVE_ANY_FIELD_SPELL);
        while (hasFieldZoneSpell) {
            Card chosenCard = CardEffectsView.getCardFromDeck(game.getPlayerDeck());
            if (chosenCard.isSpell()) {
                Spell spell = (Spell) chosenCard;
                if (spell.getSpellType().equals(SpellType.FIELD)) {
                    game.addCardToHand(spell);
                    break;
                } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL);
            } else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL);
        }
    }

    public void PotofGreed(Game game, Card card) {
        ArrayList<Card> cardsInHands = getCardsInHand(game, card);
        Deck deck = getDeck(game, card);
        if (deck.getAllCards().size() >= 2) {
            cardsInHands.add(deck.getMainDeck().getCards().get(0));
            deck.getMainDeck().getCards().remove(0);
            cardsInHands.add(deck.getMainDeck().getCards().get(0));
            deck.getMainDeck().getCards().remove(0);
        } else game.setWinner(game.getRival());
    }

    public void Raigeki(Game game, Card card) {
        Board board = getBoard(game, card);
        for (Cell tempCell : board.getMonsterZone()) {
            if (tempCell.isOccupied()) board.removeCardFromMonsterZone(tempCell.getCard());
        }
    }

    public void ChangeofHeart(Game game, Card card) {

    }

    public void harpiesFeatherDuster(Game game, Card card) {
        Board board = getBoard(game, card);
        for (Cell tempCell : board.getSpellZone()) {
            if (tempCell.isOccupied()) board.removeCardFromSpellZone(tempCell.getCard());
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
        Deck deck = getDeck(game,card);
        ArrayList<Card> cardsInHand = getCardsInHand(game,card);
        if (deck.getMainDeck().getCards().size() == 0) {
            game.setWinner(getWinner(game,card));
        }
    }

    public void SpellAbsorption(Game game, Card card) {

    }

    public void Messengerofpeace(Game game, Card card) {

    }

    public void TwinTwisters(Game game, Card card) {

    }

    public void Mysticalspacetyphoon(Game game, Card card) {
        Card chosenCard;
        while (true) {
            chosenCard = CardEffectsView.getCardFromBothBoards(game.getPlayerBoard().getSpellZone(), game.getRivalBoard().getSpellZone());
            if (chosenCard == null) return;
            if (chosenCard.isSpell()) break;
            else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_AN_SPELL);
        }
        Board board = getBoard(game, card);
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
        limits.addFieldZoneATK(MonsterType.BEAST, 100);
    }

    public void UMIIRUKA(Game game, Card card) {
        Limits playerLimits = game.getPlayerLimits();
        Limits rivalLimits = game.getRivalLimits();
        playerLimits.addFieldZoneATK(MonsterType.AQUA, 500);
        rivalLimits.addFieldZoneATK(MonsterType.AQUA, 500);
        playerLimits.addFieldZoneDEF(MonsterType.AQUA, -400);
        rivalLimits.addFieldZoneDEF(MonsterType.AQUA, -400);
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

    public int getCellNumberOfSpell(Game game, Card card) {
        Board board;
        board = getBoard(game, card);
        Cell[] cells = board.getSpellZone();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getCard().equals(card)) return i;
        }
        return 0;
    }


}



