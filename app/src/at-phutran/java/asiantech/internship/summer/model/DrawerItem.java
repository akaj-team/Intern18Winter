package asiantech.internship.summer.model;

import android.graphics.Bitmap;

public class DrawerItem {
    private int icon;
    private String title;
    private boolean hasChecked;
    private Bitmap avatarBitmap;

    public DrawerItem(int icon, String title, boolean isChecked) {
        this.icon = icon;
        this.title = title;
        this.hasChecked = isChecked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return hasChecked;
    }

    public void setChecked(boolean checked) {
        hasChecked = checked;
    }

    public Bitmap getAvatarBitmap() {
        return avatarBitmap;
    }

    public void setAvatarBitmap(Bitmap avatarBitmap) {
        this.avatarBitmap = avatarBitmap;
    }
}
