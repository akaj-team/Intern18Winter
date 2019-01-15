package asiantech.internship.summer.unittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

public class UnitTestActivity extends AppCompatActivity {
    private TextView mTvNotification;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        initView();
        onClickLogin();
    }

    private void initView() {
        mTvNotification = findViewById(R.id.tvNotification);
        mTvNotification.setTextColor(getResources().getColor(R.color.colorDarkRed));
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
    }

    private void onClickLogin() {
        mBtnLogin.setOnClickListener(view -> {
            String username = mEdtUsername.getText().toString().trim();
            String password = mEdtPassword.getText().toString().trim();
            User user = new User(username, password);
            String checkResult;
            mTvNotification.setBackground(getResources().getDrawable(R.drawable.bg_shape_stroke_red));

            if (TextUtils.isEmpty(user.getUsername())) {
                mTvNotification.setText(R.string.usernameNotEmpty);
            } else if (TextUtils.isEmpty(user.getPassword())) {
                mTvNotification.setText(R.string.passwordNotEmpty);
            } else {
                checkResult = getResources().getString(ValidateUtils.validateUserName(username))
                        + "\n" + getResources().getString(ValidateUtils.validatePassword(password))
                        + "\n" + getResources().getString(ValidateUtils.checkPasswordMatchesUsername(user));
                mTvNotification.setText(checkResult);
            }
        });
    }
}
