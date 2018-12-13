package asiantech.internship.summer.model;

public class News {
    private String mContent;
    private int mImage;
    private String mTitle;

    public News(String content, int image, String title) {
        this.mContent = content;
        this.mImage = image;
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public int getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }
}
