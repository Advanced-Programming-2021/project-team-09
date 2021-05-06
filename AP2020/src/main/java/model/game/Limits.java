package model.game;

import model.card.Card;
import model.card.CardType;
import model.card.monster.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Limits {

    int atkAddition = 0;
    int defAddition = 0;
    int attackBound = -1;

    ArrayList<EffectLimitations> limitations = new ArrayList<>();
    HashMap<Integer,Integer> spellUsageLimit = new HashMap<>();
    HashMap<CardType, Integer> fieldZoneATKAddition = new HashMap<>();
    HashMap<CardType, Integer> fieldZoneDEFAddition = new HashMap<>();
    HashSet<Integer> cantAttackCells = new HashSet<>();



    public void addLimit(EffectLimitations limitation) {
        limitations.add(limitation);
    }

    public void removeLimit(EffectLimitations limitation) {
        limitations.remove(limitation);
    }

    public void addFieldZoneATK(CardType cardType, int amount) {
        fieldZoneATKAddition.put(cardType, amount);
    }

    public void addFieldZoneDEF(CardType cardType, int amount) {
        fieldZoneDEFAddition.put(cardType, amount);
    }

    public void removeFieldZoneATK(CardType cardType) {
        fieldZoneATKAddition.remove(cardType);
    }

    public void removeFieldZoneDEF(CardType cardType) {
        fieldZoneDEFAddition.remove(cardType);
    }

    public int getATKAddition(Card card) {
        int addition = 0;
        addition += atkAddition;
        model.card.monster.MonsterType monsterType = ((Monster) card).getMonsterType();
        if (fieldZoneATKAddition.containsKey(monsterType)) {
            addition += fieldZoneATKAddition.get(monsterType);
        }
        return addition;
    }

    public int getDEFAddition(Card card) {
        int addition = 0;
        addition += defAddition;
        model.card.monster.MonsterType monsterType = ((Monster) card).getMonsterType();
        if (fieldZoneDEFAddition.containsKey(monsterType)) {
            addition += fieldZoneDEFAddition.get(monsterType);
        }
        return addition;
    }

    public boolean doesItContainsLimit(EffectLimitations limitation) {
        return limitations.contains(limitation);
    }

    public void increaseATKAddition(int amount) {
        atkAddition += amount;
    }

    public void decreaseATKAddition(int amount) {
        atkAddition -= amount;
    }

    public void increaseDEFAddition(int amount) {
        defAddition += amount;
    }

    public void decreaseDEFAddition(int amount) {
        defAddition -= amount;
    }

    public boolean canAttackCell(int cellNumber) {
        return cantAttackCells.contains(cellNumber);
    }

    public void banAttackingToCell(int cellNumber) {
        cantAttackCells.add(cellNumber);
    }

    public void unbanAttackingToCell(int cellNumber) {
        cantAttackCells.remove(cellNumber);
    }

    public void setAttackBound(int attackBound) {
        this.attackBound = attackBound;
    }

    public int getAttackBound() {
        return attackBound;
    }

    public boolean canAttackByThisLimitations(Card card) {
        if (limitations.contains(EffectLimitations.CANT_ATTACK)) return false;
        else {
            if (attackBound != -1) return true;
            else {
                return ((Monster) card).getAttack() + getATKAddition(card) <= attackBound;
            }
        }
    }

}



