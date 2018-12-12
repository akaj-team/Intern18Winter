package asiantech.internship.summer.storage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;

public class FileStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_store);
        ViewPager mViewPagerFileStore = findViewById(R.id.viewpager);
        setupViewPager(mViewPagerFileStore);
        TabLayout mTlFileStore = findViewById(R.id.tabs);
        mTlFileStore.setupWithViewPager(mViewPagerFileStore);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SharePreferenceFragment(), "SHARE PREFERENCE");
        adapter.addFragment(new InternalExternalStoreFragment(), "INTERNAL AND EXTERNAL");
        adapter.addFragment(new SqliteFragment(), "SQLITE");
        viewPager.setAdapter(adapter);
    }


}
