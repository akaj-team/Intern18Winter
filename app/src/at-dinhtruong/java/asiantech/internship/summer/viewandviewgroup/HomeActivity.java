package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.activityandfragment.LoginActivity;
import asiantech.internship.summer.asynctaskthreadhandler.AsyncTaskThreadHandlerActivity;
import asiantech.internship.summer.eventandlistener.EventAndListenerActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;

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
        Button btnAsyncThreadHandler = findViewById(R.id.btnAsyncThreadHandler);
        btnViewGroup.setOnClickListener(this);
        btnEventAndListener.setOnClickListener(this);
        btnActivityAndFragment.setOnClickListener(this);
        btnRecyclerView.setOnClickListener(this);
        btnAsyncThreadHandler.setOnClickListener(this);
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
            case R.id.btnAsyncThreadHandler: {
                Intent intent = new Intent(HomeActivity.this, AsyncTaskThreadHandlerActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
