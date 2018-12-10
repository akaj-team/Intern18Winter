package asiantech.internship.summer;

public class News {
    private String mContent;
    private int mImage;
    private String mTitle;

    public News(String mContent, int mImage, String mTitle) {
        this.mContent = mContent;
        this.mImage = mImage;
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
