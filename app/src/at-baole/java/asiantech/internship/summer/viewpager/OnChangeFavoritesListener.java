package asiantech.internship.summer.viewpager;

import java.util.List;

import asiantech.internship.summer.recyclerview.model.TimelineItem;

public interface OnChangeFavoritesListener {
    void onAddFavourite(List<TimelineItem> listFavourite);

    void onRemoveFavourite(List<TimelineItem> listFavourite);
}
