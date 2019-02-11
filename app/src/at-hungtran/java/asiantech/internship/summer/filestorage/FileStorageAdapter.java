package asiantech.internship.summer.filestorage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FileStorageAdapter extends FragmentPagerAdapter {
    private String listTab[] = {"Share Preference", "Internal External Store", "Database"};
    private SharePreferenceFragment mSharePreferenceFragment;
    private StoreFragment mStoreFragment;
    private DatabaseFragment mDatabaseFragment;

    FileStorageAdapter(FragmentManager fm) {
        super(fm);
    }

    private void initFragment() {
        mSharePreferenceFragment = new SharePreferenceFragment();
        mStoreFragment = new StoreFragment();
        mDatabaseFragment = new DatabaseFragment();
    }

    @Override
    public Fragment getItem(int position) {
        initFragment();
        if (position == 0) {
            return mSharePreferenceFragment;
        } else if (position == 1) {
            return mStoreFragment;
        } else if (position == 2) {
            return mDatabaseFragment;
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
