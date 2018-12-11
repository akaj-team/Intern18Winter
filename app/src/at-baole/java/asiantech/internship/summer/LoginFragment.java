package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {

    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnSignUp;
    private Button mBtnLogIn;
    private static final int MIN_LENGTH = 6;
    private static final String EMAIL_KEY = "Email";
    private static final String PASSWORD_KEY = "Password";
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9]{6,}$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        mEdtEmail = loginView.findViewById(R.id.edtEmail);
        mEdtPassword = loginView.findViewById(R.id.edtPassword);
        mBtnLogIn = loginView.findViewById(R.id.btnLogIn);
        mBtnSignUp = loginView.findViewById(R.id.btnSignUp);
        onClickLogin();
        onClickSignUp();
        return loginView;
    }

    public void onClickLogin() {
        mBtnLogIn.setOnClickListener(view1 -> {
            loginValidation();
        });
    }

    public void onClickSignUp() {
        mBtnSignUp.setOnClickListener(view2 -> {
            initSignUpFragment();
        });
    }

    public void initSignUpFragment() {
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.llLogin, signUpFragment);
        isInstanceOf();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void isInstanceOf() {
        if (getActivity() instanceof LoginActivity) {
            ((LoginActivity) getActivity()).setTextToolBar(R.string.capitalSignUp);
            ((LoginActivity) getActivity()).onButton();
        }
    }

    public void loginValidation() {
        if (mEdtPassword.getText().length() > MIN_LENGTH
                && validatePassword(mEdtPassword.getText().toString())
                && validateEmail(mEdtEmail.getText().toString())) {

            String email = mEdtEmail.getText().toString();
            String password = mEdtPassword.getText().toString();
            Intent intent = new Intent(getActivity(), InformationActivity.class);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            startActivity(intent);
            Toast.makeText(getActivity(), R.string.logInSuccessfully, Toast.LENGTH_LONG).show();
        } else if (!validateEmail(mEdtEmail.getText().toString())) {
            Toast.makeText(getActivity(), R.string.incorrectEmail, Toast.LENGTH_LONG).show();
        } else if (mEdtPassword.getText().length() <= MIN_LENGTH) {
            Toast.makeText(getActivity(), R.string.passwordError1, Toast.LENGTH_LONG).show();
        } else if (!validatePassword(mEdtPassword.getText().toString())) {
            Toast.makeText(getActivity(), R.string.passwordError2, Toast.LENGTH_LONG).show();
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
