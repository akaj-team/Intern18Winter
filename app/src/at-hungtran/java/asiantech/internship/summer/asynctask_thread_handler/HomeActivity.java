package asiantech.internship.summer.asynctask_thread_handler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.eventlistener.LoginActivity;
import asiantech.internship.summer.recyclerview.TimelineActivity;
import asiantech.internship.summer.restapi.RestAPIActivity;
import asiantech.internship.summer.services.ServicesActivity;
import asiantech.internship.summer.view.ViewActivity;
import asiantech.internship.summer.viewpager.PagerActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }
    private void initView() {
        Button btnViewGroup = findViewById(R.id.btnViewGroup);
        Button btnEventAndListener = findViewById(R.id.btnEventAndListener);
        Button btnActivityFragment = findViewById(R.id.btnActivityFragment);
        Button btnRecyclerView = findViewById(R.id.btnRecyclerView);
        Button btnViewPager = findViewById(R.id.btnViewPager);
        Button btnFileStorage = findViewById(R.id.btnAsyncTask);
        Button btnRestAPI = findViewById(R.id.btnRestAPI);
        Button btnService = findViewById(R.id.btnService);
        btnViewGroup.setOnClickListener(this);
        btnEventAndListener.setOnClickListener(this);
        btnActivityFragment.setOnClickListener(this);
        btnRecyclerView.setOnClickListener(this);
        btnViewPager.setOnClickListener(this);
        btnFileStorage.setOnClickListener(this);
        btnRestAPI.setOnClickListener(this);
        btnService.setOnClickListener(this);
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
            case R.id.btnViewPager: {
                Intent intent = new Intent(HomeActivity.this, PagerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnAsyncTask: {
                Intent intent = new Intent(HomeActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnRestAPI: {
                Intent intent = new Intent(HomeActivity.this, RestAPIActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnService: {
                Intent intent = new Intent(HomeActivity.this, ServicesActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
