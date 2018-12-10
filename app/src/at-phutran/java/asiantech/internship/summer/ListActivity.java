package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ListActivity extends AppCompatActivity {

    Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);
        btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this, ViewActivity.class);
            startActivity(intent);
        });
    }
}
