package asiantech.internship.summer.model;

public class TimelineItem {
    private int mImageAvt;
    private String mName;
    private int mImage;
    private String mDescription;
    private int mLike;

    public TimelineItem(int mImageAvt, String mName, int mImage, String mDescription, int mLike) {
        this.mImageAvt = mImageAvt;
        this.mName = mName;
        this.mImage = mImage;
        this.mDescription = mDescription;
        this.mLike = mLike;
    }

    public int getmImageAvt() {
        return mImageAvt;
    }

    public void setmImageAvt(int mImageAvt) {
        this.mImageAvt = mImageAvt;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmLike() {
        return mLike;
    }

    public void setmLike(int mLike) {
        this.mLike = mLike;
    }
}
