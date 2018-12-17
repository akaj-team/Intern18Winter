package asiantech.internship.summer.drawerlayout;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.model.Item;


public class DrawerLayoutActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener {
    //private static final String TAG = DrawerLayoutActivity.class.getSimpleName();
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    private List<Item> mItems;
    private RecyclerAdapter mRecyclerAdapter;
    private int positionSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        //requestMultiplePermission();
        initView();
    }

    private void initView() {
        mockData();
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerViewHeader);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrawerLayoutActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mRecyclerAdapter = new RecyclerAdapter(mItems, this);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    private void mockData() {
        mItems = new ArrayList<>();
        mItems.add(new Item(R.drawable.img_avatar_default, getString(R.string.drawerLayoutEmail), false));
        mItems.add(new Item(R.drawable.bg_inbox, getString(R.string.inbox), false));
        mItems.add(new Item(R.drawable.bg_outbox, getString(R.string.outbox), false));
        mItems.add(new Item(R.drawable.bg_trash, getString(R.string.trash), false));
        mItems.add(new Item(R.drawable.bg_warning, getString(R.string.spam), false));
    }

    @Override
    public void onClickAvatar() {
        showPictureDialog();
    }

    @Override
    public void onClickItem(int position) {
        if (positionSelected != -1) {
            mItems.get(position).setIsSelected(false);
            mRecyclerAdapter.notifyDataSetChanged();
        }
        Log.d("xxx", "onClickItem: "+position);
        positionSelected = position;
        mItems.get(position).setIsSelected(true);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.selectAction);
        String pictureDIalogItems[] = {
                getString(R.string.selectGallery),
                getString(R.string.captureCamera)
        };
        pictureDialog.setItems(pictureDIalogItems, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    choosePhotosFromGallery();
                    break;
                case 1:
                    capturePhotosFromCamera();
                    break;
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
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //String path = saveImage(bitmap);
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.imageSaved), Toast.LENGTH_LONG).show();
                    mImgAvatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.saveImageFailed), Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get(getString(R.string.data));
            mImgAvatar.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(DrawerLayoutActivity.this, R.string.imageSaved, Toast.LENGTH_LONG).show();
        }
    }

    public void saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + getString(R.string.imageDirectory));
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + getString(R.string.imageExtension));
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{getString(R.string.imagePathPNG)}, null);
            fo.close();
            f.getAbsolutePath();
        } catch (IOException ignored) {
        }
    }

//    private void requestMultiplePermission() {
//        Dexter.withActivity(this)
//                .withPermissions(
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        //Check if all permissions are granted
////                        if (report.areAllPermissionsGranted()) {
////                            Toast.makeText(getApplicationContext(), "All permissions are granted by user", Toast.LENGTH_LONG).show();
////                        }
////                        //Check for permanent denial of any permission
////                        if (report.isAnyPermissionPermanentlyDenied()) {
////                            // show alert dialog navigating to Settings
////                            //openSettingsDialog();
////                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), getString(R.string.notifyError), Toast.LENGTH_LONG).show()).onSameThread().check();
//    }
}
