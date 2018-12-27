package asiantech.internship.summer.model;

public class User {
    private String mUsername;
    private int mAvatar;
    private int mImage;
    private int mCountLike;
    private String mComment;
    private boolean hasLiked;

    public User(String username, int avatar, int image, int countLike, String comment) {
        this.mUsername = username;
        this.mAvatar = avatar;
        this.mImage = image;
        this.mCountLike = countLike;
        this.mComment = comment;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public void setAvatar(int avatar) {
        this.mAvatar = avatar;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }

    public int getCountLike() {
        return mCountLike;
    }

    public void setCountLike(int countLike) {
        this.mCountLike = countLike;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }

    /*public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean favourite) {
        isLiked = favourite;
    }*/

    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }
}
