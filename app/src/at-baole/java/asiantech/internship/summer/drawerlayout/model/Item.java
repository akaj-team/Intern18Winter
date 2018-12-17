package asiantech.internship.summer.drawerlayout.model;

public class Item {
    private int mItemImage;
    private String mItemText;

    public Item(int itemImage, String itemText) {
        this.mItemImage = itemImage;
        this.mItemText = itemText;
    }

    public int getItemImage() {
        return mItemImage;
    }

    public String getItemText() {
        return mItemText;
    }

}
