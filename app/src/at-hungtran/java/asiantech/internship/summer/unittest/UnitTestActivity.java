package asiantech.internship.summer.unittest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;
import asiantech.internship.summer.utils.Validate;

public class UnitTestActivity extends AppCompatActivity {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private TextView mTvNotifycation;
    private Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test_login);
        initView();
        onClickLogin();
    }

    private void initView() {
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mTvNotifycation = findViewById(R.id.tvNotifycation);
        mTvNotifycation.setTextColor(getResources().getColor(R.color.colorRed));
        mBtnLogin = findViewById(R.id.btnLogin);
    }

    private void onClickLogin() {
        mBtnLogin.setOnClickListener(v -> {
            String username = mEdtUsername.getText().toString();
            String password = mEdtPassword.getText().toString();
            User user = new User(username, password);
            mTvNotifycation.setBackground(getResources().getDrawable(R.drawable.border_error_rect));
            String checkResult = getResources().getString(Validate.ValidateLogin(user));
            mTvNotifycation.setText(checkResult);
        });
    }

}
