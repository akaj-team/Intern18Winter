package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import asiantech.internship.summer.eventlistener.SignUpActivity;
import asiantech.internship.summer.R;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnViewExercise1;
    Button btnViewExercise2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);
        btnViewExercise1 = findViewById(R.id.btnExercise1);
        btnViewExercise2 = findViewById(R.id.btnExercise2);
        btnViewExercise1.setOnClickListener(this);
        btnViewExercise2.setOnClickListener(this);
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
