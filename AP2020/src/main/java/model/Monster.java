package model;
import controller.*;

import java.util.ArrayList;

public class Monster extends Card {
    protected int level;
    private int attack;
    private int permanentAttack;
    private int permanentDefense;
    private int defense;
    private MonsterEffectType monsterEffectType;
    private MonsterType monsterType;
    private MonsterCardType monsterCardType;
    private Attribute attribute;


    public Monster(String monsterName){
        ArrayList<String> temp = csvInfoGetter.monsterReadFromCSV(monsterName);
        if (temp == null || temp.size() != 8){
            return;
        }
        level = Integer.parseInt(temp.get(0));
        attribute = csvInfoGetter.getAttribute(temp.get(1));
        monsterType = csvInfoGetter.getMonsterType(temp.get(2));
        monsterCardType = csvInfoGetter.getMonsterCardType(temp.get(3));
        attack = Integer.parseInt(temp.get(4));
        defense = Integer.parseInt(temp.get(5));
        permanentAttack = attack;
        permanentDefense = defense;
        description = temp.get(6);
        this.cardName = monsterName;
        this.cardType = CardType.MONSTER;
}

    public int getNumberOfTributes() {
        if (level == 5 || level == 6) return 1;
        if (level >= 7) return 2;
        return 0;
    }

    public int getLevel(){
        return this.level;
    }

    public void increaseLevel(int level){ // todo bekar nemiad fek konam
        this.level += level;
    }

    public void increaseAttack(int attack) {
        this.attack += attack;
    }

    public void decreaseAttack(int attack) {
        this.attack -= attack;
    }

    public int getAttack() {
        return this.attack;
    }

    public void resetAttack() {
        this.attack = permanentAttack;
    }

    public void increaseDefense(int defense) {
        this.defense += defense;
    }

    public void decreaseDefense(int defense) {
        this.defense -= defense;
    }

    public int getDefense() {
        return this.defense;
    }

    public void resetDefense() {
        this.defense = permanentDefense;
    }

    public MonsterType getMonsterType() {
        return this.monsterType;
    }

    public MonsterEffectType getMonsterEffectType() {
        return this.monsterEffectType;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public boolean isMonsterRitual() {
        return monsterCardType == MonsterCardType.RITUAL;
    }

    public boolean hasEffect() {
        return this.monsterEffectType != MonsterEffectType.NONE;
    }

    public void setMonsterEffectType(MonsterEffectType monsterEffectType){
        this.monsterEffectType = monsterEffectType;
    }

    public void setCardID(String cardID){
        this.cardID = cardID;
    }

    public String toString(){
        StringBuilder temp = new StringBuilder();
        temp.append("Name: " + cardName + "\n");
        temp.append("Level: " + level + "\n");
        temp.append("Type: " + monsterType + "\n");
        temp.append("ATK: " + attack + "\n");
        temp.append("DEF: " + defense + "\n");
        temp.append("Description: " + description);
        return temp.toString();
    }
}
