package controller.EffectController;

import model.User;
import model.deck.Deck;
import model.game.*;
import model.card.Card;

import java.util.ArrayList;

public class EffectController {
    public static boolean doesCardBelongsToPlayer(Game game, Card card) {
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells) {
            if (cell.getCard().equals(card)) return true;
        }
        cells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : cells) {
            if (cell.getCard().equals(card)) return true;
        }
        for (Card card1 : game.getPlayerHandCards()) {
            if (card1.equals(card)) return true;
        }
        return false;
    }

    public boolean isCellNumberValid(int cellNumber) {
        return cellNumber >= 0 && cellNumber < 5;
    }

    public State getStateOfCard(Game game, Card card) {
        State state;
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        for (Cell cell : board.getMonsterZone()) {
            if (cell.getCard().equals(card)) return cell.getState();
        }
        for (Cell cell : board.getSpellZone()) {
            if (cell.getCard().equals(card)) return cell.getState();
        }
        return null;
    }

    public User getWinner(Game game, Card card) {
        if (doesCardBelongsToPlayer(game, card)) return game.getPlayer();
        else return game.getRival();
    }

    public Board getBoard(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        return board;
    }
    public Deck getDeck(Game game, Card card) {
        if (doesCardBelongsToPlayer(game,card)) return game.getPlayerDeck();
        else return game.getRivalDeck();
    }

    public Limits getLimits(Game game,Card card) {
        if (doesCardBelongsToPlayer(game,card)) return game.getPlayerLimits();
        else return game.getRivalLimits();
    }

    public ArrayList<Card> getCardsInHand(Game game, Card card) {
        if (doesCardBelongsToPlayer(game,card)) return game.getPlayerHandCards();
        else return game.getRivalHandCards();
    }

}
