package asiantech.internship.summer.viewpager;

import java.util.List;

import asiantech.internship.summer.recyclerview.model.TimelineItem;

public interface OnChangeFavoritesListener {
    void addFavourite(List<TimelineItem> listFavourite);

    void removeFavourite(List<TimelineItem> listFavourite);
}
