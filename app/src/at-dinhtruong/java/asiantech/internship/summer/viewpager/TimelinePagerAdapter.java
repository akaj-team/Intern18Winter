package asiantech.internship.summer.viewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelinePagerItem;

public class TimelinePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private List<TimelinePagerItem> mTimelines;
    private Context mContext;
    private boolean mIsLoading;
    private onClickItem mOnClickItem;

    TimelinePagerAdapter(Context context, List<TimelinePagerItem> timeLines, onClickItem onClickItem) {
        this.mTimelines = timeLines;
        this.mContext = context;
        this.mOnClickItem = onClickItem;
    }

    TimelinePagerAdapter(Context context, List<TimelinePagerItem> timelinePagerItems) {
        this.mTimelines = timelinePagerItems;
        this.mContext = context;
    }

    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;

        private ViewHolderLoading(View view) {
            super(view);
            mProgressBar = view.findViewById(R.id.itemProgressbar);
        }
    }

    private class TimelineViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNumOfLike;
        private TextView mTvName;
        private TextView mTvDescription;
        private ImageView mImgAvatar;
        private ImageView mImgImage;
        private ImageView mImgFavourite;

        TimelineViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mTvNumOfLike = itemView.findViewById(R.id.tvNumerLike);
            mTvName = itemView.findViewById(R.id.tvName);
            mImgImage = itemView.findViewById(R.id.imgImage);
            mImgFavourite = itemView.findViewById(R.id.imgFavourite);
            favouriteEvent();
        }

        private void favouriteEvent() {
            mImgFavourite.setOnClickListener(view -> {
                int position = getLayoutPosition();
                mOnClickItem.onSelectItem(position);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return mIsLoading ? mTimelines.size() + 1 : mTimelines.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == mTimelines.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == VIEW_TYPE_ITEM) {
            View itView = layoutInflater.inflate(R.layout.fragment_recycler_view_pager, viewGroup, false);
            return new TimelineViewHolder(itView);
        }
        View itView = layoutInflater.inflate(R.layout.item_progressbar, viewGroup, false);
        return new ViewHolderLoading(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TimelineViewHolder) {
            TimelinePagerItem timeLineItem = mTimelines.get(position);
            TimelineViewHolder timelineViewHolder = (TimelineViewHolder) holder;
            Drawable drawableAvatar = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier(timeLineItem.getAvatar(), "drawable", mContext.getPackageName()));
            Drawable drawableImage = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier(timeLineItem.getImage(), "drawable", mContext.getPackageName()));
            timelineViewHolder.mImgAvatar.setImageDrawable(drawableAvatar);
            timelineViewHolder.mImgImage.setImageDrawable(drawableImage);
            timelineViewHolder.mTvName.setText(timeLineItem.getName());
            String numOfLike;
            if (timeLineItem.getNumOfLike() == 0) {
                timelineViewHolder.mTvNumOfLike.setText(String.valueOf(0));
            } else {
                numOfLike = " " + timeLineItem.getNumOfLike() + " " + mContext.getString(R.string.like);
                timelineViewHolder.mTvNumOfLike.setText(numOfLike);
            }
            timelineViewHolder.mTvDescription.setText(Html.fromHtml("<b>" + timeLineItem.getName() + "</b>" + "  " + timeLineItem.getDescription()));

        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.mProgressBar.setIndeterminate(true);
        }
    }

    void setLoaded(Boolean check) {
        mIsLoading = check;
    }

    public void add(int position, TimelinePagerItem timelineItem) {
        mTimelines.add(position, timelineItem);
        notifyItemInserted(position);
    }

    void clear() {
        mTimelines.clear();
        notifyDataSetChanged();
    }

    void addAll(List<TimelinePagerItem> timelineItems) {
        mTimelines.addAll(timelineItems);
        notifyDataSetChanged();
    }

    public interface onClickItem {
        void onSelectItem(int position);
    }
}
