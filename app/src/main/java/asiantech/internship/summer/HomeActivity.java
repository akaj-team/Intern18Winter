package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnD1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initListener();
    }

    private void initView() {
        btnD1 = findViewById(R.id.btnD1);
    }

    private void initListener() {
        btnD1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
