package controller;

import model.Cell;
import model.Game;
import model.State;
import model.User;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.deck.MainDeck;
import view.responses.GameMenuResponse;
import view.responses.GameMenuResponsesEnum;

import java.util.ArrayList;

public class GameMenuController {
    private Game game;

    public GameMenuController(Game game) {
        this.game = game;
    }

    public String showTable() {
        return game.showTable();
    }

    public Cell selectMonsterFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
    } // todo deselect cells

    public GameMenuResponsesEnum canSelectMonsterFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied())
            return GameMenuResponsesEnum.NO_CARD_FOUND;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public GameMenuResponsesEnum canSelectMonsterFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!game.getRivalBoard().getMonsterZone()[cellNumber - 1].isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        if (game.getRivalBoard().getMonsterZone()[cellNumber - 1].isFaceDown()) return GameMenuResponsesEnum.CARD_IS_HIDDEN;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public Cell selectMonsterFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getRivalBoard().getMonsterZone()[cellNumber - 1];
    }

    public GameMenuResponsesEnum canSelectSpellAndTrapFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!game.getPlayerBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public GameMenuResponsesEnum canSelectSpellAndTrapFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!game.getRivalBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        if (game.getRivalBoard().getSpellZone()[cellNumber - 1].isFaceDown()) return GameMenuResponsesEnum.CARD_IS_HIDDEN;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public Cell selectSpellAndTrapFromPlayer(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getPlayerBoard().getSpellZone()[cellNumber - 1];
    }

    public Cell selectSpellAndTrapFromRival(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getRivalBoard().getSpellZone()[cellNumber - 1];
    }

    public GameMenuResponsesEnum canSelectPlayerFieldZone() {
        if (!game.getPlayerBoard().getFieldZone().isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public Cell selectPlayerFieldZone() {
        return game.getPlayerBoard().getFieldZone();
    }

    public GameMenuResponsesEnum canSelectRivalFieldZone() {
        if (!game.getRivalBoard().getFieldZone().isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public Cell selectRivalFieldZone() {
        return game.getRivalBoard().getFieldZone();
    }

    public Boolean canDraw() {
        return game.playerHasCapacityToDraw() && game.getPlayerDeck().getMainDeck().getNumberOfAllCards() > 0;
    }

    // returns the name of card which was added to hand
    public String draw() {
        // todo use draw from game
        MainDeck tempDeck = game.getPlayerDeck().getMainDeck();
        ArrayList<Card> tempHand = game.getPlayerHandCards();
        Card tempCard = tempDeck.getCards().get(0);
        tempDeck.removeCard(tempCard.getCardName());
        tempHand.add(tempCard);
        return tempCard.getCardName();
    }

    public GameMenuResponsesEnum canSelectCardFromHand(int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size()) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (cardNumber < 1) return GameMenuResponsesEnum.INVALID_SELECTION;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public Card selectCardFromHand(int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size() || cardNumber < 1) return null;
        return tempCards.get(cardNumber - 1);
    }


    public GameMenuResponsesEnum summonStatus(int cardNumberInHand) {
        ArrayList<Card> cardsInHand = game.getPlayerHandCards();
        if (cardNumberInHand > cardsInHand.size() || cardNumberInHand < 1) return GameMenuResponsesEnum.INVALID_SELECTION;
        Card card = cardsInHand.get(cardNumberInHand - 1);
        if (!card.isMonster())
            return game.isSpellZoneFull() ? GameMenuResponsesEnum.SPELL_ZONE_IS_FULL : GameMenuResponsesEnum.SUCCESSFUL;
        if (!game.canSummon()) return GameMenuResponsesEnum.ALREADY_SUMMONED;
        if (!canNormalSummon(card.getFeatures())) return GameMenuResponsesEnum.THIS_CARD_CANT_NORMAL_SUMMON;
        Monster tempMonster = (Monster) card;
        if (tempMonster.getLevel() > 4) return canTributeSummon(tempMonster);
        return game.isMonsterZoneFull() ? GameMenuResponsesEnum.MONSTER_ZONE_IS_FULL : GameMenuResponsesEnum.SUCCESSFUL;
        // todo more conditions - card features
		// todo joda kardan spell
    }

    // for cards like beast king barbaros
    private boolean normalAndSpecialSummonChecker(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.HAS_SPECIAL_NORMAL_SUMMON) return true;
        return false;
    }

    private boolean canNormalSummon(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.NORMAL_SUMMON) return true;
        return false;
    }

    private GameMenuResponsesEnum canTributeSummon(Monster monster) {
        Cell[] monsterCells = game.getPlayerBoard().getMonsterZone();
        int numMonsters = 0;
        for (Cell cell : monsterCells) if (cell.isOccupied()) numMonsters++;
        if (monster.getLevel() > 6)
            return numMonsters >= 2 ? GameMenuResponsesEnum.SUCCESSFUL : GameMenuResponsesEnum.NOT_ENOUGH_MONSTERS;
        else
            return numMonsters >= 1 ? GameMenuResponsesEnum.SUCCESSFUL : GameMenuResponsesEnum.NOT_ENOUGH_MONSTERS;
    }


    public GameMenuResponsesEnum attackStatus(int cellNumberAttacker, int cellNumberDefender) {
        if (cellNumberAttacker > 5 || cellNumberAttacker < 1 || cellNumberDefender > 5 || cellNumberDefender < 1)
            return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].isOccupied())
            return GameMenuResponsesEnum.NO_CARD_FOUND;
        if (!game.getRivalBoard().getMonsterZone()[cellNumberAttacker - 1].isOccupied())
            return GameMenuResponsesEnum.NO_CARD_FOUND;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].canAttack())
            return GameMenuResponsesEnum.ALREADY_ATTACKED;
        if (game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].getState() == State.FACE_DOWN_DEFENCE)
            return GameMenuResponsesEnum.YOU_HAVENT_SUMMONED_YET;
        Monster attackerMonster = (Monster) game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].getCard();
        if (!game.getPlayerLimits().canAttackByThisLimitations(attackerMonster)) return GameMenuResponsesEnum.CANT_ATTACK;
        if (!game.getRivalLimits().canAttackCell(cellNumberDefender)) return GameMenuResponsesEnum.CANT_ATTACK;
        return GameMenuResponsesEnum.SUCCESSFUL;
        // todo card features
    }

    public String attack(int attackerCellNumber, int defenderCellNumber) {
        Cell defender = game.getRivalBoard().getMonsterZone(defenderCellNumber - 1);
        Cell attacker = game.getPlayerBoard().getMonsterZone(attackerCellNumber - 1);
        Monster att = (Monster) attacker.getCard();
        Monster def = (Monster) defender.getCard();
        if (defender.getState() == State.FACE_DOWN_DEFENCE) {
            rivalFlipSummon(defenderCellNumber);
            if (att.getAttack() == def.getDefense()) {
                attacker.setCanAttack(false);
                return "opponent’s monster card was " + def.getCardName() + " and no card was destroyed";
            } else if (att.getAttack() > def.getDefense()) {
                game.getRivalBoard().getGraveyard().addCard(game.getRivalBoard().getMonsterZone(defenderCellNumber - 1).removeCard());
                attacker.setCanAttack(false);
                return "opponent’s monster card was " + def.getCardName() + " and the defense position monster is destroyed";
            } else {
                game.decreaseHealth(def.getDefense() - att.getAttack());
                attacker.setCanAttack(false);
                return "opponent’s monster card was " + def.getCardName() + " and no card is destroyed and you received " + (def.getDefense() - att.getAttack()) + " battle damage";
            }
        } else if (defender.getState() == State.FACE_UP_ATTACK) {
            if (att.getAttack() == def.getAttack()) {
                game.getPlayerBoard().getGraveyard().addCard(game.getPlayerBoard().getMonsterZone(attackerCellNumber - 1).removeCard());
                game.getRivalBoard().getGraveyard().addCard(game.getRivalBoard().getMonsterZone(defenderCellNumber - 1).removeCard());
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            } else if (att.getAttack() > def.getAttack()) {
                attacker.setCanAttack(false);
                game.getRivalBoard().getGraveyard().addCard(game.getRivalBoard().getMonsterZone(defenderCellNumber - 1).removeCard());
                game.decreaseRivalHealth(att.getAttack() - def.getAttack());
                return "your opponent’s monster is destroyed and your opponent receives " + (att.getAttack() - def.getAttack()) + " battle damage";
            } else {
                game.getPlayerBoard().getGraveyard().addCard(game.getPlayerBoard().getMonsterZone(attackerCellNumber - 1).removeCard());
                game.decreaseHealth(def.getAttack() - att.getAttack());
                return "Your monster card is destroyed and you received " + (def.getAttack() - att.getAttack()) + " battle damage";
            }
        } else {
            if (att.getAttack() == def.getDefense()) {
                attacker.setCanAttack(false);
                return "no card was destroyed";
            } else if (att.getAttack() > def.getDefense()) {
                attacker.setCanAttack(false);
                game.getRivalBoard().getGraveyard().addCard(game.getRivalBoard().getMonsterZone(defenderCellNumber - 1).removeCard());
                return "the defense position monster is destroyed";
            } else {
                attacker.setCanAttack(false);
                game.decreaseHealth(def.getDefense() - att.getAttack());
                return "no card is destroyed and you received " + (def.getDefense() - att.getAttack()) + " battle damage";
            }
        } // todo page 32 AP GAME 2021
		// todo destroy attacker
		// todo lp func joda
		// todo attack controller
		
    }

    public GameMenuResponsesEnum selectedCardsCanBeTributed(int[] cellNumbers) {
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells) if (!cell.isOccupied()) return GameMenuResponsesEnum.NO_MONSTER_ON_THIS_ADDRESS;
        return GameMenuResponsesEnum.SUCCESSFUL;

    }

    public void tribute(int[] cellNumbers) {
        for (int i : cellNumbers) game.getGraveyard().addCard(game.getPlayerBoard().getMonsterZone(i - 1).removeCard());
    }

    public void summonMonster(int numberOFCardInHand) {
        Card card = game.getPlayerHandCards().get(numberOFCardInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_UP_ATTACK);
            break;
        }
        if (monsterHasSummonEffect(card.getFeatures())) ;// todo card.effect();
    }

    public void summonSpell(int cardNumberInHand) {
        Card card = game.getPlayerHandCards().get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_UP_SPELL);
            break;
        }
        // todo card.effect();
    }

    private boolean monsterHasSummonEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures cardFeatures : features) if (cardFeatures == CardFeatures.SUMMON_EFFECT) return true;
        return false;
    }

    public GameMenuResponsesEnum canSetMonster(int cardNumberInHand) {
        ArrayList<Card> handCards = game.getPlayerHandCards();
        if (cardNumberInHand > handCards.size()) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!handCards.get(cardNumberInHand - 1).isMonster()) return GameMenuResponsesEnum.PLEASE_SELECT_MONSTER;
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        int occupied = 0;
        for (Cell cell : cells) if (cell.isOccupied()) occupied++;
        if (occupied == 5) return GameMenuResponsesEnum.MONSTER_ZONE_IS_FULL;
        if (!game.canSummon()) return GameMenuResponsesEnum.ALREADY_SUMMONED;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public void setMonsterCard(int numberOFCardInHand) {
        Card card = game.getPlayerHandCards().get(numberOFCardInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_DEFENCE);
            break;
        }
    }

    public GameMenuResponsesEnum canSetPositionMonster(int cellNumber, String attackOrDefence) {
        if (cellNumber > 5 || cellNumber < 1) return GameMenuResponsesEnum.INVALID_SELECTION;
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        if (!tempCells[cellNumber - 1].isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        Card tempCard = tempCells[cellNumber - 1].getCard();
        if (tempCells[cellNumber - 1].getState() == State.FACE_DOWN_DEFENCE)
            return GameMenuResponsesEnum.YOU_HAVENT_SUMMONED_YET;
        State tempState = attackOrDefence.equals("attack") ? State.FACE_UP_ATTACK : State.FACE_UP_DEFENCE;
        if (tempCells[cellNumber - 1].getState() == tempState) return GameMenuResponsesEnum.ALREADY_IN_THIS_POSITION;
        return GameMenuResponsesEnum.SUCCESSFUL;
    }

    public void setPositionMonster(int cellNumber, State state) {
        game.getPlayerBoard().getMonsterZone(cellNumber - 1).setState(state);
		// todo changed position in cell
    }


    public GameMenuResponsesEnum canSetSpellCard(int cardNumberInHand) {
        ArrayList<Card> handCards = game.getPlayerHandCards();
        if (cardNumberInHand < handCards.size()) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (handCards.get(cardNumberInHand - 1).isMonster()) return GameMenuResponsesEnum.PLEASE_SELECT_SPELL_OR_TRAP;
        int occupied = 0;
        Cell[] cells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : cells) if (cell.isOccupied()) occupied++;
        if (occupied == 5) return GameMenuResponsesEnum.SPELL_ZONE_IS_FULL;
        return GameMenuResponsesEnum.SUCCESSFUL;
		// todo more conditions
    }

    public void setTrapCard(int cardNumberInHand) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        Card card = cards.get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (!cell.isOccupied()) {
                cell.addCard(card);
                cell.setState(State.FACE_DOWN_SPELL);
                break;
            }
        }
    }

    public void setSpellCard(int cardNumberInHand) {
        Card card = game.getPlayerHandCards().get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_SPELL);
            break;
        }
    }
	// todo server clineti tor..
	// hazfe can ha

    public GameMenuResponsesEnum canFlipSummon(int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return GameMenuResponsesEnum.INVALID_SELECTION;
        Cell tempCell = game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
        if (!tempCell.isOccupied()) return GameMenuResponsesEnum.NO_CARD_FOUND;
        if (tempCell.getRoundCounter() == 0 || tempCell.getState() != State.FACE_DOWN_DEFENCE)
            return GameMenuResponsesEnum.CANT_FLIP_SUMMON;
        return GameMenuResponsesEnum.SUCCESSFUL;

    }

    public void flipSummon(int cellNumber) {
        Cell tempCell = game.getPlayerBoard().getMonsterZone(cellNumber - 1);
        tempCell.setState(State.FACE_UP_ATTACK);
        if (monsterHasSummonEffect(tempCell.getCard().getFeatures())) ;// todo tempCell.getCard().effect();
    }

    public void rivalFlipSummon(int cellNumber) {
        Cell tempCell = game.getRivalBoard().getMonsterZone(cellNumber - 1);
        tempCell.setState(State.FACE_UP_DEFENCE);
        if (monsterHasSummonEffect(tempCell.getCard().getFeatures())) ;// todo tempCell.getCard().effect();
    }

    private boolean monsterHasFlipEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.FLIP_EFFECT) return true;
        return false;
    }

    public GameMenuResponsesEnum canRitualSummon(int cardNumberInHand, int[] tributeCells) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cardNumberInHand > cards.size()) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!cards.get(cardNumberInHand - 1).isMonster()) return GameMenuResponsesEnum.PLEASE_SELECT_MONSTER;
        if (game.getPlayerBoard().getSumLevel(new int[]{1,2,3,4,5}) < ((Monster)game.getPlayerHandCards().get(cardNumberInHand - 1)).getLevel())
            return GameMenuResponsesEnum.CANT_RITUAL_SUMMON;
        if (game.getPlayerBoard().getSumLevel(tributeCells) < ((Monster)game.getPlayerHandCards().get(cardNumberInHand - 1)).getLevel())
            return GameMenuResponsesEnum.SELECTED_LEVELS_DONT_MATCH;
        if (((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getMonsterCardType() != MonsterCardType.RITUAL)
            return GameMenuResponsesEnum.SELECTED_MONSTER_IS_NOT_RITUAL;
        boolean hasRitualSpell = false;
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) {
                if (cell.getCard().getCardName().equals("Advanced Ritual Art")){
                    hasRitualSpell = true;
                }
            }
        }
        if (!hasRitualSpell) return GameMenuResponsesEnum.NO_RITUAL_SPELL_IN_SPELLZONE;
        return GameMenuResponsesEnum.SUCCESSFUL;

    }

    public void ritualSummon(int[] tributeCellNumbers, int numberOfCardInHand) {
        tribute(tributeCellNumbers);
        Cell[] cells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : cells) {
            if (cell.isOccupied()) {
                if (cell.getCard().getCardName().equals("Advanced Ritual Art")) {
                    game.getPlayerBoard().getGraveyard().addCard(cell.removeCard());
                    break;
                }
            }
        }
        cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells){
            if (!cell.isOccupied()) {
				cell.addCard(game.getPlayerHandCards().get(numberOfCardInHand - 1));
				return;
			}
		}
    }

    public GameMenuResponsesEnum canDirectAttack(int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponsesEnum.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied())
            return GameMenuResponsesEnum.NO_CARD_FOUND;
        Cell[] tempCells = game.getRivalBoard().getMonsterZone();
        boolean hasMonster = false;
        for (Cell cell : tempCells)
            if (cell.isOccupied()) {
                hasMonster = true;
                break;
            }
        if (hasMonster) return GameMenuResponsesEnum.CANT_ATTACK;
        if (!game.getPlayerBoard().getMonsterZone(cellNumber - 1).canAttack())
            return GameMenuResponsesEnum.ALREADY_ATTACKED;
        return GameMenuResponsesEnum.SUCCESSFUL;
        // todo more conditions
    }

    public void directAttack(int cellNumber) {
        game.decreaseRivalHealth(((Monster) game.getPlayerBoard().getMonsterZone()[cellNumber - 1].getCard()).getAttack());
		// todo canAttack changed
    }

    public void specialSummon(Card card) {
        // todo
    }

    public GameMenuResponse showPlayerGraveYard() {
        return respondWithObj(game.getPlayerBoard().getGraveyard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public GameMenuResponse showRivalGraveYard() {
        return respondWithObj(game.getRivalBoard().getGraveyard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public GameMenuResponse showCardFromPlayerMonsterZone(int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showPlayerCard(game.getPlayerBoard().getMonsterZone(cellNumber - 1));
    }

    public GameMenuResponse showCardFromPlayerSpellZone(int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showPlayerCard(game.getPlayerBoard().getSpellZone()[cellNumber - 1]);
    }

    // for easier application of showCardFromPlayerMonsterZone & showCardFromPlayerSpellZone
    private GameMenuResponse showPlayerCard(Cell cell) {
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public GameMenuResponse showCardFromRivalMonsterZone(int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showRivalCard(game.getRivalBoard().getMonsterZone(cellNumber - 1));
    }

    public GameMenuResponse showCardFromRivalSpellZone(int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return showRivalCard(game.getRivalBoard().getSpellZone()[cellNumber - 1]);
    }

    // for easier application of showCardFromRivalSpellZone and showCardFromRivalMonsterZone
    private GameMenuResponse showRivalCard(Cell cell) {
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        State temp = cell.getState();
        if (temp == State.FACE_DOWN_DEFENCE || temp == State.FACE_DOWN_SPELL) return respond(GameMenuResponsesEnum.CARD_IS_HIDDEN);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public GameMenuResponse showCardFromPlayerFieldZone() {
        Cell cell = game.getPlayerBoard().getFieldZone();
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public GameMenuResponse showCardFromRivalFieldZone() {
        Cell cell = game.getRivalBoard().getFieldZone();
        if (!cell.isOccupied()) return respond(GameMenuResponsesEnum.NO_CARD_FOUND);
        return respondWithObj(cell.getCard().toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public GameMenuResponse showCardFromHand(int cardNumberInHand) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cards.size() < cardNumberInHand) return respond(GameMenuResponsesEnum.INVALID_SELECTION);
        return respondWithObj(game.getPlayerHandCards().get(cardNumberInHand - 1).toString(), GameMenuResponsesEnum.SUCCESSFUL);
    }

    public void cashOut(int maxLP, boolean is3RoundGame, User winner, User loser) {
        if (is3RoundGame) {
            winner.setScore(winner.getScore() + 3000);
            winner.increaseBalance(3000 + 3 * maxLP);
            loser.increaseBalance(300);
        } else {
            winner.increaseBalance(1000 + maxLP);
            winner.setScore(winner.getScore() + 1000);
        }
    }

    private GameMenuResponse respond(GameMenuResponsesEnum gameMenuResponsesEnum) {
        return new GameMenuResponse(gameMenuResponsesEnum);
    }

    private GameMenuResponse respondWithObj(Object obj, GameMenuResponsesEnum gameMenuResponsesEnum) {
        return new GameMenuResponse(obj, gameMenuResponsesEnum);
    }
}
