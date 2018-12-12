package asiantech.internship.summer.recycler_view;

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
import asiantech.internship.summer.recycler_view.model.TimelineItem;

public class TimelineFragment extends Fragment implements RecyclerViewAdapter.OnItemListener {
    private int mAvatar;
    private int mPicture;
    private int mSumItem;
    private int mCountLike;
    private int mCurrentItem;
    private int mScrolledItem;
    private int mTotalItem = 10;
    private View mView;
    private ProgressBar mLoading;
    private boolean isScrolled = true;
    private List<TimelineItem> mItems;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    public TimelineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mLoading = mView.findViewById(R.id.progressBar);
        initView();
        return mView;
    }

    public void initView() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setData();
        mAdapter = new RecyclerViewAdapter(mItems, this);
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
                    loadMoreView();
                }
            }
        });
    }

    @Override
    public void onClickLike(int position) {
        Toast.makeText(getContext(), R.string.liked, Toast.LENGTH_LONG).show();
        mItems.get(position).setmCountLike(mItems.get(position).getmCountLike() + 1);
        mAdapter.notifyDataSetChanged();
    }

    public void setData() {
        mItems = new ArrayList<>();
        for (int i = 0; i < mTotalItem; i++) {
            mItems.add(new TimelineItem(setAvatar(), getString(R.string.username), setPicture(), mCountLike, getString(R.string.comment)));
        }
    }

    private void loadMoreView() {
        mLoading.setVisibility(View.VISIBLE);
        initThread();
    }

    private void initThread() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                for (int i = mSumItem; i < mSumItem + mTotalItem; i++) {
                    mItems.add(new TimelineItem(setAvatar(), getString(R.string.username), setPicture(), mCountLike, getString(R.string.comment)));
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    isScrolled = true;
                    mLoading.setVisibility(View.GONE);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private int setAvatar() {
        Random randomAvatar = new Random();
        int mRandomAvatar = randomAvatar.nextInt(10);
        switch (mRandomAvatar) {
            case 1:
                mAvatar = R.drawable.img_avatar_1;
                break;
            case 2:
                mAvatar = R.drawable.img_avatar_2;
                break;
            case 3:
                mAvatar = R.drawable.img_avatar_3;
                break;
            case 4:
                mAvatar = R.drawable.img_avatar_4;
                break;
            case 5:
                mAvatar = R.drawable.img_avatar_5;
                break;
            case 6:
                mAvatar = R.drawable.img_avatar_6;
                break;
            case 7:
                mAvatar = R.drawable.img_avatar_7;
                break;
            case 8:
                mAvatar = R.drawable.img_avatar_8;
                break;
            case 9:
                mAvatar = R.drawable.img_avatar_9;
                break;
            case 10:
                mAvatar = R.drawable.img_avatar_10;
                break;
        }
        return mAvatar;
    }

    private int setPicture() {
        Random randomPicture = new Random();
        int mRandomPicture = randomPicture.nextInt(10);
        switch (mRandomPicture) {
            case 1:
                mPicture = R.drawable.img_food_1;
                break;
            case 2:
                mPicture = R.drawable.img_food_2;
                break;
            case 3:
                mPicture = R.drawable.img_food_3;
                break;
            case 4:
                mPicture = R.drawable.img_food_4;
                break;
            case 5:
                mPicture = R.drawable.img_food_5;
                break;
            case 6:
                mPicture = R.drawable.img_food_6;
                break;
            case 7:
                mPicture = R.drawable.img_food_7;
                break;
            case 8:
                mPicture = R.drawable.img_food_8;
                break;
            case 9:
                mPicture = R.drawable.img_food_9;
                break;
            case 10:
                mPicture = R.drawable.img_food_10;
                break;
        }
        return mPicture;
    }


}
