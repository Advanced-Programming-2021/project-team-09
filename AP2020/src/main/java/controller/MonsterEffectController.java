package controller;

import model.*;
import model.card.Card;
import model.card.monster.Monster;
import model.card.monster.MonsterEffectType;
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
        if (doesCardBelongsToPlayer(game, card)) board = game.getRivalBoard();
        else board = game.getPlayerBoard();
        while (true) {
            int cellNumber = CardEffectsView.getCellNumber() - 1;
            if (!isCellNumberValid(cellNumber)) CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            else {
                Cell cell = board.getMonsterZone(cellNumber);
                if (cell.isOccupied()) {
                    board.removeCardFromMonsterZone(cell.getCard());
                    return;
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            }
        }
    }

    public void GateGuardian(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game, card)) board = game.getPlayerBoard();
        else board = game.getRivalBoard();
        while (true) {
            int[] cellNumbers = CardEffectsView.getCellNumbers(3);
            if (isCellNumberValid(cellNumbers[0]) &&
                    isCellNumberValid(cellNumbers[1]) &&
                    isCellNumberValid(cellNumbers[2])) {
                Cell[] cells = new Cell[3];
                cells[0] = board.getMonsterZone(cellNumbers[0]);
                cells[1] = board.getMonsterZone(cellNumbers[1]);
                cells[2] = board.getMonsterZone(cellNumbers[2]);
                if (cells[0].isOccupied() && cells[1].isOccupied() && cells[2].isOccupied()) {
                    for (int i = 0; i < cells.length; ++i) {
                        board.removeCardFromMonsterZone(cells[i].getCard());
                        board.addCardToMonsterZone(card);
                    }
                    return;
                } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
            } else CardEffectsView.respond(CardEffectsResponses.INVALID_CELL_NUMBER);
        }
    }

    public void Scanner(Game game, Card card) {
        Board board;
        if (!card.getCardName().equals("Scanner")) card.destroy(game);
        if (doesCardBelongsToPlayer(game, card)) board = game.getRivalBoard();
        else board = game.getPlayerBoard();
        Graveyard graveyard = board.getGraveyard();
        Card card1;
        while (true) {
            card1 = CardEffectsView.getCarFromGraveyard(graveyard);
            if (card1 == null) return;
            else if (!(card1 instanceof Monster)) CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_MONSTER);
            else break;
        }
        Monster monster = (Monster) card;
        Monster monster1 = (Monster) card1;
        DuplicateMonster(monster, monster1);
        if (getStateOfCard(game,card).equals(State.FACE_UP_ATTACK) || getStateOfCard(game,card).equals(State.FACE_UP_DEFENCE)) {
            if (monster.getMonsterEffectType().equals(MonsterEffectType.CONTINUOUS)) monster.activeEffect(game);
        }
    }


    public void Marshmallon(Game game,Card card) {
        if (doesCardBelongsToPlayer(game,card)) game.decreaseRivalHealth(1000);
        else game.decreaseHealth(1000);
    }

    public void BeastKingBarbaros(Game game,Card card) {

    }

    public void Texchanger() {

    }

    public void Leotron() {

    }

    public void TheCalculator(Game game, Card card) {
        Board board;
        if (doesCardBelongsToPlayer(game,card)) board = game.getPlayerBoard();
        board = game.getRivalBoard();
        int sumLevel=0;
        for (int i = 0; i < 5; i++) {
            if(board.getMonsterZone(i).isOccupied() && board.getMonsterZone(i).isFaceUp()){
                Monster monster = (Monster) board.getMonsterZone(i).getCard();
                sumLevel += monster.getLevel();
            }
        }
        Monster monster = (Monster) card;
        monster.setAttack( sumLevel * 300);
    }

    public void AlexandriteDragon() {

    }

    public void MirageDragon(Game game, Card card) {
        Limits limits;
        Board board;
        if (doesCardBelongsToPlayer(game, card)) {
            limits = game.getRivalLimits();
            board = game.getPlayerBoard();
        }
        else {
            limits = game.getPlayerLimits();
            board = game.getRivalBoard();
        }
        if(board.getMonsterZoneCellByCard(card).isFaceUp()){
            limits.addLimit(EffectLimitations.CANT_ACTIVATE_TRAP);
        }
    }

    public void HeraldofCreation(Game game, Card card) {
        
    }

    public void ExploderDragon() {

    }

    public void WarriorDaiGrepher() {

    }

    public void DarkBlade() {

    }

    public void Wattaildragon() {

    }

    public void TerratigertheEmpoweredWarrior(Game game, Card card) {
        Board board;
        if(doesCardBelongsToPlayer(game,card)) board = game.getPlayerBoard();
        board = game.getRivalBoard();
        //todo be halat adi ehzar shode yani chi?? bedon in mizanam felan!
        //todo nabayad moqe summon kardan state ro ham moshakhas konim??!
        while (true){
            int numberOfCardInHand = CardEffectsView.getNumberOfCardInHand();
            Card chosenCard = game.getPlayerHandCards().get(numberOfCardInHand);
            if(chosenCard.isMonster()){
                Monster monster = (Monster) chosenCard;
                if(monster.getLevel() <= 4){
                    game.summonMonster(monster);
                    board.getMonsterZoneCellByCard(monster).setState(State.FACE_DOWN_DEFENCE);
                }
                else {
                    CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_LEVEL_4_OR_LESS);
                }
            }
            else {
                CardEffectsView.respond(CardEffectsResponses.PLEASE_SELECT_A_MONSTER);
            }
        }
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

    private void DuplicateMonster(Monster monster, Monster originalMonster) {
        monster.setMonsterEffectType(originalMonster.getMonsterEffectType());
        monster.setMonsterType(originalMonster.getMonsterType());
        monster.setAttack(originalMonster.getAttack());
        monster.setDefense(originalMonster.getDefense());
        monster.setLevel(originalMonster.getLevel());
        monster.setMonsterCardType(originalMonster.getMonsterCardType());
        monster.setCardName(originalMonster.getCardName());
    }

}
