package view.regexes;

abstract public class DeckMenuRegex {
    public static final String creatDeckRegex = "^deck create (?<deckName>\\w+)$";
    public static final String deleteDeckRegex = "^deck delete (?<deckName>\\w+)$";
    public static final String activeDeckRegex = "^deck set-activate (?<deckName>\\w+)$";
    public static final String addCardToMainDeckRegex = "^deck add-card --card (?<cardName>[\\w ]+) --deck (?<deckName>\\w+)$";
    public static final String addCardToSideDeckRegex = "^deck add-card --card (?<cardName>[\\w ]+) --deck (?<deckName>\\w+) --side$";
    public static final String removeCardFromMainDeckRegex = "^deck rm-card --card (?<cardName>[\\w ]+) --deck (?<deckName>\\w+)$";
    public static final String removeCardFromSideDeckRegex = "^deck rm-card --card (?<cardName>[\\w ]+) --deck (?<deckName>\\w+) --side$";

    public static final String showAllDecksRegex = "^deck show --all$";
    public static final String showAllCardsRegex = "^deck show --cards$";
    public static final String showMainDeckRegex = "^deck show --deck-name (?<deckName>\\w+)$";
    public static final String showSideDeckRegex = "^deck show --deck-name (?<deckName>\\w+) --side$";
    public static final String showHelp = "^help$";
}
