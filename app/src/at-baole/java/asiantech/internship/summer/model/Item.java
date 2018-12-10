package asiantech.internship.summer.model;

public class Item {
    private int mAvatar;
    private String mEmail;
    private int mImgBtnDropdown;
    private int mImgBtnItem;
    private String mTvItem;


    public Item(int Avatar, String Email, int ImgBtnDropdown) {
        this.mAvatar = Avatar;
        this.mEmail = Email;
        this.mImgBtnDropdown = ImgBtnDropdown;
    }

    public Item(int ImgBtnItem, String TvItem) {
        this.mImgBtnItem = ImgBtnItem;
        this.mTvItem = TvItem;
    }

    public int getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(int mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public int getmImgBtnDropdown() {
        return mImgBtnDropdown;
    }

    public void setmImgBtnDropdown(int mImgBtnDropdown) {
        this.mImgBtnDropdown = mImgBtnDropdown;
    }

    public int getmImgBtnItem() {
        return mImgBtnItem;
    }

    public void setmImgBtnItem(int mImgBtnItem) {
        this.mImgBtnItem = mImgBtnItem;
    }

    public String getmTvItem() {
        return mTvItem;
    }

    public void setmTvItem(String mTvItem) {
        this.mTvItem = mTvItem;
    }
}
