package asiantech.internship.summer.Model;

public class Paper {
    private String txtDescription;
    private int imgIcon;
    private String txtNameFirm;

    public String getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription = txtDescription;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getTxtNameFirm() {
        return txtNameFirm;
    }

    public void setTxtNameFirm(String txtNameFirm) {
        this.txtNameFirm = txtNameFirm;
    }

    public Paper(String txtDescription, int imgIcon, String txtNameFirm) {
        this.txtDescription = txtDescription;
        this.imgIcon = imgIcon;
        this.txtNameFirm = txtNameFirm;
    }
}
