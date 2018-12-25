package asiantech.internship.summer.models;

import android.graphics.Bitmap;
import android.net.Uri;

public class DrawerItem {

    private int icon;
    private String title;
    private Boolean isChecked;
    private Uri avatarUri;
    private Bitmap avatarBitmap;

    public DrawerItem(int icon, String title, Boolean isChecked) {
        this.icon = icon;
        this.title = title;
        this.isChecked = isChecked;
    }

    public DrawerItem(int avatar, String title, Uri avatarUri, Bitmap avatarBitmap) {
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
}
