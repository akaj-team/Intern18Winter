package asiantech.internship.summer.filestorage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int mPagerAdapter;

    PagerAdapter(FragmentManager fragmentManager, int pagerAdapter) {
        super(fragmentManager);
        this.mPagerAdapter = pagerAdapter;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return new SharePreferencesFragment();
            case 1:
                return new InternalExternalFragment();
            case 2:
                return new SaveDatabaseFragment();
        }
    }

    @Override
    public int getCount() {
        return mPagerAdapter;
    }
}
