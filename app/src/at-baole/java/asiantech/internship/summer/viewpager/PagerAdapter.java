package asiantech.internship.summer.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final String PAGER_TITLE_TIMELINE = "timeline";
    private static final String PAGER_TITLE_FAVOURITE = "favourite";

    PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimelineFragment();
            case 1:
            default:
                return new FavouriteFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return PAGER_TITLE_TIMELINE;
            case 1:
            default:
                return PAGER_TITLE_FAVOURITE;
        }
    }
}
