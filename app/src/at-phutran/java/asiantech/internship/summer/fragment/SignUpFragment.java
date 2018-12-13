package asiantech.internship.summer.fragment;

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

public class SignUpFragment extends Fragment {
    private static String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
    private static String PASS_PATTERN = "^[a-zA-Z0-9]{7,}$";
    private Button mBtnSignUp;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private String mValueEmail;
    private String mValuePassword;
    private String mValueConfirmPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mEdtEmail = view.findViewById(R.id.edtInputEmail);
        mEdtPassword = view.findViewById(R.id.edtInputPass);
        mEdtConfirmPassword = view.findViewById(R.id.edtConfirmPass);
        mBtnSignUp = view.findViewById(R.id.btnSignUp);
        handleEvent();
        return view;
    }
    private static boolean isMail(final String email){
        Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }
    private static boolean isPassword(final String password){
        Pattern patternPassword = Pattern.compile(PASS_PATTERN);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherPassword.matches();
    }
    private void handleEvent(){
        mBtnSignUp.setOnClickListener(v -> {
            mValueEmail = mEdtEmail.getText().toString();
            mValuePassword = mEdtPassword.getText().toString();
            mValueConfirmPassword = mEdtConfirmPassword.getText().toString();
            checkInputSignUp();
        });
    }
    private void checkInputSignUp(){
        LoginFragment loginFragment = new LoginFragment();
        if(isMail(mValueEmail) && isPassword(mValuePassword) && mValuePassword.equals(mValueConfirmPassword)){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frContent, loginFragment);
            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(getActivity(), R.string.checkInputSignUp, Toast.LENGTH_SHORT).show();
        }
    }
}
