package asiantech.internship.summer.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import asiantech.internship.summer.R;
import asiantech.internship.summer.activity_fragment.LoginActivity;
import asiantech.internship.summer.event_listener.SignUpActivity;
import asiantech.internship.summer.filestorage.FileStorageActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;
import asiantech.internship.summer.view_and_groupview.ViewActivity;
import asiantech.internship.summer.viewpager.PagerActivity;

public class ListIssuesActivity extends AppCompatActivity {
    private Button mBtnToGroupView;
    private Button mBtnToEventListener;
    private Button mBtnToActivityFragment;
    private Button mBtnToRecyclerView;
    private Button mBtnToViewPager;
    private Button mBtnToFileStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_issues);
        mappingButton();
        onClick();
    }

    private void mappingButton() {
        mBtnToGroupView = findViewById(R.id.btnToGroupView);
        mBtnToEventListener = findViewById(R.id.btnToEventListener);
        mBtnToActivityFragment = findViewById(R.id.btnToActivityFragment);
        mBtnToRecyclerView = findViewById(R.id.btnToRecyclerView);
        mBtnToViewPager = findViewById(R.id.btnToViewPager);
        mBtnToFileStorage = findViewById(R.id.btnToFileStorage);
    }

    private void onClick() {
        mBtnToGroupView.setOnClickListener(toGroupView -> {
            Intent intentGroupView = new Intent(getApplication(), ViewActivity.class);
            startActivity(intentGroupView);
        });

        mBtnToEventListener.setOnClickListener(toEventListenerView -> {
            Intent intentEventListener = new Intent(getApplication(), SignUpActivity.class);
            startActivity(intentEventListener);
        });

        mBtnToActivityFragment.setOnClickListener(toActivityFragmentView -> {
            Intent intentActivityFragment = new Intent(getApplication(), LoginActivity.class);
            startActivity(intentActivityFragment);
        });

        mBtnToRecyclerView.setOnClickListener(toRecyclerView -> {
            Intent intentRecyclerView = new Intent(getApplication(), RecyclerViewActivity.class);
            startActivity(intentRecyclerView);
        });

        mBtnToViewPager.setOnClickListener(toViewPager -> {
            Intent intentViewPager = new Intent(getApplication(), PagerActivity.class);
            startActivity(intentViewPager);
        });

        mBtnToFileStorage.setOnClickListener(toFileStorage -> {
            Intent intentFileStorage = new Intent(getApplication(), FileStorageActivity.class);
            startActivity(intentFileStorage);
        });
    }
}