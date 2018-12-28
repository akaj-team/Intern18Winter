package asiantech.internship.summer.models;

public class DrawerItem {

    private int icon;
    private String title;
    private Boolean checked;

    public DrawerItem(int icon, String title, Boolean checked) {
        this.icon = icon;
        this.title = title;
        this.checked = checked;
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
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
