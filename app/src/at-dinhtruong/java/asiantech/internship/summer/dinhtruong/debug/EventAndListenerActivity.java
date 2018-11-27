package asiantech.internship.summer.dinhtruong.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import asiantech.internship.summer.R;

public class EventAndListenerActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private ImageView mImgApply;
    private RadioButton mRbFemale;
    private RadioButton mRbMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_and_listener);
        init();
    }

    private void init() {
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mEdtEmail = findViewById(R.id.edtEmail);
        mRbFemale = findViewById(R.id.rbFemale);
        mRbMale = findViewById(R.id.rbMale);
        mImgApply = findViewById(R.id.imgApply);
        mEdtUsername.addTextChangedListener(loginTextWatcher);
        mEdtPassword.addTextChangedListener(loginTextWatcher);
        mEdtEmail.addTextChangedListener(loginTextWatcher);
        mRbFemale.setOnCheckedChangeListener(this);
        mRbMale.setOnCheckedChangeListener(this);
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
            checkAll();
        }
    };

    private boolean isValidUsername(String mUsername) {
        Pattern patternUsername;
        Matcher matcherUsername;
        final String USERNAME_PATTERN = "^.{6,18}$";
        patternUsername = Pattern.compile(USERNAME_PATTERN);
        matcherUsername = patternUsername.matcher(mUsername);
        return matcherUsername.matches();

    }

    private boolean isValidPassword(String mPassword) {
        Pattern patternPassword;
        Matcher matchePasswordr;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
        matchePasswordr = patternPassword.matcher(mPassword);
        return matchePasswordr.matches();

    }

    private boolean isValidEmail(String mEmail) {
        Pattern patternEmail;
        Matcher matcherEmail;
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
        return matcherEmail.matches();
    }

    private void checkAll() {
        String mUsernameInput = mEdtUsername.getText().toString().trim();
        String mPasswordInput = mEdtPassword.getText().toString().trim();
        String mEmailInput = mEdtEmail.getText().toString().trim();
        if (isValidUsername(mUsernameInput) && isValidPassword(mPasswordInput) && isValidEmail(mEmailInput) && (mRbMale.isChecked() || mRbFemale.isChecked())) {
            mImgApply.setVisibility(View.VISIBLE);
        } else if (!isValidUsername(mUsernameInput)) {
            mImgApply.setVisibility(View.GONE);
            mEdtUsername.setError("User Format Wrong!");
        } else if (!isValidPassword(mPasswordInput)) {
            mImgApply.setVisibility(View.GONE);
            mEdtPassword.setError("Password Format Wrong!");
        } else if (!isValidEmail(mEmailInput)) {
            mImgApply.setVisibility(View.GONE);
            mEdtEmail.setError("Email Address Format Wrong!");
        } else {
            mImgApply.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkAll();
    }
}

