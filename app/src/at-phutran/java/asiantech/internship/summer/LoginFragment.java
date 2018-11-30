package asiantech.internship.summer;

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

public class LoginFragment extends Fragment {
    private Button mBtnLogin;
    private Button mBtnSignUp;
    private Button mButton;
    private EditText mEditTextEmail;
    private EditText mEditTextPass;
    private String mValueEmail;
    private String mValuePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        mBtnSignUp = fragmentView.findViewById(R.id.btn_signUp);
        mBtnSignUp.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left, R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
            fragmentTransaction.replace(R.id.fragment_content, signUpFragment);
            if(getActivity() instanceof  LoginActivity){
                ((LoginActivity)getActivity()).setTextForToolBar("SIGN UP");
                ((LoginActivity)getActivity()).onButton();
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        mEditTextEmail = fragmentView.findViewById(R.id.edt_inputEmail);
        mEditTextPass = fragmentView.findViewById(R.id.edt_inputPass);
        mBtnLogin = fragmentView.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(v -> {
            mValuePassword = mEditTextPass.getText().toString();
            mValueEmail = mEditTextEmail.getText().toString();
            if (isEmail(mValueEmail) && isPass(mValuePassword)) {
                gotoWelcomeActivity();
            } else {
                Toast.makeText(getActivity(),"Please check your email and password",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return fragmentView;
    }
    private static boolean isEmail(final String mEmail) {
        Pattern patternEmail;
        Matcher matcherEmail;
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
        return matcherEmail.matches();
    }
    private static boolean isPass(final String mPass) {
        Pattern patternPass;
        Matcher matcherPass;
        String passPattern = "^[a-zA-Z0-9]{7,}$";
        patternPass = Pattern.compile(passPattern);
        matcherPass = patternPass.matcher(mPass);
        return matcherPass.matches();
    }
    private void gotoWelcomeActivity(){
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        intent.putExtra("textEmail", mValueEmail);
        intent.putExtra("textPassword", mValuePassword);
        startActivity(intent);
    }
}
