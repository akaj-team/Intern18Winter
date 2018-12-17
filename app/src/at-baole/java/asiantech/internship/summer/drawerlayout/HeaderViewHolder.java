package com.example.le.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public ImageView mAvatar;
    public TextView mEmail;


    public HeaderViewHolder(View itemView) {
        super(itemView);
        mAvatar = itemView.findViewById(R.id.imgAvatar);
        mEmail = itemView.findViewById(R.id.tvEmail);

    }
}
