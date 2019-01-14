package asiantech.internship.summer.unittest;

public final class UtilValidate {
    public final static String CHECK_LENGTH_USERNAME = "Username must from 9 to 20 characters";
    public final static String UPPER_USERNAME = "Username must have at least 2 non consecutive capital character";
    public final static String USERNAME_IS_NOT_SPECIAL = "Username haven't special character and space";
    public final static String USERNAME_CONTAIN_DIGIT = "Username must less 3 next digit";
    public final static String START_USERNAME = "Username can't begin with upper cases and digit";
    public final static String PASSWORD_IS_NOT_USERNAME = "Password must different with username";
    public final static String PASSWORD_CONTAIN_SPECIAL = "Password have least 2 special character or digit";
    public final static String CHECK_LENGTH_PASSWORD = "Password must more than 8 characters";
    public final static String CHARACRER_PASSWORD_REPEAT = "Password isn't loop 1 character too 2 times next";
    public final static String END_PASSWORD = "Password don't finish with special character or digit";
    public final static String UPPER_PASSWORD = "Password must have at least 3 non consecutive capital character";
    public final static String SUCCESSFUL = "Successful!!!";
    public final static String USER = "trandinhphu";
    public final static String USER_UPCASE = "TranDinhPhu";
    public final static String USERNAME_RIGHT = "tranDinhPhu";

    public static boolean isLengthUsernameRight(String username) {
        return username.length() > 8 && username.length() < 21;
    }

    public static int containUpcase(String string) {
        int n = 0;
        int size = string.length();
        for (int i = 0; i < size; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                n++;
            }
        }
        return n;
    }

    public static boolean isUpcaseNotContinous(String string, int number) {
        int size = string.length();
        int n = 0;
        if (containUpcase(string) < number) {
            return false;
        } else {
            for (int i = 0; i < size - 1; i++) {
                if (Character.isUpperCase(string.charAt(i)) && Character.isUpperCase(string.charAt(i + 1))) {
                    n++;
                }
            }
            return n <= 0;
        }
    }

    public static boolean isUsernameNotSpecialCharacter(String username) {
        int size = username.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isLetterOrDigit(username.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int sumNumberOfUsername(String username) {
        int n = 0;
        int size = username.length();
        for (int i = 0; i < size; i++) {
            if (Character.isDigit(username.charAt(i))) {
                n++;
            }
        }
        return n;
    }

    public static boolean isUsernameHaveNotMoreTwoDigitContinous(String username) {
        int size = username.length();
        int n = 0;
        if (sumNumberOfUsername(username) <= 2) {
            return true;
        } else {
            for (int i = 0; i < size - 2; i++) {
                if (Character.isDigit(username.charAt(i)) && Character.isDigit(username.charAt(i + 1)) && Character.isDigit(username.charAt(i + 2))) {
                    n++;
                }
            }
            return n <= 0;
        }
    }

    public static boolean isUsernameStartLowerCase(String username) {
        return Character.isLowerCase(username.charAt(0));
    }

    public static boolean isPasswordNotUser(String username, String password) {
        return username.length() != password.length() || !password.contains(username);
    }

    public static boolean isPasswordContainsDigitAndSpecial(String password) {
        int n = 0;
        int size = password.length();
        for (int i = 0; i < size; i++) {
            if ((!Character.isLetterOrDigit(password.charAt(i)) || Character.isDigit(password.charAt(i)))) {
                n++;
            }
        }
        return n >= 2;
    }

    public static boolean isLengthPasswordRight(String password) {
        return password.length() >= 8;
    }

    public static boolean isPasswordNotRepeatCharacter(String password) {
        int size = password.length();
        int n = 0;
        for (int i = 0; i < size - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                n++;
            }
        }
        return n <= 0;
    }

    public static boolean isPasswordDoNotEndWithDigitOrCharacter(String password) {
        return Character.isLetter(password.charAt(password.length() - 1));
    }

    public static boolean isUpcaseNotMoreTwoUpperCaseContinous(String string, int number) {
        int size = string.length();
        int n = 0;
        if (containUpcase(string) < number) {
            return false;
        } else {
            for (int i = 0; i < size - 2; i++) {
                if (Character.isUpperCase(string.charAt(i)) && Character.isUpperCase(string.charAt(i + 1)) && Character.isUpperCase(string.charAt(i + 2))) {
                    n++;
                }
            }
            return n <= 0;
        }
    }

    public static String resultLogin(String username, String password) {
        if (!isLengthUsernameRight(username)) {
            return CHECK_LENGTH_USERNAME;
        }
        if (!isUpcaseNotContinous(username, 2)) {
            return UPPER_USERNAME;
        }
        if (!isUsernameNotSpecialCharacter(username)) {
            return USERNAME_IS_NOT_SPECIAL;
        }
        if (!isUsernameHaveNotMoreTwoDigitContinous(username)) {
            return USERNAME_CONTAIN_DIGIT;
        }
        if (!isUsernameStartLowerCase(username)) {
            return START_USERNAME;
        }
        if (!isLengthPasswordRight(password)) {
            return CHECK_LENGTH_PASSWORD;
        }
        if (!isPasswordNotUser(username, password)) {
            return PASSWORD_IS_NOT_USERNAME;
        }
        if (!isPasswordContainsDigitAndSpecial(password)) {
            return PASSWORD_CONTAIN_SPECIAL;
        }
        if (!isPasswordNotRepeatCharacter(password)) {
            return CHARACRER_PASSWORD_REPEAT;
        }
        if (!isPasswordDoNotEndWithDigitOrCharacter(password)) {
            return END_PASSWORD;
        }
        if (!isUpcaseNotMoreTwoUpperCaseContinous(password, 3)) {
            return UPPER_PASSWORD;
        }
        return SUCCESSFUL;
    }
}
