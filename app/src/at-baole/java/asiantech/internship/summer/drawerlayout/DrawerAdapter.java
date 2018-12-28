package asiantech.internship.summer.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.model.DrawerItem;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<DrawerItem> mItemList;
    private OnItemClickListener mListener;

    DrawerAdapter(List<DrawerItem> listItems, OnItemClickListener listener) {
        mItemList = listItems;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_header, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBindHeaderView(mItemList.get(position));
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).onBindItemView(mItemList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
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

    public interface OnItemClickListener {
        void onAvatarClicked();

        void onItemClicked(int position);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private TextView mTvEmail;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvEmail = itemView.findViewById(R.id.tvEmail);
            mImgAvatar.setOnClickListener(view -> mListener.onAvatarClicked());
        }

        void onBindHeaderView(DrawerItem header) {
            if (header.getAvatarUri() != null) {
                mImgAvatar.setImageURI(header.getAvatarUri());
            } else {
                mImgAvatar.setImageResource(header.getItemImage());
            }
            mTvEmail.setText(header.getItemText());
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvItem;
        private LinearLayout mllItem;
        private ImageView mImgItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImgItem = itemView.findViewById(R.id.imgItem);
            mTvItem = itemView.findViewById(R.id.tvItem);
            mllItem = itemView.findViewById(R.id.llItem);
            itemView.setOnClickListener(view -> mListener.onItemClicked(getLayoutPosition()));
        }

        void onBindItemView(DrawerItem item) {
            mImgItem.setImageResource(item.getItemImage());
            mTvItem.setText(item.getItemText());
            mllItem.setSelected(item.isSelected());
            mTvItem.setTextColor(itemView.getContext().getResources().getColorStateList(R.color.bg_item_color));
        }
    }
}
