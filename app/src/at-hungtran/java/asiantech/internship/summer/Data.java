package asiantech.internship.summer;

public class Data {
    private String mTitle;
    private int mImageIcon;
    private String mPoster;
    private String mColor;

    public Data(String mTitle, int mImageIcon, String mPoster, String mColor) {
        this.mTitle = mTitle;
        this.mImageIcon = mImageIcon;
        this.mPoster = mPoster;
        this.mColor = mColor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmImageIcon() {
        return mImageIcon;
    }

    public void setmImageIcon(int mImageIcon) {
        this.mImageIcon = mImageIcon;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }
}
