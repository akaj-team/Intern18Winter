package asiantech.internship.summer.activityfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import asiantech.internship.summer.R;

public class LoginFragment extends Fragment {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
    Matcher mMatcher;
    private EditText mEdtEmail;
    private EditText mEdtPwd;
    private Pattern mPattern;
    private Button mBtnSignUp;
    private Button mBtnLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        onClickSignUp();
        onClickLogin();
        return view;
    }

    private void initView(View view) {
        mBtnSignUp = view.findViewById(R.id.btnSignUp);
        mBtnLogin = view.findViewById(R.id.btnLogin);
        mEdtPwd = view.findViewById(R.id.edtPwd);
        mEdtEmail = view.findViewById(R.id.edtEmail);
    }

    private void onClickSignUp() {
        mBtnSignUp.setOnClickListener(view -> ((LoginActivity) getActivity()).replaceFragment());
    }

    private void onClickLogin() {
        mBtnLogin.setOnClickListener(view -> {
            if (checkEmailPassword()) {
                Intent i = new Intent(getActivity(), InfomationActivity.class);
                i.putExtra(KEY_EMAIL, mEdtEmail.getText().toString());
                i.putExtra(KEY_PASS, mEdtPwd.getText().toString());
                startActivity(i);
            } else {
                Toast.makeText(getActivity(), getString(R.string.pleasecheckemailpassword),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkEmailPassword() {
        String mPassword = mEdtPwd.getText().toString();
        String mEmail = mEdtEmail.getText().toString();
        return isValidPassword(mPassword) && isValidEmail(mEmail);
    }

    private boolean isValidEmail(String email) {
        mPattern = Pattern.compile(EMAIL_PATTERN);
        mMatcher = mPattern.matcher(email);
        return mMatcher.matches();
    }

    private boolean isValidPassword(String password) {
        mPattern = Pattern.compile(PASSWORD_PATTERN);
        mMatcher = mPattern.matcher(password);
        return mMatcher.matches();
    }
}
