package asiantech.internship.summer.drawer;

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
import asiantech.internship.summer.model.DrawerItem;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private OnItemClickListener mOnclickItem;
    private List<DrawerItem> mDrawerItem;
    private Context mContext;

    DrawerAdapter(List<DrawerItem> drawerItems, Context context, DrawerLayoutActivity onClickItem) {
        this.mDrawerItem = drawerItems;
        this.mOnclickItem = onClickItem;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            View itemView = layoutInflater.inflate(R.layout.list_item_layout, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = layoutInflater.inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mIconImage.setImageResource(mDrawerItem.get(position).getIconImage());
            ((ItemViewHolder) holder).mTitle.setText(mDrawerItem.get(position).getCat());

            ((ItemViewHolder) holder).mLlItem.setSelected(mDrawerItem.get(position).isChecked());
            ((ItemViewHolder) holder).mTitle.setTextColor(mContext.getResources().getColorStateList(R.color.color_content));
            ((ItemViewHolder) holder).mLlItem.setOnClickListener(view -> mOnclickItem.onItemClicked(position));
        } else if (holder instanceof HeaderViewHolder) {
            DrawerItem object = mDrawerItem.get(0);

            if (mDrawerItem.get(position).getAvatarUri() != null) {
                ((HeaderViewHolder) holder).mImgAvt.setImageURI(object.getAvatarUri());
            } else if (mDrawerItem.get(position).getAvtBitmap() != null) {
                ((HeaderViewHolder) holder).mImgAvt.setImageBitmap(object.getAvtBitmap());
            } else {
                ((HeaderViewHolder) holder).mImgAvt.setImageResource(object.getImgAvt());
            }
            ((HeaderViewHolder) holder).mImgAvt.setOnClickListener(v -> mOnclickItem.onAvatarClicked());
            ((HeaderViewHolder) holder).mGmail.setText(object.getGmail());
            ((HeaderViewHolder) holder).mImgCheck.setImageResource(object.getImgCheck());
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
            mImgCheck = itemView.findViewById(R.id.icCheck);
            this.setIsRecyclable(false);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIconImage;
        private TextView mTitle;
        private LinearLayout mLlItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIconImage = itemView.findViewById(R.id.icImg);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mLlItem = itemView.findViewById(R.id.llItem);
            this.setIsRecyclable(false);
        }
    }
}
