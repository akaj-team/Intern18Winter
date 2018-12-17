package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import asiantech.internship.summer.R;

class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView mImgItemImage;
    TextView mTvItemContent;
    LinearLayout mItemLinearLayout;

    ItemViewHolder(View itemView) {
        super(itemView);
        mImgItemImage = itemView.findViewById(R.id.imgItem);
        mTvItemContent = itemView.findViewById(R.id.tvItem);
        mItemLinearLayout = itemView.findViewById(R.id.llItem);
    }
}
