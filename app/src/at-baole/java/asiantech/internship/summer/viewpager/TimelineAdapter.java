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

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private List<TimelineItem> mTimelineItems;
    private OnItemListener mListener;
    private Context mContext;


    TimelineAdapter(List<TimelineItem> listUsers, OnItemListener listener, Context context) {
        this.mTimelineItems = listUsers;
        this.mListener = listener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        holder.bindView(mTimelineItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mTimelineItems.size();
    }


    interface OnItemListener {
        void onClickLike(int position);
    }

    class TimelineViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private TextView mTvUsername;
        private ImageView mImgPictures;
        private TextView mTvCountLike;
        private ImageButton mImgBtnLike;
        private TextView mTvCommenter;
        private TextView mTvComment;

        private TimelineViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mImgPictures = itemView.findViewById(R.id.imgPicture);
            mTvCountLike = itemView.findViewById(R.id.tvCountLike);
            mImgBtnLike = itemView.findViewById(R.id.imgBtnLike);
            mTvCommenter = itemView.findViewById(R.id.tvCommenter);
            mTvComment = itemView.findViewById(R.id.tvComment);
            favouriteEvent();
        }

        @SuppressLint("SetTextI18n")
        private void bindView(TimelineItem timelineItem) {

            mImgAvatar.setImageResource(timelineItem.getAvatar());
            mTvUsername.setText(timelineItem.getUsername());
            mImgPictures.setImageResource(timelineItem.getPicture());
            mTvCommenter.setText(timelineItem.getCommenter());
            mTvComment.setText(timelineItem.getComment());
            likeStatus(timelineItem);
        }

        private void favouriteEvent() {
            mImgBtnLike.setOnClickListener(view -> {
                int position = getLayoutPosition();
                mListener.onClickLike(position);
                notifyDataSetChanged();
            });
        }

        private void likeStatus(TimelineItem timelineItem){
            if (timelineItem.isChecked()) {
                mTvCountLike.setSelected(true);
                mTvCountLike.setText(R.string.liked);
                mTvCountLike.setTextColor(mContext.getResources().getColorStateList(R.color.bg_like_color));
                mImgBtnLike.setBackgroundResource(R.drawable.ic_favorite_border_cyan_300_18dp);
            } else {
                mTvCountLike.setSelected(false);
                mTvCountLike.setText(R.string.like);
                mImgBtnLike.setBackgroundResource(R.drawable.ic_favorite_border_black_18dp);
            }
        }
    }
}
