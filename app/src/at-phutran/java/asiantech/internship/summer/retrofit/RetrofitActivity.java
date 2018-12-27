package asiantech.internship.summer.retrofit;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import asiantech.internship.summer.R;
import asiantech.internship.summer.viewpager.FavouriteFragment;

public class RetrofitActivity extends AppCompatActivity {

    private ViewPager mViewPagerContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retofit_gson);
        TabLayout mTabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        mViewPagerContent = findViewById(R.id.viewPagerContent);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();
        PageAdapter mPageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPagerContent.setAdapter(mPageAdapter);
        mTabLayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerContent.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    fragmentTransaction.replace(R.id.viewPagerContent, new FragmentLoadImage());
                } else {
                    FavouriteFragment favouriteFragment = new FavouriteFragment();
                    fragmentTransaction.replace(R.id.viewPagerContent, favouriteFragment);
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
