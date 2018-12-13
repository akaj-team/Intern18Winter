package asiantech.internship.summer.model;

public class News {
    private int mImage;
    private String mColor;
    private String mTitle;
    private String mContent;

    public News(String Content, int Image, String Title, String Color) {
        this.mImage = Image;
        this.mColor = Color;
        this.mTitle = Title;
        this.mContent = Content;
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

    public String getColor() {
        return mColor;
    }
}
