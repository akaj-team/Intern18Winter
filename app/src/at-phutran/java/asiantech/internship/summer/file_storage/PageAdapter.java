package asiantech.internship.summer.file_storage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private static final String sharePreference = "Share Preference";
    private static final String storage = "Storage";
    private static final String database = "Database";

    PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SharePreferenceFragment();
            case 1:
                return new StorageFragment();
            case 2:
                return new DatabaseFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return sharePreference;
            case 1:
                return storage;
            default:
                return database;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
