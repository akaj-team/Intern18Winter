package asiantech.internship.summer.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class WelcomeActivity extends AppCompatActivity {
    private static final String EMAIL_VALUE = "textEmail";
    private static final String PASSWORD_VALUE = "textPassword";
    private TextView mTvEmail;
    private TextView mTvPassword;
    private String mValueEmail;
    private String mValuePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mTvEmail = findViewById(R.id.tvEmail);
        mTvPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        mValueEmail = intent.getStringExtra(EMAIL_VALUE);
        mValuePassword = intent.getStringExtra(PASSWORD_VALUE);
        mTvEmail.setText(mValueEmail);
        mTvPassword.setText(mValuePassword);
    }
}
