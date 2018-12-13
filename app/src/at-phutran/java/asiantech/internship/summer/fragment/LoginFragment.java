package asiantech.internship.summer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import asiantech.internship.summer.R;

public class LoginFragment extends Fragment {
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
    private static final String PASS_PATTERN = "^[a-zA-Z0-9]{7,}$";
    private static final String EMAIL_KEY = "textEmail";
    private static final String PASSWORD_KEY = "textPassword";
    private Button mBtnSignUp;
    private EditText mEdtEmail;
    private EditText mEdtPass;
    private String mValueEmail;
    private String mValuePassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        mBtnSignUp = fragmentView.findViewById(R.id.btnSignUp);
        handleEvent();
        mEdtEmail = fragmentView.findViewById(R.id.edtInputEmail);
        mEdtPass = fragmentView.findViewById(R.id.edtInputPass);
        Button mBtnLogin = fragmentView.findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(v -> {
            mValuePassword = mEdtPass.getText().toString();
            mValueEmail = mEdtEmail.getText().toString();
            if (isEmail(mValueEmail) && isPass(mValuePassword)) {
                gotoWelcomeActivity();
            } else {
                Toast.makeText(getActivity(), R.string.checkInput,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return fragmentView;
    }
    private void gotoWelcomeActivity() {
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        intent.putExtra(EMAIL_KEY, mValueEmail);
        intent.putExtra(PASSWORD_KEY, mValuePassword);
        startActivity(intent);
    }
    private void handleEvent(){
        mBtnSignUp.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left, R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
            fragmentTransaction.replace(R.id.frContent, signUpFragment);
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).setTextForToolBar(getString(R.string.signUp));
                ((LoginActivity) getActivity()).onButton();
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
    private static boolean isEmail(final String email) {
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }
    private static boolean isPass(final String password) {
        Pattern patternPassword = Pattern.compile(PASS_PATTERN);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherPassword.matches();
    }
}
