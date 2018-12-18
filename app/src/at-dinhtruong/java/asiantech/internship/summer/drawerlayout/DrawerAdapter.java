package asiantech.internship.summer.drawerlayout;

import android.content.Context;
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
import asiantech.internship.summer.models.DrawerItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<DrawerItem> drawerItems;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onAvatarClicked();

        void onItemClicked(int position);
    }

    DrawerAdapter(List<DrawerItem> drawerItems, Context context, OnItemClickListener onItemClickListener) {
        this.drawerItems = drawerItems;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == VIEW_TYPE_HEADER) {
            View itView = layoutInflater.inflate(R.layout.header_layout_drawer, viewGroup, false);
            return new HeaderViewHolder(itView);
        }
        View itView = layoutInflater.inflate(R.layout.item_icon_drawer, viewGroup, false);
        return new ItemViewHoder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHoder) {
            DrawerItem item = drawerItems.get(position);
            ItemViewHoder itemViewHoder = (ItemViewHoder) viewHolder;
            itemViewHoder.mImgIcon.setImageResource(item.getIcon());
            itemViewHoder.mTvContent.setText(item.getContent());
            itemViewHoder.mTvContent.setTextColor(mContext.getResources().getColorStateList(R.color.color_content));
            itemViewHoder.mLlItem.setSelected(item.getIsChecked());
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.mTvEmail.setText(drawerItems.get(position).getContent());
            if (drawerItems.get(position).getAvatar() != null) {
                headerViewHolder.mImgAvatar.setImageURI(drawerItems.get(position).getAvatar());
            } else if (drawerItems.get(position).getAvatarBitmap() != null) {
                headerViewHolder.mImgAvatar.setImageBitmap(drawerItems.get(position).getAvatarBitmap());
            } else {
                headerViewHolder.mImgAvatar.setImageResource(drawerItems.get(position).getIcon());
            }
        }
    }

    @Override
    public int getItemCount() {
        return drawerItems.size();
    }

    class ItemViewHoder extends RecyclerView.ViewHolder {
        private ImageView mImgIcon;
        private TextView mTvContent;
        private LinearLayout mLlItem;

        ItemViewHoder(@NonNull View itemView) {
            super(itemView);
            mImgIcon = itemView.findViewById(R.id.imgIcon);
            mTvContent = itemView.findViewById(R.id.tvContent);
            mLlItem = itemView.findViewById(R.id.llItem);
            itemView.setOnClickListener(v -> mOnItemClickListener.onItemClicked(getLayoutPosition())
            );
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImgAvatar;
        private TextView mTvEmail;

        private HeaderViewHolder(View view) {
            super(view);
            mImgAvatar = view.findViewById(R.id.imgAvatar);
            mTvEmail = view.findViewById(R.id.tvEmail);
            mImgAvatar.setOnClickListener(v -> mOnItemClickListener.onAvatarClicked()
            );
        }
    }
}
