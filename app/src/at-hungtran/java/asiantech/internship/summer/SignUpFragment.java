package asiantech.internship.summer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
    private EditText mEdtEmail;
    private EditText mEdtPwd;
    private EditText mEdtRePwd;
    private Button mBtnSignUp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(view);
        onClickSignUp();
        return view;
    }

    private void initView(View view) {
        mEdtEmail = view.findViewById(R.id.edtEmail);
        mEdtPwd = view.findViewById(R.id.edtPwd);
        mEdtRePwd = view.findViewById(R.id.edtRePwd);
        mBtnSignUp = view.findViewById(R.id.btnSignUp);
    }

    private void onClickSignUp() {
        mBtnSignUp.setOnClickListener(view -> {
            if (checkEmailPassword()) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.anim_right_to_left, R.anim.anim_left_to_right);
                fragmentTransaction.replace(R.id.frContainer, new LoginFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private boolean checkEmailPassword() {
        String mPassword = mEdtPwd.getText().toString();
        String mRePassword = mEdtRePwd.getText().toString();
        String mEmail = mEdtEmail.getText().toString();
        return isValidPassword(mPassword) && isValidEmail(mEmail) && mRePassword.equals(mPassword);
    }

    private boolean isValidEmail(String mEmail) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mEmail);
        return matcher.matches();
    }

    private boolean isValidPassword(String mPassword) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(mPassword);
        return matcher.matches();
    }
}
