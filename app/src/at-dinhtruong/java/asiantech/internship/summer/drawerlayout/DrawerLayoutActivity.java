package asiantech.internship.summer.drawerlayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import asiantech.internship.summer.models.Item;
import asiantech.internship.summer.R;

public class DrawerLayoutActivity extends AppCompatActivity implements ItemAdapter.IMethodCaller {

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int SELECT_PICTURE = 1;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static final String TAKE_PHOTO = "Take Photo";
    public static final String CHOOSE_FROM_LIBRARY = "Choose from Library";
    public static final String CANCEL = "Cancel";
    private int positionSelected = -1;
    private List<Item> items;
    private ItemAdapter mItemAdapter;
    private RecyclerView mRecycleViewItem;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearLayoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initViewItem();
        slideDrawer();
    }

    private void initViewItem() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mLinearLayoutContent = findViewById(R.id.llContent);
        mRecycleViewItem = findViewById(R.id.recycleViewItem);
        items = Item.createItem();
        mRecycleViewItem.setLayoutManager(new LinearLayoutManager(this));
        mItemAdapter = new ItemAdapter(items, DrawerLayoutActivity.this, this);
        mRecycleViewItem.setAdapter(mItemAdapter);
    }

    private void slideDrawer() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        ViewGroup.LayoutParams params = mRecycleViewItem.getLayoutParams();
        params.width = width * 5 / 6;
        mRecycleViewItem.setLayoutParams(params);
        mRecycleViewItem.setHasFixedSize(true);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        super.onDrawerSlide(drawerView, slideOffset);
                        float slideX = drawerView.getWidth() * slideOffset;
                        mLinearLayoutContent.setTranslationX(slideX);
                    }
                };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public void changeAvatarMethod() {
        final CharSequence[] itemsDialog = {getString(R.string.takePhoto), getString(R.string.chooseFromLibrary), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(DrawerLayoutActivity.this);
        builder.setTitle(R.string.addPhoto);
        builder.setItems(itemsDialog, (dialog, item) -> {
            if (itemsDialog[item].equals(TAKE_PHOTO)) {
                if (!requestPermission()) {
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.permissionAccepted), Toast.LENGTH_SHORT).show();
                } else {
                    cameraIntent();
                }
            } else if (itemsDialog[item].equals(CHOOSE_FROM_LIBRARY)) {
                galleryIntent();
            } else if (itemsDialog[item].equals(CANCEL)) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void selectItemMethod(int position) {
        if (positionSelected != -1) {
            items.get(positionSelected).setCheckSelected(false);
            mItemAdapter.notifyItemChanged(positionSelected);
        }
        positionSelected = position;
        items.get(position).setCheckSelected(true);
        mItemAdapter.notifyItemChanged(positionSelected);
    }

    private void cameraIntent() {
        if (requestPermission()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.selectPicture)), SELECT_PICTURE);
    }

    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(DrawerLayoutActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    Toast.makeText(DrawerLayoutActivity.this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageURI = data.getData();
        Item item = items.get(0);
        item.setAvatar(selectedImageURI);
        item.setAvatarBitmap(null);
        mItemAdapter.notifyDataSetChanged();
    }

    private void onCaptureImageResult(Intent data) {
        Bundle getExtrasImage = data.getExtras();
        Bitmap imageBitmap = null;
        if (getExtrasImage != null) {
            imageBitmap = (Bitmap) (getExtrasImage).get(getString(R.string.data));
        }
        Item item = items.get(0);
        item.setAvatarBitmap(imageBitmap);
        item.setAvatar(null);
        mItemAdapter.notifyDataSetChanged();
    }
}