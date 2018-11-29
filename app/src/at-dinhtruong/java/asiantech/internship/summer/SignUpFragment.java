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

import asiantech.internship.summer.utils.Validate;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private Button mButtonSignUp;
    private EditText mEditTextEmail;
    private EditText mEditTextPassWord;
    private EditText mEditTextConFirmPassWord;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mButtonSignUp = view.findViewById(R.id.btnSignup);
        mEditTextEmail = view.findViewById(R.id.edtEmail);
        mEditTextPassWord = view.findViewById(R.id.edtPassword);
        mEditTextConFirmPassWord = view.findViewById(R.id.edtConfirmPassword);
        mButtonSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Validate validate = new Validate();
        String mEmail = mEditTextEmail.getText().toString();
        String mPassword = mEditTextPassWord.getText().toString();
        String mConfirmPassword = mEditTextConFirmPassWord.getText().toString();
        switch (v.getId()) {
            case R.id.btnSignup: {
                if (validate.isValidEmail(mEmail) && validate.isValidPassword(mPassword)) {
                    if (mPassword.equals(mConfirmPassword)) {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.fragment_container, new LoginFragment());
                        fragmentTransaction.addToBackStack(new Fragment().getClass().getSimpleName());
                        fragmentTransaction.commit();
                    } else {
                        mEditTextConFirmPassWord.setError("Confirm Password Wrong!");
                    }
                } else if (!validate.isValidEmail(mEmail)) {
                    mEditTextEmail.setError("Email Format Wrong!");
                } else if (!validate.isValidPassword(mPassword)) {
                    mEditTextPassWord.setError("Password format Wrong!");
                }
                break;
            }
            default:
                break;
        }

    }

}
