package asiantech.internship.summer.model;

public class TimelineItem {
    private int mImageAvt;
    private String mName;
    private int mImage;
    private String mDescription;
    private int mLike;
    private String mCommenterName;
    private boolean mIsChecked;

    public TimelineItem(int imageAvt, String name, int image, String description, int like, String commenterName) {
        this.mImageAvt = imageAvt;
        this.mName = name;
        this.mImage = image;
        this.mDescription = description;
        this.mLike = like;
        this.mCommenterName = commenterName;
    }

    public boolean isIsChecked() {
        return mIsChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.mIsChecked = isChecked;
    }

    public int getImageAvt() {
        return mImageAvt;
    }

    public void setImageAvt(int imageAvt) {
        this.mImageAvt = imageAvt;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getLike() {
        return mLike;
    }

    public void setLike(int like) {
        this.mLike = like;
    }

    public String getCommenterName() { return mCommenterName; }

    public void setCommenterName(String commenterName) { this.mCommenterName = commenterName; }
}
