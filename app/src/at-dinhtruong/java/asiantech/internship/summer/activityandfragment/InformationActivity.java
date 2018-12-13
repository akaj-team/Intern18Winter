package asiantech.internship.summer.activityandfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initInformation();
    }

    private void initInformation() {
        TextView mTvEmail = findViewById(R.id.tvEmail);
        TextView mTvPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        mTvEmail.setText(intent.getStringExtra(LoginFragment.EMAIL));
        mTvPassword.setText(intent.getStringExtra(LoginFragment.PASSWORD));
    }
}
