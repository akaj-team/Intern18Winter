package asiantech.internship.summer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
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
        mValueEmail = intent.getStringExtra("textEmail");
        mValuePassword = intent.getStringExtra("textPassword");
        mTvEmail.setText(mValueEmail);
        mTvPassword.setText(mValuePassword);
    }
}
