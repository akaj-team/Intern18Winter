package asiantech.internship.summer.models;

public class Paper {
    private String mDescription;
    private int mIcon;
    private String mNameFirm;
    private String mColor;

    public Paper(String description, int icon, String nameFirm, String color) {
        this.mDescription = description;
        this.mIcon = icon;
        this.mNameFirm = nameFirm;
        this.mColor = color;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getNameFirm() {
        return mNameFirm;
    }

    public String getColor() {
        return mColor;
    }
}
