package view.regexes;

import java.util.regex.Matcher;

abstract public class DeckMenuRegex {
    public static final String creatDeckRegex = "^deck create (?<deckName>\\w+)$";
    public static final String deleteDeckRegex = "^deck delete (?<deckName>\\w+)$";
    public static final String activeDeckRegex = "^deck set-activate (?<deckName>\\w+)$";

    public static final String showAllDecksRegex = "^deck show --all$";
    public static final String showAllCardsRegex = "^deck show --cards$";
    public static final String showMainDeckRegex = "^deck show --deck-name (?<deckName>\\w+)$";
    public static final String showHelp = "^help$";
    private static final String[] addCardToMainDeckRegex = new String[4];
    static {
        addCardToMainDeckRegex[0] = "^deck add-card --card (?<cardName>[\\w -,]+) --deck (?<deckName>\\w+)$";
        addCardToMainDeckRegex[1] = "^deck add-card --deck (?<deckName>\\w+) --card (?<cardName>[\\w -,]+)$";
        addCardToMainDeckRegex[2] = "^deck add-card -c (?<cardName>[\\w -,]+) -d (?<deckName>\\w+)$";
        addCardToMainDeckRegex[3] = "^deck add-card -d (?<deckName>\\w+) -c (?<cardName>[\\w -,]+)$";
    }
    private static final String[] addCardToSideDeckRegex = new String[12];
    static {
        addCardToSideDeckRegex[0] =  "^deck add-card --card (?<cardName>[\\w -,]+) --deck (?<deckName>\\w+) --side$";
        addCardToSideDeckRegex[1] =  "^deck add-card --card (?<cardName>[\\w -,]+) --side --deck (?<deckName>\\w+)$";
        addCardToSideDeckRegex[2] =  "^deck add-card --side --card (?<cardName>[\\w -,]+) --deck (?<deckName>\\w+)$";
        addCardToSideDeckRegex[3] =  "^deck add-card --side --deck (?<deckName>\\w+) --card (?<cardName>[\\w -,]+)$";
        addCardToSideDeckRegex[4] =  "^deck add-card --deck (?<deckName>\\w+) --side --card (?<cardName>[\\w -,]+)$";
        addCardToSideDeckRegex[5] =  "^deck add-card --deck (?<deckName>\\w+) --card (?<cardName>[\\w -,]+) --side$";
        addCardToSideDeckRegex[6] =  "^deck add-card -c (?<cardName>[\\w -,]+) -d (?<deckName>\\w+) -s$";
        addCardToSideDeckRegex[7] =  "^deck add-card -c (?<cardName>[\\w -,]+) -s -d (?<deckName>\\w+)$";
        addCardToSideDeckRegex[8] =  "^deck add-card -s -c (?<cardName>[\\w -,]+) -d (?<deckName>\\w+)$";
        addCardToSideDeckRegex[9] =  "^deck add-card -s -d (?<deckName>\\w+) -c (?<cardName>[\\w -,]+)$";
        addCardToSideDeckRegex[10] =  "^deck add-card -d (?<deckName>\\w+) -s -c (?<cardName>[\\w -,]+)$";
        addCardToSideDeckRegex[11] =  "^deck add-card -d (?<deckName>\\w+) -c (?<cardName>[\\w -,]+) -s$";
    }
    private static final String[] removeCardFromMainDeckRegex = new String[4];
    static {
        removeCardFromMainDeckRegex[0] = "^deck rm-card --card (?<cardName>[\\w -,]+) --deck (?<deckName>\\w+)$";
        removeCardFromMainDeckRegex[1] = "^deck rm-card --deck (?<deckName>\\w+) --card (?<cardName>[\\w -,]+)$";
        removeCardFromMainDeckRegex[2] = "^deck rm-card -c (?<cardName>[\\w -,]+) -d (?<deckName>\\w+)$";
        removeCardFromMainDeckRegex[3] = "^deck rm-card -d (?<deckName>\\w+) -c (?<cardName>[\\w -,]+)$";
    }
    private static final String[] removeCardFromSideDeckRegex = new String[12];
    static {
        removeCardFromSideDeckRegex[0] = "^deck rm-card --card (?<cardName>[\\w -,]+) --deck (?<deckName>\\w+) --side$";
        removeCardFromSideDeckRegex[1] = "^deck rm-card --card (?<cardName>[\\w -,]+) --side --deck (?<deckName>\\w+)$";
        removeCardFromSideDeckRegex[2] = "^deck rm-card --side --card (?<cardName>[\\w -,]+) --deck (?<deckName>\\w+)$";
        removeCardFromSideDeckRegex[3] = "^deck rm-card --side --deck (?<deckName>\\w+) --card (?<cardName>[\\w -,]+)$";
        removeCardFromSideDeckRegex[4] = "^deck rm-card --deck (?<deckName>\\w+) --card (?<cardName>[\\w -,]+) --side$";
        removeCardFromSideDeckRegex[5] = "^deck rm-card --deck (?<deckName>\\w+) --side --card (?<cardName>[\\w -,]+)$";
        removeCardFromSideDeckRegex[6] = "^deck rm-card -c (?<cardName>[\\w -,]+) -d (?<deckName>\\w+) -s$";
        removeCardFromSideDeckRegex[7] = "^deck rm-card -c (?<cardName>[\\w -,]+) -s -d (?<deckName>\\w+)$";
        removeCardFromSideDeckRegex[8] = "^deck rm-card -s -c (?<cardName>[\\w -,]+) -d (?<deckName>\\w+)$";
        removeCardFromSideDeckRegex[9] = "^deck rm-card -s -d (?<deckName>\\w+) -c (?<cardName>[\\w -,]+)$";
        removeCardFromSideDeckRegex[10] = "^deck rm-card -d (?<deckName>\\w+) -c (?<cardName>[\\w -,]+) -s$";
        removeCardFromSideDeckRegex[11] = "^deck rm-card -d (?<deckName>\\w+) -s -c (?<cardName>[\\w -,]+)$";
    }
    private static final String[] showSideDeckRegex = new String[4];
    static {
        showSideDeckRegex[0] = "^deck show --deck-name (?<deckName>\\w+) --side$";
        showSideDeckRegex[1] = "^deck show --side --deck-name (?<deckName>\\w+)$";
        showSideDeckRegex[2] = "^deck show --deck-name (?<deckName>\\w+) -s$";
        showSideDeckRegex[3] = "^deck show -s --deck-name (?<deckName>\\w+)$";
    }
    public static String[] getAddCardToMainDeck(){
        return addCardToMainDeckRegex;
    }
    public static String[] getAddCardToSideDeck(){
        return addCardToSideDeckRegex;
    }
    public static String[] getRemoveCardFromMainDeck(){
        return removeCardFromMainDeckRegex;
    }
    public static String[] getRemoveCardFromSideDeck(){
        return removeCardFromSideDeckRegex;
    }
    public static String[] getShowSideDeck(){
        return showSideDeckRegex;
    }
    public static boolean doesItAddCardToMainDeckCommand(String command){
        String[] addCardToMainDeckRegex = getAddCardToMainDeck();
        for (int i = 0; i < addCardToMainDeckRegex.length; i++) {
            if (command.matches(addCardToMainDeckRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItAddCardToSideDeckCommand(String command){
        String[] addCardToSideDeckRegex = getAddCardToSideDeck();
        for (int i = 0; i < addCardToSideDeckRegex.length; i++) {
            if (command.matches(addCardToSideDeckRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItRemoveCardFromMainDeckCommand(String command){
        String[] removeCardFromMainDeckRegex = getRemoveCardFromMainDeck();
        for (int i = 0; i < removeCardFromMainDeckRegex.length; i++) {
            if (command.matches(removeCardFromMainDeckRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItRemoveCardFromSideDeckCommand(String command){
        String[] removeCardFromSideDeckRegex = getRemoveCardFromSideDeck();
        for (int i = 0; i < removeCardFromSideDeckRegex.length; i++) {
            if (command.matches(removeCardFromSideDeckRegex[i])) return true;
        }
        return false;
    }
    public static boolean doesItShowSideDeckCommand(String command){
        String[] showSideDeckRegex = getShowSideDeck();
        for (int i = 0; i < showSideDeckRegex.length; i++) {
            if (command.matches(showSideDeckRegex[i])) return true;
        }
        return false;
    }
    public static Matcher getRightMatcherForAddCardToMainDeck(String command){
        String[] addCardToMainDeckRegex = getAddCardToMainDeck();
        for (int i = 0; i < addCardToMainDeckRegex.length; i++) {
            if (command.matches(addCardToMainDeckRegex[i])) return RegexFunctions.getCommandMatcher(command, addCardToMainDeckRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForAddCardToSideDeck(String command){
        String[] addCardToSideDeckRegex = getAddCardToSideDeck();
        for (int i = 0; i < addCardToSideDeckRegex.length; i++) {
            if (command.matches(addCardToSideDeckRegex[i])) return RegexFunctions.getCommandMatcher(command, addCardToSideDeckRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForRemoveCardFromMainDeck(String command){
        String[] removeCardFromMainDeckRegex = getAddCardToMainDeck();
        for (int i = 0; i < removeCardFromMainDeckRegex.length; i++) {
            if (command.matches(removeCardFromMainDeckRegex[i])) return RegexFunctions.getCommandMatcher(command, removeCardFromMainDeckRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForRemoveCardFromSideDeck(String command){
        String[] removeCardFromSideDeckRegex = getRemoveCardFromSideDeck();
        for (int i = 0; i < removeCardFromSideDeckRegex.length; i++) {
            if (command.matches(removeCardFromSideDeckRegex[i])) return RegexFunctions.getCommandMatcher(command, removeCardFromSideDeckRegex[i]);
        }
        return null;
    }
    public static Matcher getRightMatcherForShowSideDeck(String command){
        String[] showSideDeckRegex = getShowSideDeck();
        for (int i = 0; i < showSideDeckRegex.length; i++) {
            if (command.matches(showSideDeckRegex[i])) return RegexFunctions.getCommandMatcher(command, showSideDeckRegex[i]);
        }
        return null;
    }
}
