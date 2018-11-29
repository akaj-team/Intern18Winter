package asiantech.internship.summer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public boolean isValidPassword(String mPassword) {
        Pattern patternPassword;
        Matcher matchePassword;
        final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{7,}$";
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
        matchePassword = patternPassword.matcher(mPassword);
        return matchePassword.matches();
    }

    public boolean isValidEmail(String mEmail) {
        Pattern patternEmail;
        Matcher matcherEmail;
        String emailPattern = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
        return matcherEmail.matches();
    }
}
