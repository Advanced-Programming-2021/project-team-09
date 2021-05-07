package view.regexes;

import java.util.regex.Matcher;

abstract public class GameMenuRegex {
    private static final String[] duelNewPlayer = new String[4];
    static {
        duelNewPlayer[0] ="duel new --second-player (?<username>.+) --rounds (?<rounds>\\d+)";
        duelNewPlayer[1] ="duel new --rounds (?<rounds>\\d+) --second-player (?<username>.+)";
        duelNewPlayer[2] ="duel new -s-p (?<username>.+) -r (?<rounds>\\d+)";
        duelNewPlayer[3] ="duel new -r (?<rounds>\\d+) -s-p (?<username>.+)";
    }
    public static String[] getDuelNewPlayer(){
        return  duelNewPlayer;
    }
    public static Matcher getRightMatcherForDuelNewPlayer(String command){
        String[] duelNewPlayerRegex = getDuelNewPlayer();
        for (int i = 0; i < duelNewPlayerRegex.length; i++) {
            if (command.matches(duelNewPlayerRegex[i])) return RegexFunctions.getCommandMatcher(command, duelNewPlayerRegex[i]);
        }
        return null;
    }
    public static boolean doesItDuelNewPlayerCommand(String command){
        String[] duelNewPlayerRegex = getDuelNewPlayer();
        for (int i = 0; i < duelNewPlayerRegex.length; i++) {
            if (command.matches(duelNewPlayerRegex[i])) return true;
        }
        return false;
    }
}
