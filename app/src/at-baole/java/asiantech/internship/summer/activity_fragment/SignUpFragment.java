package asiantech.internship.summer.activity_fragment;

import android.os.Bundle;
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

public class SignUpFragment extends Fragment {

    private static final int MIN_LENGTH = 6;
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9]{6,}$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private Button mBtnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View signUpView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mEdtEmail = signUpView.findViewById(R.id.edtEmail);
        mEdtPassword = signUpView.findViewById(R.id.edtPassword);
        mEdtConfirmPassword = signUpView.findViewById(R.id.edtConfirmPassword);
        mBtnSignUp = signUpView.findViewById(R.id.btnSignUp);
        onClickSignUp();
        return signUpView;
    }

    public void onClickSignUp() {
        mBtnSignUp.setOnClickListener(view -> {
            signUpValidation();
        });
    }

    public void signUpValidation() {
        if (mEdtPassword.getText().length() > MIN_LENGTH
                && validatePassword(mEdtPassword.getText().toString())
                && validateEmail(mEdtEmail.getText().toString())
                && mEdtConfirmPassword.getText().toString().equals(mEdtPassword.getText().toString())) {
            Toast.makeText(getActivity(), R.string.signUpSuccessully, Toast.LENGTH_LONG).show();
        } else if (!validateEmail(mEdtEmail.getText().toString())) {
            Toast.makeText(getActivity(), R.string.invalidEmail, Toast.LENGTH_LONG).show();
        } else if (mEdtPassword.getText().length() <= MIN_LENGTH) {
            Toast.makeText(getActivity(), R.string.password7Letters, Toast.LENGTH_LONG).show();
        } else if (!validatePassword(mEdtPassword.getText().toString())) {
            Toast.makeText(getActivity(), R.string.passwordOnlyCharAndNum, Toast.LENGTH_LONG).show();
        } else if (!mEdtConfirmPassword.getText().toString().equals(mEdtPassword.getText().toString())) {
            Toast.makeText(getActivity(), R.string.confirmPasswordError, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
