package asiantech.internship.summer.drawerlayout.model;

public class Item {
    int mItemImage;
    private String mItemText;

    public Item(int itemImage, String itemText) {
        this.mItemImage = itemImage;
        this.mItemText = itemText;
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
}