package asiantech.internship.summer.unittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;

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
        onLickLogin();
    }

    private void initView() {
        mTvNotification = findViewById(R.id.tvNotification);
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
    }

    public void onLickLogin() {
        mBtnLogin.setOnClickListener(view -> {
            String username = mEdtUsername.getText().toString().trim();
            String password = mEdtPassword.getText().toString().trim();
            mTvNotification.setTextColor(getResources().getColor(R.color.colorRed));
        });
    }
}
