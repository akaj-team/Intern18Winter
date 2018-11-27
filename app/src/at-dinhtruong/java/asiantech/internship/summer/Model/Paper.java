package asiantech.internship.summer.Model;

public class Paper {
    private String mDescription;
    private int mIcon;
    private String mNameFirm;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getmNameFirm() {
        return mNameFirm;
    }

    public void setmNameFirm(String mNameFirm) {
        this.mNameFirm = mNameFirm;
    }

    public Paper(String mDescription, int mIcon, String mNameFirm) {
        this.mDescription = mDescription;
        this.mIcon = mIcon;
        this.mNameFirm = mNameFirm;
    }
}
