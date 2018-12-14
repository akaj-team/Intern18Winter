package asiantech.internship.summer.model;

public class Data {
    private int mIcon;
    private String mContent;
    private boolean mIsChecked;

    public Data() {
    }

    public Data(int mIcon, String mContent) {
        this.mIcon = mIcon;
        this.mContent = mContent;
    }
    public boolean getmIsChecked() {
        return mIsChecked;
    }

    public void setmIsChecked(Boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
