package asiantech.internship.summer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.eventlistener.LoginActivity;
import asiantech.internship.summer.recyclerview.TimelineActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        Button btnViewGroup = findViewById(R.id.btnViewGroup);
        Button btnEventAndListener = findViewById(R.id.btnEventAndListener);
        Button btnActivityFragment = findViewById(R.id.btnActivityFragment);
        Button btnRecyclerView = findViewById(R.id.btnRecyclerView);
        btnViewGroup.setOnClickListener(this);
        btnEventAndListener.setOnClickListener(this);
        btnActivityFragment.setOnClickListener(this);
        btnRecyclerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnViewGroup: {
                Intent intent = new Intent(HomeActivity.this, ViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnEventAndListener: {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnActivityFragment: {
                Intent intent = new Intent(HomeActivity.this, asiantech.internship.summer.activityfragment.LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnRecyclerView: {
                Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
