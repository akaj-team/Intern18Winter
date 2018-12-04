package asiantech.internship.summer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.models.TimelineItem;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TimelineItem> timelines;
    private Context mContext;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int mChildCount;
    private int mTotalItemCount;
    private int mFirstVisible;
    private String mCountLike;

    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private ViewHolderLoading(View view) {
            super(view);
            progressBar = view.findViewById(R.id.itemProgressbar);
        }
    }

    private class TimelineViewHolder extends RecyclerView.ViewHolder {
        private TextView mCountLike;
        private TextView mName;
        private TextView mDescription;
        private ImageView mAvatar;
        private ImageView mImage;
        private ImageView mFavourite;

        TimelineViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.imgAvatar);
            mDescription = itemView.findViewById(R.id.tvDescription);
            mCountLike = itemView.findViewById(R.id.tvNumerLike);
            mName = itemView.findViewById(R.id.tvName);
            mImage = itemView.findViewById(R.id.imgImage);
            mFavourite = itemView.findViewById(R.id.imgFavourite);
        }
    }

    @Override
    public int getItemCount() {
        return isLoading ? timelines.size() + 1 : timelines.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == timelines.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    TimelineAdapter(Context context, List<TimelineItem> timelines, RecyclerView recyclerView) {
        this.timelines = timelines;
        this.mContext = context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = linearLayoutManager.getItemCount();
                mChildCount = linearLayoutManager.getChildCount();
                mFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (mFirstVisible + mChildCount == mTotalItemCount) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == VIEW_TYPE_ITEM) {
            View itView = layoutInflater.inflate(R.layout.fragment_recycler_view_pager, viewGroup, false);
            return new TimelineViewHolder(itView);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View itView = layoutInflater.inflate(R.layout.item_progressbar, viewGroup, false);
            return new ViewHolderLoading(itView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TimelineViewHolder) {
            TimelineItem timelineItem = timelines.get(position);
            TimelineViewHolder timelineViewHolder = (TimelineViewHolder) holder;
            Drawable drawableAvatar = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier(timelineItem.getmAvatar(), "drawable", mContext.getPackageName()));
            Drawable drawableImage = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier(timelineItem.getmImage(), "drawable", mContext.getPackageName()));
            timelineViewHolder.mAvatar.setImageDrawable(drawableAvatar);
            timelineViewHolder.mImage.setImageDrawable(drawableImage);
            timelineViewHolder.mName.setText(timelineItem.getmName());
            if (timelineItem.getmCountLike() == 0) {
                timelineViewHolder.mCountLike.setText("");
            } else if (timelineItem.getmCountLike() == 1) {
                mCountLike = timelineItem.getmCountLike() + mContext.getString(R.string.like);
                timelineViewHolder.mCountLike.setText(mCountLike);
            } else {
                mCountLike = timelineItem.getmCountLike() + mContext.getString(R.string.likes);
                timelineViewHolder.mCountLike.setText(mCountLike);
            }
            timelineViewHolder.mDescription.setText(Html.fromHtml("<b>" + timelineItem.getmName() + "</b>" + "  " + timelineItem.getmDescription()));
            timelineViewHolder.mFavourite.setOnClickListener(view -> {
                timelineItem.setmCountLike(timelineItem.getmCountLike() + 1);
                if (timelineItem.getmCountLike() < 2) {
                    mCountLike = timelineItem.getmCountLike() + mContext.getString(R.string.like);
                    timelineViewHolder.mCountLike.setText(mCountLike);
                } else {
                    mCountLike = timelineItem.getmCountLike() + mContext.getString(R.string.likes);
                    timelineViewHolder.mCountLike.setText(mCountLike);
                }
            });

        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded(Boolean value) {
        isLoading = value;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void add(int position, TimelineItem timelineItem) {
        timelines.add(position, timelineItem);
        notifyItemInserted(position);
    }
    public void clear() {
        timelines.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<TimelineItem> list) {
        timelines.addAll(list);
        notifyDataSetChanged();
    }
}
