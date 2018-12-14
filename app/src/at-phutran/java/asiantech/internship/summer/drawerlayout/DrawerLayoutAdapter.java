package asiantech.internship.summer.drawerlayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Data;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Onclick mOnclick;
    private List<Data> mItems;
    public DrawerLayoutAdapter(List<Data> itemObjects, Onclick onclick) {
        this.mOnclick = onclick;
        this.mItems = itemObjects;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_drawer, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Data mData = mItems.get(position);
        if(holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).imageHeader.setImageResource((mData.getmIcon()));
            ((HeaderViewHolder) holder).headerTitle.setText(mData.getmContent());
            ((HeaderViewHolder) holder).imageHeader.setOnClickListener(view -> {
                mOnclick.onclickAvatar();

            });
        }else if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).itemImage.setImageResource(mData.getmIcon());
            ((ItemViewHolder) holder).itemContent.setText(mData.getmContent());
            ((ItemViewHolder) holder).linearLayout.setSelected(mData.getmIsChecked());
            ((ItemViewHolder) holder).linearLayout.setOnClickListener(view -> {
                mOnclick.changeSelect(position);
                notifyDataSetChanged();
            });
        }
    }
    private Data getItem(int position) {
        return mItems.get(position);
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
    public interface Onclick{
        void onclickAvatar();
        void changeSelect(int i);
    }
}
