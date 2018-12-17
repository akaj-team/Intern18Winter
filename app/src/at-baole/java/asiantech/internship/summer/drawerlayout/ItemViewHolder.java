package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView mItemImage;
    TextView mItemContent;

    ItemViewHolder(View itemView) {
        super(itemView);
        mItemImage = itemView.findViewById(R.id.imgItem);
        mItemContent = itemView.findViewById(R.id.tvItem);
    }
}
