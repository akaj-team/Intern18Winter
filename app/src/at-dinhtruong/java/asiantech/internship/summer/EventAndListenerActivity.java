package asiantech.internship.summer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventAndListenerActivity extends AppCompatActivity {
    private EditText medtUsername;
    private EditText medtPassword;
    private EditText medtEmail;
    private ImageView mimgApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_and_listener);
        init();
    }

    private void init() {
        medtUsername = findViewById(R.id.edtUsername);
        medtPassword = findViewById(R.id.edtPassword);
        medtEmail = findViewById(R.id.edtEmail);
        mimgApply = findViewById(R.id.imgApply);
        medtUsername.addTextChangedListener(loginTextWatcher);
        medtPassword.addTextChangedListener(loginTextWatcher);
        medtEmail.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String mUsernameInput = medtUsername.getText().toString().trim();
            String mPasswordInput = medtPassword.getText().toString().trim();
            String mEmailInput = medtEmail.getText().toString().trim();
            if (isValidEmail(mEmailInput)) {
                Log.d("TestEmailtrue", "ok"+mEmailInput);
            } else {
                Log.d("TestEmailFalse", "false"+mEmailInput);
            }
            if (isValidUsername(mUsernameInput) && isValidPassword(mPasswordInput) && isValidEmail(mEmailInput)) {
                mimgApply.setVisibility(View.VISIBLE);
            } else {
                mimgApply.setVisibility(View.GONE);
            }
        }
    };

    private static boolean isValidUsername(final String username) {
        Pattern patternUsername;
        Matcher matcherUsername;
        final String USERNAME_PATTERN = "^.{6,18}$";
        patternUsername = Pattern.compile(USERNAME_PATTERN);
        matcherUsername = patternUsername.matcher(username);
        return matcherUsername.matches();

    }

    private static boolean isValidPassword(final String password) {
        Pattern patternPassword;
        Matcher matchePasswordr;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
        matchePasswordr = patternPassword.matcher(password);
        return matchePasswordr.matches();

    }

    private static boolean isValidEmail(final String mEmail) {
        Pattern patternEmail;
        Matcher matcherEmail;
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
        return matcherEmail.matches();
    }
}
