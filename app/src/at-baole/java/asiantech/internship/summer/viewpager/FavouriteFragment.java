package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.model.TimelineItem;

public class FavouriteFragment extends Fragment implements OnChangeFavoritesListener {
    List<TimelineItem> mTimelineItems = new ArrayList<>();
    private FavouriteAdapter mFavouriteAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        FavouriteAdapter mAdapter = new FavouriteAdapter(mTimelineItems, getContext());
        mRecyclerView.setAdapter(mAdapter);
        if (getActivity() instanceof PagerActivity) {
            ((PagerActivity) getActivity()).setOnChangeFavoritesListener(this);
        }
    }

    @Override
    public void addFavourite(List<TimelineItem> listFavourite) {
        mFavouriteAdapter = new FavouriteAdapter(listFavourite, getContext());
        mRecyclerView.setAdapter(mFavouriteAdapter);
    }

    @Override
    public void removeFavourite(List<TimelineItem> listFavourite) {
        mFavouriteAdapter = new FavouriteAdapter(listFavourite, getContext());
        mRecyclerView.setAdapter(mFavouriteAdapter);
    }
}
