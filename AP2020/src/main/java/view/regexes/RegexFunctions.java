package view.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFunctions {
    public static Matcher getCommandMatcher(String command, String regex) {
        Pattern commandPattern = Pattern.compile(regex);
        return commandPattern.matcher(command);
    }

    public static Matcher getRightMatcherForChangePassword(String command) {
        Matcher matcher;
        if (command.matches(ProfileMenuRegex.changePasswordRegexType1))
            matcher = getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType1);
        else if (command.matches(ProfileMenuRegex.changePasswordRegexType1Short))
            matcher = getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType1Short);
        else if (command.matches(ProfileMenuRegex.changePasswordRegexType2))
            matcher = getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType2);
        else if (command.matches(ProfileMenuRegex.changePasswordRegexType2Short))
            matcher = getCommandMatcher(command, ProfileMenuRegex.changePasswordRegexType2Short);
        else matcher = null;
        return matcher;
    }

    public static Matcher getRightMatcherForChangeNickname(String command) {
        Matcher matcher;
        if (command.matches(ProfileMenuRegex.changeNicknameRegex))
            matcher = getCommandMatcher(command, ProfileMenuRegex.changeNicknameRegex);
        else if (command.matches(ProfileMenuRegex.changeNicknameRegexShort))
            matcher = getCommandMatcher(command, ProfileMenuRegex.changeNicknameRegexShort);
        else matcher = null;
        return matcher;
    }
}
