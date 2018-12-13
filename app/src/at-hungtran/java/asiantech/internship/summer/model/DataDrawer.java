package asiantech.internship.summer.model;

import android.net.Uri;

public class DataDrawer {
    private int mIconImage;
    private String mCat;
    private int mImgAvt;
    private String mGmail;
    private int mImgCheck;
    private boolean isChecked;
    private Uri mAvatarUri;

    public DataDrawer(int iconImage, String cat) {
        this.mIconImage = iconImage;
        this.mCat = cat;
    }

    public DataDrawer(int imgAvt, String gmail, int imgCheck) {
        this.mImgAvt = imgAvt;
        this.mGmail = gmail;
        this.mImgCheck = imgCheck;
    }

    public Uri getAvatarUri() {
        return mAvatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.mAvatarUri = avatarUri;
    }

    public int getIconImage() {
        return mIconImage;
    }

    public void setIconImage(int iconImage) {
        this.mIconImage = iconImage;
    }

    public String getCat() {
        return mCat;
    }

    public void setCat(String cat) {
        this.mCat = cat;
    }

    public int getImgAvt() {
        return mImgAvt;
    }

    public void setImgAvt(int imgAvt) {
        this.mImgAvt = imgAvt;
    }

    public String getGmail() {
        return mGmail;
    }

    public void setGmail(String gmail) {
        this.mGmail = gmail;
    }

    public int getImgCheck() {
        return mImgCheck;
    }

    public void setImgCheck(int imgCheck) {
        this.mImgCheck = imgCheck;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}