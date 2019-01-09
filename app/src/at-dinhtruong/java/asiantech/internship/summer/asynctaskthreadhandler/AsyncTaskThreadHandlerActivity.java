package asiantech.internship.summer.asynctaskthreadhandler;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;

public class AsyncTaskThreadHandlerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_thread_handler);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AsyncTaskFragment(), getString(R.string.asyncTask));
        viewPagerAdapter.addFragment(new HandlerFragment(), getString(R.string.handler));
        viewPagerAdapter.addFragment(new ThreadFragment(), getString(R.string.thread));
        viewPager.setAdapter(viewPagerAdapter);
    }
}
