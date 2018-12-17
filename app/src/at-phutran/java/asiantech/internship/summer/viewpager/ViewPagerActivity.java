package asiantech.internship.summer.viewpager;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPagerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();
        TabLayout mTabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        mViewPagerContent = findViewById(R.id.viewPagerContent);
        PageAdapter mPageAdapter = new PageAdapter(getSupportFragmentManager(), mTabLayoutHeader.getTabCount());
        mViewPagerContent.setAdapter(mPageAdapter);
        mTabLayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerContent.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    fragmentTransaction.replace(R.id.viewPagerContent, new TimelineFragment());
                }
                else {
                    fragmentTransaction.replace(R.id.viewPagerContent, new FavouriteFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPagerContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutHeader));
    }
}
