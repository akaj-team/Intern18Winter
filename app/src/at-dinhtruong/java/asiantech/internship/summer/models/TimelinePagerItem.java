package asiantech.internship.summer.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimelinePagerItem {
    private int mNumOfLike;
    private String mAvatar;
    private String mName;
    private String mImage;
    private String mDescription;
    private boolean mCheckSelected;

    public TimelinePagerItem(int numOfLike, String avatar, String name, String image, String description, boolean checkSelected) {
        this.mNumOfLike = numOfLike;
        this.mAvatar = avatar;
        this.mName = name;
        this.mImage = image;
        this.mDescription = description;
        this.mCheckSelected = checkSelected;
    }

    public int getNumOfLike() {
        return mNumOfLike;
    }

    public void setNumOfLike(int numOfLike) {
        this.mNumOfLike = numOfLike;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isCheckSelected() {
        return mCheckSelected;
    }

    public void setCheckSelected(boolean checkSelected) {
        this.mCheckSelected = checkSelected;
    }

    public static List<TimelinePagerItem> createTimelines() {
        Random random = new Random();
        List<TimelinePagerItem> timelineItems = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            int randomAvatar = random.nextInt(10) + 1;
            int randomImage = random.nextInt(10) + 1;
            timelineItems.add(new TimelinePagerItem(0, "img_avatar" + randomAvatar, "Nguyen Van " + i, "img_image" + randomImage, " Đây là tất cả phần mô tả cho nội dung thứ " + i, false));
        }
        return timelineItems;
    }
}
