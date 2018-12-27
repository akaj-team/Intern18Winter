package asiantech.internship.summer.retrofit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.ImageItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLoadImage extends Fragment {
    private RetrofitAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;
    private List<ImageItem> mImageItems;
    private static final String ACCESS_TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_image, container, false);
        mImageItems = new ArrayList<>();
        mService = ApiUtils.getSOService();
        mRecyclerView = view.findViewById(R.id.recyclerViewContent);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        loadAnswers();
        return view;
    }

    private void loadAnswers() {
        mService.getImages(ACCESS_TOKEN,1,20).enqueue(new Callback<List<ImageItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImageItem>> call, @NonNull Response<List<ImageItem>> response) {
                if(response.isSuccessful()) {
                    mImageItems = response.body();
                    mAdapter = new RetrofitAdapter(mImageItems, Objects.requireNonNull(getActivity()).getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageItem>> call, @NonNull Throwable t) {
                Log.i("xxx", "onFailure: ");
            }
        });
    }
}
