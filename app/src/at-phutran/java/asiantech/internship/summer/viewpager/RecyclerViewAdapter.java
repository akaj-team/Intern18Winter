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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
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
        holder.mImgAvatar.setImageResource(user.getAvatar());
        holder.mTvUserComment.setText(user.getUsername());
        holder.mImageCook.setImageResource(user.getImage());
        holder.mImgLike.setSelected(user.isFavourite());
        holder.mTvUsername.setText(user.getUsername());
        holder.mTvComment.setText(user.getComment());
        if(user.isFavourite()){
            holder.mImgLike.setImageResource(R.drawable.ic_favorite_red_500_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgLike;
        private ImageView mImgAvatar;
        private TextView mTvUsername;
        private TextView mTvUserComment;
        private TextView mTvComment;
        private ImageView mImageCook;
        ViewHolder(View itemView) {
            super(itemView);
            mImgLike = itemView.findViewById(R.id.imgLike);
            mImgAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUser);
            mImageCook = itemView.findViewById(R.id.imgCook);
            mTvUserComment = itemView.findViewById(R.id.tvUserComment);
            mTvComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
            handleEvent();
        }
        private void handleEvent() {
            mImgLike.setOnClickListener(view -> {
                if (mOnclickLike == null) {
                    return;
                }
                int position = getLayoutPosition();
                mOnclickLike.eventLike(position);
            });
        }
    }
    public interface OnclickLike {
        void eventLike(int position);
    }
}
