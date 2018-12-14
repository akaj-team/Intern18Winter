package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView itemImage;
    public TextView itemContent;
    public LinearLayout linearLayout;
    public ItemViewHolder(View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.imgIcon);
        itemContent = itemView.findViewById(R.id.tvContent);
        linearLayout = itemView.findViewById(R.id.llRow);
    }
}
