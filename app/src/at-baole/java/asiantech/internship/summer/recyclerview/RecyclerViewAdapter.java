package asiantech.internship.summer.recyclerview;

import android.annotation.SuppressLint;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TimelineViewHolder> {
    private List<TimelineItem> mTimelineItems;
    private OnItemListener mListener;


    RecyclerViewAdapter(List<TimelineItem> listUsers, OnItemListener listener) {
        this.mTimelineItems = listUsers;
        this.mListener = listener;
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
        private static final String TEXT_LIKE_SINGULAR = "like";
        private static final String TEXT_LIKE_PLURAL = "likes";
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
            this.setIsRecyclable(false);
            mImgBtnLike.setOnClickListener(view -> mListener.onClickLike(getAdapterPosition()));
        }

        @SuppressLint("SetTextI18n")
        private void bindView(TimelineItem timelineItem) {
            if (timelineItem == null) {
                mImgAvatar.setImageResource(R.drawable.img_avatar_default);
                mTvUsername.setText(R.string.username);
                mImgPictures.setImageResource(R.drawable.img_food_1);
                mTvCommenter.setText(R.string.username);
                mTvComment.setText(R.string.comment);

            } else {
                mImgAvatar.setImageResource(timelineItem.getAvatar());
                mTvUsername.setText(timelineItem.getUsername());
                mImgPictures.setImageResource(timelineItem.getPicture());
                mTvCommenter.setText(timelineItem.getCommenter());
                mTvComment.setText(timelineItem.getComment());
                if (timelineItem.getCountLike() < 2) {
                    mTvCountLike.setText(timelineItem.getCountLike() + " " + TEXT_LIKE_SINGULAR);
                } else {
                    mTvCountLike.setText(timelineItem.getCountLike() + " " + TEXT_LIKE_PLURAL);
                }
            }
        }
    }
}
