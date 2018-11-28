package asiantech.internship.summer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private int mCheck = 0;
    private boolean mCheckUser;
    private boolean mCheckPass;
    private boolean mCheckEmail;
    private boolean mCheckButton = false;
    protected EditText mEdtUser;
    protected EditText mEdtPass;
    protected EditText mEdtEmail;
    protected Button mBtnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        mEdtUser = findViewById(R.id.edtUser);
        mEdtPass = findViewById(R.id.edtPass);
        mEdtEmail = findViewById(R.id.edtEmail);
        mBtnCheck = findViewById(R.id.btn_check);
        unShow();
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
                    mEdtUser.setError("Vui lòng nhập ít nhất 6 kí tự");
                    mCheckUser = false;
                    unShow();
                }else{
                    mCheckUser = true;
                    signUpFull();
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
                if(isValidEmail(mEdtEmail.getText().toString())){
                    mCheckEmail = true;
                    signUpFull();
                }else{
                    mCheckEmail = false;
                    unShow();
                    mEdtEmail.setError("Lỗi cú pháp email");
                }
            }
        });
    }

    private void show(){
        mBtnCheck.setVisibility(View.VISIBLE);
    }
    private void unShow(){
        mBtnCheck.setVisibility(View.GONE);
    }
    private void checkInputPass(){
        if (mEdtPass.getText().length() < 6) {
            mEdtPass.setError("Vui lòng nhập ít nhất 6 kí tự");
            mCheckPass = false;
            unShow();
        }else {
            for (int i = 0; i < mEdtPass.getText().length(); i++) {
                if ((int) mEdtPass.getText().charAt(i) >= 48 && (int) mEdtPass.getText().charAt(i) <= 57) {
                    mCheck += 1;
                }
            }
            if (mCheck == 0 || mCheck == mEdtPass.getText().length()) {
                mEdtPass.setError("Vui lòng nhập kí tự và số");
                unShow();
                mCheckPass = false;
            }else{
                mCheckPass = true;
                signUpFull();
                mCheck = 0;
            }
        }
    }
    private void signUpFull(){
        if(mCheckUser && mCheckPass && mCheckEmail && mCheckButton){
            show();
        }
    }
    public void onRadioButtonClicked(View view) {
        mCheckButton = true;
        signUpFull();
    }
    private static boolean isValidEmail(final String mEmail) {
        Pattern patternEmail;
        Matcher matcherEmail;
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
        return matcherEmail.matches();
    }
}
