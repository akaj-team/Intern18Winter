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

import asiantech.internship.summer.utils.Validate;

public class LoginFragment extends Fragment implements View.OnClickListener {
    final public static String EMAIL ="email";
    final public static String PASSWORD ="password";
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private EditText mEditTextEmail;
    private EditText mEditTextPassWord;

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
        mButtonSignUp = view.findViewById(R.id.btnSignup);
        mButtonLogin = view.findViewById(R.id.btnLogin);
        mEditTextEmail = view.findViewById(R.id.edtEmail);
        mEditTextPassWord = view.findViewById(R.id.edtPassword);
        mButtonLogin.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
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
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.right_to_left1, R.anim.right_to_left2, R.anim.left_to_right1, R.anim.left_to_right2);
                fragmentTransaction.add(R.id.fragment_container, new SignUpFragment());
                fragmentTransaction.addToBackStack(new Fragment().getClass().getSimpleName());
                fragmentTransaction.commit();
                break;
            }
            case R.id.btnLogin: {
                String mEmail = mEditTextEmail.getText().toString();
                String mPassWord = mEditTextPassWord.getText().toString();
                if (validate.isValidEmail(mEmail) && validate.isValidPassword(mPassWord)) {
                    Intent intent = new Intent(getActivity(), ShowInformationActivity.class);
                    intent.putExtra(EMAIL, mEmail);
                    intent.putExtra(PASSWORD, mPassWord);
                    startActivity(intent);
                }
                break;
            }
        }

    }
}
