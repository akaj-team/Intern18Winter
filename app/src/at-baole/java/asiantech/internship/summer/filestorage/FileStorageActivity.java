package asiantech.internship.summer.filestorage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();

        TabLayout mTabLayoutHeader = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        PagerAdapter mPageAdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayoutHeader.getTabCount());
        mViewPager.setAdapter(mPageAdapter);

        mTabLayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    fragmentTransaction.replace(R.id.viewPager, new SharePreferencesFragment());
                }
                if (tab.getPosition() == 1) {
                    fragmentTransaction.replace(R.id.viewPager, new InternalExternalFragment());
                } else {
                    fragmentTransaction.replace(R.id.viewPager, new SaveDatabaseFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutHeader));
    }
}
