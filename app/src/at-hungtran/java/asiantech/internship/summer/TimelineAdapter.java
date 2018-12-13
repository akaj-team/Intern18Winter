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

import asiantech.internship.summer.model.TimelineItem;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private List<TimelineItem> mTimelineItems;

    TimelineAdapter(List<TimelineItem> timelineItems, Context context) {
        this.mTimelineItems = timelineItems;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TimelineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        holder.mImgAvt.setImageResource(mTimelineItems.get(position).getImageAvt());
        holder.mTvName.setText(mTimelineItems.get(position).getName());
        holder.mImgImage.setImageResource(mTimelineItems.get(position).getImage());

        holder.mImgBtnLike.setOnClickListener(view -> {
            mTimelineItems.get(position).setLike(mTimelineItems.get(position).getLike() + 1);
            if (mTimelineItems.get(position).getLike() == 1) {
                holder.mTvLike.setText(holder.itemView.getContext().getString(R.string.like, mTimelineItems.get(position).getLike()));
            } else {
                holder.mTvLike.setText(holder.itemView.getContext().getString(R.string.likes, mTimelineItems.get(position).getLike()));
            }
        });
        holder.mTvCommenterName.setText(mTimelineItems.get(position).getCommenterName());
        holder.mTvDescription.setText(mTimelineItems.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mTimelineItems.size();
    }

    class TimelineViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvt;
        private TextView mTvName;
        private ImageView mImgImage;
        private ImageButton mImgBtnLike;
        private TextView mTvDescription;
        private TextView mTvLike;
        private TextView mTvCommenterName;

        TimelineViewHolder(View itemView) {
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
