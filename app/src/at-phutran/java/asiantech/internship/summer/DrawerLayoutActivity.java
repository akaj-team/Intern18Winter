package asiantech.internship.summer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class DrawerLayoutActivity extends AppCompatActivity implements DrawerLayoutAdapter.Onclick{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView addHeaderRecyclerView;
    private LinearLayout mLayout;
    private LinearLayout mContent;
    private DrawerLayout mDrawerLayout;
    static List<Data> data;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        addHeaderRecyclerView = findViewById(R.id.recyclerViewContent);
        //set width for drawer layout
        mLayout = findViewById(R.id.llDrawerLayout);
        ViewGroup.LayoutParams params = mLayout.getLayoutParams();
        params.width = 4 * getWithScreen() / 5;
        mLayout.setLayoutParams(params);
        //Move content to side in Drawer Layout
        mDrawerLayout = findViewById(R.id.drawerLayout);
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
        addHeaderRecyclerView.setLayoutManager(linearLayoutManager);
        addHeaderRecyclerView.setHasFixedSize(true);
        DrawerLayoutAdapter customAdapter = new DrawerLayoutAdapter(getDataSource(), this);
        addHeaderRecyclerView.setAdapter(customAdapter);

    }
    private List<Data> getDataSource(){
        data = new ArrayList<>();
        data.add(new Data(R.drawable.img_avatar, getString(R.string.tranthithuthao)));
        data.add(new Data(R.drawable.ic_move_to_inbox_black_24dp, "Inbox"));
        data.add(new Data(R.drawable.ic_send_black_24dp, "Outbox"));
        data.add(new Data(R.drawable.ic_delete_black_24dp, "Trash"));
        data.add(new Data(R.drawable.ic_error_black_24dp, "Spam"));
        return data;
    }
    private int getWithScreen(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        int width = size.x;
        return width;
    }
    @Override
    public void onclickAvatar(){
        requestMultiplePermissions();
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    private void  requestMultiplePermissions(){
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
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void changeSelect(int i) {
        for (Data item : data) {
            item.setmIsChecked(false);
        }
        data.get(i).setmIsChecked(true);
    }
}
