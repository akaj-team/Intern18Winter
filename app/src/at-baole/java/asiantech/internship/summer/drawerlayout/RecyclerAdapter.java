package com.example.le.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.le.myapplication.model.Item;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Item> itemObjects;
    private OnItemListener mListener;

    public RecyclerAdapter(List<Item> itemObjects, OnItemListener listener) {
        this.itemObjects = itemObjects;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item mObject = itemObjects.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).mAvatar.setImageResource(mObject.getItemImage());
            ((HeaderViewHolder) holder).mEmail.setText(mObject.getItemText());
            ((HeaderViewHolder) holder).mAvatar.setOnClickListener(view -> mListener.onClickAvatar());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mItemImage.setImageResource(mObject.getItemImage());
            ((ItemViewHolder) holder).mItemContent.setText(mObject.getItemText());
        }
    }

    private Item getItem(int position) {
        return itemObjects.get(position);
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    interface OnItemListener {
        void onClickAvatar();
    }
}
