package asiantech.internship.summer;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Data> data;

    NewsAdapter(List<Data> data) {
        this.data = data;
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
        holder.linearLayout.setBackgroundColor(Color.parseColor(data.get(position).getmColor()));
        holder.tvTitle.setText(data.get(position).getmTitle());
        holder.ciImgIcon.setImageResource(data.get(position).getmImageIcon());
        holder.tvPoster.setText(data.get(position).getmPoster());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView tvTitle;
        CircleImageView ciImgIcon;
        TextView tvPoster;

        NewsViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearlayout);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ciImgIcon = itemView.findViewById(R.id.imgIcon);
            tvPoster = itemView.findViewById(R.id.tvPoster);
            this.setIsRecyclable(false);
        }
    }
}
