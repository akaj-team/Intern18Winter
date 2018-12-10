package asiantech.internship.summer;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> items;
    private Context mContext;
    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private IMethodCaller iMethodCaller;

    public interface IMethodCaller {
        void yourDesiredMethod();
    }
    public void updateList(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public ItemAdapter(List<Item> items, Context context, IMethodCaller iMethodCaller) {
        this.items = items;
        this.mContext = context;
        this.iMethodCaller = iMethodCaller;
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
            itemViewHoder.mImgIcon.setImageResource(item.getmIcon());
            itemViewHoder.mTvContent.setText(item.getMcontent());
            itemViewHoder.mLlItem.setOnClickListener(v -> {

            });

        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.mImgAvatar.setImageResource(items.get(position).getmIcon());
            headerViewHolder.mTvEmail.setText(items.get(position).getMcontent());
            headerViewHolder.mImgAvatar.setOnClickListener(v -> {
                        iMethodCaller.yourDesiredMethod();
                        notifyDataSetChanged();
                    }
            );

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
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImgAvatar;
        private TextView mTvEmail;

        private HeaderViewHolder(View view) {
            super(view);
            mImgAvatar = view.findViewById(R.id.imgAvatar);
            mTvEmail = view.findViewById(R.id.tvEmail);

        }
    }
}
