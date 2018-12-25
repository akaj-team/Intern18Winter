package asiantech.internship.summer.asynctask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int mNumPager;

    public PagerAdapter(FragmentManager fm, int numPager) {
        super(fm);
        mNumPager = numPager;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentAsyncTask();
            case 1:
                return new FragmentThread();
            case 2:
                return new FragmentHandler();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumPager;
    }
}
