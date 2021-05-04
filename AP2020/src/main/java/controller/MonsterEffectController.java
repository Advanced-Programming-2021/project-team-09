package controller;

import model.Board;
import model.Cell;
import model.Game;
import model.Limits;
import model.card.Card;
import model.deck.Graveyard;
import view.CardEffectsView;
import view.responses.CardEffectsResponses;

public class MonsterEffectController {

    public void CommandKnight(Game game, Card card) {
        Limits limits;
        if (doesCardBelongsToPlayer(game, card)) limits = game.getPlayerLimits();
        else limits = game.getRivalLimits();
        limits.increaseATKAddition(400);
        int cellNumber = getCellNumberOfMonster(game, card);
        limits.banAttackingToCell(cellNumber);
    }

    public void YomiShip() {

    }

    public void HornImp() {

    }

    public void SilverFang() {

    }

    public void Suijin() {

    }

    public void Fireyarou() {

    }

    public void Curtainofthedarkones() {

    }

    public void FeralImp() {

    }

    public void Darkmagician() {

    }

    public void Wattkid() {

    }

    public void Babydragon() {

    }

    public void Herooftheeast() {

    }

    public void Battlewarrior() {

    }

    public void Crawlingdragon() {

    }

    public void Flamemanipulator() {

    }

    public void BlueEyeswhitedragon() {

    }

    public void CrabTurtle() {

    }

    public void SkullGuardian() {

    }

    public void SlotMachine() {

    }

    public void Haniwa() {

    }

    public void ManEaterBug(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game,card)) board = game.getRivalBoard();
        else board = game.getPlayerBoard();

        int cellNumber = CardEffectsView.getCellNumber() - 1;
        if (!isCellNumberValid(cellNumber)) CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        else {
            Cell cell = board.getMonsterZone(cellNumber);
            if (cell.isOccupied()) {
                board.removeCardFromMonsterZone(cell.getCard());
            } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        }
    }

    public void GateGuardian(Game game,Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game,card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        int[] cellNumbers = CardEffectsView.getCellNumbers(3);
        if (isCellNumberValid(cellNumbers[0]) &&
            isCellNumberValid(cellNumbers[1]) &&
            isCellNumberValid(cellNumbers[2])) {
            Cell[] cells = new Cell[3];
            cells[0] = board.getMonsterZone(cellNumbers[0]);
            cells[1] = board.getMonsterZone(cellNumbers[1]);
            cells[2] = board.getMonsterZone(cellNumbers[2]);
            if (cells[0].isOccupied() && cells[1].isOccupied() && cells[2].isOccupied()) {
                for (int i =0 ; i < cells.length; ++i) {
                    board.removeCardFromMonsterZone(cells[i].getCard());
                    board.addCardToMonsterZone(card);
                }
            }
        }
    }

    public void Scanner(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game,card)) board = game.getRivalBoard();
        else board = game.getPlayerBoard();
        Graveyard graveyard = board.getGraveyard();
        Card card1 = CardEffectsView.getCarFromGraveyard(graveyard);
        if (card1 == null) return;
        

    }

    public void Bitron() {

    }

    public void Marshmallon() {

    }

    public void BeastKingBarbaros() {

    }

    public void Texchanger() {

    }

    public void Leotron() {

    }

    public void TheCalculator() {

    }

    public void AlexandriteDragon() {

    }

    public void MirageDragon() {

    }

    public void HeraldofCreation() {

    }

    public void ExploderDragon() {

    }

    public void WarriorDaiGrepher() {

    }

    public void DarkBlade() {

    }

    public void Wattaildragon() {

    }

    public void TerratigertheEmpoweredWarrior() {

    }

    public void TheTricky() {

    }

    public void SpiralSerpent() {

    }

    public int getCellNumberOfMonster(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        Cell[] cells = board.getMonsterZone();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getCard().equals(card)) return i + 1;
        }
        return 0;
    }

    public boolean doesCardBelongsToPlayer(Game game, Card card) {
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells) {
            if (cell.getCard().equals(card)) return true;
        }
        cells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : cells) {
            if (cell.getCard().equals(card)) return true;
        }
        for(Card card1 : game.getPlayerHandCards()) {
            if (card1.equals(card)) return true;
        }
        return false;
    }

    public boolean isCellNumberValid(int cellNumber) {
        return cellNumber >= 0 && cellNumber < 5;
    }

}
