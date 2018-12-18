package asiantech.internship.summer.viewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import asiantech.internship.summer.R;

@SuppressLint("Registered")
public class PagerActivity extends AppCompatActivity {
    private OnChangingFavoritesListener onChangingFavoritesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    public OnChangingFavoritesListener getOnChangingFavoritesListener() {
        return onChangingFavoritesListener;
    }

    public void setOnChangingFavoritesListener(OnChangingFavoritesListener onChangingFavoritesListener) {
        this.onChangingFavoritesListener = onChangingFavoritesListener;
    }
}