package asiantech.internship.summer.model;

public class Data {
    private String mTitle;
    private int mImageIcon;
    private String mPoster;
    private String mColor;

    public Data(String title, int imageIcon, String poster, String color) {
        this.mTitle = title;
        this.mImageIcon = imageIcon;
        this.mPoster = poster;
        this.mColor = color;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setImageIcon(int imageIcon) {
        this.mImageIcon = imageIcon;
    }

    public void setPoster(String poster) {
        this.mPoster = poster;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public int getmImageIcon() {
        return mImageIcon;
    }

    public String getmPoster() {
        return mPoster;
    }
}
