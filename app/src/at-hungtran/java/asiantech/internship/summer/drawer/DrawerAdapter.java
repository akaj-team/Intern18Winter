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
import asiantech.internship.summer.model.DataDrawer;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private onClick mOnclick;
    private List<DataDrawer> mDataDrawer;

    DrawerAdapter(List<DataDrawer> dataDrawers, Context context, onClick onClick) {
        this.mDataDrawer = dataDrawers;
        this.mOnclick = onClick;
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
            ((ItemViewHolder) holder).mIconImage.setImageResource(mDataDrawer.get(position).getIconImage());
            ((ItemViewHolder) holder).mCat.setText(mDataDrawer.get(position).getCat());
            ((ItemViewHolder) holder).mLinearLayout.setSelected(mDataDrawer.get(position).isChecked());

            ((ItemViewHolder) holder).mLinearLayout.setOnClickListener(view -> mOnclick.selectItem(position));

        } else if (holder instanceof HeaderViewHolder) {
            DataDrawer object = mDataDrawer.get(0);

            if (mDataDrawer.get(position).getAvatarUri() != null) {
                ((HeaderViewHolder) holder).mImgAvt.setImageURI(object.getAvatarUri());
            } else if (mDataDrawer.get(position).getAvtBitmap() != null) {
                ((HeaderViewHolder) holder).mImgAvt.setImageBitmap(object.getAvtBitmap());
            } else {
                ((HeaderViewHolder) holder).mImgAvt.setImageResource(object.getImgAvt());
            }
            ((HeaderViewHolder) holder).mImgAvt.setOnClickListener(v -> mOnclick.avatarClick());
            ((HeaderViewHolder) holder).mGmail.setText(object.getGmail());
            ((HeaderViewHolder) holder).mImgCheck.setImageResource(object.getImgCheck());
        }
    }

    @Override
    public int getItemCount() {
        return mDataDrawer.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public interface onClick {
        void avatarClick();

        void selectItem(int position);
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
        private TextView mCat;
        private LinearLayout mLinearLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIconImage = itemView.findViewById(R.id.icImg);
            mCat = itemView.findViewById(R.id.tvCat);
            mLinearLayout = itemView.findViewById(R.id.linearlayout);
            this.setIsRecyclable(false);
        }
    }
}
