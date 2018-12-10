package asiantech.internship.summer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private Context mContext;
    private List<Item> mItem;

    public ItemAdapter(List<Item> mItem, Context mContext){
        this.mItem = mItem;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ItemAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemHolder holder, int position) {
        holder.mImgAvatar.setImageResource(mItem.get(position).getmAvatar());
        holder.mTvEmail.setText(mItem.get(position).getmEmail());
        holder.mImgBtnDropdown.setImageResource(mItem.get(position).getmImgBtnDropdown());
        holder.mImgBtnItem.setImageResource(mItem.get(position).getmImgBtnItem());
        holder.mTvItem.setText(mItem.get(position).getmTvItem());
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private TextView mTvEmail;
        private ImageButton mImgBtnDropdown;
        private ImageButton mImgBtnItem;
        private TextView mTvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvEmail = itemView.findViewById(R.id.tvEmail);
            mImgBtnDropdown = itemView.findViewById(R.id.ImgBtnDropdown);
            mImgBtnItem = itemView.findViewById(R.id.ImgBtnItem);
            mTvItem = itemView.findViewById(R.id.tvItem);
        }
    }
}
