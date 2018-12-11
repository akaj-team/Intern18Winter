package asiantech.internship.summer.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Data;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Data> mData;

    NewsAdapter(List<Data> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.single_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.mLinearLayout.setBackgroundColor(Color.parseColor(mData.get(position).getColor()));
        holder.mTvTitle.setText(mData.get(position).getTitle());
        holder.mImgIcon.setImageResource(mData.get(position).getmImageIcon());
        holder.mTvPoster.setText(mData.get(position).getmPoster());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLinearLayout;
        private TextView mTvTitle;
        private CircleImageView mImgIcon;
        private TextView mTvPoster;

        NewsViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.linearlayout);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mImgIcon = itemView.findViewById(R.id.imgIcon);
            mTvPoster = itemView.findViewById(R.id.tvPoster);
        }
    }
}
