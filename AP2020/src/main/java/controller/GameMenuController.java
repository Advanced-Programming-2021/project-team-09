package controller;

import model.game.Cell;
import model.game.Game;
import model.game.State;
import model.User;
import model.card.Card;
import model.card.CardFeatures;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.deck.MainDeck;
import view.responses.GameMenuResponses;

import java.util.ArrayList;

public class GameMenuController {

    static public String showTable(Game game) {
        return game.showTable();
    }

    static public Cell selectMonsterFromPlayer(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
    } // todo deselect cells

    static public GameMenuResponses canSelectMonsterFromPlayer(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public GameMenuResponses canSelectMonsterFromRival(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getRivalBoard().getMonsterZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (game.getRivalBoard().getMonsterZone()[cellNumber - 1].isFaceDown()) return GameMenuResponses.CARD_IS_HIDDEN;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public Cell selectMonsterFromRival(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getRivalBoard().getMonsterZone()[cellNumber - 1];
    }

    static public GameMenuResponses canSelectSpellAndTrapFromPlayer(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public GameMenuResponses canSelectSpellAndTrapFromRival(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getRivalBoard().getSpellZone()[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (game.getRivalBoard().getSpellZone()[cellNumber - 1].isFaceDown()) return GameMenuResponses.CARD_IS_HIDDEN;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public Cell selectSpellAndTrapFromPlayer(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getPlayerBoard().getSpellZone()[cellNumber - 1];
    }

    static public Cell selectSpellAndTrapFromRival(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return null;
        return game.getRivalBoard().getSpellZone()[cellNumber - 1];
    }

    static public GameMenuResponses canSelectPlayerFieldZone(Game game) {
        if (!game.getPlayerBoard().getFieldZone().isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public Cell selectPlayerFieldZone(Game game) {
        return game.getPlayerBoard().getFieldZone();
    }

    static public GameMenuResponses canSelectRivalFieldZone(Game game) {
        if (!game.getRivalBoard().getFieldZone().isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public Cell selectRivalFieldZone(Game game) {
        return game.getRivalBoard().getFieldZone();
    }

    static public Boolean canDraw(Game game) {
        return game.playerHasCapacityToDraw() && game.getPlayerDeck().getMainDeck().getNumberOfAllCards() > 0;
    }

    // returns the name of card which was added to hand
    static public String draw(Game game) {
        // todo use draw from game
        MainDeck tempDeck = game.getPlayerDeck().getMainDeck();
        ArrayList<Card> tempHand = game.getPlayerHandCards();
        Card tempCard = tempDeck.getCards().get(0);
        tempDeck.removeCard(tempCard.getCardName());
        tempHand.add(tempCard);
        return tempCard.getCardName();
    }

    static public GameMenuResponses canSelectCardFromHand(Game game, int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size()) return GameMenuResponses.INVALID_SELECTION;
        if (cardNumber < 1) return GameMenuResponses.INVALID_SELECTION;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public Card selectCardFromHand(Game game, int cardNumber) {
        ArrayList<Card> tempCards = game.getPlayerHandCards();
        if (cardNumber > tempCards.size() || cardNumber < 1) return null;
        return tempCards.get(cardNumber - 1);
    }


    static public GameMenuResponses summonStatus(Game game, int cardNumberInHand) {
        ArrayList<Card> cardsInHand = game.getPlayerHandCards();
        if (cardNumberInHand > cardsInHand.size() || cardNumberInHand < 1) return GameMenuResponses.INVALID_SELECTION;
        Card card = cardsInHand.get(cardNumberInHand - 1);
        if (!card.isMonster())
            return game.isSpellZoneFull() ? GameMenuResponses.SPELL_ZONE_IS_FULL : GameMenuResponses.SUCCESSFUL;
        if (!game.canSummon()) return GameMenuResponses.ALREADY_SUMMONED;
        if (!canNormalSummon(card.getFeatures())) return GameMenuResponses.THIS_CARD_CANT_NORMAL_SUMMON;
        Monster tempMonster = (Monster) card;
        if (tempMonster.getLevel() > 4) return canTributeSummon(game,tempMonster);
        return game.isMonsterZoneFull() ? GameMenuResponses.MONSTER_ZONE_IS_FULL : GameMenuResponses.SUCCESSFUL;
        // todo more conditions - card features
    }

    // for cards like beast king barbaros
    static private boolean normalAndSpecialSummonChecker(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.HAS_SPECIAL_NORMAL_SUMMON) return true;
        return false;
    }

    static private boolean canNormalSummon(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.NORMAL_SUMMON) return true;
        return false;
    }

    static private GameMenuResponses canTributeSummon(Game game, Monster monster) {
        Cell[] monsterCells = game.getPlayerBoard().getMonsterZone();
        int numMonsters = 0;
        for (Cell cell : monsterCells) if (cell.isOccupied()) numMonsters++;
        if (monster.getLevel() > 6)
            return numMonsters >= 2 ? GameMenuResponses.SUCCESSFUL : GameMenuResponses.NOT_ENOUGH_MONSTERS;
        else
            return numMonsters >= 1 ? GameMenuResponses.SUCCESSFUL : GameMenuResponses.NOT_ENOUGH_MONSTERS;
    }


    static public GameMenuResponses attackStatus(Game game, int cellNumberAttacker, int cellNumberDefender) {
        if (cellNumberAttacker > 5 || cellNumberAttacker < 1 || cellNumberDefender > 5 || cellNumberDefender < 1)
            return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        if (!game.getRivalBoard().getMonsterZone()[cellNumberAttacker - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].canAttack())
            return GameMenuResponses.ALREADY_ATTACKED;
        if (game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].getState() == State.FACE_DOWN_DEFENCE)
            return GameMenuResponses.YOU_HAVENT_SUMMONED_YET;
        Monster attackerMonster = (Monster) game.getPlayerBoard().getMonsterZone()[cellNumberAttacker - 1].getCard();
        if (!game.getPlayerLimits().canAttackByThisLimitations(attackerMonster)) return GameMenuResponses.CANT_ATTACK;
        if (!game.getRivalLimits().canAttackCell(cellNumberDefender)) return GameMenuResponses.CANT_ATTACK;
        return GameMenuResponses.SUCCESSFUL;
        // todo card features
    }

    static public String attack(Game game, int attackerCellNumber, int defenderCellNumber) {
        Cell defender = game.getRivalBoard().getMonsterZone(defenderCellNumber - 1);
        Cell attacker = game.getPlayerBoard().getMonsterZone(attackerCellNumber - 1);
        Monster att = (Monster) attacker.getCard();
        Monster def = (Monster) defender.getCard();
        if (defender.getState() == State.FACE_DOWN_DEFENCE) {
            rivalFlipSummon(game,defenderCellNumber);
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
    }

    static public GameMenuResponses selectedCardsCanBeTributed(Game game, int[] cellNumbers) {
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : cells) if (!cell.isOccupied()) return GameMenuResponses.NO_MONSTER_ON_THIS_ADDRESS;
        return GameMenuResponses.SUCCESSFUL;

    }

    static public void tribute(Game game, int[] cellNumbers) {
        for (int i : cellNumbers) game.getGraveyard().addCard(game.getPlayerBoard().getMonsterZone(i - 1).removeCard());
    }

    static public void summonMonster(Game game, int numberOFCardInHand) {
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

    static public void summonSpell(Game game, int cardNumberInHand) {
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

    static private boolean monsterHasSummonEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures cardFeatures : features) if (cardFeatures == CardFeatures.SUMMON_EFFECT) return true;
        return false;
    }

    static public GameMenuResponses canSetMonster(Game game, int cardNumberInHand) {
        ArrayList<Card> handCards = game.getPlayerHandCards();
        if (cardNumberInHand > handCards.size()) return GameMenuResponses.INVALID_SELECTION;
        if (!handCards.get(cardNumberInHand - 1).isMonster()) return GameMenuResponses.PLEASE_SELECT_MONSTER;
        Cell[] cells = game.getPlayerBoard().getMonsterZone();
        int occupied = 0;
        for (Cell cell : cells) if (cell.isOccupied()) occupied++;
        if (occupied == 5) return GameMenuResponses.MONSTER_ZONE_IS_FULL;
        if (!game.canSummon()) return GameMenuResponses.ALREADY_SUMMONED;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public void setMonsterCard(Game game, int numberOFCardInHand) {
        Card card = game.getPlayerHandCards().get(numberOFCardInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_DEFENCE);
            break;
        }
    }

    static public GameMenuResponses canSetPositionMonster(Game game, int cellNumber, String attackOrDefence) {
        if (cellNumber > 5 || cellNumber < 1) return GameMenuResponses.INVALID_SELECTION;
        Cell[] tempCells = game.getPlayerBoard().getMonsterZone();
        if (!tempCells[cellNumber - 1].isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        Card tempCard = tempCells[cellNumber - 1].getCard();
        if (tempCells[cellNumber - 1].getState() == State.FACE_DOWN_DEFENCE)
            return GameMenuResponses.YOU_HAVENT_SUMMONED_YET;
        State tempState = attackOrDefence.equals("attack") ? State.FACE_UP_ATTACK : State.FACE_UP_DEFENCE;
        if (tempCells[cellNumber - 1].getState() == tempState) return GameMenuResponses.ALREADY_IN_THIS_POSITION;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public void setPositionMonster(Game game, int cellNumber, State state) {
        game.getPlayerBoard().getMonsterZone(cellNumber - 1).setState(state);
    }


    static public GameMenuResponses canSetSpellCard(Game game, int cardNumberInHand) {
        ArrayList<Card> handCards = game.getPlayerHandCards();
        if (cardNumberInHand < handCards.size()) return GameMenuResponses.INVALID_SELECTION;
        if (handCards.get(cardNumberInHand - 1).isMonster()) return GameMenuResponses.PLEASE_SELECT_SPELL_OR_TRAP;
        int occupied = 0;
        Cell[] cells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : cells) if (cell.isOccupied()) occupied++;
        if (occupied == 5) return GameMenuResponses.SPELL_ZONE_IS_FULL;
        return GameMenuResponses.SUCCESSFUL;
    }

    static public void setSpellCard(Game game, int cardNumberInHand) {
        Card card = game.getPlayerHandCards().get(cardNumberInHand - 1);
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) continue;
            cell.addCard(card);
            cell.setState(State.FACE_DOWN_SPELL);
            break;
        }
    }


    static public GameMenuResponses canFlipSummon(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return GameMenuResponses.INVALID_SELECTION;
        Cell tempCell = game.getPlayerBoard().getMonsterZone()[cellNumber - 1];
        if (!tempCell.isOccupied()) return GameMenuResponses.NO_CARD_FOUND;
        if (tempCell.getRoundCounter() == 0 || tempCell.getState() != State.FACE_DOWN_DEFENCE)
            return GameMenuResponses.CANT_FLIP_SUMMON;
        return GameMenuResponses.SUCCESSFUL;

    }

    static public void flipSummon(Game game, int cellNumber) {
        Cell tempCell = game.getPlayerBoard().getMonsterZone(cellNumber - 1);
        tempCell.setState(State.FACE_UP_ATTACK);
        if (monsterHasSummonEffect(tempCell.getCard().getFeatures())) ;// todo tempCell.getCard().effect();
    }

    static public void rivalFlipSummon(Game game, int cellNumber) {
        Cell tempCell = game.getRivalBoard().getMonsterZone(cellNumber - 1);
        tempCell.setState(State.FACE_UP_DEFENCE);
        if (monsterHasSummonEffect(tempCell.getCard().getFeatures())) ;// todo tempCell.getCard().effect();
    }

    static private boolean monsterHasFlipEffect(ArrayList<CardFeatures> features) {
        for (CardFeatures feature : features) if (feature == CardFeatures.FLIP_EFFECT) return true;
        return false;
    }

    static public GameMenuResponses canRitualSummon(Game game, int cardNumberInHand, int[] tributeCells) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cardNumberInHand > cards.size()) return GameMenuResponses.INVALID_SELECTION;
        if (!cards.get(cardNumberInHand - 1).isMonster()) return GameMenuResponses.PLEASE_SELECT_MONSTER;
        if (game.getPlayerBoard().getSumLevel(new int[]{1, 2, 3, 4, 5}) < ((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getLevel())
            return GameMenuResponses.CANT_RITUAL_SUMMON;
        if (game.getPlayerBoard().getSumLevel(tributeCells) < ((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getLevel())
            return GameMenuResponses.SELECTED_LEVELS_DONT_MATCH;
        if (((Monster) game.getPlayerHandCards().get(cardNumberInHand - 1)).getMonsterCardType() != MonsterCardType.RITUAL)
            return GameMenuResponses.SELECTED_MONSTER_IS_NOT_RITUAL;
        boolean hasRitualSpell = false;
        Cell[] tempCells = game.getPlayerBoard().getSpellZone();
        for (Cell cell : tempCells) {
            if (cell.isOccupied()) {
                if (cell.getCard().getCardName().equals("Advanced Ritual Art")) {
                    hasRitualSpell = true;
                }
            }
        }
        if (!hasRitualSpell) return GameMenuResponses.NO_RITUAL_SPELL_IN_SPELLZONE;
        return GameMenuResponses.SUCCESSFUL;

    }

   static public void ritualSummon(Game game,int[] tributeCellNumbers, int numberOfCardInHand) {
        tribute(game,tributeCellNumbers);
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
        for (Cell cell : cells)
            if (!cell.isOccupied()) cell.addCard(game.getPlayerHandCards().get(numberOfCardInHand - 1));
    }

    static public GameMenuResponses canDirectAttack(Game game, int cellNumber) {
        if (cellNumber < 1 || cellNumber > 5) return GameMenuResponses.INVALID_SELECTION;
        if (!game.getPlayerBoard().getMonsterZone()[cellNumber - 1].isOccupied())
            return GameMenuResponses.NO_CARD_FOUND;
        Cell[] tempCells = game.getRivalBoard().getMonsterZone();
        boolean hasMonster = false;
        for (Cell cell : tempCells)
            if (cell.isOccupied()) {
                hasMonster = true;
                break;
            }
        if (hasMonster) return GameMenuResponses.CANT_ATTACK;
        if (!game.getPlayerBoard().getMonsterZone(cellNumber - 1).canAttack())
            return GameMenuResponses.ALREADY_ATTACKED;
        return GameMenuResponses.SUCCESSFUL;
        // todo more conditions
    }

    static public void directAttack(Game game, int cellNumber) {
        game.decreaseRivalHealth(((Monster) game.getPlayerBoard().getMonsterZone()[cellNumber - 1].getCard()).getAttack());
    }

    static public void setTrapCard(Game game, int cardNumberInHand) {
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

    static public void specialSummon(Game game, Card card) {
        // todo
    }

    static public String showPlayerGraveYard(Game game) {
        return game.getPlayerBoard().getGraveyard().toString();
    }

    static public String showRivalGraveYard(Game game) {
        return game.getRivalBoard().getGraveyard().toString();
    }

    static public String showCardFromPlayerMonsterZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return "invalid selection";
        if (!game.getPlayerBoard().getMonsterZone(cellNumber - 1).isOccupied()) return "E";
        return game.getPlayerBoard().getMonsterZone()[cellNumber - 1].getCard().toString();
    }

    static public String showCardFromPlayerSpellZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return "invalid selection";
        if (!game.getPlayerBoard().getSpellZone()[cellNumber - 1].isOccupied()) return "E";
        return game.getPlayerBoard().getSpellZone()[cellNumber - 1].getCard().toString();
    }

    static public String showCardFromRivalMonsterZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return "invalid selection";
        if (!game.getRivalBoard().getMonsterZone(cellNumber - 1).isOccupied()) return "E";
        if (game.getRivalBoard().getMonsterZone(cellNumber - 1).getState() == State.FACE_DOWN_DEFENCE)
            return "card is hidden";
        return game.getRivalBoard().getMonsterZone(cellNumber - 1).getCard().toString();
    }

    static public String showCardFromRivalSpellZone(Game game, int cellNumber) {
        if (cellNumber > 5 || cellNumber < 1) return "invalid selection";
        if (!game.getRivalBoard().getSpellZone()[cellNumber - 1].isOccupied()) return "E";
        if (game.getRivalBoard().getSpellZone()[cellNumber - 1].getState() == State.FACE_DOWN_SPELL)
            return "card is hidden";
        return game.getRivalBoard().getSpellZone()[cellNumber - 1].getCard().toString();
    }

    static public String showCardFromPlayerFieldZone(Game game) {
        Cell cell = game.getPlayerBoard().getFieldZone();
        if (!cell.isOccupied()) return "E";
        return cell.getCard().toString();
    }

    static public String showCardFromRivalFieldZone(Game game) {
        Cell cell = game.getRivalBoard().getFieldZone();
        if (!cell.isOccupied()) return "E";
        return cell.getCard().toString();
    }

    static public String showCardFromHand(Game game, int cardNumberInHand) {
        ArrayList<Card> cards = game.getPlayerHandCards();
        if (cards.size() < cardNumberInHand) return "invalid selection";
        return game.getPlayerHandCards().get(cardNumberInHand - 1).toString();
    }

    static public void cashOut(Game game,int maxLP, boolean is3RoundGame, User winner, User loser) {
        if (is3RoundGame) {
            winner.setScore(winner.getScore() + 3000);
            winner.increaseBalance(3000 + 3 * maxLP);
            loser.increaseBalance(300);
        } else {
            winner.increaseBalance(1000 + maxLP);
            winner.setScore(winner.getScore() + 1000);
        }
    }

    static public void sendToGraveYard(Game game,Card card) {
        //ToDo!
    }
}
