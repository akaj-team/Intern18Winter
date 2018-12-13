package asiantech.internship.summer.file_storage;

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
                return new SharePreferenceFragment();
            case 1:
                return new StorageFragment();
            case 2:
                return new DatabaseFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageOfTab;
    }
}
