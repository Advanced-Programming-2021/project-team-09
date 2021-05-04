package controller;

import model.Board;
import model.Cell;
import model.Game;
import model.Limits;
import model.card.Card;

public class MonsterEffectController {

    public void CommandKnight(Game game, Card card){
        Limits limits;
        if (doesCardBelongsToPlayer(game,card)) limits = game.getPlayerLimits();
        else limits = game.getRivalLimits();
        limits.increaseATKAddition(400);
        int 

    }

    public void BattleOX(){

    }

    public void AxeRaider(){

    }

    public void YomiShip(){

    }

    public void HornImp(){

    }

    public void SilverFang(){

    }

    public void Suijin(){

    }

    public void Fireyarou(){

    }

    public void Curtainofthedarkones(){

    }

    public void FeralImp(){

    }

    public void Darkmagician(){

    }

    public void Wattkid(){

    }

    public void Babydragon(){

    }

    public void Herooftheeast(){

    }

    public void Battlewarrior(){

    }

    public void Crawlingdragon(){

    }

    public void Flamemanipulator(){

    }

    public void BlueEyeswhitedragon(){

    }

    public void CrabTurtle(){

    }

    public void SkullGuardian(){

    }

    public void SlotMachine(){

    }

    public void Haniwa(){

    }

    public void ManEaterBug(){

    }

    public void GateGuardian(){

    }

    public void Scanner(){

    }

    public void Bitron(){

    }

    public void Marshmallon(){

    }

    public void BeastKingBarbaros(){

    }

    public void Texchanger(){

    }

    public void Leotron(){

    }

    public void TheCalculator(){

    }

    public void AlexandriteDragon(){

    }

    public void MirageDragon(){

    }

    public void HeraldofCreation(){

    }

    public void ExploderDragon(){

    }

    public void WarriorDaiGrepher(){

    }

    public void DarkBlade(){

    }

    public void Wattaildragon(){

    }

    public void TerratigertheEmpoweredWarrior(){

    }

    public void TheTricky(){

    }

    public void SpiralSerpent(){

    }


    public int getCellNumberOfMonster(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game,card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        Cell[] cells = board.getMonsterZone();
        for (int i = 0; i < cells.length ; i++) {
            if (cells[i].getCard().equals(card)) return i+1;
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
        return false;
    }
}
