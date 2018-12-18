package asiantech.internship.summer.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class DrawerItem {

    private int icon;
    private String title;
    private Boolean isChecked;
    private Uri avatarUri;
    private Bitmap avatarBitmap;

    private DrawerItem(int icon, String title, Boolean isChecked) {
        this.icon = icon;
        this.title = title;
        this.isChecked = isChecked;
    }

    private DrawerItem(int avatar, String title, Uri avatarUri, Bitmap avatarBitmap) {
        this.icon = avatar;
        this.title = title;
        this.avatarUri = avatarUri;
        this.avatarBitmap = avatarBitmap;
    }

    public Uri getAvatar() {
        return avatarUri;
    }

    public void setAvatar(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getIcon() {
        return icon;
    }

    public String getContent() {
        return title;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Bitmap getAvatarBitmap() {
        return avatarBitmap;
    }

    public void setAvatarBitmap(Bitmap avatarBitmap) {
        this.avatarBitmap = avatarBitmap;
    }

    public static List<DrawerItem> createItem() {
        List<DrawerItem> items = new ArrayList<>();
        items.add(new DrawerItem(R.drawable.img_avatar_drawer_layout, "dinh.truong@asiantech.vn", null, null));
        items.add(new DrawerItem(R.drawable.bg_inbox, "Inbox", false));
        items.add(new DrawerItem(R.drawable.bg_outbox, "Outbox", false));
        items.add(new DrawerItem(R.drawable.bg_trash, "Trash", false));
        items.add(new DrawerItem(R.drawable.bg_spam, "Spam", false));
        return items;
    }
}
