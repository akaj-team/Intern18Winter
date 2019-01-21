package asiantech.internship.summer.restapi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
    private static final int WRITE_GALLERY_PERMISSION_REQUEST_CODE = 201;
    private static final int WRITE_CAMERA_PERMISSION_REQUEST_CODE = 202;
    private static final String CHECK_DO_NOT_ASK_AGAIN = "dontAskAgain";
    private static final String CHECK_CAMERA = "checkCamera";
    private static final String CHECK_GALLERY = "checkGallery";

    private APIImages mAPIImages;
    private RecyclerView mRecyclerViewImage;
    private ProgressDialog mProgressDialog;
    private ListImageAdapter mImageAdapter;
    private List<ImageItem> mImages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initView();
        initRecyclerView();
        initApi();
    }

    private void initView() {
        Button mBtnLoadImage = findViewById(R.id.btnLoadImage);
        Button mBtnUploadImage = findViewById(R.id.btnUploadImage);
        mRecyclerViewImage = findViewById(R.id.recyclerViewRestful);

        mBtnLoadImage.setOnClickListener(this);
        mBtnUploadImage.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecyclerViewImage.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewImage.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ListImageAdapter(mImages);
        mRecyclerViewImage.setAdapter(mImageAdapter);
    }

    private void initApi() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit getImagesRetrofit = new Retrofit.Builder()
                .baseUrl(APIImages.LOAD_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mAPIImages = getImagesRetrofit.create(APIImages.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoadImage: {
                loadListImages();
                break;
            }
            case R.id.btnUploadImage: {
                showImageOptionsDialog();
                break;
            }
        }
    }

    private void showImageOptionsDialog() {
        AlertDialog.Builder imagesDialog = new AlertDialog.Builder(this);
        imagesDialog.setTitle(R.string.selectAction);
        String imagesDialogItems[] = {
                getString(R.string.chooseGallery),
                getString(R.string.captureCamera),
                getString(R.string.cancelAction)
        };
        imagesDialog.setItems(imagesDialogItems, (dialogInterface, option) -> {
            switch (option) {
                case CHOOSE_GALLERY: {
                    if (checkAndRequestGalleryPermission()) {
                        choosePhotosFromGallery();
                        Toast.makeText(this, R.string.galleryPermissionAccepted, Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case CAPTURE_CAMERA: {
                    if (checkAndRequestCameraPermission()) {
                        capturePhotosFromCamera();
                        Toast.makeText(this, R.string.cameraPermissionAccepted, Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case CANCEL_ACTION:
                    dialogInterface.dismiss();
            }
        });
        imagesDialog.show();
    }

    private void choosePhotosFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void capturePhotosFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    private boolean checkAndRequestCameraPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, WRITE_CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean checkAndRequestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RestAPIActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_GALLERY_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        SharedPreferences sharedPreferences = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE);
        boolean isCheckGallery;

        switch (requestCode) {
            case WRITE_GALLERY_PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePhotosFromGallery();
                    Toast.makeText(this, R.string.galleryPermissionAccepted, Toast.LENGTH_LONG).show();
                } else {
                    isCheckGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false);
                    boolean isShowRationaleWrite = false;

                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        isShowRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    if (!isShowRationaleWrite) {
                        if (isCheckGallery) {
                            showAlertDialog(getString(R.string.permissionMessageGallery));
                            Toast.makeText(this, R.string.noPermissionsGranted, Toast.LENGTH_LONG).show();
                        }
                        SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
                        editor.putBoolean(CHECK_GALLERY, true);
                        editor.apply();
                    }
                }
                break;
            }
            case WRITE_CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    capturePhotosFromCamera();
                    Toast.makeText(this, R.string.cameraPermissionAccepted, Toast.LENGTH_LONG).show();
                } else {
                    boolean isCheckCamera = sharedPreferences.getBoolean(CHECK_CAMERA, false);
                    boolean isShowCameraPermissionRationale = false;
                    boolean isShowWritePermissionRationale = false;
                    isCheckGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false);

                    if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        isShowCameraPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
                        isShowWritePermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
                    if (!isShowCameraPermissionRationale && !isShowWritePermissionRationale) {
                        if (isCheckCamera && isCheckGallery) {
                            showAlertDialog(getString(R.string.permissionMessageCamera));
                            Toast.makeText(this, R.string.noPermissionsGranted, Toast.LENGTH_LONG).show();
                        }
                        editor.putBoolean(CHECK_CAMERA, true);
                        editor.putBoolean(CHECK_GALLERY, true);
                    } else if (!isShowCameraPermissionRationale) {
                        editor.putBoolean(CHECK_CAMERA, true);
                    } else if (!isShowWritePermissionRationale) {
                        editor.putBoolean(CHECK_GALLERY, true);
                    }
                    editor.apply();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(extras).get(getString(R.string.data));
                if (imageBitmap != null) {
                    final Uri ImageUri = getCameraImageUri(imageBitmap);
                    uploadImage(ImageUri);
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
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        cursor.close();
        return path;
    }

    private Uri getCameraImageUri(Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, getString(R.string.title), null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri) {
        showProgressbarDialog();
        File file = new File(Objects.requireNonNull(getRealPathImage(imageUri)));

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), APIImages.TOKEN);
        mAPIImages.uploadImage(APIImages.UPLOAD_URL, token, image).enqueue(new Callback<ImageItem>() {
            @Override
            public void onResponse(@NonNull Call<ImageItem> call, @NonNull Response<ImageItem> response) {
                if (response.isSuccessful()) {
                    mImages.add(0, response.body());
                    mImageAdapter.notifyItemInserted(0);
                    mProgressDialog.dismiss();
                    Toast.makeText(RestAPIActivity.this, getString(R.string.uploadSuccess), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageItem> call, @NonNull Throwable t) {
                Toast.makeText(RestAPIActivity.this, getString(R.string.uploadFailed), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void loadListImages() {
        showProgressbarDialog();
        mAPIImages.getImages(APIImages.TOKEN).enqueue(new Callback<List<ImageItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImageItem>> call,
                                   @NonNull Response<List<ImageItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (ImageItem imageItem : response.body()) {
                        if (!imageItem.getImageId().isEmpty()) {
                            mImages.add(imageItem);
                        }
                    }
                    mImageAdapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageItem>> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RestAPIActivity.this, R.string.getImageFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.LoadImagesDialogMessage));
        mProgressDialog.show();
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alert = builder.create();
        alert.setTitle(R.string.permissionNeeded);
        alert.setMessage(getString(R.string.permissionsDialogMessageFirstHalf) + " "
                + message + " " + getString(R.string.permissionsDialogMessageSecondHalf) + " "
                + getString(R.string.directionToSettings));
        alert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.gotoSettings), (dialogInterface, positive) -> goToSetting(dialogInterface));
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.backToApp), (dialogInterface, negative) -> dialogInterface.dismiss());
        alert.setCancelable(true);
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorDarkGreen));
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorDarkGreen));
    }

    private void goToSetting(DialogInterface dialogInterface) {
        SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
        editor.putBoolean(CHECK_CAMERA, false);
        editor.putBoolean(CHECK_GALLERY, false);
        editor.apply();
        dialogInterface.dismiss();
        Intent intentToSettings = new Intent();
        intentToSettings.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
        intentToSettings.setData(uri);
        getApplicationContext().startActivity(intentToSettings);
    }
}
