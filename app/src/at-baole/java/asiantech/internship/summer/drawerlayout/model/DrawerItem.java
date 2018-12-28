package asiantech.internship.summer.drawerlayout.model;

public class DrawerItem {
    private int icon;
    private String title;
    private boolean checkSelected;

    public DrawerItem(int icon, String title, boolean isSelected) {
        this.icon = icon;
        this.title = title;
        this.checkSelected = isSelected;
    }

    public int getItemImage() {
        return icon;
    }

    public String getItemText() {
        return title;
    }

    public boolean isSelected() {
        return checkSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.checkSelected = isSelected;
    }
}
