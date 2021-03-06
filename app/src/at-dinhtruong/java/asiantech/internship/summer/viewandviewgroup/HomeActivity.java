package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.activityandfragment.LoginActivity;
import asiantech.internship.summer.eventandlistener.EventAndListenerActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;
import asiantech.internship.summer.storage.FileStoreActivity;
import asiantech.internship.summer.asynctaskthreadhandler.AsyncTaskThreadHandlerActivity;
import asiantech.internship.summer.canvas.CanvasActivity;
import asiantech.internship.summer.restapi.RestApiActivity;
import asiantech.internship.summer.unittest.UnitTestActivity;
import asiantech.internship.summer.viewpager.ViewPagerActivity;
import asiantech.internship.summer.drawerlayout.DrawerLayoutActivity;

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
        Button btnActivityAndFragment = findViewById(R.id.btnActivityAndFragment);
        Button btnRecyclerView = findViewById(R.id.btnRecyclerView);
        Button btnViewPager = findViewById(R.id.btnViewPager);
        Button btnFileStorage = findViewById(R.id.btnFileStorage);
        Button btnRestApi = findViewById(R.id.btnRestApi);
        Button btnAsyncThreadHandler = findViewById(R.id.btnAsyncThreadHandler);
        Button btnCanvas = findViewById(R.id.btnCanvas);
        Button btnUnitTest = findViewById(R.id.btnUnitTest);
        Button btnDrawerLayout = findViewById(R.id.btnDrawerLayout);
        btnViewGroup.setOnClickListener(this);
        btnEventAndListener.setOnClickListener(this);
        btnActivityAndFragment.setOnClickListener(this);
        btnRecyclerView.setOnClickListener(this);
        btnViewPager.setOnClickListener(this);
        btnFileStorage.setOnClickListener(this);
        btnRestApi.setOnClickListener(this);
        btnAsyncThreadHandler.setOnClickListener(this);
        btnCanvas.setOnClickListener(this);
        btnUnitTest.setOnClickListener(this);
        btnDrawerLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnViewGroup: {
                Intent intent = new Intent(HomeActivity.this, ViewAndViewGroupActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnEventAndListener: {
                Intent intent = new Intent(HomeActivity.this, EventAndListenerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnActivityAndFragment: {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnRecyclerView: {
                Intent intent = new Intent(HomeActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnViewPager: {
                Intent intent = new Intent(HomeActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnFileStorage: {
                Intent intent = new Intent(HomeActivity.this, FileStoreActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnRestApi: {
                Intent intent = new Intent(HomeActivity.this, RestApiActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnAsyncThreadHandler: {
                Intent intent = new Intent(HomeActivity.this, AsyncTaskThreadHandlerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnCanvas: {
                Intent intent = new Intent(HomeActivity.this, CanvasActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnUnitTest: {
                Intent intent = new Intent(HomeActivity.this, UnitTestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnDrawerLayout: {
                Intent intent = new Intent(HomeActivity.this, DrawerLayoutActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
