package asiantech.internship.summer.viewpager;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import java.util.List;
import java.util.Objects;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;

public class FavouriteFragment extends Fragment implements OnChangingFavoritesListener {
    @SuppressLint("StaticFieldLeak")
    private FavoriteAdapter mFavoriteAdapter;
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
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
        if(getActivity() instanceof  ViewPagerActivity) {
            ((ViewPagerActivity) getActivity()).setOnChangingFavoritesListener(this);
        }
    }

    @Override
    public void onAdding(List<User> list) {
        mFavoriteAdapter = new FavoriteAdapter(list , Objects.requireNonNull(getActivity()).getApplicationContext());
        mRecyclerView.setAdapter(mFavoriteAdapter);
    }

    @Override
    public void onRemoving(List<User> list) {
        mFavoriteAdapter = new FavoriteAdapter(list , Objects.requireNonNull(getActivity()).getApplicationContext());
        mRecyclerView.setAdapter(mFavoriteAdapter);
    }
}
