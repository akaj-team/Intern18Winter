package asiantech.internship.summer.asynctask_thread_handler;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String listTab[] = {"AsyncTask", "Thread", "Handler"};
    private AsyncTaskFragment mAsyncTaskFragment;
    private ThreadFragment mThreadFragment;
    private HandlerFragment mHandlerFragment;

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mAsyncTaskFragment = new AsyncTaskFragment();
        mThreadFragment = new ThreadFragment();
        mHandlerFragment = new HandlerFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mAsyncTaskFragment;
        } else if (position == 1) {
            return mThreadFragment;
        } else if (position == 2) {
            return mHandlerFragment;
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
