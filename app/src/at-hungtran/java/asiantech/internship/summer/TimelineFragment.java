package asiantech.internship.summer;

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
import java.util.Random;

import asiantech.internship.summer.model.TimelineItem;

public class TimelineFragment extends Fragment {
    private static final String COMMENTS = "チャン ヴァン フン ";
    private static final String NAME = "Le Thi Quynh Chau ";
    View view;
    ArrayList<TimelineItem> timelineItems = new ArrayList<>();
    RecyclerView recyclerView;
    private Boolean isScroll = true;
    private ProgressBar mLoading;
    private int mCurrentItems, mTotalItems;
    private int mScrollOutItems;
    private int mRand;
    private int mImage;
    private int mLike = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        mLoading = view.findViewById(R.id.progress_bar);
        initView();
        return view;
    }

    public void initView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        for (int i = 0; i < 10; i++) {
            timelineItems.add(new TimelineItem(inputRandomImgAvt(), NAME + (i + 1), inputRandomImage(), COMMENTS + (i + 1), mLike, NAME + (i + 1)));
        }
        TimelineAdapter timelineAdapter = new TimelineAdapter(timelineItems, getActivity());
        recyclerView.setAdapter(timelineAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentItems = layoutManager.getChildCount();
                mTotalItems = layoutManager.getItemCount();
                mScrollOutItems = layoutManager.findFirstVisibleItemPosition();
                if (isScroll && (mScrollOutItems + mCurrentItems == mTotalItems)) {
                    isScroll = false;
                    addDataMore();
                }
            }
        });
    }

    private int inputRandomImage() {
        Random random = new Random();
        mRand = random.nextInt(10) + 1;
        switch (mRand) {
            case 1:
                mImage = R.drawable.img_nature_11;
                break;
            case 2:
                mImage = R.drawable.img_itachi;
                break;
            case 3:
                mImage = R.drawable.img_violet_evergarden;
                break;
            case 4:
                mImage = R.drawable.img_nature_4;
                break;
            case 5:
                mImage = R.drawable.img_violet;
                break;
            case 6:
                mImage = R.drawable.img_nature_7;
                break;
            case 7:
                mImage = R.drawable.img_nature_8;
                break;
            case 8:
                mImage = R.drawable.img_nature_6;
                break;
            case 9:
                mImage = R.drawable.img_nature_1;
                break;
            default:
                mImage = R.drawable.img_nature_4;
        }
        return mImage;
    }

    private int inputRandomImgAvt() {
        Random random = new Random();
        mRand = random.nextInt(10) + 1;
        switch (mRand) {
            case 1:
                mImage = R.drawable.img_nature_11;
                break;
            case 2:
                mImage = R.drawable.img_itachi;
                break;
            case 3:
                mImage = R.drawable.img_violet_evergarden;
                break;
            case 4:
                mImage = R.drawable.img_nature_1;
                break;
            case 5:
                mImage = R.drawable.img_violet;
                break;
            case 6:
                mImage = R.drawable.img_nature_7;
                break;
            case 7:
                mImage = R.drawable.img_nature_8;
                break;
            case 8:
                mImage = R.drawable.img_nature_3;
                break;
            case 9:
                mImage = R.drawable.img_nature_4;
                break;
            default:
                mImage = R.drawable.img_nature_6;
        }
        return mImage;
    }

    private void addDataMore() {
        mLoading.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                for (int i = mTotalItems; i < mTotalItems + 10; i++) {
                    timelineItems.add(new TimelineItem(inputRandomImgAvt(), NAME + (i + 1), inputRandomImage(), COMMENTS + (i + 1), mLike, NAME + (i + 1)));
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        isScroll = true;
                        mLoading.setVisibility(View.GONE);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}
