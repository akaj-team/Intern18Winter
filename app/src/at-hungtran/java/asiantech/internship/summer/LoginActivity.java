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
    private EditText mEdtUser;
    private EditText mEdtPwd;
    private EditText mEdtEmail;

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

    void checkUsernamePasswordandEmail() {
        Button btnLogin = findViewById(R.id.btn);

        String mUsername = mEdtUser.getText().toString();
        String mPassword = mEdtPwd.getText().toString();
        String mEmail = mEdtEmail.getText().toString();
        mEdtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        if (!isValidUsername(mUsername) || !isValidPassword(mPassword) || !isValidEmail(mEmail)) {
            btnLogin.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    boolean isValidUsername(String mUsername) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[[0-9]a-zA-Z]).{6,18}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(mUsername);

        return matcher.matches();

    }

    boolean isValidPassword(String mPassword) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(mPassword);

        return matcher.matches();

    }

    boolean isValidEmail(String mEmail) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mEmail);

        return matcher.matches();
    }
}
