package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.eventlistener.SignUpActivity;
import asiantech.internship.summer.fragment.LoginActivity;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnViewView;
    private Button mBtnListener;
    private Button mBtnFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mBtnViewView = findViewById(R.id.btnExercise1);
        mBtnListener = findViewById(R.id.btnExercise2);
        mBtnFragment = findViewById(R.id.btnExercise3);
        mBtnViewView.setOnClickListener(this);
        mBtnListener.setOnClickListener(this);
        mBtnFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExercise1: {
                Intent intent = new Intent(ListActivity.this, ViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise2: {
                Intent intent = new Intent(ListActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

            case R.id.btnExercise3: {
                Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
