package asiantech.internship.summer;

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
import asiantech.internship.summer.models.TimelineItem;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private List<TimelineItem> timeLines;
    private Context mContext;
    private boolean isLoading;
    private onClickItem mOnClickItem;

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
        return isLoading ? timeLines.size() + 1 : timeLines.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == timeLines.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    TimelineAdapter(Context context, List<TimelineItem> timeLines, onClickItem onClickItem) {
        this.timeLines = timeLines;
        this.mContext = context;
        this.mOnClickItem = onClickItem;
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
            TimelineItem timeLineItem = timeLines.get(position);
            TimelineViewHolder timelineViewHolder = (TimelineViewHolder) holder;
            Drawable drawableAvatar = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier(timeLineItem.getAvatar(), "drawable", mContext.getPackageName()));
            Drawable drawableImage = mContext.getResources().getDrawable(mContext.getResources()
                    .getIdentifier(timeLineItem.getImage(), "drawable", mContext.getPackageName()));
            timelineViewHolder.mAvatar.setImageDrawable(drawableAvatar);
            timelineViewHolder.mImage.setImageDrawable(drawableImage);
            timelineViewHolder.mName.setText(timeLineItem.getName());
            String mCountLike;
            if (timeLineItem.getCountLike() == 0) {
                timelineViewHolder.mCountLike.setText("");
            } else if (timeLineItem.getCountLike() == 1) {
                mCountLike = " " + timeLineItem.getCountLike() + " " +mContext.getString(R.string.like);
                timelineViewHolder.mCountLike.setText(mCountLike);
            } else {
                mCountLike = " " +timeLineItem.getCountLike() + " " +  mContext.getString(R.string.likes);
                timelineViewHolder.mCountLike.setText(mCountLike);
            }
            timelineViewHolder.mDescription.setText(Html.fromHtml("<b>" + timeLineItem.getName() + "</b>" + "  " + timeLineItem.getDescription()));
            timelineViewHolder.mFavourite.setOnClickListener(view -> {
                mOnClickItem.onSelectItem(position);
                notifyDataSetChanged();
            });

        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    void setLoaded(Boolean value) {
        isLoading = value;
    }
    public void add(int position, TimelineItem timelineItem) {
        timeLines.add(position, timelineItem);
        notifyItemInserted(position);
    }

    void clear() {
        timeLines.clear();
        notifyDataSetChanged();
    }

    void addAll(List<TimelineItem> list) {
        timeLines.addAll(list);
        notifyDataSetChanged();
    }

    public interface onClickItem {
        void onSelectItem(int position);
    }
}
