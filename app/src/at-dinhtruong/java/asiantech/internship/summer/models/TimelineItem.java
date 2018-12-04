package asiantech.internship.summer.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimelineItem {
    private int mCountLike;
    private String mAvatar;
    private String mName;
    private String mImage;
    private String mDescription;

    public int getmCountLike() {
        return mCountLike;
    }

    public void setmCountLike(int mCountLike) {
        this.mCountLike = mCountLike;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public TimelineItem(int mCountLike, String mAvatar, String mName, String mImage, String mDescription) {
        this.mCountLike = mCountLike;
        this.mAvatar = mAvatar;
        this.mName = mName;
        this.mImage = mImage;
        this.mDescription = mDescription;
    }

    public static List<TimelineItem> createTimelines() {
        Random random = new Random();
        List<TimelineItem> timeLines = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            int mRandomAvatar = random.nextInt(10) + 1;
            int mRandomImage = random.nextInt(10) + 1;
            timeLines.add(new TimelineItem(0, "img_avatar" + mRandomAvatar, "Nguyen Van " + i, "img_image" + mRandomImage, " Đây là tất cả phần mô tả cho nội dung thứ " + i));
        }
        return timeLines;
    }
}
