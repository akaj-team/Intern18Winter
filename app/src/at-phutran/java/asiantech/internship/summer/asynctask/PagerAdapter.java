package asiantech.internship.summer.asynctask;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private static final String ASYNCTASK = "Asynctask";
    private static final String THREAD = "Thread";
    private static final String HANDLER = "Handler";
    public PagerAdapter(FragmentManager fm) {
        super(fm);
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return ASYNCTASK;
            case 2:
                return THREAD;
            default:
                return HANDLER;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
