package asiantech.internship.summer.models;

public class DrawerItem {

    private int icon;
    private String title;
    private Boolean isChecked;

    public DrawerItem(int icon, String title, Boolean isChecked) {
        this.icon = icon;
        this.title = title;
        this.isChecked = isChecked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
