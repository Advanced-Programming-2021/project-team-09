package view.regexes;

import java.util.regex.Matcher;

abstract public class OneRoundGameRegexes {
    public static final String deselectCard = "select -d";
    public static final String nextPhase = "next phase";
    public static final String summon = "summon";
    public static final String set = "set";
    public static final String flipSummon = "flip-summon";
    public static final String attackToOpponentMonster = "attack (?<cellNumber>\\d+)";
    public static final String attackDirect = "attack direct";
    public static final String activeEffect = "active effect";
    public static final String showGraveyard = "show graveyard";
    public static final String showCard = "card show --selected";
    public static final String surrender = "surrender";


    private static final String[] selectMyMonsterCell = new String[2];
    static {
        selectMyMonsterCell[0] = "select --monster (?<cellNumber>\\d+)";
        selectMyMonsterCell[1] = "select -m (?<cellNumber>\\d+)";
    }

    private static final String[] selectMySpellCell = new String[2];
    static {
        selectMySpellCell[0] = "select --spell (?<cellNumber>\\d+)";
        selectMySpellCell[1] = "select -s (?<cellNumber>\\d+)";
    }
    private static final String[] selectMyFieldZone = new String[2];
    static {
        selectMyFieldZone[0] = "select --field";
        selectMyFieldZone[1] = "select -f";
    }
    private static final String[] selectCardFromMyHand = new String[2];
    static {
        selectCardFromMyHand[0] = "select --hand (?<cellNumber>\\d+)";
        selectCardFromMyHand[1] = "select -h (?<cellNumber>\\d+)";
    }
    private static final String[] selectOpponentMonsterCell = new String[4];
    static {
        selectOpponentMonsterCell[0] = "select --monster (?<cellNumber>\\d+) --opponent";
        selectOpponentMonsterCell[1] = "select --opponent --monster (?<cellNumber>\\d+)";
        selectOpponentMonsterCell[2] = "select -m (?<cellNumber>\\d+) -op";
        selectOpponentMonsterCell[3] = "select -op -m (?<cellNumber>\\d+)";
    }
    private static final String[] selectOpponentSpellCell = new String[4];
    static {
        selectOpponentSpellCell[0] = "select --spell (?<cellNumber>\\d+) --opponent";
        selectOpponentSpellCell[1] = "select --opponent --spell (?<cellNumber>\\d+)";
        selectOpponentSpellCell[2] = "select -s (?<cellNumber>\\d+) -op";
        selectOpponentSpellCell[3] = "select -op -s (?<cellNumber>\\d+)";
    }
    private static final String[] selectOpponentFieldZone = new String[4];
    static {
        selectOpponentFieldZone [0] = "select --field --opponent";
        selectOpponentFieldZone [1] = "select --opponent --field";
        selectOpponentFieldZone [2] = "select -f -op";
        selectOpponentFieldZone [3] = "select -op -f";
    }
    private static final String[] setAttack = new String[2];
    static {
        setAttack[0] ="set --position attack";
        setAttack[1] ="set -p attack";
    }
    private static final String[] setDefense = new String[2];
    static {
        setDefense[0] ="set --position defense";
        setDefense[1] ="set -p defence";
    }

    public static String[] getSelectMyMonsterCell(){
        return selectMyMonsterCell;
    }

    public static String[] getSelectMySpellCell(){
        return selectMySpellCell;
    }
    public static String[] getSelectMyFieldZone(){
        return selectMyFieldZone;
    }
    public static String[] getSelectCardFromMyHand(){
        return selectCardFromMyHand;
    }
    public static String[] getSelectOpponentMonsterCell(){
        return selectOpponentMonsterCell;
    }
    public static String[] getSelectOpponentSpellCell(){
        return selectOpponentSpellCell;
    }
    public static String[] getSelectOpponentFieldZone(){
        return selectOpponentFieldZone;
    }
    public static String[] getSetAttack(){
        return setAttack;
    }
    public static String[] getSetDefense(){
        return setDefense;
    }
    public static boolean doesItSelectMyMonsterCellCommand(String command){
        String[] selectMyMonsterCellRegex = getSelectMyMonsterCell();
        for (int i = 0; i < selectMyMonsterCellRegex.length; i++) {
            if (command.matches(selectMyMonsterCellRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSelectMySpellCellCommand(String command){
        String[] selectMySpellCellRegex = getSelectMySpellCell();
        for (int i = 0; i < selectMySpellCellRegex.length; i++) {
            if (command.matches(selectMySpellCellRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSelectMyFieldZoneCommand(String command){
        String[] selectMyFieldZoneRegex = getSelectMyFieldZone();
        for (int i = 0; i < selectMyFieldZoneRegex.length; i++) {
            if (command.matches(selectMyFieldZoneRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSelectCardFromMyHandCommand(String command){
        String[] selectCardFromMyHandRegex = getSelectCardFromMyHand();
        for (int i = 0; i < selectCardFromMyHandRegex.length; i++) {
            if (command.matches(selectCardFromMyHandRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSelectOpponentMonsterCellCommand(String command){
        String[] selectOpponentMonsterCellRegex = getSelectOpponentMonsterCell();
        for (int i = 0; i < selectOpponentMonsterCellRegex.length; i++) {
            if (command.matches(selectOpponentMonsterCellRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSelectOpponentSpellCellCommand(String command){
        String[] selectOpponentSpellCellRegex = getSelectOpponentSpellCell();
        for (int i = 0; i < selectOpponentSpellCellRegex.length; i++) {
            if (command.matches(selectOpponentSpellCellRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSelectOpponentFieldZoneCommand(String command){
        String[] selectOpponentFieldZoneRegex = getSelectOpponentFieldZone();
        for (int i = 0; i < selectOpponentFieldZoneRegex.length; i++) {
            if (command.matches(selectOpponentFieldZoneRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSetAttackCommand(String command){
        String[] setAttackRegex = getSetAttack();
        for (int i = 0; i < setAttackRegex.length; i++) {
            if (command.matches(setAttackRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItSetDefenseCommand(String command){
        String[] setDefenseRegex = getSetDefense();
        for (int i = 0; i < setDefenseRegex.length; i++) {
            if (command.matches(setDefenseRegex[i])) return true;
        }
        return false;
    }
    public static Matcher getRightMatcherForSelectMyMonsterCell(String command){
        String[] selectMyMonsterCellRegex = getSelectMyMonsterCell();
        for (int i = 0; i < selectMyMonsterCellRegex.length; i++) {
            if (command.matches(selectMyMonsterCellRegex[i])) return RegexFunctions.getCommandMatcher(command, selectMyMonsterCellRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSelectMySpellCell(String command){
        String[] selectMySpellCellRegex = getSelectMySpellCell();
        for (int i = 0; i < selectMySpellCellRegex.length; i++) {
            if (command.matches(selectMySpellCellRegex[i])) return RegexFunctions.getCommandMatcher(command, selectMySpellCellRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSelectMyFieldZone(String command){
        String[] selectMyFieldZoneRegex = getSelectMyFieldZone();
        for (int i = 0; i < selectMyFieldZoneRegex.length; i++) {
            if (command.matches(selectMyFieldZoneRegex[i])) return RegexFunctions.getCommandMatcher(command, selectMyFieldZoneRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSelectCardFromMyHand(String command){
        String[] selectCardFromMyHandRegex = getSelectCardFromMyHand();
        for (int i = 0; i < selectCardFromMyHandRegex.length; i++) {
            if (command.matches(selectCardFromMyHandRegex[i])) return RegexFunctions.getCommandMatcher(command, selectCardFromMyHandRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSelectOpponentMonsterCell(String command){
        String[] selectOpponentMonsterCellRegex = getSelectOpponentMonsterCell();
        for (int i = 0; i < selectOpponentMonsterCellRegex.length; i++) {
            if (command.matches(selectOpponentMonsterCellRegex[i])) return RegexFunctions.getCommandMatcher(command, selectOpponentMonsterCellRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSelectOpponentSpellCell(String command){
        String[] selectOpponentSpellCellRegex = getSelectOpponentSpellCell();
        for (int i = 0; i < selectOpponentSpellCellRegex.length; i++) {
            if (command.matches(selectOpponentSpellCellRegex[i])) return RegexFunctions.getCommandMatcher(command, selectOpponentSpellCellRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSelectOpponentFieldZone(String command){
        String[] selectOpponentFieldZoneRegex = getSelectOpponentFieldZone();
        for (int i = 0; i < selectOpponentFieldZoneRegex.length; i++) {
            if (command.matches(selectOpponentFieldZoneRegex[i])) return RegexFunctions.getCommandMatcher(command, selectOpponentFieldZoneRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSetAttack(String command){
        String[] setAttackRegex = getSetAttack();
        for (int i = 0; i < setAttackRegex.length; i++) {
            if (command.matches(setAttackRegex[i])) return RegexFunctions.getCommandMatcher(command, setAttackRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForSetDefense(String command){
        String[] setDefenseRegex = getSetDefense();
        for (int i = 0; i < setDefenseRegex.length; i++) {
            if (command.matches(setDefenseRegex[i])) return RegexFunctions.getCommandMatcher(command, setDefenseRegex[i]);
        }
        return null;
    }
}
