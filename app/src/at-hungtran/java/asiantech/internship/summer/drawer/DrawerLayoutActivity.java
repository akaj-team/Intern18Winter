package asiantech.internship.summer.drawer;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.DrawerItem;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerAdapter.OnItemClickListener, ChoosePhotoDialogFragment.OnItemClickListener {
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int READ_PERMISSION_REQUEST_CAMERA = 1111;
    private static final int WRITE_PERMISSION_REQUEST_CAMERA = 1112;
    private static final int READ_PERMISSION_REQUEST_GALLERY = 1113;
    private static final int WRITE_PERMISSION_REQUEST_CALLERY = 11114;
    List<DrawerItem> mDrawerItem = new ArrayList<>();
    private ChoosePhotoDialogFragment mDialogFragment;
    private DrawerAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private int positionSelected = -1;
    private DrawerLayout mDrawerLayout;
    private TextView mTvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initView();
        initDrawer();
    }

    private int displayWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.rvDrawer);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mTvName = findViewById(R.id.tvName);
        params.width = 5 * displayWidth() / 6;
        recyclerView.setLayoutParams(params);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        mAdapter = new DrawerAdapter(mDrawerItem, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mDrawerItem.add(new DrawerItem(R.drawable.img_avt, getString(R.string.gmail), R.drawable.ic_arrow_drop_down_black_24dp));

        mDrawerItem.add(new DrawerItem(R.drawable.bg_inbox, getString(R.string.inbox)));
        mDrawerItem.add(new DrawerItem(R.drawable.bg_outbox, getString(R.string.outbox)));
        mDrawerItem.add(new DrawerItem(R.drawable.bg_delete, getString(R.string.trash)));
        mDrawerItem.add(new DrawerItem(R.drawable.bg_spam, getString(R.string.spam)));
    }


    @Override
    public void onAvatarClicked() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mDialogFragment = ChoosePhotoDialogFragment.newInstance();
        mDialogFragment.setOnHeadlineSelectedListener(this);
        mDialogFragment.show(ft, getString(R.string.dialog));
    }


    private void openGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, GALLERY_REQUEST);
        mDialogFragment.dismiss();
    }

    private void takePicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, CAMERA_REQUEST);
        mDialogFragment.dismiss();
    }

    @Override
    public void onChooseImage(int type) {
        if (type == ChoosePhotoDialogFragment.CHOOSE_GALLERY) {
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
        if (type == ChoosePhotoDialogFragment.CHOOSE_CAMERA) {
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
            takePicture();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Bitmap image = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    DrawerItem drawerItem = mDrawerItem.get(0);
                    drawerItem.setAvtBitmap(image);
                    drawerItem.setAvatarUri(null);
                    mAdapter.notifyItemChanged(0);
                }

                break;
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    mDrawerItem.get(0).setAvatarUri(selectedImage);
                    mAdapter.notifyItemChanged(0);
                }
                break;
        }
    }

    public void initDrawer() {
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

    @Override
    public void onItemClicked(int position) {
        if (positionSelected != -1) {
            mDrawerItem.get(positionSelected).setChecked(false);
            mAdapter.notifyItemChanged(positionSelected);
        }
        positionSelected = position;
        mDrawerItem.get(position).setChecked(true);
        mAdapter.notifyItemChanged(positionSelected);
    }
}
