package view.regexes;

import java.util.regex.Matcher;

abstract public class LoginMenuRegex {
    private static final String[] createUserRegex = new String[12];
    private static final String[] loginRegex = new String[4];

    static {
        createUserRegex[0] = "^user create --username (?<username>.+) --nickname (?<nickname>.+) --password (?<password>.+)$";
        createUserRegex[1] = "^user create --nickname (?<nickname>.+) --username (?<username>.+) --password (?<password>.+)$";
        createUserRegex[2] = "^user create --nickname (?<nickname>.+) --password (?<password>.+) --username (?<username>.+)$";
        createUserRegex[3] = "^user create --password (?<password>.+) --nickname (?<nickname>.+) --username (?<username>.+)$";
        createUserRegex[4] = "^user create --password (?<password>.+) --username (?<username>.+) --nickname (?<nickname>.+)$";
        createUserRegex[5] = "^user create --username (?<username>.+) --password (?<password>.+) --nickname (?<nickname>.+)$";

        createUserRegex[6] = "^user create -u (?<username>.+) -n (?<nickname>.+) -p (?<password>.+)$";
        createUserRegex[7] = "^user create -n (?<nickname>.+) -u (?<username>.+) -p (?<password>.+)$";
        createUserRegex[8] = "^user create -n (?<nickname>.+) -p (?<password>.+) -u (?<username>.+)$";
        createUserRegex[9] = "^user create -p (?<password>.+) -n (?<nickname>.+) -u (?<username>.+)$";
        createUserRegex[10] = "^user create -p (?<password>.+) -u (?<username>.+) -n (?<nickname>.+)$";
        createUserRegex[11] = "^user create -u (?<username>.+) -p (?<password>.+) -n (?<nickname>.+)$";
    }

    static {
        loginRegex[0] = "^user login --password (?<password>.+) --username (?<username.+>)$";
        loginRegex[1] = "^user login --username (?<username.+>) --password (?<password>.+)$";
        loginRegex[2] = "^user login -u (?<username.+>) -p (?<password>.+)$";
        loginRegex[3] = "^user login -p (?<password>.+) -u (?<username.+>)$";
    }

    public static String[] getLoginRegex(){
        return loginRegex;
    }

    public static String[] getCreateUserRegex() {
        return createUserRegex;
    }

    public static Matcher getRightMatcherForCreateUser(String command){
        String[] createUserRegex = getCreateUserRegex();
        for (int i = 0; i < createUserRegex.length; ++i){
            if (command.matches(createUserRegex[i])) return RegexFunctions.getCommandMatcher(command,createUserRegex[i]);
        }
        return null;
    }

    public static Matcher getRightMatcherForLogin (String command){
        String[] loginRegex = getLoginRegex();
        for (int i = 0; i < loginRegex.length; ++i){
            if (command.matches(loginRegex[i])) return RegexFunctions.getCommandMatcher(command,loginRegex[i]);
        }
        return null;
    }

    public static boolean doesItLoginCommand (String command){
        String[] loginRegex = getLoginRegex();
        for (int i = 0; i < loginRegex.length; ++i){
            if (command.matches(loginRegex[i])) return true;
        }
        return false;
    }

    public static boolean doesItCreateUserCommand (String command){
        String[] createUserRegex = getCreateUserRegex();
        for (int i = 0; i < createUserRegex.length; ++i){
            if (command.matches(createUserRegex[i])) return true;
        }
        return false;
    }

}
