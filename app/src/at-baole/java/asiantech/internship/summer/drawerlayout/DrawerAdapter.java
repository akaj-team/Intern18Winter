package asiantech.internship.summer.drawerlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.model.DrawerItem;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //private static final String TAG = DrawerAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<DrawerItem> mListItems;
    private OnItemClickListener mListener;
    private Context mContext;

    DrawerAdapter(List<DrawerItem> listItems, OnItemClickListener listener, Context context) {
        this.mListItems = listItems;
        this.mListener = listener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DrawerItem mItem = mListItems.get(position);
        if (holder instanceof HeaderViewHolder) {
            if (mItem.getAvatarBitmap() != null) {
                ((HeaderViewHolder) holder).mImgAvatar.setImageBitmap(mItem.getAvatarBitmap());
            } else {
                ((HeaderViewHolder) holder).mImgAvatar.setImageResource(mItem.getItemImage());

            }
            ((HeaderViewHolder) holder).mTvEmail.setText(mItem.getItemText());
            ((HeaderViewHolder) holder).mImgAvatar.setOnClickListener(view -> mListener.onAvatarClicked());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mImgItem.setImageResource(mItem.getItemImage());
            ((ItemViewHolder) holder).mTvItem.setText(mItem.getItemText());
            ((ItemViewHolder) holder).mTvItem.setTextColor(mContext.getResources().getColorStateList(R.color.bg_item_color));
            ((ItemViewHolder) holder).mllItem.setSelected(mItem.isSelected());
            ((ItemViewHolder) holder).mllItem.setOnClickListener(view -> {
                mListener.onItemClicked(holder.getLayoutPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    interface OnItemClickListener {
        void onAvatarClicked();

        void onItemClicked(int position);
    }
}
