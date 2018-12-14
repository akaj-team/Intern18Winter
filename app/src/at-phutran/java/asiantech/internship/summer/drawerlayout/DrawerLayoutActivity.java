package asiantech.internship.summer.drawerlayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import asiantech.internship.summer.model.Data;

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerLayoutAdapter.Onclick{

    protected RecyclerView mAddHeaderRecyclerView;
    private LinearLayout mContent;
    private List<Data> mData;
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    private DrawerLayoutAdapter mDrawerLayoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        mAddHeaderRecyclerView = findViewById(R.id.recyclerViewContent);
        //set width for drawer layout
        LinearLayout mLayout = findViewById(R.id.llDrawerLayout);
        ViewGroup.LayoutParams params = mLayout.getLayoutParams();
        params.width = 4 * getWithScreen() / 5;
        mLayout.setLayoutParams(params);
        //Move content to side in Drawer Layout
        DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        mContent = findViewById(R.id.llContent);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.anim.slide_in_left, R.anim.slide_in_right) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                mContent.setTranslationX(slideX);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrawerLayoutActivity.this);
        mAddHeaderRecyclerView.setLayoutManager(linearLayoutManager);
        mAddHeaderRecyclerView.setHasFixedSize(true);
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(getDataSource(), this);
        mAddHeaderRecyclerView.setAdapter(mDrawerLayoutAdapter);
    }
    private List<Data> getDataSource(){
        mData = new ArrayList<>();
        mData.add(new Data(R.drawable.img_avatar, getString(R.string.tranthithuthao), null, null));
        mData.add(new Data(R.drawable.ic_move_to_inbox_black_24dp, getString(R.string.inbox), null, null));
        mData.add(new Data(R.drawable.ic_send_black_24dp, getString(R.string.outbox), null, null));
        mData.add(new Data(R.drawable.ic_delete_black_24dp, getString(R.string.trash), null, null));
        mData.add(new Data(R.drawable.ic_error_black_24dp, getString(R.string.spam), null, null));
        return mData;
    }
    private int getWithScreen(){
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
    public void onclickAvatar(){
        requestMultiplePermissions();
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.action);
        String[] pictureDialogItems = {
                getString(R.string.gallery),
                getString(R.string.camera) };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
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
            onSelectFromGalleryResult(data);
        }else{
            onCaptureImageResult(data);
        }
    }
    private void onSelectFromGalleryResult(Intent intent) {
        Data data = mData.get(0);
        Uri contentURI = intent.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            data.setAvatarBitmap(bitmap);
            mDrawerLayoutAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }

    }

    private void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(extras).get(getString(R.string.data));
        Toast.makeText(DrawerLayoutActivity.this, R.string.save, Toast.LENGTH_SHORT).show();
        Data item = mData.get(0);
        item.setAvatarBitmap(imageBitmap);
        mDrawerLayoutAdapter.notifyDataSetChanged();
    }

    private void requestMultiplePermissions(){
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
    public void changeSelect(int i) {
        for (Data item : mData) {
            item.setChecked(false);
        }
        mData.get(i).setChecked(true);
    }
}
