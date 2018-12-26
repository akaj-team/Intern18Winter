package asiantech.internship.summer.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int mPageOfTab;
    public PageAdapter(FragmentManager fm, int pageOfTab) {
        super(fm);
        this.mPageOfTab = pageOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimelineFragment();
            case 1:
                return new FavouriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageOfTab;
    }
}
