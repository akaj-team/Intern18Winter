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
    private List<User> mListUsers;
    private Context mContext;


    public FavoriteAdapter(List<User> mUsers, Context applicationContext) {
        this.mListUsers = mUsers;
        this.mContext = applicationContext;
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
        holder.mImgAvatar.setImageResource(user.getAvatar());
        holder.mTvUserComment.setText(user.getUsername());
        holder.mImageCook.setImageResource(user.getImage());
        holder.mTvUsername.setText(user.getUsername());
        holder.mTvComment.setText(user.getComment());
    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private TextView mTvUsername;
        private TextView mTvUserComment;
        private TextView mTvComment;
        private ImageView mImageCook;
        ViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUser);
            mImageCook = itemView.findViewById(R.id.imgCook);
            mTvUserComment = itemView.findViewById(R.id.tvUserComment);
            mTvComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
        }
    }
}
