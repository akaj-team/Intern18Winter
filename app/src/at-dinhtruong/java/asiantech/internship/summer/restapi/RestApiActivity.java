package asiantech.internship.summer.restapi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Image;
import asiantech.internship.summer.utils.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 200;
    private static final int REQUEST_SELECT_PICTURE = 201;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 123;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_GALLERY = 124;
    private static final int mPage = 1;
    private static final int mPerPage = 20;
    private static final String ACCESS_TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    private static final String BASE_URL = "https://api.gyazo.com/api/";
    private static final String UPLOAD_URL = "https://upload.gyazo.com/api/upload";
    private int mActionUpload = 0;
    private ImageAdapter mImageAdapter;
    private SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initViews();
    }

    private void initViews() {
        setUpApi();
        List<Image> images = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ImageAdapter(images);
        recyclerView.setAdapter(mImageAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        loadImages();
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
    }

    private void loadImages() {
        mService.getImages(ACCESS_TOKEN, mPage, mPerPage).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                mImageAdapter.updateList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCamera: {
                mActionUpload = REQUEST_IMAGE_CAPTURE;
                if (!requestPermission()) {
                    Toast.makeText(RestApiActivity.this, R.string.accept, Toast.LENGTH_SHORT).show();
                } else {
                    openCamera();
                }
                break;
            }
            case R.id.btnGallery: {
                mActionUpload = REQUEST_SELECT_PICTURE;
                if (!requestPermission()) {
                    Toast.makeText(RestApiActivity.this, R.string.accept, Toast.LENGTH_SHORT).show();
                } else {
                    openGallery();
                }
                break;
            }
            default:
                break;
        }
    }

    private void openCamera() {
        if (requestPermission()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        if (requestPermission()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.selectPicture)), REQUEST_SELECT_PICTURE);
        }
    }

    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && mActionUpload == REQUEST_IMAGE_CAPTURE) {
            ActivityCompat.requestPermissions(RestApiActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && mActionUpload == REQUEST_SELECT_PICTURE) {
            ActivityCompat
                    .requestPermissions(RestApiActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS_GALLERY);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(RestApiActivity.this, R.string.permissionDenied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_ASK_PERMISSIONS_GALLERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(RestApiActivity.this, R.string.permissionDenied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageURI = data.getData();
        uploadImage(selectedImageURI);
    }

    private void onCaptureImageResult(Intent data) {
        Bundle getExtrasImage = data.getExtras();
        Bitmap imageBitmap = null;
        if (getExtrasImage != null) {
            imageBitmap = (Bitmap) (getExtrasImage).get(getString(R.string.data));
        }
        if (imageBitmap != null) {
            uploadImage(getImageUri(getApplicationContext(), imageBitmap));
        }
    }

    private void uploadImage(Uri imageUri) {
        File file = new File(Objects.requireNonNull(RealPathUtil.getRealPath(getApplicationContext(), imageUri)));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), ACCESS_TOKEN);
        mService.uploadImage(UPLOAD_URL, token, image).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                if (response.isSuccessful()) {
                    loadImages();
                    mImageAdapter.notifyDataSetChanged();
                    Toast.makeText(RestApiActivity.this, R.string.uploadCompleted, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                Toast.makeText(RestApiActivity.this, R.string.uploadFailed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, getString(R.string.title), null);
        return Uri.parse(path);
    }

    private void setUpApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit getImagesRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        mService = getImagesRetrofit.create(SOService.class);
    }
}
