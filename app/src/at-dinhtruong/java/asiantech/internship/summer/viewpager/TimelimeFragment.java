package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelinePagerItem;


public class TimelimeFragment extends Fragment implements TimelinePagerAdapter.onClickItem {
    private static final int NUM_OF_ITEM_ON_PAGE = 10;
    private boolean mIsLoadmore = true;
    private int mTotalItemCount;
    private int mChildCount;
    private int mFirstVisible;
    private TimelinePagerAdapter mTimelineAdapter;
    private List<TimelinePagerItem> mTimelineItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        initTimeline(view);
        return view;
    }

    private void initTimeline(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewPager);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mTimelineItems = TimelinePagerItem.createTimelines();
        mTimelineAdapter = new TimelinePagerAdapter(getActivity(), mTimelineItems, this);
        mRecyclerView.setAdapter(mTimelineAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = linearLayoutManager.getItemCount();
                mChildCount = linearLayoutManager.getChildCount();
                mFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (mFirstVisible + mChildCount == mTotalItemCount && mIsLoadmore) {
                    mIsLoadmore = false;
                    mTimelineAdapter.setLoaded(true);
                    mTimelineAdapter.notifyDataSetChanged();
                    addItemLoadmore(mTimelineAdapter);
                }
            }
        });
        final SwipeRefreshLayout swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync();
            }

            private void fetchTimelineAsync() {
                mTimelineAdapter.clear();
                mTimelineAdapter.addAll(TimelinePagerItem.createTimelines());
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void addItemLoadmore(TimelinePagerAdapter timelineAdapter) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                int fromIndex = mTimelineItems.size();
                int toIndex = fromIndex + NUM_OF_ITEM_ON_PAGE;
                Random random = new Random();
                for (int i = fromIndex; i < toIndex; i++) {
                    int randomAvatar = random.nextInt(10) + 1;
                    int randomImage = random.nextInt(10) + 1;
                    mTimelineItems.add(new TimelinePagerItem(0, "img_avatar" + randomAvatar, "Nguyen Van " + (i + 1), "img_image" + randomImage, "Noi dung thu " + (i + 1), false));
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    mIsLoadmore = true;
                    timelineAdapter.setLoaded(false);
                    timelineAdapter.notifyDataSetChanged();
                });
            } catch (InterruptedException ignored) {
            }
        }) {
        }.start();
    }

    @Override
    public void onSelectItem(int position) {
        TimelinePagerItem timelineItem = mTimelineItems.get(position);
        boolean check = timelineItem.isCheckSelected();
        if (check) {
            timelineItem.setCheckSelected(false);
            if (getActivity() instanceof ViewPagerActivity) {
                ((ViewPagerActivity) getActivity()).getFavouriteItems().remove(timelineItem);
                timelineItem.setNumOfLike(0);
                ((ViewPagerActivity) getActivity()).getFavoriteAdapter().notifyDataSetChanged();
            }
        } else {
            ((ViewPagerActivity) getActivity()).getFavouriteItems().add(0, timelineItem);
            timelineItem.setCheckSelected(true);
            timelineItem.setNumOfLike(1);
            if (getActivity() instanceof ViewPagerActivity) {
                ((ViewPagerActivity) getActivity()).getFavoriteAdapter().notifyDataSetChanged();
            }
        }
    }
}
