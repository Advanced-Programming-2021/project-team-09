package view.regexes;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuRegex {
    public static final String changeNicknameRegex ="^profile change --nickname (?<nickname>[\\w]+)$";
    public static final String changeNicknameRegexShort="^profile change -n (?<nickname>[\\w]+)$";
    public static final String changePasswordRegexType1 ="^profile change --password --current (?<currentPassword>.+) --new (?<newPassword>.+)$";
    public static final String changePasswordRegexType2 ="^profile change --password --new (?<newPassword>.+) --current (?<currentPassword>.+)$";
    public static final String changePasswordRegexType1Short ="^profile change -p -c (?<currentPassword>.+) -n (?<newPassword>.+)$";
    public static final String changePasswordRegexType2Short ="^profile change -p -n (?<newPassword>.+) -c (?<currentPassword>.+)$";
}
