package asiantech.internship.summer.eventlistener;

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

import asiantech.internship.summer.R;

public class LoginActivity extends AppCompatActivity {
    private static final String USERNAME_PATTERN = "^(?=.*[[0-9]a-zA-Z]).{6,18}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern mPattern;
    private Matcher mMatcher;
    private EditText mEdtUser;
    private EditText mEdtPwd;
    private EditText mEdtEmail;
    private Button mBtnLogin;
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
        mBtnLogin = findViewById(R.id.btnLogin);

        // set listeners
        mEdtUser.addTextChangedListener(mTextWatcher);
        mEdtPwd.addTextChangedListener(mTextWatcher);
        mEdtEmail.addTextChangedListener(mTextWatcher);

        mEdtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        // run once to disable if empty
        checkUsernamePasswordandEmail();

    }

    private void checkUsernamePasswordandEmail() {
        String username = mEdtUser.getText().toString();
        String password = mEdtPwd.getText().toString();
        String email = mEdtEmail.getText().toString();

        if (!isValidUsername(username) || !isValidPassword(password) || !isValidEmail(email)) {
            mBtnLogin.setVisibility(View.GONE);
        } else {
            mBtnLogin.setVisibility(View.VISIBLE);
        }
    }

    private boolean isValidUsername(String userName) {
        mPattern = Pattern.compile(USERNAME_PATTERN);
        mMatcher = mPattern.matcher(userName);

        return mMatcher.matches();

    }

    private boolean isValidPassword(String password) {
        mPattern = Pattern.compile(PASSWORD_PATTERN);
        mMatcher = mPattern.matcher(password);

        return mMatcher.matches();

    }

    private boolean isValidEmail(String email) {
        mPattern = Pattern.compile(EMAIL_PATTERN);
        mMatcher = mPattern.matcher(email);

        return mMatcher.matches();
    }
}
