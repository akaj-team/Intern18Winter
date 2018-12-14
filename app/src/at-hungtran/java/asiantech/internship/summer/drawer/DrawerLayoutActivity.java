package asiantech.internship.summer.drawer;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import asiantech.internship.summer.model.DataDrawer;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerAdapter.onClick, ChoosePhotoDialogFragment.onItemClick {
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int READ_PERMISSION_REQUEST_CAMERA = 1111;
    private static final int WRITE_PERMISSION_REQUEST_CAMERA = 1112;
    private static final int READ_PERMISSION_REQUEST_GALLERY = 1113;
    private static final int WRITE_PERMISSION_REQUEST_CALLERY = 11114;
    List<DataDrawer> mDataDrawer = new ArrayList<>();
    private ChoosePhotoDialogFragment mDialogFragment;
    private DrawerAdapter mDrawerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private float lastTranslate = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initView();
        drawerToggle();
        displayWidth();
    }

    public int displayWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.width = 4 * displayWidth() / 5;
        recyclerView.setLayoutParams(params);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mDataDrawer.add(new DataDrawer(R.drawable.img_avt, "abudory96@gmail.com", R.drawable.ic_arrow_drop_down_black_24dp));

        mDataDrawer.add(new DataDrawer(R.drawable.ic_move_to_inbox_grey_24dp, "Inbox"));
        mDataDrawer.add(new DataDrawer(R.drawable.ic_send_grey_24dp, "Outbox"));
        mDataDrawer.add(new DataDrawer(R.drawable.ic_delete_grey_24dp, "Trash"));
        mDataDrawer.add(new DataDrawer(R.drawable.ic_error_grey_24dp, "Spam"));
        mDrawerAdapter = new DrawerAdapter(mDataDrawer, this, this);
        recyclerView.setAdapter(mDrawerAdapter);
    }


    @Override
    public void avatarClick() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mDialogFragment = ChoosePhotoDialogFragment.newInstance();
        mDialogFragment.setOnHeadlineSelectedListener(this);
        mDialogFragment.show(ft, "dialog");
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
    public void itemClick(int type) {
        if (type == 1) {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{READ_EXTERNAL_STORAGE},
                        READ_PERMISSION_REQUEST_GALLERY
                );
                return;
            }

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
        if (type == 2) {
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
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap image = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    DataDrawer dataDrawer = mDataDrawer.get(0);
                    dataDrawer.setAvtBitmap(image);
                    dataDrawer.setAvatarUri(null);
                    mDrawerAdapter.notifyDataSetChanged();
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    mDataDrawer.get(0).setAvatarUri(selectedImage);
                    mDrawerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void drawerToggle() {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        TextView mTvName = findViewById(R.id.tvName);

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

    @Override
    public void selectItem(int position) {
        for (DataDrawer item : mDataDrawer) {
            item.setChecked(false);
        }
        mDataDrawer.get(position).setChecked(true);
        mDrawerAdapter.notifyDataSetChanged();
    }
}
