package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView mItemImage;
    public TextView mItemContent;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mItemImage = itemView.findViewById(R.id.imgItem);
        mItemContent = itemView.findViewById(R.id.tvItem);
    }
}
