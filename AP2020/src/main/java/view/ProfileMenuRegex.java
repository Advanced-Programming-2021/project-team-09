package view;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuRegex {
    public static final String changeNicknameRegex ="profile change --nickname (?<nickname>[\\w]+)";
    public static final String changeNicknameRegexShort="profile change -n (?<nickname>[\\w]+)";
    public static final String changePasswordRegexType1 ="profile change --password --current (?<currentPassword>.+) --new (?<newPassword>.+)";
    public static final String changePasswordRegexType2 ="profile change --password --new (?<newPassword>.+) --current (?<currentPassword>.+)";
    public static final String changePasswordRegexType1Short ="profile change -p -c (?<currentPassword>.+) -n (?<newPassword>.+)";
    public static final String changePasswordRegexType2Short ="profile change -p -n (?<newPassword>.+) -c (?<currentPassword>.+)";
    public static final Pattern changeNicknamePattern = Pattern.compile(changeNicknameRegex);
    public static final Pattern changeNicknamePatternShort = Pattern.compile(changeNicknameRegexShort);
    public static final Pattern changePasswordPatternType1 = Pattern.compile(changePasswordRegexType1);
    public static final Pattern changePasswordPatternType2 = Pattern.compile(changePasswordRegexType2);
    public static final Pattern changePasswordPatternType1Short = Pattern.compile(changePasswordRegexType1Short);
    public static final Pattern changePasswordPatternType2Short = Pattern.compile(changePasswordRegexType2Short);
    public static Matcher getCommandMatcher(String command , String regex){
        Pattern commandPattern = Pattern.compile(regex);
        return commandPattern.matcher(command);
    }


}
