package asiantech.internship.summer;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.Model.User;

public class RecyclerViewFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    private Boolean isScroll = true;
    private ProgressBar mLoading;
    private int mCurrentItems, mTotalItems;
    private int mScrollOutItems;
    private int mRandom, mImage;
    private int mLikeSum = 0;
    private int mSumItem = 10;
    private String mUsername = "Trần Thị Thanh ";
    private String mComment = "Xinh quá đi";
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mLoading = view.findViewById(R.id.progressBar);
        initView();
        return view;
    }

    public void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //line
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(((RecyclerViewActivity) getActivity()).getApplicationContext(), R.drawable.custom_divider);
        dividerItemDecoration.setDrawable(drawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        //listUser
        users = new ArrayList<>();
        for (int i = 0; i < mSumItem; i++) {
            users.add(new User(mUsername + (i + 1), R.drawable.img_avt2, inputRandomImage(), mLikeSum, mComment));
        }
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(users, ((RecyclerViewActivity) getActivity()).getApplicationContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItems = linearLayoutManager.getChildCount();
                mTotalItems = linearLayoutManager.getItemCount();
                mScrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScroll && (mScrollOutItems + mCurrentItems == mTotalItems)) {
                    isScroll = false;
                    addDataMore();
                    Log.i("xxxxx", "onScrolled: " + users.size());
                }
                Log.i("xxxxx", "mCurrentItems: " + mCurrentItems);
                Log.i("xxxxx", "mScrollOutItems: " + mScrollOutItems);
                Log.i("xxxxx", "mTotalItems: " + mTotalItems);
            }
        });

    }

    private int inputRandomImage() {
        Random random = new Random();
        mRandom = random.nextInt(10) + 1;
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

    private void addDataMore() {
        mLoading.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                for (int i = mTotalItems; i < mTotalItems + mSumItem; i++) {
                    users.add(new User(mUsername + (i + 1), R.drawable.img_avt2, inputRandomImage(), mLikeSum, mComment));
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        isScroll = true;
                        mLoading.setVisibility(View.GONE);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            } catch (InterruptedException ex) {
            }
        }).start();
    }
}
