package asiantech.internship.summer.retrofit;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.ImageItem;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class RetrofitActivity extends AppCompatActivity {
    private static final String MY_PREFS_NAME = "MyFile";
    private static final int CHOOSE_GALLERY = 0;
    private static final int CHOOSE_CAMERA = 1;
    private static final int GALLERY = 111;
    private static final int CAMERA = 222;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 333;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_GALLERY = 444;
    private static final String ACCESS_TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476";
    private static final String UPLOAD_URL = "https://upload.gyazo.com/api/upload";
    private SharedPreferences isSharedPreferences;
    private RetrofitAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;
    private List<ImageItem> mImageItems;
    private ProgressDialog mProgressDialog;

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        mImageItems = new ArrayList<>();
        mService = RetrofitClient.getClient().create(SOService.class);
        mRecyclerView = findViewById(R.id.recyclerViewContent);
        Button btnInsert = findViewById(R.id.btnInsertImage);
        isSharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        initRecyclerView();
        loadData();
        btnInsert.setOnClickListener(v -> eventHandle());
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RetrofitAdapter(mImageItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void eventHandle() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getString(R.string.action));
        String[] pictureDialogItems = {getString(R.string.gallery), getString(R.string.camera)};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case CHOOSE_GALLERY:
                            if (checkPermissionForGallery()) {
                                chooseGallery();
                            }
                            break;
                        case CHOOSE_CAMERA:
                            if (checkPermissionForCamera()) {
                                chooseCamera();
                            }
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void chooseGallery() {
        if (checkPermissionForGallery()) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        }
    }

    private void chooseCamera() {
        if (checkPermissionForCamera()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isNotAllowAskWriteExternal;
        boolean isNotAllowAskCamera;
        boolean isShowRationaleWrite;
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    chooseCamera();
                } else {
                    boolean isShowRationaleCamera = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
                    isShowRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    isNotAllowAskWriteExternal = getValueShowInSharedPreferences(getString(R.string.isWrite));
                    isNotAllowAskCamera = getValueShowInSharedPreferences(getString(R.string.isCamera));
                    if (!isShowRationaleWrite && !isShowRationaleCamera) {
                        if (isNotAllowAskWriteExternal && isNotAllowAskCamera) {
                            showSettingsAlert(getString(R.string.noteCamera));
                        }
                    }
                    if (!isShowRationaleWrite) {
                        saveValueShowInSharedPreferences(getString(R.string.isWrite));
                    }
                    if (!isShowRationaleCamera) {
                        saveValueShowInSharedPreferences(getString(R.string.isCamera));
                    }
                }
                break;
            }
            case REQUEST_CODE_ASK_PERMISSIONS_GALLERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseGallery();
                } else {
                    isShowRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    isNotAllowAskWriteExternal = getValueShowInSharedPreferences(getString(R.string.isWrite));
                    if (!isShowRationaleWrite) {
                        if (isNotAllowAskWriteExternal) {
                            showSettingsAlert(getString(R.string.noteGallery));
                        }
                        saveValueShowInSharedPreferences(getString(R.string.isWrite));
                    }
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void saveValueShowInSharedPreferences(String value) {
        SharedPreferences.Editor editor = isSharedPreferences.edit();
        editor.putBoolean(value, true);
        editor.apply();
    }

    private boolean getValueShowInSharedPreferences(String value) {
        return isSharedPreferences.getBoolean(value, false);
    }

    private void showSettingsAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.optionChoose));
        alertDialog.setMessage(getString(R.string.noteAccess) + " " + message);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel),
                (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.setting),
                (dialog, which) -> {
                    dialog.dismiss();
                    startInstalledAppDetailsActivity(RetrofitActivity.this);
                });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                openGallery(data);
            } else {
                openCamera(data);
            }
        }
    }

    private void openGallery(Intent intent) {
        Uri selectedImageURI = intent.getData();
        if (selectedImageURI != null) {
            uploadImage(selectedImageURI);
        }
    }

    private void openCamera(Intent data) {
        Bundle bundle = data.getExtras();
        Bitmap imageBitmap = null;
        if (bundle != null) {
            imageBitmap = (Bitmap) (bundle).get(getString(R.string.data));
        }
        if (imageBitmap != null) {
            uploadImage(getImageUri(getApplicationContext(), imageBitmap));
        }
    }

    private boolean checkPermissionForCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
            return false;
        }
        return true;
    }

    private boolean checkPermissionForGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS_GALLERY);
            return false;
        }
        return true;
    }

    private void uploadImage(Uri imageUri) {
        showProgressbarDialog();
        File file = new File(Objects.requireNonNull(RealPathUtil.getRealPathFromUriAPI11to18(getApplicationContext(), imageUri)));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), ACCESS_TOKEN);
        mService.uploadImage(UPLOAD_URL, token, image).enqueue(new Callback<ImageItem>() {
            @Override
            public void onResponse(@NonNull Call<ImageItem> call, @NonNull Response<ImageItem> response) {
                if (response.isSuccessful()) {
                    mImageItems.add(0, response.body());
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(RetrofitActivity.this, R.string.successful, Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageItem> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RetrofitActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, getString(R.string.titles), null);
        return Uri.parse(path);
    }

    private void loadData() {
        showProgressbarDialog();
        mService.getImages(ACCESS_TOKEN, 1, 20).enqueue(new Callback<List<ImageItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImageItem>> call, @NonNull Response<List<ImageItem>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    for (ImageItem image : response.body()) {
                        if (!image.getImageId().isEmpty()) {
                            mImageItems.add(image);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageItem>> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RetrofitActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.show();
    }
}
