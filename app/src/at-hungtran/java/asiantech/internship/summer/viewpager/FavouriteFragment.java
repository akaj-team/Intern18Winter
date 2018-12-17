package asiantech.internship.summer.viewpager;

import android.os.Bundle;
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

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.TimelineItem;

public class FavouriteFragment extends Fragment {
    public static FavouriteAdapter mFavouriteAdapter;
    List<TimelineItem> mTimelineItems = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ProgressBar mProgressBarLoading = view.findViewById(R.id.progress_bar);
        initView(view);
        return view;
    }

    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mTimelineItems = createTimelineItem();
        mFavouriteAdapter = new FavouriteAdapter(mTimelineItems, getActivity());
        recyclerView.setAdapter(mFavouriteAdapter);
    }

    public List<TimelineItem> createTimelineItem() {
            mTimelineItems = PagerActivity.itemList;
        return mTimelineItems;
    }

}