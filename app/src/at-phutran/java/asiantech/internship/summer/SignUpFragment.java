package asiantech.internship.summer;

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

public class SignUpFragment extends Fragment {
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
        LoginFragment loginFragment = new LoginFragment();
        mEdtEmail = view.findViewById(R.id.edt_inputEmail);
        mEdtPassword = view.findViewById(R.id.edt_inputPass);
        mEdtConfirmPassword = view.findViewById(R.id.edt_confirmPass);
        mBtnSignUp = view.findViewById(R.id.btn_signUp);
        mBtnSignUp.setOnClickListener(v -> {
            mValueEmail = mEdtEmail.getText().toString();
            mValuePassword = mEdtPassword.getText().toString();
            mValueConfirmPassword = mEdtConfirmPassword.getText().toString();
            if(isMail(mValueEmail) && isPassword(mValuePassword) && mValuePassword.equals(mValueConfirmPassword)){
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_content, loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }else{
                Toast.makeText(getActivity(), "Please check again", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private static boolean isMail(final String mEmail){
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
        Pattern patternEmail;
        Matcher matcherEmail;
        patternEmail = Pattern.compile(emailPattern);
        matcherEmail = patternEmail.matcher(mEmail);
        return matcherEmail.matches();
    }
    private static boolean isPassword(final String mPassword){
        String passwordPattern = "^[a-zA-Z0-9]{7,}$";
        Pattern patternPassword;
        Matcher matcherPassword;
        patternPassword = Pattern.compile(passwordPattern);
        matcherPassword = patternPassword.matcher(mPassword);
        return matcherPassword.matches();
    }
}
