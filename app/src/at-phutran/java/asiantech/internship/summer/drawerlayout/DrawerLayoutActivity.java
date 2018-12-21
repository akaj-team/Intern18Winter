package asiantech.internship.summer.drawerlayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.DrawerItem;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerLayoutAdapter.OnItemClickListener {

    private static final int GALLERY = 111;
    private static final int CHOOSE_GALLERY = 0;
    private static final int CAMERA = 222;
    private static final int CHOOSE_CAMERA = 1;
    protected RecyclerView mRecyclerViewDrawer;
    private TextView mTvContent;
    private List<DrawerItem> mData;
    private DrawerLayoutAdapter mDrawerLayoutAdapter;
    private int mPosition = -1;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        mRecyclerViewDrawer = findViewById(R.id.recyclerViewContent);
        //set width for drawer layout
        RecyclerView mLayout = findViewById(R.id.recyclerViewContent);
        ViewGroup.LayoutParams params = mLayout.getLayoutParams();
        params.width = 4 * getWithScreen() / 5;
        mLayout.setLayoutParams(params);
        //Move content to side in Drawer Layout
        slide();
        initData();
        initView();

    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add(new DrawerItem(R.drawable.img_avatar, getString(R.string.tranthithuthao), false));
        mData.add(new DrawerItem(R.drawable.ic_move_to_inbox_black_24dp, getString(R.string.inbox), false));
        mData.add(new DrawerItem(R.drawable.ic_send_black_24dp, getString(R.string.outbox), false));
        mData.add(new DrawerItem(R.drawable.ic_delete_black_24dp, getString(R.string.trash), false));
        mData.add(new DrawerItem(R.drawable.ic_error_black_24dp, getString(R.string.spam), false));
    }
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrawerLayoutActivity.this);
        mRecyclerViewDrawer.setLayoutManager(linearLayoutManager);
        mRecyclerViewDrawer.setHasFixedSize(true);
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(mData, this);
        mRecyclerViewDrawer.setAdapter(mDrawerLayoutAdapter);
    }
    private void slide(){
        DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        mTvContent = findViewById(R.id.tvContent);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.anim.slide_in_left, R.anim.slide_in_right) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                mTvContent.setTranslationX(slideX);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private int getWithScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        return size.x;
    }

    @Override
    public void onAvatarClicked() {
        requestMultiplePermissions();
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.action);
        String[] pictureDialogItems = {
                getString(R.string.gallery),
                getString(R.string.camera)};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case CHOOSE_GALLERY:
                            choosePhotoFromGallary();
                            break;
                        case CHOOSE_CAMERA:
                            takePhotoFromCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
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
        DrawerItem drawerItem = mData.get(0);
        Uri contentURI = intent.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            drawerItem.setAvatarBitmap(bitmap);
            mDrawerLayoutAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }

    }

    private void openCamera(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(extras).get(getString(R.string.data));
        Toast.makeText(DrawerLayoutActivity.this, R.string.save, Toast.LENGTH_SHORT).show();
        DrawerItem item = mData.get(0);
        item.setAvatarBitmap(imageBitmap);
        mDrawerLayoutAdapter.notifyItemChanged(0);
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), R.string.permission, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    @Override
    public void onItemChecked(int i) {
        if (mPosition >= 0) {
            mData.get(mPosition).setChecked(false);
            mDrawerLayoutAdapter.notifyItemChanged(mPosition);
        }
        mData.get(i).setChecked(true);
        mPosition = i;
        mDrawerLayoutAdapter.notifyItemChanged(i);
    }
}
