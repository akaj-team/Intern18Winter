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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.Model.User;

public class RecyclerViewFragment extends Fragment implements RecyclerViewAdapter.OnclickLike {
    private static final int SUMLIKE = 0;
    private static final int SUMITEM = 10;
    private RecyclerView mRecyclerView;
    private View mView;
    private Boolean isScroll = true;
    private ProgressBar mLoading;
    private int mCurrentItems, mTotalItems, mScrollOutItems;;
    private int mRandom, mImage;
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mLoading = mView.findViewById(R.id.progressBar);
        initView();
        return mView;
    }

    public void initView() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //line
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(((RecyclerViewActivity) getActivity()).getApplicationContext(), R.drawable.custom_divider);
        dividerItemDecoration.setDrawable(drawable);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        //listUser
        users = new ArrayList<>();
        for (int i = 0; i < SUMITEM; i++) {
            users.add(new User(getString(R.string.username) + (i + 1), R.drawable.img_avt2, inputRandomImage(), SUMLIKE, getString(R.string.comment)));
        }
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(users, ((RecyclerViewActivity) getActivity()).getApplicationContext(), this);
        mRecyclerView.setAdapter(recyclerViewAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                }
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
                for (int i = mTotalItems; i < mTotalItems + SUMITEM; i++) {
                    users.add(new User(getString(R.string.username) + (i + 1), R.drawable.img_avt2, inputRandomImage(), SUMLIKE, getString(R.string.comment)));
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        isScroll = true;
                        mLoading.setVisibility(View.GONE);
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            } catch (InterruptedException ex) {
            }
        }).start();
    }

    @Override
    public void sumCountLike(int position, TextView textViewCountLike) {
        users.get(position).setCountLike(users.get(position).getCountLike() + 1);
        if (users.get(position).getCountLike() == 1) {
            textViewCountLike.setText(users.get(position).getCountLike() + getString(R.string.like));
        } else {
            textViewCountLike.setText(users.get(position).getCountLike() + getString(R.string.likes));
        }
    }
}
