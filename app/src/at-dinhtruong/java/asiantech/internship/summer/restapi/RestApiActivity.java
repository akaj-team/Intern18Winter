package asiantech.internship.summer.restapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private List<Image> mImages;
    private SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initViews();
    }

    private void initViews() {
        mService = ApiUtils.getSOService();
        mImages = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ImageAdapter(mImages);
        recyclerView.setAdapter(mImageAdapter);
        loadAnswers();
    }

    private void loadAnswers() {
        mService.getAnswers("6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e",1,20).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d("xxxxxx", "onResponse: "+response.body().getCreatedAt());
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

            }
        });
    }
}
