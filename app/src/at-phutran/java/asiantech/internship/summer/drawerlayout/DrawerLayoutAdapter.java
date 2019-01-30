package asiantech.internship.summer.drawerlayout;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.DrawerItem;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private OnItemClickListener mOnItemClickListener;
    private List<DrawerItem> mItems;

    DrawerLayoutAdapter(List<DrawerItem> items, OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mItems = items;
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
        DrawerItem mData = mItems.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBind();
            ((HeaderViewHolder) holder).tvTitle.setText(mData.getTitle());
            ((HeaderViewHolder) holder).imgAvatar.setOnClickListener(view -> mOnItemClickListener.onAvatarClicked());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mImgIcon.setImageResource(mData.getIcon());
            ((ItemViewHolder) holder).mTvContent.setText(mData.getTitle());
            ((ItemViewHolder) holder).onBind();
        }
    }


    @Override
    public int getItemCount() {
        return mItems.size();
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

    public interface OnItemClickListener {
        void onAvatarClicked();

        void onItemChecked(int position);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvTitle;
        DrawerItem drawerItem;

        HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.avatarCircleImageView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        private void onBind() {
            int position = getLayoutPosition();
            drawerItem = mItems.get(position);
            if (drawerItem.getAvatarBitmap() != null) {
                imgAvatar.setImageBitmap(drawerItem.getAvatarBitmap());
            } else {
                imgAvatar.setImageResource(drawerItem.getIcon());
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        int position;
        private ImageView mImgIcon;
        private TextView mTvContent;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImgIcon = itemView.findViewById(R.id.imgIcon);
            mTvContent = itemView.findViewById(R.id.tvContent);
            handleEventSelected();
        }

        private void handleEventSelected() {
            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener == null) {
                    return;
                }
                position = getLayoutPosition();
                mOnItemClickListener.onItemChecked(position);
            });
        }

        private void onBind() {
            if (mItems.get(getLayoutPosition()).isChecked()) {
                mTvContent.setTextColor(Color.BLUE);
                mImgIcon.setColorFilter(Color.BLUE);
            } else {
                mTvContent.setTextColor(Color.BLACK);
                mImgIcon.setColorFilter(Color.BLACK);
            }
        }
    }
}
