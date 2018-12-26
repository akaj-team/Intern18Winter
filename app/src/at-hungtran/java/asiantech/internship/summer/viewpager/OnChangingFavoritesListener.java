package asiantech.internship.summer.viewpager;

import java.util.List;

import asiantech.internship.summer.model.TimelineItem;

public interface OnChangingFavoritesListener {
    void onAddFavourite(List<TimelineItem> listFavourite);
    void onRemoveFavourite(List<TimelineItem> listFavourite);
}
