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
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.TimelineItem;

public class FavouriteFragment extends Fragment implements OnChangingFavoritesListener{
    private FavouriteAdapter mFavouriteAdapter;
    List<TimelineItem> mTimelineItems = new ArrayList<>();
    private RecyclerView mRecyclerView;

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
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mFavouriteAdapter = new FavouriteAdapter(mTimelineItems, getActivity());
        mRecyclerView.setAdapter(mFavouriteAdapter);

        if(getActivity() instanceof  PagerActivity) {
            ((PagerActivity) getActivity()).setOnChangingFavoritesListener(this);
        }
    }

    @Override
    public void onAddFavourite(List<TimelineItem> listFavourite) {
        mFavouriteAdapter = new FavouriteAdapter(listFavourite , Objects.requireNonNull(getActivity()).getApplicationContext());
        mRecyclerView.setAdapter(mFavouriteAdapter);
    }

    @Override
    public void onRemoveFavourite(List<TimelineItem> listFavourite) {
        mFavouriteAdapter = new FavouriteAdapter(listFavourite , Objects.requireNonNull(getActivity()).getApplicationContext());
        mRecyclerView.setAdapter(mFavouriteAdapter);
    }
}