package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import asiantech.internship.summer.R;

class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView mImgItem;
    TextView mTvItem;
    LinearLayout mllItem;

    ItemViewHolder(View itemView) {
        super(itemView);
        mImgItem = itemView.findViewById(R.id.imgItem);
        mTvItem = itemView.findViewById(R.id.tvItem);
        mllItem = itemView.findViewById(R.id.llItem);
    }
}
