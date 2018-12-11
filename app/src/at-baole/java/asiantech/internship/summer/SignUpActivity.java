package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final int MIN_LENGTH = 6;
    private static final String PASSWORD_PATTERN = "(?=.*?[a-z])(?=.*?[0-9]).{6,}";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;
    private ImageButton mImgBtnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mEdtEmail = findViewById(R.id.edtEmail);
        mRbMale = findViewById(R.id.rbMale);
        mRbFemale = findViewById(R.id.rbFemale);
        mImgBtnApply = findViewById(R.id.btnApply);

        mEdtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkUsernameLength();
                isValidInformation();
            }
        });

        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPasswordLength();
                checkPasswordFormat();
                isValidInformation();
            }
        });

        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkEmailFormat();
                isValidInformation();
            }
        });

        mRbMale.setOnClickListener(view -> {
            isValidInformation();
        });

        mRbFemale.setOnClickListener(view -> {
            isValidInformation();
        });
    }

    private void checkUsernameLength() {
        if (mEdtUsername.getText().length() < MIN_LENGTH) {
            mEdtUsername.setError(getString(R.string.username6Letters));
            mEdtUsername.requestFocus();
        } else {
            mEdtUsername.setError(null);
        }
    }

    private void checkPasswordLength() {
        if (mEdtPassword.getText().length() < MIN_LENGTH) {
            mEdtPassword.setError(getString(R.string.password6Letters));
        }
    }

    private void checkPasswordFormat() {
        if (mEdtPassword.getText().length() >= MIN_LENGTH) {
            if (!validatePassword(mEdtPassword.getText().toString().trim())) {
                mEdtPassword.setError(getString(R.string.password1char1num));
                mEdtPassword.requestFocus();
            } else {
                mEdtPassword.setError(null);
            }
        }
    }

    private void checkEmailFormat() {
        if (!validateEmail(mEdtEmail.getText().toString().trim())) {
            mEdtEmail.setError(getString(R.string.invalidEmail));
            mEdtEmail.requestFocus();
        } else {
            mEdtEmail.setError(null);
        }
    }

    private void onClickApplyButton() {
        mImgBtnApply.setOnClickListener(view ->
                Toast.makeText(SignUpActivity.this, R.string.signUpSuccessfully, Toast.LENGTH_LONG).show());
    }

    //isValidInformation() method checks all the necessary conditions in order for Button Apply to be enabled
    private void isValidInformation() {
        if (mEdtUsername.getText().length() >= MIN_LENGTH
                && mEdtPassword.getText().length() >= MIN_LENGTH
                && validatePassword(mEdtPassword.getText().toString().trim())
                && validateEmail(mEdtEmail.getText().toString().trim())
                && (mRbMale.isChecked() || mRbFemale.isChecked())) {
            mImgBtnApply.setEnabled(true);
            onClickApplyButton();
        } else {
            mImgBtnApply.setEnabled(false);
        }
    }

    //This method checks if Password contains at least one character and one number
    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //This method checks if Email has the correct e-mail address format
    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
