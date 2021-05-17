package view.regexes;

import java.util.regex.Matcher;

abstract public class DuelMenuRegex {
    private static final String[] duelNewPlayer = new String[12];

    static {
        duelNewPlayer[0] = "duel --new --second-player (?<username>.+) --rounds (?<rounds>\\d+)";
        duelNewPlayer[1] = "duel --new --rounds (?<rounds>\\d+) --second-player (?<username>.+)";
        duelNewPlayer[2] = "duel --second-player (?<username>.+) --new --rounds (?<rounds>\\d+)";
        duelNewPlayer[3] = "duel --second-player (?<username>.+) --rounds (?<rounds>\\d+) --new";
        duelNewPlayer[4] = "duel --rounds (?<rounds>\\d+) --new --second-player (?<username>.+)";
        duelNewPlayer[5] = "duel --rounds (?<rounds>\\d+) --second-player (?<username>.+) --new";
        duelNewPlayer[6] = "duel -n -s-p (?<username>.+) -r (?<rounds>\\d+)";
        duelNewPlayer[7] = "duel -n -r (?<rounds>\\d+) -s-p (?<username>.+)";
        duelNewPlayer[8] = "duel -r (?<rounds>\\d+) -s-p (?<username>.+) -n";
        duelNewPlayer[9] = "duel -r (?<rounds>\\d+) -n -s-p (?<username>.+)";
        duelNewPlayer[10] = "duel -s-p (?<username>.+) -n -r (?<rounds>\\d+)";
        duelNewPlayer[11] = "duel -s-p (?<username>.+) -r (?<rounds>\\d+) -n";
    }

    private static final String[] duelNewAi = new String[12];

    static {
        duelNewAi[0] = "duel --new --ai --rounds (?<rounds>\\d+)";
        duelNewAi[1] = "duel --rounds (?<rounds>\\d+) --ai --new";
        duelNewAi[2] = "duel --ai --rounds (?<rounds>\\d+) --new";
        duelNewAi[3] = "duel --ai --new --rounds (?<rounds>\\d+)";
        duelNewAi[4] = "duel --rounds (?<rounds>\\d+) --new --ai";
        duelNewAi[5] = "duel --new --rounds (?<rounds>\\d+) --ai";
        duelNewAi[6] = "duel -n -a -r (?<rounds>\\d+)";
        duelNewAi[7] = "duel -r (?<rounds>\\d+) -a -n";
        duelNewAi[8] = "duel -a -r (?<rounds>\\d+) -n";
        duelNewAi[9] = "duel -a -n -r (?<rounds>\\d+)";
        duelNewAi[10] = "duel -r (?<rounds>\\d+) -n -a";
        duelNewAi[11] = "duel -n -r (?<rounds>\\d+) -a";
    }

    public static String[] getDuelNewPlayer() {
        return duelNewPlayer;
    }

    public static String[] getDuelNewAi() {
        return duelNewAi;
    }

    public static Matcher getRightMatcherForDuelNewPlayer(String command) {
        String[] duelNewPlayerRegex = getDuelNewPlayer();
        for (String newPlayerRegex : duelNewPlayerRegex) {
            if (command.matches(newPlayerRegex)) return RegexFunctions.getCommandMatcher(command, newPlayerRegex);
        }
        return null;
    }

    public static Matcher getRightMatcherForDuelNewAi(String command) {
        String[] duelNewAiRegex = getDuelNewAi();
        for (String newPlayerRegex : duelNewAiRegex) {
            if (command.matches(newPlayerRegex)) return RegexFunctions.getCommandMatcher(command, newPlayerRegex);
        }
        return null;
    }

    public static boolean doesItDuelNewPlayerCommand(String command) {
        String[] duelNewPlayerRegex = getDuelNewPlayer();
        for (String newPlayerRegex : duelNewPlayerRegex) {
            if (command.matches(newPlayerRegex)) return true;
        }
        return false;
    }

    public static boolean doesItDuelNewAiCommand(String command) {
        String[] duelNewAiRegex = getDuelNewAi();
        for (String newPlayerRegex : duelNewAiRegex) {
            if (command.matches(newPlayerRegex)) return true;
        }
        return false;
    }
}
