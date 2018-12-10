package asiantech.internship.summer;

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

public class SignUpActivity extends AppCompatActivity {
    private static Matcher matcherEmail;
    private static Pattern patternEmail;
    private static String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
    private int mCheck = 0;
    private boolean mCheckUser;
    private boolean mCheckPass;
    private boolean mCheckEmail;
    private boolean mCheckButton = false;
    private EditText mEdtUser;
    private EditText mEdtPass;
    private EditText mEdtEmail;
    private Button mBtnCheck;

    private static boolean isValidEmail(String mEmail) {
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
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
                if (mEdtUser.getText().length() < 6) {
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
        if (mEdtPass.getText().length() < 6) {
            mEdtPass.setError(getString(R.string.errorInput));
            mCheckPass = false;
            unShowButtonApply();
        } else {
            for (int i = 0; i < mEdtPass.getText().length(); i++) {
                if ((int) mEdtPass.getText().charAt(i) >= 48 && (int) mEdtPass.getText().charAt(i) <= 57) {
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
