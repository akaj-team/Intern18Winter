package asiantech.internship.summer.drawerlayout;

import android.graphics.Color;
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
import asiantech.internship.summer.model.Data;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Onclick mOnclick;
    private List<Data> mItems;

    DrawerLayoutAdapter(List<Data> itemObjects, Onclick onclick) {
        this.mOnclick = onclick;
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
            ((HeaderViewHolder) holder).tvTitle.setText(mData.getContent());
            ((HeaderViewHolder) holder).imgAvatar.setOnClickListener(view -> mOnclick.onclickAvatar());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).imgIcon.setImageResource(mData.getIcon());
            ((ItemViewHolder) holder).tvContent.setText(mData.getContent());
//            ((ItemViewHolder) holder).linearLayout.setSelected(mData.isChecked());
            if (mData.isChecked()) {
                ((ItemViewHolder) holder).tvContent.setTextColor(Color.BLUE);
                ((ItemViewHolder) holder).imgIcon.setColorFilter(Color.BLUE);
            } else {
                ((ItemViewHolder) holder).tvContent.setTextColor(Color.BLACK);
                ((ItemViewHolder) holder).imgIcon.setColorFilter(Color.BLACK);
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

    public interface Onclick {
        void onclickAvatar();

        void changeSelect(int i);
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
        ImageView imgIcon;
        TextView tvContent;
        LinearLayout llItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvContent = itemView.findViewById(R.id.tvContent);
            llItem = itemView.findViewById(R.id.llItem);
            handleEvent();
        }

        private void handleEvent() {
            llItem.setOnClickListener(view -> {
                if (mOnclick == null) {
                    return;
                }
                int position = getLayoutPosition();
                mOnclick.changeSelect(position);
            });
        }
    }
}
