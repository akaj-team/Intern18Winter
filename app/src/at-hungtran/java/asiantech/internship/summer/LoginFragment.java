package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private EditText mEdtPwd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("xx", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        Button mBtnLogin = view.findViewById(R.id.btnLogin);
        mEdtPwd = view.findViewById(R.id.edtPwd);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LogActivity) getActivity()).replaceFragmemnt();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmailPassword()) {
                    Intent i = new Intent(getActivity(), ViewActivity.class);
                    i.putExtra("email", mEdtEmail.getText().toString());
                    i.putExtra("password", mEdtPwd.getText().toString());
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Please check your email and password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkEmailPassword() {
        Button btnLogin = getView().findViewById(R.id.btnLogin);
        String mPassword = mEdtPwd.getText().toString();
        String mEmail = mEdtEmail.getText().toString();
        return isValidPassword(mPassword) && isValidEmail(mEmail);
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
