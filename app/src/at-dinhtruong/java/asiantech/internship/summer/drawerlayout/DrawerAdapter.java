package asiantech.internship.summer.drawerlayout;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.DrawerItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<DrawerItem> mDrawerItems;
    private OnItemClickListener mOnItemClickListener;
    private Uri mSelectedImage;

    public interface OnItemClickListener {
        void onAvatarClicked();

        void onItemClicked(int position);
    }

    DrawerAdapter(List<DrawerItem> drawerItems, OnItemClickListener onItemClickListener) {
        mDrawerItems = drawerItems;
        mOnItemClickListener = onItemClickListener;
    }

    void setSetImageUri(Uri selectedImage) {
        mSelectedImage = selectedImage;
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size() + 1;
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
            View itView = layoutInflater.inflate(R.layout.drawer_item_header, viewGroup, false);
            return new HeaderViewHolder(itView);
        }
        View itView = layoutInflater.inflate(R.layout.drawer_item, viewGroup, false);
        return new ItemViewHoder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHoder) {
            ((ItemViewHoder) viewHolder).onBind();
        } else if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).onBind();
        }
    }

    class ItemViewHoder extends RecyclerView.ViewHolder {
        private ImageView mImgIcon;
        private TextView mTvTitle;

        ItemViewHoder(@NonNull View itemView) {
            super(itemView);
            mImgIcon = itemView.findViewById(R.id.imgIcon);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(v -> mOnItemClickListener.onItemClicked(getAdapterPosition()));
        }

        private void onBind() {
            DrawerItem item = mDrawerItems.get(getAdapterPosition()-1);
            mImgIcon.setImageResource(item.getIcon());
            mTvTitle.setText(item.getTitle());
            mTvTitle.setTextColor(itemView.getContext().getResources().getColorStateList(R.color.color_content));
            itemView.setSelected(item.getIsChecked());
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImgAvatar;
        private TextView mTvEmail;

        HeaderViewHolder(View view) {
            super(view);
            mImgAvatar = view.findViewById(R.id.imgAvatar);
            mTvEmail = view.findViewById(R.id.tvEmail);
            mImgAvatar.setOnClickListener(v -> mOnItemClickListener.onAvatarClicked());
        }

        private void onBind() {
            mTvEmail.setText(R.string.dinhTruongAsiantechVn);
            if (mSelectedImage != null) {
                mImgAvatar.setImageURI(mSelectedImage);
            } else {
                mImgAvatar.setImageResource(R.drawable.img_avatar);
            }
        }
    }
}
