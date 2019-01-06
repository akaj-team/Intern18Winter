package asiantech.internship.summer.unittest;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.User;

class Validate {

    private static boolean isUsernameLength(String username) {
        return username.length() > 8 && username.length() < 21;
    }

    private static boolean isUsernameAtLeastTwoNonConsecutiveUppercaseLetters(String username) {
        int length = username.length();
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (Character.isUpperCase(username.charAt(i))) {
                positions.add(i);
            }
        }
        int size = positions.size();
        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                if (positions.get(i + 1) - positions.get(i) == 1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isUsernameSpecialCharacters(String username) {
        int length = username.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(username.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUsernameAtMostTwoConsecutiveNumericCharacters(String username) {
        return username.equals("");
    }

    private static boolean isUsernameDoNotStartWithCapitalLettersOrNumbers(String username) {
        Character firstChar = username.charAt(0);
        return !Character.isUpperCase(firstChar) && !Character.isDigit(firstChar);
    }

    private static boolean isPasswordDifferentUsername(User user) {
        return !user.getUsername().equals(user.getPassword());
    }

    private static boolean isPasswordAtLeastTwoSpecialCharactersOrNumbers(String password) {
        int count = 0;
        int length = password.length();
        for (int i = 0; i < length; i++) {
            if ((!Character.isLetterOrDigit(password.charAt(i)) && !Character.isSpaceChar(password.charAt(i)) || Character.isDigit(password.charAt(i)))) {
                count++;
            }
        }
        return count > 1;
    }

    private static boolean isPasswordLoopCharacterMoreThanTwoTimesInARow(String password) {
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

    private static boolean isPasswordDoNotEndWithDigitOrSpecialCharacter(String password) {
        int length = password.length();
        return Character.isLetter(password.charAt(length - 1));
    }

    private static boolean isPasswordAtMostThreeConsecutiveNumericCharacters(String password) {
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

     static int checkLogin(User user) {
        if (!Validate.isUsernameLength(user.getUsername())) {
            return R.string.usernameShouldHave920Characters;
        }
        if (!Validate.isUsernameDoNotStartWithCapitalLettersOrNumbers(user.getUsername())) {
            return R.string.usernameDoNotStartWithCapitalLettersOrNumbers;
        }
        if (!Validate.isUsernameAtLeastTwoNonConsecutiveUppercaseLetters(user.getUsername())) {
            return R.string.usernameShouldHaveAtLeast2NonConsecutiveUpperCaseLetters;
        }
        if (!Validate.isUsernameAtMostTwoConsecutiveNumericCharacters(user.getUsername())) {
            return R.string.theUsernameShouldHaveAtMost2ConsecutiveNumericCharacters;
        }
        if (!Validate.isUsernameSpecialCharacters(user.getUsername())) {
            return R.string.theUsernameShouldNotHaveSpecialCharacters;
        }
        if (!Validate.isPasswordDifferentUsername(user)) {
            return R.string.theUsernameAndPasswordMustBeDifferent;
        }
        if (!Validate.isPasswordAtLeastTwoSpecialCharactersOrNumbers(user.getPassword())) {
            return R.string.passwordShouldHasAtLeastTwoSpecialCharactersOrNumbers;
        }
        if (!Validate.isPasswordLoopCharacterMoreThanTwoTimesInARow(user.getPassword())) {
            return R.string.passwordShouldNotLoopCharacterMoreThanTwoTimesInARow;
        }
        if (Validate.isPasswordDoNotEndWithDigitOrSpecialCharacter(user.getPassword())) {
            return R.string.passwordDoNotEndWithDigitOrSpecialCharacter;
        }
        if (Validate.isPasswordAtMostThreeConsecutiveNumericCharacters(user.getPassword())) {
            return R.string.passwordAtMostThreeConsecutiveNumericCharacters;
        }
        return R.string.loginSuccessful;
    }
}
