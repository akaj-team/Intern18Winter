package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

class HeaderViewHolder extends RecyclerView.ViewHolder {
    ImageView mImgAvatar;
    TextView mTvEmail;

    HeaderViewHolder(View itemView) {
        super(itemView);
        mImgAvatar = itemView.findViewById(R.id.imgAvatar);
        mTvEmail = itemView.findViewById(R.id.tvEmail);
    }
}
