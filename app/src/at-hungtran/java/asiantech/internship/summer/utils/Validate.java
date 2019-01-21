package asiantech.internship.summer.utils;

import java.util.regex.Pattern;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;

public final class Validate {
    private static final Pattern VALID_USERNAME_HAS_2_UPPERCASE_REGEX = Pattern.compile("^.*[A-Z].+[A-Z]");
    private static final Pattern VALID_USERNAME_NO_SPECIAL_CHARACTERS_AND_SPACE_REGEX = Pattern.compile("^(\\d|\\w)+$");
    private static final Pattern VALID_USERNAME_AT_MOST_2_CONSECITIVE_DIGITS_REGEX = Pattern.compile("^.*\\d{3,}");
    private static final Pattern VALID_USERNAME_NOT_BEGIN_WITH_UPPERCASE_AND_DIGIT_REGEX = Pattern.compile("^[A-Z0-9].+$");

    private static final Pattern VALID_PASSWORD_HAS_2_SPECIAL_CHAR_REGEX = Pattern.compile("^.*[^a-zA-Z].*[^a-zA-Z]");
    private static final Pattern VALID_PASSWORD_SMALLEST_LENGTH_IS_8_CHAR_REGEX = Pattern.compile("^.{8,}$");
    private static final Pattern VALID_PASSWORD_NO_MORE_2_CHAR_CONSECUTIVE_REGEX = Pattern.compile("^(?!.*(.)\\1)");
    private static final Pattern VALID_PASSWORD_NOT_END_WITH_DIGIT_OR_SPECIAL_CHAR_REGEX = Pattern.compile("^.*?[a-zA-Z]$");
    private static final Pattern VALID_PASSWORD_AT_LEAST_3_UPPERCASE_NON_CONSECUTIVE_REGEX = Pattern.compile("^.*[A-Z].+[A-Z].+[A-Z]");

    private Validate() {
        // no-op
    }

    public static int validateLogin(User user) {
        if (user.getUserName().length() < 9 || user.getUserName().length() > 20) {
            return R.string.errorUsernameLength;
        }
        if (!VALID_USERNAME_HAS_2_UPPERCASE_REGEX.matcher(user.getUserName()).find()) {
            return R.string.errorUsernameLeastTwoNonConsecutiveUppercaseLetters;
        }
        if (!VALID_USERNAME_NO_SPECIAL_CHARACTERS_AND_SPACE_REGEX.matcher(user.getUserName()).find()) {
            return R.string.errorUsernameNoSpecialCharactersAndSpace;
        }
        if (VALID_USERNAME_AT_MOST_2_CONSECITIVE_DIGITS_REGEX.matcher(user.getUserName()).find()) {
            return R.string.errorUsernameNoAtMost2ConsecivitiveDigits;
        }
        if (VALID_USERNAME_NOT_BEGIN_WITH_UPPERCASE_AND_DIGIT_REGEX.matcher(user.getUserName()).find()) {
            return R.string.errorUsernameNotBeginWithUppercaseAndDigit;
        }

        if (user.getUserName().equalsIgnoreCase(user.getPassword())) {
            return R.string.errorPasswordRepeatChar;
        }
        if (!VALID_PASSWORD_HAS_2_SPECIAL_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.errorPasswordNotHas2SpecialCharOrDigits;
        }
        if (!VALID_PASSWORD_SMALLEST_LENGTH_IS_8_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.errorPasswordSmallestLengthIs8Char;
        }
        if (!VALID_PASSWORD_NO_MORE_2_CHAR_CONSECUTIVE_REGEX.matcher(user.getPassword()).find()) {
            return R.string.errorPasswordNoMore2CharConsecutive;
        }
        if (!VALID_PASSWORD_NOT_END_WITH_DIGIT_OR_SPECIAL_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.errorPasswordNotEndWithDigitOrSpecialChar;
        }
        if (!VALID_PASSWORD_AT_LEAST_3_UPPERCASE_NON_CONSECUTIVE_REGEX.matcher(user.getPassword()).find()) {
            return R.string.errorPasswordAtLeast3UppercaseNonConsecutive;
        }
        return R.string.validUsernamePassword;
    }
}
