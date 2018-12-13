package asiantech.internship.summer.activityandfragment;

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

import asiantech.internship.summer.R;
import asiantech.internship.summer.utils.Validate;

public class LoginFragment extends Fragment implements View.OnClickListener {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private EditText mEdtEmail;
    private EditText mEdtPassWord;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        Button btnSignUp = view.findViewById(R.id.btnSignup);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        mEdtPassWord = view.findViewById(R.id.edtPassword);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Validate validate = new Validate();
        switch (v.getId()) {
            case R.id.btnSignup: {
                if (getActivity() instanceof LoginActivity) {
                    ((LoginActivity) getActivity()).setTitleToolBar(getString(R.string.signup));
                    ((LoginActivity) getActivity()).setButtonBack(R.drawable.ic_arrow_back_black_36dp);
                }
                assert getFragmentManager() != null;
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.right_to_left1, R.anim.right_to_left2, R.anim.left_to_right1, R.anim.left_to_right2);
                fragmentTransaction.add(R.id.fragment_container, new SignUpFragment());
                fragmentTransaction.addToBackStack(SignUpFragment.class.getSimpleName());
                fragmentTransaction.commit();
                break;
            }
            case R.id.btnLogin: {
                String email = mEdtEmail.getText().toString();
                String passWord = mEdtPassWord.getText().toString();
                if (validate.isValidEmail(email) && validate.isValidPassword(passWord)) {
                    Intent intent = new Intent(getActivity(), InformationActivity.class);
                    intent.putExtra(EMAIL, email);
                    intent.putExtra(PASSWORD, passWord);
                    startActivity(intent);
                }
                break;
            }
        }

    }
}
