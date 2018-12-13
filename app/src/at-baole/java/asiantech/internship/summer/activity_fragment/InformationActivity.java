package asiantech.internship.summer.activity_fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class InformationActivity extends AppCompatActivity {
    private TextView mTvEmail;
    private TextView mTvPassword;
    private String mEmail;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_information);

        mTvEmail = findViewById(R.id.tvEmail);
        mTvPassword = findViewById(R.id.tvPassword);
        mEmail = getIntent().getExtras().getString(String.valueOf(R.string.email));
        mTvEmail.setText(mEmail);
        mPassword = getIntent().getExtras().getString(String.valueOf(R.string.password));
        mTvPassword.setText(mPassword);
    }
}
