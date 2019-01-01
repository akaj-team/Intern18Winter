package asiantech.internship.summer.restapi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import asiantech.internship.summer.restapi.model.ImageItem;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY = 101;
    private static final int CAMERA = 102;
    private static final int CHOOSE_GALLERY = 0;
    private static final int CAPTURE_CAMERA = 1;
    private static final int CANCEL_ACTION = 99;
    private static final int GALLERY_PERMISSION_REQUEST_CODE = 201;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 202;


    private final CharSequence[] mChoiceOption = {"Camera", "Gallery"};
    private APIImages mAPIImages;
    private boolean mIsLoading;
    private int mCurrentPage;
    private int mLastQueryImageNumber;
    private List<ImageItem> mImages = new ArrayList<>();
    private RecyclerView mRecyclerViewImage;
    private ProgressDialog mProgressDialog;
    private ListImageAdapter mImageAdapter;
    private int mActionChangeAvatar = 79;

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
        mBtnUploadImage.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecyclerViewImage.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewImage.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ListImageAdapter(mImages, this);
        mRecyclerViewImage.setAdapter(mImageAdapter);
    }

    private void setUpApi() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit getImagesRetrofit = new Retrofit.Builder()
                .baseUrl(APIImages.LOAD_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mAPIImages = getImagesRetrofit.create(APIImages.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetImage: {
                loadListImages();
                break;
            }
            case R.id.btnUploadImage: {
                showPictureDialog();
                break;
            }
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.selectAction);
        String pictureDialogItems[] = {
                getString(R.string.chooseGallery),
                getString(R.string.captureCamera),
                getString(R.string.cancelAction)
        };
        pictureDialog.setItems(pictureDialogItems, (dialogInterface, position) -> {
            switch (position) {
                case CHOOSE_GALLERY: {
                    mActionChangeAvatar = 80;
                    if (requestPermission()) {
                        choosePhotosFromGallery();
                    }
                    break;
                }
                case CAPTURE_CAMERA: {
                    mActionChangeAvatar = 81;
                    if (requestPermission()) {
                        CapturePhotosFromCamera();
                    }
                    break;
                }
                case CANCEL_ACTION:
                    dialogInterface.dismiss();
            }
        });
        pictureDialog.show();
    }

    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && mActionChangeAvatar == 80) {
            ActivityCompat.requestPermissions(RestAPIActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST_CODE);
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && mActionChangeAvatar == 81) {
            ActivityCompat.requestPermissions(RestAPIActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GALLERY_PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePhotosFromGallery();
                    Toast.makeText(RestAPIActivity.this, R.string.galleryPermissionAccepted, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RestAPIActivity.this, R.string.galleryPermissionDenied, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CapturePhotosFromCamera();
                    Toast.makeText(RestAPIActivity.this, R.string.cameraPermissionAccepted, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RestAPIActivity.this, R.string.cameraPermissionDenied, Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void choosePhotosFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, GALLERY);
    }

    private void CapturePhotosFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) Objects.requireNonNull(extras).get(getString(R.string.data));
                if (photo != null) {
                    final Uri photoUri = getUriImageCamera(photo);
                    uploadImage(photoUri);
                }
            } else if (requestCode == GALLERY) {
                final Uri imageUri = data.getData();
                uploadImage(imageUri);
            }
        }
    }

    private String getRealPathImage(Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    private Uri getUriImageCamera(Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, getString(R.string.title), null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri) {
        File file = new File(Objects.requireNonNull(getRealPathImage(imageUri)));

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), APIImages.TOKEN);
        mAPIImages.uploadImage(APIImages.UPLOAD_URL, token, image).enqueue(new Callback<ImageItem>() {
            @Override
            public void onResponse(@NonNull Call<ImageItem> call, @NonNull Response<ImageItem> response) {
                if (response.isSuccessful()) {
                    mImageAdapter.notifyItemInserted(0);
                    Toast.makeText(RestAPIActivity.this, getString(R.string.uploadSuccess), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageItem> call, @NonNull Throwable t) {
                Toast.makeText(RestAPIActivity.this, getString(R.string.uploadFailed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadListImages() {
        addProgressbarDialog();
        mAPIImages.getImages(APIImages.TOKEN, mCurrentPage).enqueue(new Callback<List<ImageItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImageItem>> call,
                                   @NonNull Response<List<ImageItem>> response) {
                if (response.isSuccessful()) {
                    List<ImageItem> addImages = response.body();
                    if (addImages != null) {
                        mImages.addAll(addImages);
                        mImageAdapter.notifyDataSetChanged();
                        mLastQueryImageNumber = addImages.size();
                        mCurrentPage++;
                        mIsLoading = false;
                        mProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageItem>> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RestAPIActivity.this, R.string.getImageFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.LoadImagesDialogMessage));
        mProgressDialog.show();
    }
}
