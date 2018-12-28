package asiantech.internship.summer.asynctask;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import asiantech.internship.summer.R;

public class AsyncTaskThreadHandlerActivity extends AppCompatActivity {
    private ViewPager mViewPagerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask_thread_handler);
        TabLayout tabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        mViewPagerContent = findViewById(R.id.viewPagerContent);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPagerContent.setAdapter(pagerAdapter);
        tabLayoutHeader.setupWithViewPager(mViewPagerContent);
    }
}
