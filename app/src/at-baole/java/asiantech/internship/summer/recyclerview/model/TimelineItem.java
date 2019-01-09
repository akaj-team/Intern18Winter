package asiantech.internship.summer.recyclerview.model;

public class TimelineItem {
    private int mAvatar;
    private int mPicture;
    private int mCountLike;
    private String mUsername;
    private String mCommenter;
    private String mComment;
    private boolean mIsChecked;


    public TimelineItem(int avatar, String username, int picture, int countLike, String commenter, String comment) {
        this.mAvatar = avatar;
        this.mUsername = username;
        this.mPicture = picture;
        this.mCountLike = countLike;
        this.mCommenter = commenter;
        this.mComment = comment;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public int getPicture() {
        return mPicture;
    }

    public int getCountLike() {
        return mCountLike;
    }

    public void setCountLike(int countLike) {
        this.mCountLike = countLike;
    }

    public String getCommenter() {
        return mCommenter;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.mIsChecked = isChecked;
    }
}
