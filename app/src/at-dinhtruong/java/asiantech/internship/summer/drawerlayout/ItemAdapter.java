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

import asiantech.internship.summer.models.Item;
import asiantech.internship.summer.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> items;
    private Context mContext;
    private final int VIEW_TYPE_HEADER = 0;
    private IMethodCaller iMethodCaller;

    public interface IMethodCaller {
        void changeAvatarMethod(int adapterPosition);

        void selectItemMethod(int position);
    }

    ItemAdapter(List<Item> items, Context context, IMethodCaller iMethodCaller) {
        this.items = items;
        this.mContext = context;
        this.iMethodCaller = iMethodCaller;
    }

    @Override
    public int getItemViewType(int position) {
        int viewTypeItem = 1;
        return position == 0 ? VIEW_TYPE_HEADER : viewTypeItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == VIEW_TYPE_HEADER) {
            View itView = layoutInflater.inflate(R.layout.header_layout, viewGroup, false);
            return new HeaderViewHolder(itView);
        }
        View itView = layoutInflater.inflate(R.layout.item_icon, viewGroup, false);
        return new ItemViewHoder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHoder) {
            Item item = items.get(position);
            ItemViewHoder itemViewHoder = (ItemViewHoder) viewHolder;
            itemViewHoder.mImgIcon.setImageResource(item.getIcon());
            itemViewHoder.mTvContent.setText(item.getContent());
            itemViewHoder.mTvContent.setTextColor(mContext.getResources().getColorStateList(R.color.color_content));
            itemViewHoder.mLlItem.setSelected(item.getCheckSelected());
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.mTvEmail.setText(items.get(position).getContent());
            if (items.get(position).getAvatar() != null) {
                headerViewHolder.mImgAvatar.setImageURI(items.get(position).getAvatar());
            } else if (items.get(position).getAvatarBitmap() != null) {
                headerViewHolder.mImgAvatar.setImageBitmap(items.get(position).getAvatarBitmap());
            } else {
                headerViewHolder.mImgAvatar.setImageResource(items.get(position).getIcon());
            }

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
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
            mLlItem.setOnClickListener(v -> {
                        iMethodCaller.selectItemMethod(getLayoutPosition());
                        notifyDataSetChanged();
                    }
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
            mImgAvatar.setOnClickListener(v -> {
                        iMethodCaller.changeAvatarMethod(getAdapterPosition());
                        notifyDataSetChanged();
                    }
            );

        }
    }
}
