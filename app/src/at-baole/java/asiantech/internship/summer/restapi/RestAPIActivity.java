package asiantech.internship.summer.restapi;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.restapi.model.ImageItem;
import asiantech.internship.summer.restapi.model.QueryImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener{
    private final int FIRST_PAGE = 1;
    private APIImages mAPIImages;

    private boolean mIsLoading;
    private int mCurrentPage;
    private int mLastQueryImageNumber;
    private List<ImageItem> mImages = new ArrayList<>();
    private RecyclerView mRecyclerViewImage;
    private ProgressDialog mProgressDialog;
    private ListImageAdapter mListImageAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initView();
        setUpApi();
        initRecyclerView();
    }

    private void initView() {
        Button mBtnGetImage = findViewById(R.id.btnGetImage);
        Button mBtnUploadImage = findViewById(R.id.btnUploadImage);
        mRecyclerViewImage = findViewById(R.id.recycleViewRestful);

        mBtnGetImage.setOnClickListener(this);
        // mBtnUploadImage.setOnClickListener(this);
    }
    private void initRecyclerView(){
        mRecyclerViewImage.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewImage.setLayoutManager(gridLayoutManager);
        mListImageAdapter = new ListImageAdapter(mImages, this);
        mRecyclerViewImage.setAdapter(mListImageAdapter);
    }
    private void setUpApi() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit getImagesRetrofit = new Retrofit.Builder()
                .baseUrl(APIImages.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mAPIImages = getImagesRetrofit.create(APIImages.class);
    }
    @Override
    public void onClick(View v) {
        getListImages();
    }

    private void getListImages() {
        addProgressbarDialog();
        mAPIImages.getImages(APIImages.TOKEN, mCurrentPage , APIImages.PER_PAGE)
                .enqueue(new Callback<List<QueryImage>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<QueryImage>> call,
                                           @NonNull Response<List<QueryImage>> response) {
                        if (response.isSuccessful()) {
                            List<QueryImage> addImages = response.body();
                            if (addImages != null) {
                                mImages.addAll(addImages);
                                mListImageAdapter.notifyDataSetChanged();
                                mLastQueryImageNumber = addImages.size();
                                mCurrentPage++;
                                mIsLoading = false;
                                mProgressDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<QueryImage>> call, @NonNull Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(RestAPIActivity.this, "Get image OnFailure", Toast.LENGTH_LONG).show();
                    }
                });


    }
    private void addProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Please wait, we are doing your image files");
        mProgressDialog.show();
    }
}
