package com.example.le.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView mItemImage;
    public TextView mItemContent;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mItemImage = itemView.findViewById(R.id.imgItem);
        mItemContent = itemView.findViewById(R.id.tvItem);
    }
}
