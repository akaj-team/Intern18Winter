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
        setContentView(R.layout.activity_file_storage);
        ViewPager viewPagerFileStore = findViewById(R.id.viewpager);
        setupViewPager(viewPagerFileStore);
        TabLayout tlFileStore = findViewById(R.id.tabs);
        tlFileStore.setupWithViewPager(viewPagerFileStore);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new SharePreferenceFragment(), getString(R.string.sharePreference));
        viewPagerAdapter.addFragment(new InternalExternalStoreFragment(), getString(R.string.internalAndExternal));
        viewPagerAdapter.addFragment(new SqliteFragment(), getString(R.string.sQLite));
        viewPager.setAdapter(viewPagerAdapter);
    }
}
