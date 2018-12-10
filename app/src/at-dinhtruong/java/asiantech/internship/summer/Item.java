package asiantech.internship.summer;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int mIcon;
    private String mcontent;
    private String mBackground;

    public Item(int mIcon, String mcontent, String mBackground) {
        this.mIcon = mIcon;
        this.mcontent = mcontent;
        this.mBackground = mBackground;
    }

    public int getmIcon() {

        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public String getmBackground() {
        return mBackground;
    }

    public void setmBackground(String mBackground) {
        this.mBackground = mBackground;
    }

    public static List<Item> createItem() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.img_avatar, "dinh.truong@asiantech.vn", "pathBackground"));
        items.add(new Item(R.drawable.ic_move_to_inbox_black_36dp, "Inbox", "pathBackground"));
        items.add(new Item(R.drawable.ic_send_black_36dp, "Outbox", "pathBackground"));
        items.add(new Item(R.drawable.ic_delete_black_36dp, "Trash", "pathBackground"));
        items.add(new Item(R.drawable.ic_error_black_36dp, "Spam", "pathBackground"));
        return items;
    }
}
