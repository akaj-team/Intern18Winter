package asiantech.internship.summer.drawerlayout.model;

public class Item {
    private int mItemImage;
    private String mItemText;
    private boolean mIsSelected;

    public Item(int itemImage, String itemText, boolean isSelected) {
        this.mItemImage = itemImage;
        this.mItemText = itemText;
        this.mIsSelected = isSelected;
    }

    public int getItemImage() {
        return mItemImage;
    }

    public void setItemImage(int itemImage) {
        this.mItemImage = itemImage;
    }

    public String getItemText() {
        return mItemText;
    }

    public void setItemText(String itemText) {
        this.mItemText = itemText;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.mIsSelected = isSelected;
    }
}
