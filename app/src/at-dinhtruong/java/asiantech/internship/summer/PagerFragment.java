package asiantech.internship.summer;

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

import asiantech.internship.summer.models.TimelineItem;

public class PagerFragment extends Fragment {
    private boolean mIsLoadmore = false;

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
        List<TimelineItem> timelineItems = TimelineItem.createTimelines();
        TimelineAdapter timelineAdapter = new TimelineAdapter(getActivity(), timelineItems, mRecyclerView);
        mRecyclerView.setAdapter(timelineAdapter);
        timelineAdapter.setOnLoadMoreListener(() -> {
            if (mIsLoadmore) {
                return;
            }
            mIsLoadmore = true;
            timelineAdapter.setLoaded(true);
            timelineAdapter.notifyDataSetChanged();
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    int mIndex = timelineItems.size() + 1;
                    int mEnd = mIndex + 10;
                    Random random = new Random();
                    for (int i = mIndex; i < mEnd; i++) {
                        int mRandomAvatar = random.nextInt(10) + 1;
                        int mRandomImage = random.nextInt(10) + 1;
                        timelineItems.add(new TimelineItem(0, "img_avatar" + mRandomAvatar, "Nguyen Van " + i, "img_image" + mRandomImage, "Noi dung thu " + i));
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        mIsLoadmore = false;
                        timelineAdapter.setLoaded(false);
                        timelineAdapter.notifyDataSetChanged();
                    });
                } catch (InterruptedException ignored) {

                }
            }) {
            }.start();
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
}
