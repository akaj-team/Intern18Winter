package asiantech.internship.summer.retrofit;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import asiantech.internship.summer.R;

public class RetrofitActivity extends AppCompatActivity {

    private ViewPager mViewPagerContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retofit_gson);
        TabLayout mTabLayoutHeader = findViewById(R.id.tabLayoutHeader);
        mViewPagerContent = findViewById(R.id.viewPagerContent);
        PageAdapter mPageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPagerContent.setAdapter(mPageAdapter);
        mTabLayoutHeader.setupWithViewPager(mViewPagerContent);
    }

}
