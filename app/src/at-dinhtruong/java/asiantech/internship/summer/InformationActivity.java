package asiantech.internship.summer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {
    private TextView mTvEmail;
    private TextView mTvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initInformation();
    }

    private void initInformation() {
        mTvEmail = findViewById(R.id.tvEmail);
        mTvPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        mTvEmail.setText(intent.getStringExtra(LoginFragment.EMAIL));
        mTvPassword.setText(intent.getStringExtra(LoginFragment.PASSWORD));
    }
}
