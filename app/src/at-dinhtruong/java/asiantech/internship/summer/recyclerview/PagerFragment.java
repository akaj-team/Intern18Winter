package asiantech.internship.summer.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelineItem;


public class PagerFragment extends Fragment implements TimelineAdapter.onClickItem {
    private static final int NUM_OF_ITEM_ON_PAGE = 10;
    private boolean mIsLoadmore = true;
    private List<TimelineItem> mTimelineItems;
    private int mTotalItemCount;
    private int mChildCount;
    private int mFirstVisible;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mTimelineItems = TimelineItem.createTimelines();
        TimelineAdapter timelineAdapter = new TimelineAdapter(getActivity(), mTimelineItems, this);
        mRecyclerView.setAdapter(timelineAdapter);
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
                    timelineAdapter.setLoaded(true);
                    timelineAdapter.notifyDataSetChanged();
                    addItemLoadmore(timelineAdapter);
                }
            }
        });
        SwipeRefreshLayout swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync();
            }

            private void fetchTimelineAsync() {
                timelineAdapter.clear();
                timelineAdapter.addAll(TimelineItem.createTimelines());
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void addItemLoadmore(TimelineAdapter timelineAdapter) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                //int fromIndex = mTimelineItems.size() + 1;
                int fromIndex = mTimelineItems.size();
                int toIndex = fromIndex + NUM_OF_ITEM_ON_PAGE;
                Random random = new Random();
                for (int i = fromIndex; i < toIndex; i++) {
                    int randomAvatar = random.nextInt(10) + 1;
                    int randomImage = random.nextInt(10) + 1;
                    mTimelineItems.add(new TimelineItem(0, "img_avatar" + randomAvatar, "Nguyen Van " + (i + 1), "img_image" + randomImage, "Noi dung thu " + (i + 1)));
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
        TimelineItem timelineItem = mTimelineItems.get(position);
        timelineItem.setCountLike(timelineItem.getCountLike() + 1);
    }
}
