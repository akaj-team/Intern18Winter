package asiantech.internship.summer.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class Item {

    private int mIcon;
    private String mContent;
    private Boolean mCheckSelected;
    private Uri mAvatar;
    private Bitmap mAvatarBitmap;

    private Item(int icon, String content, Boolean checkSelected, Uri avatar, Bitmap avatarBitmap) {
        this.mIcon = icon;
        this.mContent = content;
        this.mCheckSelected = checkSelected;
        this.mAvatar = avatar;
        this.mAvatarBitmap = avatarBitmap;
    }

    public Uri getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Uri avatar) {
        this.mAvatar = avatar;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getContent() {
        return mContent;
    }

    public Boolean getCheckSelected() {
        return mCheckSelected;
    }

    public void setCheckSelected(Boolean checkSelected) {
        this.mCheckSelected = checkSelected;
    }

    public Bitmap getAvatarBitmap() {
        return mAvatarBitmap;
    }

    public void setAvatarBitmap(Bitmap avatarBitmap) {
        this.mAvatarBitmap = avatarBitmap;
    }

    public static List<Item> createItem() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.img_avatar, "dinh.truong@asiantech.vn", false, null, null));
        items.add(new Item(R.drawable.bg_inbox, "Inbox", false, null, null));
        items.add(new Item(R.drawable.bg_outbox, "Outbox", false, null, null));
        items.add(new Item(R.drawable.bg_trash, "Trash", false, null, null));
        items.add(new Item(R.drawable.bg_spam, "Spam", false, null, null));
        return items;
    }
}
