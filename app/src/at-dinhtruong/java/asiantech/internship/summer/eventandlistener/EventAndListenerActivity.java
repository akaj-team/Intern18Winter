package asiantech.internship.summer.eventandlistener;

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
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
    private static final String USERNAME_PATTERN = "^.{6,18}$";
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
        initViewsAndListener();
    }

    private void initViewsAndListener() {
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
            checkUserPassEmail();
        }
    };

    private boolean isValidUsername(String userName) {
        Pattern patternUsername = Pattern.compile(USERNAME_PATTERN);
        Matcher matcherUsername = patternUsername.matcher(userName);
        return matcherUsername.matches();
    }

    private boolean isValidPassword(String passWord) {
        Pattern patternPassword = Pattern.compile(PASSWORD_PATTERN);
        Matcher matchePassword = patternPassword.matcher(passWord);
        return matchePassword.matches();
    }

    private boolean isValidEmail(String email) {
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }

    private void checkUserPassEmail() {
        String userNameInput = mEdtUsername.getText().toString().trim();
        String passWordInput = mEdtPassword.getText().toString().trim();
        String emailInput = mEdtEmail.getText().toString().trim();
        if (isValidUsername(userNameInput) && isValidPassword(passWordInput) && isValidEmail(emailInput) && (mRbMale.isChecked() || mRbFemale.isChecked())) {
            mImgApply.setVisibility(View.VISIBLE);
        } else if (!isValidUsername(userNameInput)) {
            mImgApply.setVisibility(View.GONE);
            mEdtUsername.setError(getString(R.string.userFormatWrong));
        } else if (!isValidPassword(passWordInput)) {
            mImgApply.setVisibility(View.GONE);
            mEdtPassword.setError(getString(R.string.passWordFormatWrong));
        } else if (!isValidEmail(emailInput)) {
            mImgApply.setVisibility(View.GONE);
            mEdtEmail.setError(getString(R.string.emailAddressFormatWrong));
        } else {
            mImgApply.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkUserPassEmail();
    }
}
