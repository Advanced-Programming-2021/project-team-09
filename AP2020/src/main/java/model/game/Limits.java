package model.game;

import model.card.Card;
import model.card.monster.Monster;
import model.card.monster.MonsterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Limits {

    int atkAddition = 0;
    int defAddition = 0;
    int attackBound = -1;
    ArrayList<Card> atkBounders = new ArrayList<>();
    ArrayList<EffectLimitations> limitations = new ArrayList<>();
    HashMap<Integer, Integer> spellUsageLimit = new HashMap<>();
    HashMap<MonsterType, Integer> fieldZoneATKAddition = new HashMap<>();
    HashMap<MonsterType, Integer> fieldZoneDEFAddition = new HashMap<>();
    HashMap<Card, Card> equipMonster = new HashMap<>();
    HashMap<Card, Integer> equipGadgetATKAddition = new HashMap<>();
    HashMap<Card, Integer> equipGadgetDEFAddition = new HashMap<>();
    HashSet<Integer> cantAttackCells = new HashSet<>();
    ArrayList<Card> monstersWeDontHaveControl = new ArrayList<>();


    public void removeCardLimitOnATKBound(Card card) {
        atkBounders.remove(card);
    }

    public void addCardLimitOnATKBound(Card card) {
        atkBounders.add(card);
    }

    public ArrayList<Card> getAtkBounders() {
        return atkBounders;
    }
    public void equipMonsterToCard(Card spell, Card monster) {
        equipMonster.put(spell, monster);
    }

    public void unEquipMonster(Card spell) {
        equipMonster.remove(spell);
        equipGadgetATKAddition.remove(spell);
        equipGadgetDEFAddition.remove(spell);
    }

    public void equipGadgetATKAddition(Card spell, int amount) {
        equipGadgetATKAddition.put(spell, amount);
    }

    public void equipGadgetDEFAddition(Card spell, int amount) {
        equipGadgetDEFAddition.put(spell, amount);
    }


    public void loseControlOfMonster(Card card) {
        monstersWeDontHaveControl.add(card);
    }

    public boolean hasControlOnMonster(Card card) {
        return !monstersWeDontHaveControl.contains(card);
    }

    public void getControlBackOnMonster(Card card) {
        monstersWeDontHaveControl.remove(card);
    }

    public void addLimit(EffectLimitations limitation) {
        limitations.add(limitation);
    }

    public void removeLimit(EffectLimitations limitation) {
        limitations.remove(limitation);
    }

    public void addFieldZoneATK(MonsterType cardType, int amount) {
        fieldZoneATKAddition.put(cardType, amount);
    }

    public void addFieldZoneDEF(MonsterType cardType, int amount) {
        fieldZoneDEFAddition.put(cardType, amount);
    }

    public void removeFieldZoneATK(MonsterType cardType) {
        fieldZoneATKAddition.remove(cardType);
    }

    public void removeFieldZoneDEF(MonsterType cardType) {
        fieldZoneDEFAddition.remove(cardType);
    }

    public int getATKAddition(Card card) {
        int addition = 0;
        addition += atkAddition;
        model.card.monster.MonsterType monsterType = ((Monster) card).getMonsterType();
        if (fieldZoneATKAddition.containsKey(monsterType)) {
            addition += fieldZoneATKAddition.get(monsterType);
        }
        ArrayList<Card> spellsThatEquipped = getSpellsThatEquipped(card);
        for (Card spell : spellsThatEquipped) {
            addition += equipGadgetATKAddition.get(spell);
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
        ArrayList<Card> spellsThatEquipped = getSpellsThatEquipped(card);
        for (Card spell : spellsThatEquipped) {
            addition += equipGadgetDEFAddition.get(spell);
        }
        return addition;
    }

    private ArrayList<Card> getSpellsThatEquipped(Card card) {
        ArrayList<Card> spellsThatEquipped = new ArrayList<>();
        for (Card spell : equipMonster.keySet()) {
            if (equipMonster.get(spell).equals(card)) spellsThatEquipped.add(spell);
        }
        return spellsThatEquipped;
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
        if (attackBound > this.attackBound)
            this.attackBound = attackBound;
    }

    public void removeAttackBound() {
        attackBound = 0;
    }


    public int getAttackBound() {
        return attackBound;
    }

    public boolean canAttackByThisLimitations(Card card) {
        if (limitations.contains(EffectLimitations.CANT_ATTACK) || !hasControlOnMonster(card)) return false;
        else {
            if (attackBound != -1) return true;
            else {
                return ((Monster) card).getAttack() + getATKAddition(card) <= attackBound;
            }
        }
    }

}



