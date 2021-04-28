package view.regexes;

import java.util.regex.Matcher;

public class ShopMenuRegexes {
    public static String[] shopShowCardsRegex = new String[2];
    public static final String shopCardRegex = "^shop buy (?<cardName>.+)$";

    static {
        shopShowCardsRegex[0] = "^shop show --all$";
        shopShowCardsRegex[1] = "^shop show -a$";
    }

    public static boolean isItShowAllCards(String command) {
        return command.matches(shopShowCardsRegex[0]) || command.matches(shopShowCardsRegex[1]);
    }

}
