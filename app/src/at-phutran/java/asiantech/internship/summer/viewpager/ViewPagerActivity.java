package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import asiantech.internship.summer.R;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPagerContent;
    private OnChangingFavoritesListener mOnChangingFavoritesListener;

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

    public OnChangingFavoritesListener getOnChangingFavoritesListener() {
        return mOnChangingFavoritesListener;
    }

    public void setOnChangingFavoritesListener(OnChangingFavoritesListener onChangingFavoritesListener) {
        this.mOnChangingFavoritesListener = onChangingFavoritesListener;
    }
}
