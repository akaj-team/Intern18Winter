package asiantech.internship.summer.retrofit;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import asiantech.internship.summer.viewpager.FavouriteFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private static final String TAB_LOAD_IMAGE = "Load images";
    private static final String TAB_INSERT_IMAGE = "Insert images";
    PageAdapter(FragmentManager fm) {
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return TAB_LOAD_IMAGE;
            default:
                return TAB_INSERT_IMAGE;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
