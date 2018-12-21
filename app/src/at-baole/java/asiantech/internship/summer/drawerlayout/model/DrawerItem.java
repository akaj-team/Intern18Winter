package asiantech.internship.summer.drawerlayout.model;

import android.graphics.Bitmap;

public class DrawerItem {
    private int icon;
    private String title;
    private boolean isSelected;
    private Bitmap avatarBitmap;

    public DrawerItem(int icon, String title, boolean isSelected) {
        this.icon = icon;
        this.title = title;
        this.isSelected = isSelected;
    }

    public int getItemImage() {
        return icon;
    }

    public String getItemText() {
        return title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Bitmap getAvatarBitmap() {
        return avatarBitmap;
    }

    public void setAvatarBitmap(Bitmap avatarBitmap) {
        this.avatarBitmap = avatarBitmap;
    }
}
