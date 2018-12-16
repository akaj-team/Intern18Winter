package asiantech.internship.summer.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Data {
    private int mIcon;
    private String mContent;
    private boolean mIsChecked;
    private Bitmap mAvatarBitmap;

    public Data(int mIcon, String mContent, Bitmap mAvatarBitmap) {
        this.mIcon = mIcon;
        this.mContent = mContent;
        this.mAvatarBitmap = mAvatarBitmap;
    }

    public Bitmap getAvatarBitmap() {
        return mAvatarBitmap;
    }

    public void setAvatarBitmap(Bitmap avatarBitmap) {
        this.mAvatarBitmap = avatarBitmap;
    }
    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(Boolean isChecked) {
        this.mIsChecked = isChecked;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getContent() {
        return mContent;
    }
}
