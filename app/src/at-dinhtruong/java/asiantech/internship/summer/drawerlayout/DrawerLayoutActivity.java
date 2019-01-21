package asiantech.internship.summer.drawerlayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.DrawerItem;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerAdapter.OnItemClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 200;
    private static final int REQUEST_SELECT_PICTURE = 201;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 123;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_GALLERY = 124;
    private int mPositionSelected = -1;
    private static final String CHECK_DO_NOT_ASK_AGAIN = "dontAskAgain";
    private static final String CHECK_CAMERA = "checkCamera";
    private static final String CHECK_GALLERY = "checkGallery";
    private List<DrawerItem> mDrawerItems;
    private DrawerAdapter mAdapterItem;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayoutDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initView();
        initDrawer();
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        RecyclerView recycleViewItem = findViewById(R.id.recycleViewItem);
        mFrameLayoutDrawer = findViewById(R.id.framLayoutDrawer);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        ViewGroup.LayoutParams params = recycleViewItem.getLayoutParams();
        params.width = width * 5 / 6;
        recycleViewItem.setLayoutParams(params);
        recycleViewItem.setHasFixedSize(true);
        mDrawerItems = createItem();
        recycleViewItem.setLayoutManager(new LinearLayoutManager(this));
        mAdapterItem = new DrawerAdapter(mDrawerItems, this);
        recycleViewItem.setAdapter(mAdapterItem);
    }

    private void initDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        super.onDrawerSlide(drawerView, slideOffset);
                        float slideX = drawerView.getWidth() * slideOffset;
                        mFrameLayoutDrawer.setTranslationX(slideX);
                    }
                };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public void onAvatarClicked() {
        final CharSequence[] itemsDialog = {getString(R.string.takePhoto), getString(R.string.chooseFromLibrary), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(DrawerLayoutActivity.this);
        builder.setTitle(R.string.addPhoto);
        builder.setItems(itemsDialog, (dialog, item) -> {
            if (itemsDialog[item].equals(getString(R.string.takePhoto))) {
                if (!checkAndRequestCameraPermission()) {
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.permissionAccepted), Toast.LENGTH_SHORT).show();
                } else {
                    openCamera();
                }
            } else if (itemsDialog[item].equals(getString(R.string.chooseFromLibrary))) {
                if (!checkAndRequestGalleryPermission()) {
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.permissionAccepted), Toast.LENGTH_SHORT).show();
                } else {
                    openGallery();
                }
            } else if (itemsDialog[item].equals(getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onItemClicked(int position) {
        if (mPositionSelected != -1) {
            mDrawerItems.get(mPositionSelected - 1).setChecked(false);
            mAdapterItem.notifyItemChanged(mPositionSelected);
        }
        mPositionSelected = position;
        mDrawerItems.get(mPositionSelected - 1).setChecked(true);
        mAdapterItem.notifyItemChanged(mPositionSelected);
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

    private boolean checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DrawerLayoutActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
            return false;
        }
        return true;
    }

    private boolean checkAndRequestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(DrawerLayoutActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS_GALLERY);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SharedPreferences sharedPreferences = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE);
        boolean isCheckGallery;
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    boolean isCheckCamera = sharedPreferences.getBoolean(CHECK_CAMERA, false);
                    isCheckGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false);
                    boolean showRationale = false;
                    boolean showRationaleWrite = false;
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
                    if (!showRationale && !showRationaleWrite) {
                        if (isCheckCamera && isCheckGallery) {
                            onPermissionDialog();
                        }
                        editor.putBoolean(CHECK_CAMERA, true);
                        editor.putBoolean(CHECK_GALLERY, true);
                    } else if (!showRationale) {
                        editor.putBoolean(CHECK_CAMERA, true);
                    } else if (!showRationaleWrite) {
                        editor.putBoolean(CHECK_GALLERY, true);
                    }
                    editor.apply();
                }
                break;
            }
            case REQUEST_CODE_ASK_PERMISSIONS_GALLERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    isCheckGallery = sharedPreferences.getBoolean(CHECK_GALLERY, false);
                    boolean showRationaleWrite = false;
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        showRationaleWrite = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    if (!showRationaleWrite) {
                        if (isCheckGallery) {
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
        mAdapterItem.setSetImageUri(selectedImageURI);
        mAdapterItem.notifyItemChanged(0);
    }

    private void onCaptureImageResult(Intent data) {
        Bundle getExtrasImage = data.getExtras();
        if (getExtrasImage != null) {
            Bitmap imageBitmap = (Bitmap) (getExtrasImage).get(getString(R.string.data));
            Uri uriImage = null;
            if (imageBitmap != null) {
                uriImage = getImageUri(getApplicationContext(), imageBitmap);
            }
            mAdapterItem.setSetImageUri(uriImage);
            mAdapterItem.notifyItemChanged(0);
        }
    }

    private List<DrawerItem> createItem() {
        List<DrawerItem> items = new ArrayList<>();
        items.add(new DrawerItem(R.drawable.bg_inbox, getString(R.string.inbox), false));
        items.add(new DrawerItem(R.drawable.bg_outbox, getString(R.string.outbox), false));
        items.add(new DrawerItem(R.drawable.bg_trash, getString(R.string.trash), false));
        items.add(new DrawerItem(R.drawable.bg_spam, getString(R.string.spam), false));
        return items;
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, getString(R.string.title), null);
        return Uri.parse(path);
    }

    private void onPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.acceptDialog);
        builder.setMessage(R.string.youHaveDeniedPermission);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancle, (dialogInterface, i) -> Toast.makeText(DrawerLayoutActivity.this, R.string.denied, Toast.LENGTH_SHORT).show());
        builder.setPositiveButton(R.string.setting, (dialogInterface, i) -> {
            SharedPreferences.Editor editor = getSharedPreferences(CHECK_DO_NOT_ASK_AGAIN, MODE_PRIVATE).edit();
            editor.putBoolean(CHECK_CAMERA, false);
            editor.putBoolean(CHECK_GALLERY, false);
            editor.apply();
            dialogInterface.dismiss();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
            intent.setData(uri);
            getApplicationContext().startActivity(intent);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
