package asiantech.internship.summer.viewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelinePagerItem;

public class ViewPagerActivity extends AppCompatActivity {
    private List<TimelinePagerItem> favouritePagerItems = new ArrayList<>();

    private TimelinePagerAdapter mFavouritePagerAdapter;

    public TimelinePagerAdapter getFavoriteAdapter() {
        return mFavouritePagerAdapter;
    }

    public void setFavouriteAdapter(TimelinePagerAdapter favouritePagerAdapter) {
        this.mFavouritePagerAdapter = favouritePagerAdapter;
    }

    public List<TimelinePagerItem> getFavouriteItems() {
        return favouritePagerItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager viewPagerFileStore = findViewById(R.id.viewPager);
        setupViewPager(viewPagerFileStore);
        TabLayout tlFileStore = findViewById(R.id.tabLayoutViewPager);
        tlFileStore.setupWithViewPager(viewPagerFileStore);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerPagerAdapter viewPagerAdapter = new ViewPagerPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TimelimeFragment(), getString(R.string.pager));
        viewPagerAdapter.addFragment(new FavouriteFragment(), getString(R.string.favourite));
        viewPager.setAdapter(viewPagerAdapter);
    }
}
