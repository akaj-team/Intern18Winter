package asiantech.internship.summer.drawer;

import android.graphics.Bitmap;
import android.net.Uri;
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
import asiantech.internship.summer.model.DrawerItem;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private OnItemClickListener mOnClickItem;
    private List<DrawerItem> mDrawerItems;
    private Uri mSelectedImage;
    private Bitmap mImage;

    DrawerAdapter(List<DrawerItem> drawerItems, DrawerLayoutActivity onClickItem) {
        mDrawerItems = drawerItems;
        mOnClickItem = onClickItem;
    }

    void setAvatarUri(Uri selectedImage) {
        this.mSelectedImage = selectedImage;
    }

    void setAvartarBitmap(Bitmap image) {
        this.mImage = image;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            View itemView = layoutInflater.inflate(R.layout.drawer_item, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = layoutInflater.inflate(R.layout.drawer_item_header, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).onBind();
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBind();
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public interface OnItemClickListener {
        void onAvatarClicked();

        void onItemClicked(int position);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvt;
        private TextView mTvGmail;
        private ImageView mImgCheck;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mImgAvt = itemView.findViewById(R.id.imgAvt);
            mTvGmail = itemView.findViewById(R.id.tvGmail);
            mImgCheck = itemView.findViewById(R.id.imgIconCheck);
            mImgAvt.setOnClickListener(v -> mOnClickItem.onAvatarClicked());
        }

        private void onBind() {
            if (mSelectedImage != null) {
                mImgAvt.setImageURI(mSelectedImage);
            } else if (mImage != null) {
                mImgAvt.setImageBitmap(mImage);
            }
            mTvGmail.setText(R.string.gmail);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIconImage;
        private TextView mTvTitle;
        private LinearLayout mLlItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIconImage = itemView.findViewById(R.id.imgIcon);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mLlItem = itemView.findViewById(R.id.llItem);
            mLlItem.setOnClickListener(view -> mOnClickItem.onItemClicked(getAdapterPosition()));
        }

        private void onBind() {
            DrawerItem drawerItem = mDrawerItems.get(getAdapterPosition());
            mIconImage.setImageResource(drawerItem.getIconResource());
            mTvTitle.setText(drawerItem.getTitle());
            mLlItem.setSelected(drawerItem.isChecked());
            mTvTitle.setTextColor(itemView.getContext().getResources().getColorStateList(R.color.color_content));
        }
    }
}
