package asiantech.internship.summer.model;

import android.net.Uri;

public class Object {
    private int mIconImage;
    private String mCat;
    private int mImgAvt;
    private String mGmail;
    private int mImgCheck;
    private boolean isChecked;
    private Uri mAvatarUri;

    public Object(int mIconImage, String mCat) {
        this.mIconImage = mIconImage;
        this.mCat = mCat;
    }

    public Object(int mImgAvt, String mGmail, int mImgCheck) {
        this.mImgAvt = mImgAvt;
        this.mGmail = mGmail;
        this.mImgCheck = mImgCheck;
    }

    public Uri getAvatarUri() {
        return mAvatarUri;
    }

    public void setAvatarUri(Uri mAvatarUri) {
        this.mAvatarUri = mAvatarUri;
    }

    public int getmIconImage() {
        return mIconImage;
    }

    public void setmIconImage(int mIconImage) {
        this.mIconImage = mIconImage;
    }

    public String getmCat() {
        return mCat;
    }

    public void setmCat(String mCat) {
        this.mCat = mCat;
    }

    public int getmImgAvt() {
        return mImgAvt;
    }

    public void setmImgAvt(int mImgAvt) {
        this.mImgAvt = mImgAvt;
    }

    public String getmGmail() {
        return mGmail;
    }

    public void setmGmail(String mGmail) {
        this.mGmail = mGmail;
    }

    public int getmImgCheck() {
        return mImgCheck;
    }

    public void setmImgCheck(int mImgCheck) {
        this.mImgCheck = mImgCheck;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}