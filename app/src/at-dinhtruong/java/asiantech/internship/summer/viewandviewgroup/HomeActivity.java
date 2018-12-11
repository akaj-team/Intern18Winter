package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        Button btnViewGroup = findViewById(R.id.btnViewGroup);
        btnViewGroup.setOnClickListener(this);
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
