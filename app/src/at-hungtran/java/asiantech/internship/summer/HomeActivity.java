package asiantech.internship.summer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button btnViewandViewGroup;
    private Button btnEventandListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initView1();
        initListener();
        innitListener1();
    }

    private void initView() {
        btnViewandViewGroup = findViewById(R.id.idBtn);
    }

    private void initView1() {
        btnEventandListener = findViewById(R.id.idBtn1);
    }

    private void initListener() {
        btnViewandViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LayoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void innitListener1() {
        btnEventandListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });
    }
}
