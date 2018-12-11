package asiantech.internship.summer.EventListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import asiantech.internship.summer.R;

public class SignUpActivity extends AppCompatActivity {
    private static final int NUMBER_ZERO_IN_DECIMARL = 48;
    private static final int NUMBER_NINE_IN_DECIMARL = 57;
    private static final int LENGHT_MIN = 6;
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
    private int mCheck = 0;
    private boolean mCheckUser;
    private boolean mCheckPass;
    private boolean mCheckEmail;
    private boolean mCheckButton = false;
    private EditText mEdtUser;
    private EditText mEdtPass;
    private EditText mEdtEmail;
    private Button mBtnCheck;

    private static boolean isValidEmail(String email) {
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        mEdtUser = findViewById(R.id.edtUser);
        mEdtPass = findViewById(R.id.edtPass);
        mEdtEmail = findViewById(R.id.edtEmail);
        mBtnCheck = findViewById(R.id.btnCheck);
        unShowButtonApply();
        mEdtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEdtUser.getText().length() < LENGHT_MIN) {
                    mEdtUser.setError(getString(R.string.errorInput));
                    mCheckUser = false;
                    unShowButtonApply();
                } else {
                    mCheckUser = true;
                    chechInputFullInformation();
                }
            }
        });

        mEdtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInputPass();
            }
        });

        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isValidEmail(mEdtEmail.getText().toString())) {
                    mCheckEmail = true;
                    chechInputFullInformation();
                } else {
                    mCheckEmail = false;
                    unShowButtonApply();
                    mEdtEmail.setError(getString(R.string.errorInputEmail));
                }
            }
        });
    }

    private void showButtonApply() {
        mBtnCheck.setVisibility(View.VISIBLE);
    }

    private void unShowButtonApply() {
        mBtnCheck.setVisibility(View.GONE);
    }

    private void checkInputPass() {
        if (mEdtPass.getText().length() < LENGHT_MIN) {
            mEdtPass.setError(getString(R.string.errorInput));
            mCheckPass = false;
            unShowButtonApply();
        } else {
            for (int i = 0; i < mEdtPass.getText().length(); i++) {
                if ((int) mEdtPass.getText().charAt(i) >= NUMBER_ZERO_IN_DECIMARL && (int) mEdtPass.getText().charAt(i) <= NUMBER_NINE_IN_DECIMARL) {
                    mCheck += 1;
                }
            }
            if (mCheck == 0 || mCheck == mEdtPass.getText().length()) {
                mEdtPass.setError(getString(R.string.errorInputPassword));
                unShowButtonApply();
                mCheckPass = false;
            } else {
                mCheckPass = true;
                chechInputFullInformation();
                mCheck = 0;
            }
        }
    }

    private void chechInputFullInformation() {
        if (mCheckUser && mCheckPass && mCheckEmail && mCheckButton) {
            showButtonApply();
        }
    }

    public void onRadioButtonClicked(View view) {
        mCheckButton = true;
        chechInputFullInformation();
    }
}
