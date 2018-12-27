package asiantech.internship.summer.drawer;

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
    private List<DrawerItem> mDrawerItem;

    DrawerAdapter(List<DrawerItem> drawerItems, DrawerLayoutActivity onClickItem) {
        mDrawerItem = drawerItems;
        mOnClickItem = onClickItem;
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
            ((ItemViewHolder) holder).onBind(position);
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBind(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItem.size();
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
        private TextView mGmail;
        private ImageView mImgCheck;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mImgAvt = itemView.findViewById(R.id.imgAvt);
            mGmail = itemView.findViewById(R.id.tvGmail);
            mImgCheck = itemView.findViewById(R.id.imgIconCheck);
            this.setIsRecyclable(false);
        }
        private void onBind(int position){
            DrawerItem object = mDrawerItem.get(position);

            if (mDrawerItem.get(position).getAvatarUri() != null) {
                mImgAvt.setImageURI(object.getAvatarUri());
            } else if (mDrawerItem.get(position).getAvtBitmap() != null) {
                mImgAvt.setImageBitmap(object.getAvtBitmap());
            } else {
                mImgAvt.setImageResource(object.getIconResource());
            }
            mImgAvt.setOnClickListener(v -> mOnClickItem.onAvatarClicked());
            mGmail.setText(object.getGmail());
            mImgCheck.setImageResource(object.getImgCheck());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIconImage;
        private TextView mTitle;
        private LinearLayout mLlItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIconImage = itemView.findViewById(R.id.imgIcon);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mLlItem = itemView.findViewById(R.id.llItem);
        }
        private void onBind(int position){
            mIconImage.setImageResource(mDrawerItem.get(position).getIconImage());
            mTitle.setText(mDrawerItem.get(position).getCat());

            mLlItem.setSelected(mDrawerItem.get(position).isChecked());
            mTitle.setTextColor(itemView.getContext().getResources().getColorStateList(R.color.color_content));
            mLlItem.setOnClickListener(view -> mOnClickItem.onItemClicked(position));
        }
    }
}
