package asiantech.internship.summer.model;

public class DrawerItem {
    private int iconResource;
    private String title;
    private boolean isChecked;

    public DrawerItem(int iconResource, String title, boolean isChecked) {
        this.iconResource = iconResource;
        this.title = title;
        this.isChecked = isChecked;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}