package asiantech.internship.summer.drawerlayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.model.Item;


public class DrawerLayoutActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener {
    private static final String TAG = DrawerLayoutActivity.class.getSimpleName();
    private static final String IMAGE_DIRECTORY = "Avatar Selection";
    private RecyclerView recyclerView;
    private int GALLERY = 1;
    private int CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestMultiplePermission();
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.add_header);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrawerLayoutActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerAdapter recyclerViewAdapter = new RecyclerAdapter(addData(), this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private List<Item> addData() {
        List<Item> data = new ArrayList<Item>();
        data.add(new Item(R.drawable.img_avatar_1, getString(R.string.email)));
        data.add(new Item(R.drawable.ic_move_to_inbox_black_36dp, getString(R.string.inbox)));
        data.add(new Item(R.drawable.ic_send_black_36dp, getString(R.string.outbox)));
        data.add(new Item(R.drawable.ic_delete_black_36dp, getString(R.string.trash)));
        data.add(new Item(R.drawable.ic_warning_black_36dp, getString(R.string.spam)));
        return data;
    }

    @Override
    public void onClickAvatar() {
        showPictureDialog();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String pictureDIalogItems[] = {
                "Select a photo from gallery",
                "Capture a photo from camera"
        };
        pictureDialog.setItems(pictureDIalogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        choosePhotosFromGallery();
                        break;
                    case 1:
                        capturePhotosFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    public void choosePhotosFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    public void capturePhotosFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView mImgAvatar;
        mImgAvatar = findViewById(R.id.imgAvatar);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(DrawerLayoutActivity.this, "Image Saved", Toast.LENGTH_LONG).show();
                    mImgAvatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(DrawerLayoutActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mImgAvatar.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(DrawerLayoutActivity.this, "Image Saved", Toast.LENGTH_LONG).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + "png");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e) {
        }
        return "";
    }

    private void requestMultiplePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        //Check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user", Toast.LENGTH_LONG).show();
                        }
                        //Check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "There is some error!", Toast.LENGTH_LONG).show();
            }
        }).onSameThread().check();
    }
}
