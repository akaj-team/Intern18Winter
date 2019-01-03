package asiantech.internship.summer.retrofit;

import android.Manifest;
import android.app.ProgressDialog;
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

public class RetrofitActivity extends AppCompatActivity {
    private static final int CHOOSE_GALLERY = 0;
    private static final int CHOOSE_CAMERA = 1;
    private static final int GALLERY = 111;
    private static final int CAMERA = 222;
    private static final String ACCESS_TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476";
    private static final String UPLOAD_URL = "https://upload.gyazo.com/api/upload";
    private int mActionChangeAvatar;
    private RetrofitAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;
    private List<ImageItem> mImageItems;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        mImageItems = new ArrayList<>();
        mService = RetrofitClient.getClient().create(SOService.class);
        mRecyclerView = findViewById(R.id.recyclerViewContent);
        Button btnInsert = findViewById(R.id.btnInsertImage);
        initRecyclerView();
        loadData();
        btnInsert.setOnClickListener(v -> eventHandle());
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RetrofitAdapter(mImageItems, getApplicationContext());
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
                            mActionChangeAvatar = GALLERY;
                            chooseGallery();
                            break;
                        case CHOOSE_CAMERA:
                            mActionChangeAvatar = CAMERA;
                            chooseCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void chooseGallery() {
        if (checkPermission()) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        }
    }

    private void chooseCamera() {
        if (checkPermission()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY) {
            openGallery(data);
        } else {
            openCamera(data);
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

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && mActionChangeAvatar == CAMERA) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && mActionChangeAvatar == GALLERY) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY);
            return false;
        }
        return true;
    }

    private void uploadImage(Uri imageUri) {
        onProgressbarDialog();
        File file = new File(Objects.requireNonNull(RealPathUtil.getRealPathFromURI_API11to18(getApplicationContext(), imageUri)));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), ACCESS_TOKEN);
        mService.uploadImage(UPLOAD_URL, token, image).enqueue(new Callback<ImageItem>() {
            @Override
            public void onResponse(@NonNull Call<ImageItem> call, @NonNull Response<ImageItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RetrofitActivity.this, R.string.successful, Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
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
        onProgressbarDialog();
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

    private void onProgressbarDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.show();
    }
}
