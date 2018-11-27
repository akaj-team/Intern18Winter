package asiantech.internship.summer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        btnViewGroup.setOnClickListener(this);
    }

    private void initView() {
        btnViewGroup = findViewById(R.id.btnViewGroup);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnViewGroup: {
                Intent intent = new Intent(HomeActivity.this, ViewAndViewGroupActivity.class);
                startActivity(intent);
                break;
            }

        }
    }
}
