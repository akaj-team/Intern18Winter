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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.model.TimelineItem;

public class TimelineFragment extends Fragment implements TimelineAdapter.OnItemListener {
    List<TimelineItem> mFavourite;
    private int mSumItem;
    private int mCountLike = 0;
    private int mCurrentItem;
    private int mScrolledItem;
    private int mTotalItem = 10;
    private ProgressBar mProgressBarLoading;
    private boolean isScrolled = true;
    private List<TimelineItem> mTimelineItems;
    private RecyclerView mRecyclerView;
    private TimelineAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mProgressBarLoading = view.findViewById(R.id.progressBar);
        initView();
        return view;
    }

    public void initView() {
        mFavourite = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setData();
        mAdapter = new TimelineAdapter(mTimelineItems, this, getContext());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItem = linearLayoutManager.getChildCount();
                mSumItem = linearLayoutManager.getItemCount();
                mScrolledItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolled && (mScrolledItem + mCurrentItem == mSumItem)) {
                    isScrolled = false;
                    loadMoreData();
                }
            }
        });
    }

    @Override
    public void onClickLike(int position) {
        TimelineItem timelineItem = mTimelineItems.get(position);
        if (!timelineItem.isChecked()) {
            timelineItem.setIsChecked(true);
            Toast.makeText(getContext(), R.string.addFavourite, Toast.LENGTH_LONG).show();
            mFavourite.add(0, timelineItem);
            if (getActivity() instanceof PagerActivity) {
                ((PagerActivity) getActivity()).getOnChangeFavoritesListener().addFavourite(mFavourite);
            }
        } else {
            timelineItem.setIsChecked(false);
            Toast.makeText(getContext(), R.string.removeFavourite, Toast.LENGTH_LONG).show();
            mFavourite.remove(timelineItem);
            if (getActivity() instanceof PagerActivity) {
                ((PagerActivity) getActivity()).getOnChangeFavoritesListener().removeFavourite(mFavourite);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setData() {
        mTimelineItems = new ArrayList<>();
        for (int i = 0; i < mTotalItem; i++) {
            mTimelineItems.add(new TimelineItem(getAvatarImageId(), getString(R.string.username) + " " + (i + 1), getImageFoodId(), mCountLike, getString(R.string.username) + " " + (i + 1), getString(R.string.comment)));
        }
    }

    private void loadMoreData() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                for (int i = mSumItem; i < mSumItem + mTotalItem; i++) {
                    mTimelineItems.add(new TimelineItem(getAvatarImageId(), getString(R.string.username) + " " + (i + 1), getImageFoodId(), mCountLike, getString(R.string.username) + " " + (i + 1), getString(R.string.comment)));
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    isScrolled = true;
                    mProgressBarLoading.setVisibility(View.GONE);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                });
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    private int getAvatarImageId() {
        Random random = new Random();
        int index = random.nextInt(10);
        switch (index) {
            case 0:
                return R.drawable.img_avatar_1;
            case 1:
                return R.drawable.img_avatar_2;
            case 2:
                return R.drawable.img_avatar_3;
            case 3:
                return R.drawable.img_avatar_4;
            case 4:
                return R.drawable.img_avatar_5;
            case 5:
                return R.drawable.img_avatar_6;
            case 6:
                return R.drawable.img_avatar_7;
            case 7:
                return R.drawable.img_avatar_8;
            case 8:
                return R.drawable.img_avatar_9;
            case 9:
                return R.drawable.img_avatar_10;
        }
        return index;
    }

    private int getImageFoodId() {
        Random random = new Random();
        int index = random.nextInt(10);
        switch (index) {
            case 0:
                return R.drawable.img_food_1;
            case 1:
                return R.drawable.img_food_2;
            case 2:
                return R.drawable.img_food_3;
            case 3:
                return R.drawable.img_food_4;
            case 4:
                return R.drawable.img_food_5;
            case 5:
                return R.drawable.img_food_6;
            case 6:
                return R.drawable.img_food_7;
            case 7:
                return R.drawable.img_food_8;
            case 8:
                return R.drawable.img_food_9;
            case 9:
                return R.drawable.img_food_10;
        }
        return index;
    }
}
