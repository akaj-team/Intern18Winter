package asiantech.internship.summer.viewpager;

import android.annotation.SuppressLint;
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

import asiantech.internship.summer.R;
import asiantech.internship.summer.recyclerview.model.TimelineItem;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private List<TimelineItem> mTimelineItems;
    private Context mContext;

    FavouriteAdapter(List<TimelineItem> mTimelineItems, Context context) {
        this.mTimelineItems = mTimelineItems;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        holder.bindView(mTimelineItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mTimelineItems.size();
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private TextView mTvUsername;
        private ImageView mImgPictures;
        private TextView mTvCommenter;
        private TextView mTvComment;
        private TextView mTvCountLike;
        private ImageButton mImgBtnLike;

        FavouriteViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mImgPictures = itemView.findViewById(R.id.imgPicture);
            mTvCommenter = itemView.findViewById(R.id.tvCommenter);
            mTvComment = itemView.findViewById(R.id.tvComment);
            mTvCountLike = itemView.findViewById(R.id.tvCountLike);
            mImgBtnLike = itemView.findViewById(R.id.imgBtnLike);
        }

        @SuppressLint("SetTextI18n")
        private void bindView(TimelineItem timelineItem) {
            mImgAvatar.setImageResource(timelineItem.getAvatar());
            mTvUsername.setText(timelineItem.getUsername());
            mImgPictures.setImageResource(timelineItem.getPicture());
            mTvCommenter.setText(timelineItem.getCommenter());
            mTvComment.setText(timelineItem.getComment());
            if (timelineItem.isChecked()) {
                mTvCountLike.setSelected(true);
                mTvCountLike.setText(R.string.liked);
                mImgBtnLike.setBackgroundResource(R.drawable.ic_favorite_border_cyan_300_18dp);
                mTvCountLike.setTextColor(mContext.getResources().getColorStateList(R.color.bg_like_color));
            } else {
                mTvCountLike.setSelected(false);
                mTvCountLike.setText(R.string.like);
            }
        }
    }
}
