package asiantech.internship.summer.drawerlayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
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
import asiantech.internship.summer.drawerlayout.model.DrawerItem;


public class DrawerLayoutActivity extends AppCompatActivity implements DrawerAdapter.OnItemClickListener {
    private static final int GALLERY = 101;
    private static final int CAMERA = 102;
    private static final int CHOOSE_GALLERY = 0;
    private static final int CAPTURE_CAMERA = 1;
    private int mPositionSelected = -1;
    private List<DrawerItem> mItems;
    private DrawerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private TextView mTvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initView();
        initDrawer();
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mTvName = findViewById(R.id.tvName);
        mockData();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHeader);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrawerLayoutActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter(mItems, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void initDrawer() {
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigationDrawerOpen, R.string.navigationDrawerClose) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                mTvName.setTranslationX(slideX);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }


    private void mockData() {
        mItems = new ArrayList<>();
        mItems.add(new DrawerItem(R.drawable.img_avatar_default, getString(R.string.drawerLayoutEmail), false));
        mItems.add(new DrawerItem(R.drawable.bg_ic_inbox, getString(R.string.inbox), false));
        mItems.add(new DrawerItem(R.drawable.bg_ic_outbox, getString(R.string.outbox), false));
        mItems.add(new DrawerItem(R.drawable.bg_ic_trash, getString(R.string.trash), false));
        mItems.add(new DrawerItem(R.drawable.bg_ic_warning, getString(R.string.spam), false));
    }

    @Override
    public void onAvatarClicked() {
        showPictureDialog();
    }

    @Override
    public void onItemClicked(int position) {
        if (mPositionSelected != -1) {
            mItems.get(mPositionSelected).setIsSelected(false);
            mAdapter.notifyItemChanged(mPositionSelected);
        }
        mPositionSelected = position;
        mItems.get(position).setIsSelected(true);
        mAdapter.notifyItemChanged(position);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.selectAction);
        String pictureDialogItems[] = {
                getString(R.string.selectGallery),
                getString(R.string.captureCamera)
        };
        pictureDialog.setItems(pictureDialogItems, (dialogInterface, position) -> {
            switch (position) {
                case CHOOSE_GALLERY:
                    choosePhotosFromGallery();
                    break;
                case CAPTURE_CAMERA:
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
        DrawerItem item = mItems.get(0);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap galleryBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    item.setAvatarBitmap(galleryBitmap);
                    mAdapter.notifyItemChanged(0);
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.imageSaved), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.saveImageFailed), Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap cameraBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get(getString(R.string.data));
            item.setAvatarBitmap(cameraBitmap);
            saveImage(cameraBitmap);
            mAdapter.notifyItemChanged(0);
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
}
