package asiantech.internship.summer.restapi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
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
import asiantech.internship.summer.model.ImageItem;
import asiantech.internship.summer.model.QueryImage;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RestAPIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    private static final int READ_PERMISSION_REQUEST_CAMERA = 1111;
    private static final int WRITE_PERMISSION_REQUEST_CAMERA = 1112;
    private static final int READ_PERMISSION_REQUEST_GALLERY = 1113;
    private static final int WRITE_PERMISSION_REQUEST_CALLERY = 11114;

    private final CharSequence[] mChoiceOption = {"Camera", "Gallery"};
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
        mBtnUploadImage.setOnClickListener(this);
    }

    private void initRecyclerView() {
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
        switch (v.getId()) {
            case R.id.btnGetImage: {
                getListImages();
                break;
            }
            case R.id.btnUploadImage: {
                showDialogOption();
                break;
            }
        }
    }

    private void showDialogOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.label_api_option))
                .setItems(mChoiceOption, (dialogInterface, i) -> {
                    if (mChoiceOption[i].equals(mChoiceOption[0])) {
                        int permissionCheck = ContextCompat.checkSelfPermission(this,
                                CAMERA);
                        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{CAMERA},
                                    READ_PERMISSION_REQUEST_CAMERA
                            );
                            return;
                        }

                        int permissionWrite = ContextCompat.checkSelfPermission(this,
                                WRITE_EXTERNAL_STORAGE);
                        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{WRITE_EXTERNAL_STORAGE},
                                    WRITE_PERMISSION_REQUEST_CAMERA
                            );
                            return;
                        }
                        openCamera();
                    } else if (mChoiceOption[i].equals(mChoiceOption[1])) {
                        int permissionWrite = ContextCompat.checkSelfPermission(this,
                                WRITE_EXTERNAL_STORAGE);
                        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{WRITE_EXTERNAL_STORAGE},
                                    WRITE_PERMISSION_REQUEST_CALLERY
                            );
                            return;
                        }
                        openGallery();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void openGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, REQUEST_GALLERY);
    }

    private void openCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) Objects.requireNonNull(extras).get("data");
                if (photo != null) {
                    final Uri photoUri = getUriImageCamera(photo);
                    uploadImage(photoUri);
                }
            } else if (requestCode == REQUEST_GALLERY) {
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
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri) {
        File file = new File(Objects.requireNonNull(getRealPathImage(imageUri)));

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("imagedata", file.getName(), requestBody);

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), APIImages.TOKEN);
        mAPIImages.uploadImage(APIImages.UPLOAD_URL, token, image).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RestAPIActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                Toast.makeText(RestAPIActivity.this, "Upload failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListImages() {
        addProgressbarDialog();
        mAPIImages.getImages(APIImages.TOKEN, mCurrentPage, APIImages.PER_PAGE)
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
