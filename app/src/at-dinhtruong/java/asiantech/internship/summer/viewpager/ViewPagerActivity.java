package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelinePagerItem;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    public static List<TimelinePagerItem> favouritePagerItems;

    private FavouritePagerAdapter mFavouritePagerAdapter;

    public FavouritePagerAdapter getFavoriteAdapter() {
        return mFavouritePagerAdapter;
    }

    public void setFavouriteAdapter(FavouritePagerAdapter favouritePagerAdapter) {
        this.mFavouritePagerAdapter = favouritePagerAdapter;
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