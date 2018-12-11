package asiantech.internship.summer.storage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;

public class FileStoreActivity extends AppCompatActivity {
    private TabLayout mTlFileStore;
    private ViewPager mViewPagerFileStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_store);
        mViewPagerFileStore = findViewById(R.id.viewpager);
        setupViewPager(mViewPagerFileStore);
        mTlFileStore = findViewById(R.id.tabs);
        mTlFileStore.setupWithViewPager(mViewPagerFileStore);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }


}
