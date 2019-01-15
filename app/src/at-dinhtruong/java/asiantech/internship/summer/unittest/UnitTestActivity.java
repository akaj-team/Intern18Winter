package asiantech.internship.summer.unittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.User;

public class UnitTestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private TextView mTvNotificationError;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        initViews();
        mBtnLogin.setOnClickListener(this);
    }

    private void initViews() {
        mEdtUsername = findViewById(R.id.editUserName);
        mEdtPassword = findViewById(R.id.edtPassword);
        mTvNotificationError = findViewById(R.id.tvNotificationError);
        mBtnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View view) {
        mTvNotificationError.setVisibility(view.getVisibility());
        String username = mEdtUsername.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        User user = new User(username, password);
        if (view.getId() == R.id.btnLogin) {
            mTvNotificationError.setText(Validate.checkLogin(user));
        }
    }
}
