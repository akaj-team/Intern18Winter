package asiantech.internship.summer.unittest;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.User;

class Validate {
    static final String USERNAME_9_20_CHARATER = "Username should have 9 to 20 characters";
    static final String USERNAME_AT_LEAST_2_UPPER_CASE = "Username should have at least 2 uppercase letters";
    static final String USERNAME_SHOULD_NOT_HAVE_SPECIAL_CHARACTER = "Username should not have special characters";
    static final String USERNAME_SHOULD_HAVE_AT_MOST_2_NUMBER = "Username should have at most 2 number";
    static final String USERNAME_SHOULD_NOT_START_WHITH_UPPER_CASE = "Username do not start with uppercase or numbers";
    static final String USERNAME_AND_PASSWORD_ARE_DIFFERENT = "Username and password are different";
    static final String PASSWORD_SHOULD_HAVE_2_SPECIAL_OR_NUMBER = "Password should have at least 2 special characters or numbers";
    static final String PASSWORD_SHOULD_NOT_LOOP_CHARACTER_TWO_TIMES = "Password should not loop character more than two times";
    static final String PASSWORD_SHOULD_NOT_END_DIGIT_OR_SEPECIAL_CHAR = "Password do dot end with digit or special character";
    static final String PASSWORD_AT_LEAST_3_UPPERCASE_NOT_CONSECUTIVE = "Password at least three uppercase not consecutive";

    static boolean isUsernameLength(String username) {
        return username.length() > 8 && username.length() < 21;
    }

    private static int containUpcase(String usename) {
        int n = 0;
        int size = usename.length();
        for (int i = 0; i < size; i++) {
            if (Character.isUpperCase(usename.charAt(i))) {
                n++;
            }
        }
        return n;
    }

    static boolean isUsernameAtLeastTwoNonConsecutiveUppercaseLetters(String string) {
        int size = string.length();
        int n = 0;
        if (containUpcase(string) < 2) {
            return false;
        } else {
            for (int i = 0; i < size - 1; i++) {
                if (Character.isUpperCase(string.charAt(i)) && Character.isUpperCase(string.charAt(i + 1))) {
                    n++;
                }
            }
            return n < 1;
        }
    }

    static boolean isUsernameSpecialCharacters(String username) {
        int length = username.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(username.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int containDigit(String username) {
        int n = 0;
        int size = username.length();
        for (int i = 0; i < size; i++) {
            if (Character.isDigit(username.charAt(i))) {
                n++;
            }
        }
        return n;
    }

    static boolean isUsernameAtMostTwoConsecutiveNumericCharacters(String usename) {
        int length = usename.length();
        int count = 0;
        if (containDigit(usename) < 3) {
            return true;
        } else {
            for (int i = 0; i < length - 2; i++) {
                if (Character.isDigit(usename.charAt(i)) && Character.isDigit(usename.charAt(i + 1)) && Character.isDigit(usename.charAt(i + 2))) {
                    count++;
                }
            }
            return count <= 0;
        }
    }

    static boolean isUsernameDoNotStartWithCapitalLettersOrNumbers(String username) {
        Character firstChar = username.charAt(0);
        return !Character.isUpperCase(firstChar) && !Character.isDigit(firstChar);
    }

    static boolean isPasswordDifferentUsername(User user) {
        return !user.getUsername().equals(user.getPassword());
    }

    static boolean isPasswordAtLeastTwoSpecialCharactersOrNumbers(String password) {
        int count = 0;
        int length = password.length();
        for (int i = 0; i < length; i++) {
            if ((!Character.isLetterOrDigit(password.charAt(i)) && !Character.isSpaceChar(password.charAt(i)) || Character.isDigit(password.charAt(i)))) {
                count++;
            }
        }
        return count > 1;
    }

    static boolean isPasswordLoopCharacterMoreThanTwoTimesInARow(String password) {
        int length = password.length();
        if (length > 7) {
            for (int i = 0; i < length - 2; i++) {
                if (password.charAt(i) == password.charAt(i + 1) && password.charAt(i) == password.charAt(i + 2)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    static boolean isPasswordDoNotEndWithDigitOrSpecialCharacter(String password) {
        int length = password.length();
        return Character.isLetter(password.charAt(length - 1));
    }

    static boolean isPasswordAtMostThreeConsecutiveNumericCharacters(String password) {
        int length = password.length();
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                positions.add(i);
            }
        }
        int size = positions.size();
        if (size > 2) {
            for (int i = 0; i < size - 1; i++) {
                if (positions.get(i + 1) - positions.get(i) == 1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    static String checkLogin(User user) {
        if (!Validate.isUsernameLength(user.getUsername())) {
            return USERNAME_9_20_CHARATER;
        }
        if (!Validate.isUsernameAtLeastTwoNonConsecutiveUppercaseLetters(user.getUsername())) {
            return USERNAME_AT_LEAST_2_UPPER_CASE;
        }
        if (!Validate.isUsernameSpecialCharacters(user.getUsername())) {
            return USERNAME_SHOULD_NOT_HAVE_SPECIAL_CHARACTER;
        }
        if (!Validate.isUsernameAtMostTwoConsecutiveNumericCharacters(user.getUsername())) {
            return USERNAME_SHOULD_HAVE_AT_MOST_2_NUMBER;
        }

        if (!Validate.isUsernameDoNotStartWithCapitalLettersOrNumbers(user.getUsername())) {
            return USERNAME_SHOULD_NOT_START_WHITH_UPPER_CASE;
        }
        if (!Validate.isPasswordDifferentUsername(user)) {
            return USERNAME_AND_PASSWORD_ARE_DIFFERENT;
        }
        if (!Validate.isPasswordAtLeastTwoSpecialCharactersOrNumbers(user.getPassword())) {
            return PASSWORD_SHOULD_HAVE_2_SPECIAL_OR_NUMBER;
        }
        if (!Validate.isPasswordLoopCharacterMoreThanTwoTimesInARow(user.getPassword())) {
            return PASSWORD_SHOULD_NOT_LOOP_CHARACTER_TWO_TIMES;
        }
        if (!Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(user.getPassword())) {
            return PASSWORD_SHOULD_NOT_END_DIGIT_OR_SEPECIAL_CHAR;
        }
        if (!Validate.isPasswordAtMostThreeConsecutiveNumericCharacters(user.getPassword())) {
            return PASSWORD_AT_LEAST_3_UPPERCASE_NOT_CONSECUTIVE;
        }
        return String.valueOf(R.string.loginSuccessful);
    }
}
