package asiantech.internship.summer.activityandfragment;

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

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtEmail;
    private EditText mEdtPassWord;
    private EditText mEdtConfirmPassWord;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        Button mBtnSignUp = view.findViewById(R.id.btnSignup);
        mEdtEmail = view.findViewById(R.id.edtEmail);
        mEdtPassWord = view.findViewById(R.id.edtPassword);
        mEdtConfirmPassWord = view.findViewById(R.id.edtConfirmPassword);
        mBtnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Validate validate = new Validate();
        String email = mEdtEmail.getText().toString();
        String passWord = mEdtPassWord.getText().toString();
        String confirmPassword = mEdtConfirmPassWord.getText().toString();
        switch (v.getId()) {
            case R.id.btnSignup: {
                if (validate.isValidEmail(email) && validate.isValidPassword(passWord)) {
                    if (passWord.equals(confirmPassword)) {
                        if (getActivity() instanceof LoginActivity) {
                            ((LoginActivity) getActivity()).setTitleToolBar(getString(R.string.login));
                            ((LoginActivity) getActivity()).setButtonBack(0);
                        }
                        assert getFragmentManager() != null;
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.right_to_left1, R.anim.right_to_left2);
                        fragmentTransaction.add(R.id.fragment_container, new LoginFragment());
                        fragmentTransaction.addToBackStack(SignUpFragment.class.getSimpleName());
                        fragmentTransaction.commit();
                    } else {
                        mEdtConfirmPassWord.setError(getString(R.string.confirmPasswordWrong));
                    }
                } else if (!validate.isValidEmail(email)) {
                    mEdtEmail.setError(getString(R.string.emailFormatWrong));
                } else if (!validate.isValidPassword(passWord)) {
                    mEdtPassWord.setError(getString(R.string.passwordFormatWrong));
                }
                break;
            }
            default:
                break;
        }

    }

}
