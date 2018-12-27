package asiantech.internship.summer.retrofit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import asiantech.internship.summer.viewpager.FavouriteFragment;

public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentLoadImage();
            case 1:
                return new FavouriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
