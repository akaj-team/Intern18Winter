package asiantech.internship.summer.filestorage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private String listTab[] = {"Fragment 1", "Fragment 2", "Fragment 3"};
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    private ThirdFragment mThirdFragment;

    TabLayoutAdapter(FragmentManager fm) {
        super(fm);
        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment();
        mThirdFragment = new ThirdFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mFirstFragment;
        } else if (position == 1) {
            return mSecondFragment;
        } else if (position == 2) {
            return mThirdFragment;
        }
        return null;
     }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
