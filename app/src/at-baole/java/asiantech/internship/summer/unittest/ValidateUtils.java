package asiantech.internship.summer.unittest;

import android.text.TextUtils;

import java.util.regex.Pattern;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

public final class ValidateUtils {
    private static Pattern Username_Length_More_Than_8_And_Less_Than_21;
    private static Pattern Username_At_Least_2_NonConsecutive_Uppercase_Letters;
    private static Pattern Username_Not_Contain_Special_Characters;
    private static Pattern Username_At_Most_2_Consecutive_Digits;
    private static Pattern Username_Not_Start_With_Uppercase_Letters_Or_Digits;

    private static Pattern Password_At_Least_2_Special_Characters_Or_Digits;
    private static Pattern Password_Length_At_Least_8;
    private static Pattern Password_At_Most_2_Consecutive_Repeated_Letters;
    private static Pattern Password_Not_End_With_Digits_Or_Special_Characters;
    private static Pattern Password_At_Least_3_NonConsecutive_Uppercase_Letters;

    private ValidateUtils() {
        // No-op
    }

    private static void compileRegex() {
        Username_Length_More_Than_8_And_Less_Than_21 = Pattern.compile("^.{9,20}$");
        Username_At_Least_2_NonConsecutive_Uppercase_Letters = Pattern.compile("^.*[A-Z].+[A-Z]");
        Username_Not_Contain_Special_Characters = Pattern.compile("[^A-Za-z0-9]");
        Username_At_Most_2_Consecutive_Digits = Pattern.compile("^.*\\d{3,}");
        Username_Not_Start_With_Uppercase_Letters_Or_Digits = Pattern.compile("^[a-z]");

        Password_At_Least_2_Special_Characters_Or_Digits = Pattern.compile("^.*[^a-zA-Z].*[^a-zA-Z]");
        Password_Length_At_Least_8 = Pattern.compile("^.{8,}$");
        Password_At_Most_2_Consecutive_Repeated_Letters = Pattern.compile("^.*(.)\\1");
        Password_Not_End_With_Digits_Or_Special_Characters = Pattern.compile("^.*[a-zA-Z]$");
        Password_At_Least_3_NonConsecutive_Uppercase_Letters = Pattern.compile("^.*[A-Z].+[A-Z].+[A-Z]");
    }

    public static int validateUserName(String username) {
        compileRegex();
        if (!Username_Length_More_Than_8_And_Less_Than_21.matcher(username).find()) {
            return R.string.errorUsernameLengthMoreThan8AndLessThan21;
        }
        if (!Username_At_Least_2_NonConsecutive_Uppercase_Letters.matcher(username).find()) {
            return R.string.errorUsenameAtLeast2NonConsecutiveUppercaseLetters;
        }
        if (username.contains(" ")) {
            return R.string.errorUsernameNotContainSpace;
        }
        if (Username_Not_Contain_Special_Characters.matcher(username).find()) {
            return R.string.errorUsernameNotContainSpecialCharacters;
        }
        if (Username_At_Most_2_Consecutive_Digits.matcher(username).find()) {
            return R.string.errorUsernameAtMost2ConsecutiveDigits;
        }
        if (!Username_Not_Start_With_Uppercase_Letters_Or_Digits.matcher(username).find()) {
            return R.string.errorUsernameNotStartWithUppercaseLettersOrDigits;
        }
        return R.string.validUsername;
    }

    public static int validatePassword(String password) {
        compileRegex();
        if (!Password_At_Least_2_Special_Characters_Or_Digits.matcher(password).find()) {
            return R.string.errorPasswordAtLeast2SpecialCharactersOrDigits;
        }
        if (!Password_Length_At_Least_8.matcher(password).find()) {
            return R.string.errorPasswordLengthAtLeast8;
        }
        if (Password_At_Most_2_Consecutive_Repeated_Letters.matcher(password).find()) {
            return R.string.errorPasswordAtMost2ConsecutiveRepeatedLetters;
        }
        if (!Password_Not_End_With_Digits_Or_Special_Characters.matcher(password).find()) {
            return R.string.errorPasswordNotEndWithDigitsOrSpecialLetters;
        }
        if (!Password_At_Least_3_NonConsecutive_Uppercase_Letters.matcher(password).find()) {
            return R.string.errorPasswordAtLeast3NonConsecutiveUppercaseLetters;
        }
        return R.string.validPassword;
    }

    public static int checkPasswordMatchesUsername(User user) {
        if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
            if (user.getUsername().equals(user.getPassword())) {
                return R.string.PasswordMatchesUsername;
            }
        }
        return R.string.PasswordNotMatchUsername;
    }

    public static boolean isEmptyUsername(User user) {
        return TextUtils.isEmpty(user.getUsername());
    }

    public static boolean isEmptyPassword(User user) {
        return TextUtils.isEmpty(user.getPassword());
    }
}
