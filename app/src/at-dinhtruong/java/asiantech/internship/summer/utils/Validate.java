package asiantech.internship.summer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{7,}$";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";

    public boolean isValidPassword(String passWord) {
        Pattern patternPassword;
        Matcher matcherPassword;
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
        matcherPassword = patternPassword.matcher(passWord);
        return matcherPassword.matches();
    }

    public boolean isValidEmail(String email) {
        Pattern patternEmail;
        Matcher matcherEmail;
        patternEmail = Pattern.compile(EMAIL_PATTERN);
        matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }
}
