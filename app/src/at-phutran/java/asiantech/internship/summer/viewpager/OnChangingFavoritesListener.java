package asiantech.internship.summer.viewpager;

import java.util.List;
import asiantech.internship.summer.model.User;

public interface OnChangingFavoritesListener {
    void onAdding(List<User> users);
    void onRemoving(List<User> users);
}
