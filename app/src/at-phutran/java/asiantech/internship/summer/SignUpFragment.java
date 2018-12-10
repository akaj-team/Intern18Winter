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
    private static String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4})(\\]?)$";
    private static String passPattern = "^[a-zA-Z0-9]{7,}$";
    private static Pattern patternInput;
    private static Matcher matcherInput;
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
        patternInput = Pattern.compile(emailPattern);
        matcherInput = patternInput.matcher(email);
        return matcherInput.matches();
    }
    private static boolean isPassword(final String password){
        patternInput = Pattern.compile(passPattern);
        matcherInput = patternInput.matcher(password);
        return matcherInput.matches();
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
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(getActivity(), R.string.checkInputSignUp, Toast.LENGTH_SHORT).show();
        }
    }
}
