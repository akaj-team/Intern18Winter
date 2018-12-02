package asiantech.internship.summer;

import android.os.Bundle;
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

public class LoginFragment extends Fragment {
    private Button btnSignUp;
    private EditText mEdtEmail;
    private EditText mEdtPwd;
    private Button mBtnLogin;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        btnSignUp = view.findViewById(R.id.btnSignUp);
        mBtnLogin = view.findViewById(R.id.btnLogin);
        mEdtPwd = view.findViewById(R.id.edtPwd);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new SignUpFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmailPassword() == true) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, new SignUpFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private boolean checkEmailPassword() {
        Button btnLogin = getView().findViewById(R.id.btnLogin);
        String mPassword = mEdtPwd.getText().toString();
        String mEmail = mEdtEmail.getText().toString();
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
