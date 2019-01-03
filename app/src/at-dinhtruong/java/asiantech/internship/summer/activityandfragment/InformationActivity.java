package asiantech.internship.summer.activityandfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        tvEmail.setText(intent.getStringExtra(LoginFragment.EMAIL));
        tvPassword.setText(intent.getStringExtra(LoginFragment.PASSWORD));
    }
}
