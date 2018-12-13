package asiantech.internship.summer.activityfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class InfomationActivity extends AppCompatActivity {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        TextView mTvEmail = findViewById(R.id.tvEmail);
        TextView mTvPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        String mValueEmail = intent.getStringExtra(KEY_EMAIL);
        String mValuePwd = intent.getStringExtra(KEY_PASS);
        mTvEmail.setText(mValueEmail);
        mTvPassword.setText(mValuePwd);
    }
}
