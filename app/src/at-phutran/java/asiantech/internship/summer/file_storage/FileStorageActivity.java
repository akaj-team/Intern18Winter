package asiantech.internship.summer.file_storage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity {
    private ViewPager mViewPagerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TabLayout mTabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        mViewPagerContent = findViewById(R.id.viewPagerContent);
        PageAdapter mPageAdapter = new PageAdapter(getSupportFragmentManager(), mTabLayoutHeader.getTabCount());
        mViewPagerContent.setAdapter(mPageAdapter);
        mTabLayoutHeader.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerContent.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    fragmentTransaction.replace(R.id.viewPagerContent, new SharePreferenceFragment());
                }
                if (tab.getPosition() == 1) {
                    fragmentTransaction.replace(R.id.viewPagerContent, new StorageFragment());
                } else {
                    fragmentTransaction.replace(R.id.viewPagerContent, new DatabaseFragment());
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
