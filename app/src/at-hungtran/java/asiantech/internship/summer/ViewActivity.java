package asiantech.internship.summer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        TextView mTvEmail = findViewById(R.id.tvEmail);
        TextView mTvPassword = findViewById(R.id.tvPassword);
        Intent intent = getIntent();
        String mValueEmail = intent.getStringExtra("email");
        String mValuePwd = intent.getStringExtra("password");
        mTvEmail.setText(mValueEmail);
        mTvPassword.setText(mValuePwd);
    }
}
