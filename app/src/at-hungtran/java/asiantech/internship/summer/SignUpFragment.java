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
    private EditText mEdtSUEmail;
    private EditText mEdtSUPwd;
    private EditText mEdtSURePwd;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(view);
        
        
        return view;
    }
    private void initView(View view){
        mEdtSUEmail = view.findViewById(R.id.edtSUEmail);
        mEdtSUPwd = view.findViewById(R.id.edtSUPwd);
        mEdtSURePwd = view.findViewById(R.id.edtSURePwd);
        Button mBtnSUSignUp = view.findViewById(R.id.btnSUSignUp);
        mBtnSUSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmailPassword()) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.anim_right_to_left, R.anim.anim_left_to_right);

                    fragmentTransaction.replace(R.id.fragment, new LoginFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }
    private boolean checkEmailPassword() {
        Button btnLogin =view.findViewById(R.id.btnLogin);
        String mPassword = mEdtSUPwd.getText().toString();
        String mRePassword = mEdtSURePwd.getText().toString();
        String mEmail = mEdtSUEmail.getText().toString();
        return isValidPassword(mPassword) && isValidEmail(mEmail) && mRePassword.equals(mPassword);
    }
    private boolean isValidEmail(String mEmail) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mEmail);
        return matcher.matches();
    }
    private boolean isValidPassword(String mPassword) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,18}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(mPassword);
        return matcher.matches();
    }

}
