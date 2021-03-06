package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.TimelineItem;

public class TimelineFragment extends Fragment implements TimelineAdapter.onClick {
    private static final String COMMENT = "チャン ヴァン フン ";
    private static final String NAME_OF_PEOPLE = "Le Thi Quynh Chau ";
    private TimelineAdapter mTimelineAdapter;
    List<TimelineItem> mTimelineItems = new ArrayList<>();
    List<TimelineItem> mFavourite = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Boolean mIsScroll = true;
    private ProgressBar mProgressBarLoading;
    private int mCurrentItem;
    private int mTotalItem;
    private int mScrollOutItems;
    private Boolean mChecked = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        mProgressBarLoading = view.findViewById(R.id.progress_bar);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mTimelineItems = createTimelineItem();
        mTimelineAdapter = new TimelineAdapter(mTimelineItems, getActivity(), this);
        mRecyclerView.setAdapter(mTimelineAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItem = layoutManager.getChildCount();
                mTotalItem = layoutManager.getItemCount();
                mScrollOutItems = layoutManager.findFirstVisibleItemPosition();
                if (mIsScroll && (mScrollOutItems + mCurrentItem == mTotalItem)) {
                    mIsScroll = false;
                    loadMoreData();
                }
            }
        });
    }

    private int getRandomImageId() {
        Random random = new Random();
        int rand = random.nextInt(10) + 1;
        switch (rand) {
            case 1:
                return R.drawable.img_nature_11;
            case 2:
                return R.drawable.img_itachi;
            case 3:
                return R.drawable.img_violet_evergarden;
            case 4:
                return R.drawable.img_nature_4;
            case 5:
                return R.drawable.img_violet;
            case 6:
                return R.drawable.img_nature_7;
            case 7:
                return R.drawable.img_nature_8;
            case 8:
                return R.drawable.img_nature_6;
            case 9:
                return R.drawable.img_nature_1;
            default:
                return R.drawable.img_nature_4;
        }
    }

    private int inputRandomImgAvt() {
        Random random = new Random();
        int rand = random.nextInt(10) + 1;
        switch (rand) {
            case 1:
                return R.drawable.img_nature_11;
            case 2:
                return R.drawable.img_itachi;
            case 3:
                return R.drawable.img_violet_evergarden;
            case 4:
                return R.drawable.img_nature_4;
            case 5:
                return R.drawable.img_violet;
            case 6:
                return R.drawable.img_nature_7;
            case 7:
                return R.drawable.img_nature_8;
            case 8:
                return R.drawable.img_nature_6;
            case 9:
                return R.drawable.img_nature_1;
            default:
                return R.drawable.img_nature_4;
        }
    }

    private void loadMoreData() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                createTimelineItem();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mIsScroll = true;
                        mProgressBarLoading.setVisibility(View.GONE);
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    public List<TimelineItem> createTimelineItem() {
        int size = mTimelineItems.size();
        for (int i = size; i < size + 10; i++) {
            int mLike = 0;
            mTimelineItems.add(new TimelineItem(inputRandomImgAvt(), NAME_OF_PEOPLE + (i + 1), getRandomImageId(), COMMENT + (i + 1), mLike, NAME_OF_PEOPLE + (i + 1)));
        }
        return mTimelineItems;
    }

    @Override
    public void likeClick(int position) {
        TimelineItem timelineItem = mTimelineItems.get(position);
        if (timelineItem.isIsChecked()) {
            timelineItem.setLike(timelineItem.getLike() - 1);
            mFavourite.remove(timelineItem);
            if (getActivity() instanceof PagerActivity) {
                ((PagerActivity) getActivity()).getOnChangingFavoritesListener().onRemoveFavourite(mFavourite);
            }
            timelineItem.setIsChecked(false);
        } else {
            timelineItem.setIsChecked(true);
            timelineItem.setLike(timelineItem.getLike() + 1);
            mFavourite.add(0, timelineItem);
            if (getActivity() instanceof PagerActivity) {
                ((PagerActivity) getActivity()).getOnChangingFavoritesListener().onAddFavourite(mFavourite);
            }
        }
        mTimelineAdapter.notifyDataSetChanged();
    }
}
