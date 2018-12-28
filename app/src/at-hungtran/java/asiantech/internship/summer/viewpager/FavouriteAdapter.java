package asiantech.internship.summer.viewpager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.TimelineItem;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private List<TimelineItem> mTimelineItems;

    FavouriteAdapter(List<TimelineItem> timelineItems) {
        this.mTimelineItems = timelineItems;
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        TimelineItem item = mTimelineItems.get(position);
        holder.mImgAvt.setImageResource(item.getImageAvt());
        holder.mTvName.setText(item.getName());
        holder.mImgImage.setImageResource(item.getImage());
        String numOfLike;
        if (item.getLike() == 0) {
            holder.mTvLike.setText(String.valueOf(0));
        } else if (item.getLike() == 1) {
            holder.mTvLike.setText(holder.itemView.getContext().getString(R.string.like, item.getLike()));
        }
        holder.mTvCommenterName.setText(item.getCommenterName());
        holder.mTvDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return mTimelineItems.size();
    }

    public interface onClick {
        void likeClick(int position);
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvt;
        private TextView mTvName;
        private ImageView mImgImage;
        private ImageButton mImgBtnLike;
        private TextView mTvDescription;
        private TextView mTvLike;
        private TextView mTvCommenterName;

        FavouriteViewHolder(View itemView) {
            super(itemView);
            mImgAvt = itemView.findViewById(R.id.icAvt);
            mTvName = itemView.findViewById(R.id.tvName);
            mImgImage = itemView.findViewById(R.id.imgImage);
            mImgBtnLike = itemView.findViewById(R.id.imgBtnLike);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mTvLike = itemView.findViewById(R.id.tvLike);
            mTvCommenterName = itemView.findViewById(R.id.tvCommenterName);
        }

    }
}
