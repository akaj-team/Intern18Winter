package asiantech.internship.summer.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class DrawerItem {
    private int iconResource;
    private String title;
    private int imgAvt;
    private String gmail;
    private int imgCheck;
    private boolean isChecked;
    private Uri avatarUri;
    private Bitmap avtBitmap;

    public DrawerItem(int iconImage, String title) {
        this.iconResource = iconImage;
        this.title = title;
    }

    public DrawerItem(int imgAvt, String gmail, int imgCheck) {
        this.imgAvt = imgAvt;
        this.gmail = gmail;
        this.imgCheck = imgCheck;
    }

    public Bitmap getAvtBitmap() {
        return avtBitmap;
    }

    public void setAvtBitmap(Bitmap avtBitmap) {
        this.avtBitmap = avtBitmap;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getIconImage() {
        return iconResource;
    }

    public void setIconImage(int iconImage) {
        this.iconResource = iconImage;
    }

    public String getCat() {
        return title;
    }

    public void setCat(String title) {
        this.title = title;
    }

    public int getImgAvt() {
        return imgAvt;
    }

    public void setImgAvt(int imgAvt) {
        this.imgAvt = imgAvt;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(int imgCheck) {
        this.imgCheck = imgCheck;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}