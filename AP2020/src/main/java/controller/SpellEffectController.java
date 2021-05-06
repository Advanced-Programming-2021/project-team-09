package controller;

import controller.EffectController.EffectController;
import model.card.spell_traps.Spell;
import model.card.spell_traps.SpellType;
import model.game.Board;
import model.game.Cell;
import model.game.Game;
import model.card.Card;
import view.CardEffectsView;
import view.responses.CardEffectsResponses;


public class SpellEffectController {
    public void monsterReborn(Game game, Card card){
        Card chosenCard = CardEffectsView.getCardFromBothGraveyards(game.getPlayerBoard().getGraveyard(), game.getRivalBoard().getGraveyard());
        game.addCardToHand(card);
    }

    public void terraForming(Game game, Card card){
        boolean hasFieldZoneSpell = false;
        for (Card tempCard:game.getPlayerDeck().getAllCards()) {
            if(tempCard.isSpell()){
                Spell spell = (Spell) tempCard;
                if(spell.getSpellType().equals(SpellType.FIELD)) hasFieldZoneSpell = true;
            }
        }
        while (hasFieldZoneSpell){
            Card chosenCard = CardEffectsView.getCardFromDeck(game.getPlayerDeck());
            if(chosenCard.isSpell()){
                Spell spell = (Spell) chosenCard;
                if (spell.getSpellType().equals(SpellType.FIELD)){
                    game.addCardToHand(spell);
                    break;
                }
                else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_FIELD_SPELL);
            }
            else CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_AN_SPELL);
        }
    }

    public void potofGreed(Game game, Card card){
        if(game.getPlayerDeck().getAllCards().size()>=2){
            game.addCardToHand(game.getPlayerDeck().getMainDeck().getCards().get(0));
            game.getPlayerDeck().getMainDeck().getCards().remove(0);
            game.addCardToHand(game.getPlayerDeck().getMainDeck().getCards().get(0));
            game.getPlayerDeck().getMainDeck().getCards().remove(0);
        }
        else game.setWinner(game.getRival());
    }

    public void raigeki(Game game, Card card){
        for (Cell tempCell:game.getPlayerBoard().getMonsterZone()) {
            if(tempCell.isOccupied()) game.getPlayerBoard().removeCardFromMonsterZone(tempCell.getCard());
        }
    }

    public void changeofHeart(Game game, Card card){

    }

    public void harpiesFeatherDuster(Game game, Card card){
        for (Cell tempCell:game.getPlayerBoard().getSpellZone()) {
            if(tempCell.isOccupied()) game.getPlayerBoard().removeCardFromSpellZone(tempCell.getCard());
        }
    }
    public void swordsofRevealingLight(Game game, Card card){

    }

    public void darkHole(Game game, Card card){
        for (Cell tempCell:game.getPlayerBoard().getMonsterZone()) {
            if(tempCell.isOccupied()) game.getPlayerBoard().removeCardFromMonsterZone(tempCell.getCard());
        }
        for (Cell tempCell:game.getRivalBoard().getMonsterZone()) {
            if(tempCell.isOccupied()) game.getRivalBoard().removeCardFromMonsterZone(tempCell.getCard());
        }
    }

    public void supplySquad(Game game, Card card){

    }

    public void spellAbsorption(Game game, Card card){

    }

    public void messengerofpeace(Game game, Card card){

    }

    public void twinTwisters(Game game, Card card){

    }

    public void mysticalspacetyphoon(Game game, Card card){
        Card chosenCard = CardEffectsView.getCardFromBothBoards(game.getPlayerBoard().getSpellZone(), game.getRivalBoard().getSpellZone());
        Board board;
        if(EffectController.doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        board = game.getRivalBoard();
        board.removeCardFromSpellZone(chosenCard);
    }

    public void ringofDefense(Game game, Card card){

    }

    public void yami(Game game, Card card){

    }

    public void forest(Game game, Card card){

    }

    public void closedForest(Game game, Card card){

    }

    public void umiiruka(Game game, Card card){

    }

    public void swordofDarkDestruction(Game game, Card card){

    }

    public void blackPendant(Game game, Card card){

    }

    public void unitedWeStand(Game game, Card card){

    }

    public void magnumShield(Game game, Card card){

    }

    public void advancedRitualArt(Game game, Card card){

    }

    public void magicCylinder(Game game, Card card){

    }

    public void mirrorForce(Game game, Card card){

    }

    public void mindCrush(Game game, Card card){

    }

    public void trapHole(Game game, Card card){

    }

    public void torrentialTribute(Game game, Card card){

    }

    public void timeSeal(Game game, Card card){

    }

    public void negateAttack(Game game, Card card){

    }

    public void solemnWarning(Game game, Card card){

    }

    public void magicJammer(Game game, Card card){

    }

    public void calloftheHaunted(Game game, Card card){

    }
}
