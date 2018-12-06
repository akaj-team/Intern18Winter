package asiantech.internship.summer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.model.TimelineItem;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private List<TimelineItem> timelineItems;

    TimelineAdapter(List<TimelineItem> timelineItems, Context context) {
        this.timelineItems = timelineItems;
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
        holder.imgAvt.setImageResource(timelineItems.get(position).getmImageAvt());
        holder.tvName.setText(timelineItems.get(position).getmName());
        holder.imgImage.setImageResource(timelineItems.get(position).getmImage());

        holder.btnLike.setOnClickListener(view -> {
            timelineItems.get(position).setmLike(timelineItems.get(position).getmLike() + 1);
            if (timelineItems.get(position).getmLike() == 1) {
                holder.tvLike.setText(holder.itemView.getContext().getString(R.string.like, timelineItems.get(position).getmLike()));
            } else {
                holder.tvLike.setText(holder.itemView.getContext().getString(R.string.likes, timelineItems.get(position).getmLike()));
            }
        });
        holder.tvDescription.setText(timelineItems.get(position).getmDescription());
    }

    @Override
    public int getItemCount() {
        return timelineItems.size();
    }

    class TimelineViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvt;
        TextView tvName;
        ImageView imgImage;
        Button btnLike;
        TextView tvDescription;
        TextView tvLike;

        TimelineViewHolder(View itemView) {
            super(itemView);
            imgAvt = itemView.findViewById(R.id.ic_avt);
            tvName = itemView.findViewById(R.id.tv_name);
            imgImage = itemView.findViewById(R.id.img_image);
            btnLike = itemView.findViewById(R.id.btn_like);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvLike = itemView.findViewById(R.id.tv_like);
            this.setIsRecyclable(false);
        }
    }
}
