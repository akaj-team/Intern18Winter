package asiantech.internship.summer.asynctask;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();
        TabLayout tabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        mViewPagerContent = findViewById(R.id.viewPagerContent);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayoutHeader.getTabCount());
        mViewPagerContent.setAdapter(pagerAdapter);
        tabLayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerContent.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    fragmentTransaction.replace(R.id.viewPagerContent, new FragmentAsyncTask());
                }
                if(tab.getPosition() == 1){
                    fragmentTransaction.replace(R.id.viewPagerContent, new FragmentThread());
                }
                else{
                    fragmentTransaction.replace(R.id.viewPagerContent, new FragmentHandler());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPagerContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutHeader));
    }
}
