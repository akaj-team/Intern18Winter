package asiantech.internship.summer.model;

public class User {
    private String username;
    private int avatar;
    private int image;
    private int countLike;
    private String comment;

    public User(String username, int avatar, int image, int countLike, String comment) {
        this.username = username;
        this.avatar = avatar;
        this.image = image;
        this.countLike = countLike;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
