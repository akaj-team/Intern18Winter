package asiantech.internship.summer.recycler_view.model;

public class TimelineItem {
    private int mAvatar;
    private int mPicture;
    private int mCountLike;
    private String mComment;
    private String mUsername;

    public TimelineItem(int avatar, String username, int picture, int countLike, String comment) {
        this.mAvatar = avatar;
        this.mUsername = username;
        this.mPicture = picture;
        this.mCountLike = countLike;
        this.mComment = comment;
    }

    public int getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(int avatar) {
        this.mAvatar = avatar;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String username) {
        this.mUsername = username;
    }

    public int getmPicture() {
        return mPicture;
    }

    public void setmPicture(int picture) {
        this.mPicture = picture;
    }

    public int getmCountLike() {
        return mCountLike;
    }

    public void setmCountLike(int countLike) {
        this.mCountLike = countLike;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String comment) {
        this.mComment = comment;
    }
}
