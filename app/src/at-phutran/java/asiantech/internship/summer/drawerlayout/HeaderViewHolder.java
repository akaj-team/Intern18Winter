package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

class HeaderViewHolder extends RecyclerView.ViewHolder {
    ImageView imageHeader;
    TextView headerTitle;
    HeaderViewHolder(View itemView) {
        super(itemView);
        imageHeader = itemView.findViewById(R.id.avatarCircleImageView);
        headerTitle = itemView.findViewById(R.id.tvTitle);
    }
}
