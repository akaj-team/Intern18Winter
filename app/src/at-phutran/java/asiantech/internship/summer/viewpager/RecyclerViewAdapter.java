package asiantech.internship.summer.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.User;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<User> mListUsers;
    private Context mContext;
    private OnclickLike mOnclickLike;

    RecyclerViewAdapter(List<User> listUsers, Context context, OnclickLike onclickLike) {
        this.mListUsers = listUsers;
        this.mContext = context;
        this.mOnclickLike = onclickLike;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mListUsers.get(position);
        holder.imgAvatar.setImageResource(user.getAvatar());
        holder.tvUserComment.setText(user.getUsername());
        holder.imgCook.setImageResource(user.getImage());
        holder.imgLike.setSelected(user.isHasLiked());
        holder.tvUsername.setText(user.getUsername());
        holder.tvComment.setText(user.getComment());
        if (user.isHasLiked()) {
            holder.imgLike.setImageResource(R.drawable.ic_favorite_red_500_24dp);
        } else {
            holder.imgLike.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }

    public interface OnclickLike {
        void eventLike(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLike;
        ImageView imgAvatar;
        TextView tvUsername;
        TextView tvUserComment;
        TextView tvComment;
        ImageView imgCook;

        ViewHolder(View itemView) {
            super(itemView);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            tvUsername = itemView.findViewById(R.id.tvUser);
            imgCook = itemView.findViewById(R.id.imgCook);
            tvUserComment = itemView.findViewById(R.id.tvUserComment);
            tvComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
            handleEvent();
        }

        private void handleEvent() {
            imgLike.setOnClickListener(view -> {
                if (mOnclickLike == null) {
                    return;
                }
                int position = getLayoutPosition();
                mOnclickLike.eventLike(position);
            });
        }
    }
}
