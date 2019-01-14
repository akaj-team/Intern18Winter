package asiantech.internship.summer.unittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import asiantech.internship.summer.R;

public class UnitTestActivity extends AppCompatActivity {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        mEdtUsername = findViewById(R.id.edtUsername);
        mEdtPassword = findViewById(R.id.edtPassword);
        mTvResult = findViewById(R.id.tvResult);
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(v -> {
            String username = mEdtUsername.getText().toString().trim();
            String password = mEdtPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                mTvResult.setText(R.string.pleaseInput);
            } else {
                String checkResult = UtilValidate.resultLogin(username, password);
                mTvResult.setText(checkResult);
                if (checkResult.equals(UtilValidate.SUCCESSFUL)) {
                    mTvResult.setTextColor(getResources().getColor(R.color.colorGreen));
                }
            }
        });
    }
}
