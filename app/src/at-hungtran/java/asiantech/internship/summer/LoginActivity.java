package asiantech.internship.summer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity {
    private static final String USERNAME_PATTERN = "^(?=.*[[0-9]a-zA-Z]).{6,18}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern mPattern;
    private Matcher mMatcher;
    private EditText mEdtUser;
    private EditText mEdtPwd;
    private EditText mEdtEmail;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkUsernamePasswordandEmail();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdtUser = findViewById(R.id.edtUser);
        mEdtPwd = findViewById(R.id.edtPwd);
        mEdtEmail = findViewById(R.id.edtEmail);

        // set listeners
        mEdtUser.addTextChangedListener(mTextWatcher);
        mEdtPwd.addTextChangedListener(mTextWatcher);
        mEdtEmail.addTextChangedListener(mTextWatcher);

        // run once to disable if empty
        checkUsernamePasswordandEmail();
    }

    private void checkUsernamePasswordandEmail() {
        Button btnLogin = findViewById(R.id.btn);

        String username = mEdtUser.getText().toString();
        String password = mEdtPwd.getText().toString();
        String email = mEdtEmail.getText().toString();
        mEdtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        if (!isValidUsername(username) || !isValidPassword(password) || !isValidEmail(email)) {
            btnLogin.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    private boolean isValidUsername(String mUsername) {
        mPattern = Pattern.compile(USERNAME_PATTERN);
        mMatcher = mPattern.matcher(mUsername);

        return mMatcher.matches();

    }

    private boolean isValidPassword(String mPassword) {
        mPattern = Pattern.compile(PASSWORD_PATTERN);
        mMatcher = mPattern.matcher(mPassword);

        return mMatcher.matches();

    }

    private boolean isValidEmail(String mEmail) {
        mPattern = Pattern.compile(EMAIL_PATTERN);
        mMatcher = mPattern.matcher(mEmail);

        return mMatcher.matches();
    }
}
