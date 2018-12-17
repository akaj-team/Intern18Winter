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
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelinePagerItem;

public class FavouritePagerAdapter extends RecyclerView.Adapter<FavouritePagerAdapter.FavouritePagerViewHolder> {
    private List<TimelinePagerItem> mFavourites;
    private Context mContext;

    FavouritePagerAdapter(Context context, List<TimelinePagerItem> favourites) {
        this.mFavourites = favourites;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FavouritePagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.fragment_recycler_view_pager, viewGroup, false);
        return new FavouritePagerViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritePagerViewHolder holder, int position) {
        TimelinePagerItem timeLineItem = mFavourites.get(position);
        Drawable drawableAvatar = mContext.getResources().getDrawable(mContext.getResources()
                .getIdentifier(timeLineItem.getAvatar(), "drawable", mContext.getPackageName()));
        Drawable drawableImage = mContext.getResources().getDrawable(mContext.getResources()
                .getIdentifier(timeLineItem.getImage(), "drawable", mContext.getPackageName()));
        holder.mImgAvatar.setImageDrawable(drawableAvatar);
        holder.mImgImage.setImageDrawable(drawableImage);
        holder.mTvName.setText(timeLineItem.getName());
        String numOfLike;
        if (timeLineItem.getNumOfLike() == 0) {
            holder.mTvNumOfLike.setText(String.valueOf(0));
        } else {
            numOfLike = " " + timeLineItem.getNumOfLike() + " " + mContext.getString(R.string.like);
            holder.mTvNumOfLike.setText(numOfLike);
        }
        holder.mTvDescription.setText(Html.
                fromHtml("<b>" + timeLineItem.getName() + "</b>" + "  " + timeLineItem.getDescription()));
    }

    @Override
    public int getItemCount() {
        return mFavourites.size();
    }

    class FavouritePagerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNumOfLike;
        private TextView mTvName;
        private TextView mTvDescription;
        private ImageView mImgAvatar;
        private ImageView mImgImage;
        //private ImageView mImgFavourite;

        FavouritePagerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mTvNumOfLike = itemView.findViewById(R.id.tvNumerLike);
            mTvName = itemView.findViewById(R.id.tvName);
            mImgImage = itemView.findViewById(R.id.imgImage);
            //ImageView mImgFavourite = itemView.findViewById(R.id.imgFavourite);
        }

    }
}
