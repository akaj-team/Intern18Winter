package asiantech.internship.summer.file_storage;

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
        TabLayout tabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        ViewPager viewPagerContent = findViewById(R.id.viewPagerContent);
        PageAdapter mPageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPagerContent.setAdapter(mPageAdapter);
        tabLayoutHeader.setupWithViewPager(viewPagerContent);
    }
}
