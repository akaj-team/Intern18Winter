package asiantech.internship.summer.asynctaskthreadhandler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AsyncTaskThreadHandlerPagerAdapter extends FragmentPagerAdapter {
    private int mPagerAdapter;

    AsyncTaskThreadHandlerPagerAdapter(FragmentManager fragmentManager, int pagerAdapter) {
        super(fragmentManager);
        this.mPagerAdapter = pagerAdapter;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return new AsyncTaskFragment();
            case 1:
                return new ThreadFragment();
            case 2:
                return new HandlerFragment();
        }
    }

    @Override
    public int getCount() {
        return mPagerAdapter;
    }
}
