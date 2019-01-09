package asiantech.internship.summer.restapi;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
    private static final int PAGE = 1;
    private static final int PERPAGE = 20;
    private static final String CHECK_DO_NOT_ASK_AGAIN = "dontAskAgain";
    private static final String CHECK_CAMERA = "checkCamera";
    private static final String CHECK_GALLERY = "checkGallery";
    private static final String ACCESS_TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476";
    private static final String BASE_URL = "https://api.gyazo.com/api/";
    private static final String UPLOAD_URL = "https://upload.gyazo.com/api/upload";
    private SOService mService;
    private List<Image> mImages;
    private ImageAdapter mImageAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initViews();
        initRecyclerView();
    }

    private void initViews() {
        setUpApi();
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mImages = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ImageAdapter(mImages);
        recyclerView.setAdapter(mImageAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        loadImages();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCamera: {
                if (!checkAndRequestCameraPermission()) {
                    Toast.makeText(RestApiActivity.this, R.string.checkPermission, Toast.LENGTH_SHORT).show();
                } else {
                    openCamera();
                }
                break;
            }
            case R.id.btnGallery: {
                if (!checkAndRequestGalleryPermission()) {
                    Toast.makeText(RestApiActivity.this, R.string.checkPermission, Toast.LENGTH_SHORT).show();
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
        if (checkAndRequestCameraPermission()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        if (checkAndRequestGalleryPermission()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.selectPicture)), REQUEST_SELECT_PICTURE);
        }
    }

    private boolean checkAndRequestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(RestApiActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS_GALLERY);
            return false;
        }
        return true;
    }

    private boolean checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RestApiActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SharedPreferences sharedPreferences = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    boolean isCheckedCamera = sharedPreferences.getBoolean(CHECK_CAMERA, false);
                    boolean showRationale = false;
                    boolean showRationaleWrite = false;
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[1]);
                    }
                    if (!showRationale && !showRationaleWrite) {
                        if (isCheckedCamera) {
                            onPermissionDialog();
                        }
                        SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
                        editor.putBoolean(CHECK_CAMERA, true);
                        editor.apply();
                    }
                }
                break;
            }
            case REQUEST_CODE_ASK_PERMISSIONS_GALLERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    boolean isCheckedGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false);
                    boolean showRationaleWrite = false;
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
                    }
                    if (!showRationaleWrite) {
                        if (isCheckedGallery) {
                            onPermissionDialog();
                        }
                        SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
                        editor.putBoolean(CHECK_GALLERY, true);
                        editor.apply();
                    }
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

    private void loadImages() {
        onProgressbarDialog();
        mService.getImages(ACCESS_TOKEN, PAGE, PERPAGE).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                if (response.body() != null) {
                    for (Image objImage : response.body()) {
                        if (!objImage.getImageId().isEmpty()) {
                            mImages.add(objImage);
                        }
                    }
                }
                mProgressDialog.dismiss();
                mImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RestApiActivity.this, R.string.loadImagesFailse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(Uri imageUri) {
        onProgressbarDialog();
        File file = new File(Objects.requireNonNull(RealPathUtil.getRealPath(getApplicationContext(), imageUri)));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), ACCESS_TOKEN);
        mService.uploadImage(UPLOAD_URL, token, image).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                if (response.isSuccessful()) {
                    mImages.add(0, response.body());
                    mImageAdapter.notifyItemInserted(0);
                    Toast.makeText(RestApiActivity.this, R.string.uploadCompleted, Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
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

    private void onProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.show();
    }

    private void onPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Accept dialog");
        builder.setMessage("You have denied permission. Allow all permission at [Setting]->[Permission], go to [Setting]");
        builder.setCancelable(false);
        builder.setNegativeButton("Setting", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
            intent.setData(uri);
            getApplicationContext().startActivity(intent);
        });
        builder.setPositiveButton("Cancle", (dialogInterface, i) -> Toast.makeText(RestApiActivity.this, "Không cho phep", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
