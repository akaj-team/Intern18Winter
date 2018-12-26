package asiantech.internship.summer.viewpager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;

public class TimelineFragment extends Fragment implements RecyclerViewAdapter.OnclickLike {
    private static final int NUM_OF_LIKE_DEFAULT = 0;
    private static final int NUM_OF_ITEM_IN_ONE_PAGE = 10;
    public List<User> sListFavourite;
    private RecyclerView mRecyclerView;
    private Boolean isScroll = true;
    private ProgressBar mLoading;
    private int mCurrentItem;
    private int mTotalItem;
    private int mScrollOutItem;
    private List<User> mUsers;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        mLoading = view.findViewById(R.id.progressBar);
        sListFavourite = new ArrayList<>();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //line
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext(), R.drawable.custom_divider);
        assert drawable != null;
        dividerItemDecoration.setDrawable(drawable);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        //listUser
        mUsers = new ArrayList<>();
        creatData();
        mRecyclerViewAdapter = new RecyclerViewAdapter(mUsers, getActivity().getApplicationContext(), this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItem = linearLayoutManager.getChildCount();
                mTotalItem = linearLayoutManager.getItemCount();
                mScrollOutItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScroll && (mScrollOutItem + mCurrentItem == mTotalItem)) {
                    isScroll = false;
                    loadMoreData();
                }
            }
        });
    }

    private int inputRandomImage() {
        Random random = new Random();
        int mRandom = random.nextInt(10) + 1;
        int mImage;
        switch (mRandom) {
            case 1:
                mImage = R.drawable.img_avt1;
                break;
            case 2:
                mImage = R.drawable.img_avt2;
                break;
            case 3:
                mImage = R.drawable.img_avt3;
                break;
            case 4:
                mImage = R.drawable.img_avt4;
                break;
            case 5:
                mImage = R.drawable.img_avt5;
                break;
            case 6:
                mImage = R.drawable.img_avt6;
                break;
            case 7:
                mImage = R.drawable.img_avt7;
                break;
            case 8:
                mImage = R.drawable.img_avt8;
                break;
            case 9:
                mImage = R.drawable.img_avt9;
                break;
            default:
                mImage = R.drawable.img_avt10;
        }
        return mImage;
    }

    private void loadMoreData() {
        mLoading.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                creatData();
                new Handler(Looper.getMainLooper()).post(() -> {
                    isScroll = true;
                    mLoading.setVisibility(View.GONE);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                });
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    @Override
    public void eventLike(int position) {
        User user = mUsers.get(position);
        if (user.isHasLiked()) {
            user.setHasLiked(false);
            sListFavourite.remove(user);
            if (getActivity() instanceof ViewPagerActivity) {
                ((ViewPagerActivity) getActivity()).getOnChangingFavoritesListener().onRemoving(sListFavourite);
            }
        } else {
            user.setHasLiked(true);
            sListFavourite.add(0, user);
            if (getActivity() instanceof ViewPagerActivity) {
                ((ViewPagerActivity) getActivity()).getOnChangingFavoritesListener().onAdding(sListFavourite);
            }
        }
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void creatData() {
        int fromIndex = mUsers.size();
        int toIndex = fromIndex + NUM_OF_ITEM_IN_ONE_PAGE;
        for (int i = fromIndex; i < toIndex; i++) {
            mUsers.add(new User(getString(R.string.username) + (i + 1), R.drawable.img_avt2, inputRandomImage(), NUM_OF_LIKE_DEFAULT, getString(R.string.comment)));
        }
    }
}
