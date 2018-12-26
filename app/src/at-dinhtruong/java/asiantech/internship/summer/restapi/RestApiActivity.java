package asiantech.internship.summer.restapi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Image;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestApiActivity extends AppCompatActivity {
    private ImageAdapter mImageAdapter;
    private SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initViews();
    }

    private void initViews() {
        mService = ApiUtils.getSOService();
        List<Image> images = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Log.d("xxxxx", "initViews: " + images.size());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mImageAdapter = new ImageAdapter(images, getApplicationContext());
        recyclerView.setAdapter(mImageAdapter);
        loadAnswers();
    }

    private void loadAnswers() {
        mService.getAnswers("6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e", 1, 20).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                if (response.body() != null) {
                    mImageAdapter.updateAnswers(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                Log.d("xxxxxx", "Throwable: " + t.getMessage());
            }
        });
    }
}
