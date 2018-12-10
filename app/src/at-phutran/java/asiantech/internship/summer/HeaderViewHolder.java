package asiantech.internship.summer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageHeader;
    public TextView headerTitle;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        imageHeader = itemView.findViewById(R.id.avatarCircleImageView);
        headerTitle = itemView.findViewById(R.id.tvTitle);
    }
}
