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
import asiantech.internship.summer.model.Data;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private OnItemClickListener mOnItemClickListener;
    private List<Data> mItems;

    DrawerLayoutAdapter(List<Data> itemObjects, OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        this.mItems = itemObjects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_drawer, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data mData = mItems.get(position);
        if (holder instanceof HeaderViewHolder) {
            if (mData.getAvatarBitmap() != null) {
                ((HeaderViewHolder) holder).imgAvatar.setImageBitmap((mData.getAvatarBitmap()));
            } else {
                ((HeaderViewHolder) holder).imgAvatar.setImageResource((mData.getIcon()));
            }
            ((HeaderViewHolder) holder).tvTitle.setText(mData.getTitle());
            ((HeaderViewHolder) holder).imgAvatar.setOnClickListener(view -> mOnItemClickListener.onclickAvatar());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mImgIcon.setImageResource(mData.getIcon());
            ((ItemViewHolder) holder).mTvContent.setText(mData.getTitle());
            if (mData.isChecked()) {
                ((ItemViewHolder) holder).mTvContent.setTextColor(Color.BLUE);
                ((ItemViewHolder) holder).mImgIcon.setColorFilter(Color.BLUE);
            } else {
                ((ItemViewHolder) holder).mTvContent.setTextColor(Color.BLACK);
                ((ItemViewHolder) holder).mImgIcon.setColorFilter(Color.BLACK);
            }
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

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvTitle;

        HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.avatarCircleImageView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
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
                int position = getLayoutPosition();
                mOnItemClickListener.changeSelect(position);
            });
        }
    }
    public interface OnItemClickListener {
        void onclickAvatar();
        void changeSelect(int i);
    }
}
