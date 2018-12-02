package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {
    private Button mBtnSUSignUp;
    private EditText mEdtSUEmail;
    private EditText mEdtSUPwd;
    private EditText mEdtSURePwd;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(view);
        
        
        return view;
    }
    private void initView(View view){
        mEdtSUEmail = view.findViewById(R.id.edtSUEmail);
        mEdtSUPwd = view.findViewById(R.id.edtSUPwd);
        mEdtSURePwd = view.findViewById(R.id.edtSURePwd);
        mBtnSUSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private boolean checkEmailPassword() {
        Button btnLogin = getView().findViewById(R.id.btnLogin);
        String mPassword = mEdtSUPwd.getText().toString();
        String mRePassword = mEdtSURePwd.getText().toString();
        String mEmail = mEdtSUEmail.getText().toString();
        if (!isValidPassword(mPassword) || !isValidEmail(mEmail)) {
            return false;
        } else {
            return true;
        }
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
