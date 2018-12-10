package asiantech.internship.summer;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.model.Object;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerAdapter.onClick, ChoosePhotoDialogFragment.onItemClick {
    RecyclerView recyclerView;
    DrawerAdapter drawerAdapter;
    List<Object> objects = new ArrayList<>();
    TextView mTvName;
    Uri imageUri;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private float lastTranslate = 0.0f;
    ChoosePhotoDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initView();
        drawerToggle();
    }

    public void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        objects.add(new Object(R.drawable.img_avt, "abudory96@gmail.com", R.drawable.ic_arrow_drop_down_black_24dp));

        objects.add(new Object(R.drawable.ic_move_to_inbox_black_24dp, "Inbox"));
        objects.add(new Object(R.drawable.ic_send_black_24dp, "Outbox"));
        objects.add(new Object(R.drawable.ic_delete_black_24dp, "Trash"));
        objects.add(new Object(R.drawable.ic_error_black_24dp, "Spam"));
        drawerAdapter = new DrawerAdapter(objects, this, this);
        recyclerView.setAdapter(drawerAdapter);
    }


    @Override
    public void avatarClick() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialogFragment = ChoosePhotoDialogFragment.newInstance();
        dialogFragment.setOnHeadlineSelectedListener(this);
        dialogFragment.show(ft, "dialog");
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1234);
        dialogFragment.dismiss();

    }

    private void takePicture() {

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
                        1114
                );
                return;
            }

            int permissionWrite = ContextCompat.checkSelfPermission(this,
                    WRITE_EXTERNAL_STORAGE);
            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{WRITE_EXTERNAL_STORAGE},
                        1113
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
                        1111
                );
                return;
            }

            int permissionWrite = ContextCompat.checkSelfPermission(this,
                    WRITE_EXTERNAL_STORAGE);
            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{WRITE_EXTERNAL_STORAGE},
                        1112
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
                    Uri selectedImage = imageReturnedIntent.getData();
                    objects.get(0).setAvatarUri(selectedImage);
                    drawerAdapter.notifyDataSetChanged();
                }

                break;
            case 1234:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    objects.get(0).setAvatarUri(selectedImage);
                    drawerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void drawerToggle() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mTvName = findViewById(R.id.tvName);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                mTvName.setTranslationX(slideX);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        // ... more of your code
    }

    @Override
    public void selectItem(int position) {
        for (Object item : objects) {
            item.setChecked(false);
        }
        objects.get(position).setChecked(true);
//        drawerAdapter.notifyItemChanged(position);
        drawerAdapter.notifyDataSetChanged();
    }
}
