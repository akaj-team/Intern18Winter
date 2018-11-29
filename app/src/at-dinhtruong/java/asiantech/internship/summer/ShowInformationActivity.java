package asiantech.internship.summer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowInformationActivity extends AppCompatActivity {
    private TextView mTextViewEmail;
    private TextView mTextViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        initInfor();
    }

    private void initInfor() {
        mTextViewEmail = findViewById(R.id.tvEmail);
        mTextViewPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        mTextViewEmail.setText(intent.getStringExtra("Email"));
        mTextViewPassword.setText(intent.getStringExtra("Password"));
    }
}
