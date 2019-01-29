package asiantech.internship.summer.filestorage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;

public class FileStorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage);

        TabLayout tabLayoutHeader = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayoutHeader.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new SharedPreferencesFragment(), getString(R.string.sharePreferences));
        pagerAdapter.addFragment(new InternalExternalFragment(), getString(R.string.internalAndExternal));
        pagerAdapter.addFragment(new SaveDatabaseFragment(), getString(R.string.saveDatabase));
        viewPager.setAdapter(pagerAdapter);
    }
}
