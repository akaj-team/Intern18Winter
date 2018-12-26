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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    private List<User> mListUser;
    private Context mContext;

    FavoriteAdapter(List<User> mUsers, Context context) {
        mListUser = mUsers;
        mContext = context;
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
        User user = mListUser.get(position);
        holder.imgAvatar.setImageResource(user.getAvatar());
        holder.tvUserComment.setText(user.getUsername());
        holder.imgCook.setImageResource(user.getImage());
        holder.tvUsername.setText(user.getUsername());
        holder.tvComment.setText(user.getComment());
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUsername;
        TextView tvUserComment;
        TextView tvComment;
        ImageView imgCook;
        ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            tvUsername = itemView.findViewById(R.id.tvUser);
            imgCook = itemView.findViewById(R.id.imgCook);
            tvUserComment = itemView.findViewById(R.id.tvUserComment);
            tvComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
        }
    }
}
