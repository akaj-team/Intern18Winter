package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.eventlistener.SignUpActivity;
import asiantech.internship.summer.R;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnViewView;
    Button btnListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        btnViewView = findViewById(R.id.btnExercise1);
        btnListener = findViewById(R.id.btnExercise2);
        btnViewView.setOnClickListener(this);
        btnListener.setOnClickListener(this);
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
        }
    }
}
