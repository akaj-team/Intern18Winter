package asiantech.internship.summer.recyclerview;

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

import asiantech.internship.summer.model.User;
import asiantech.internship.summer.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<User> mListUsers;
    private OnclickLike mOnclickLike;
    private Context mContext;

    RecyclerViewAdapter(List<User> mListUsers, Context mContext, OnclickLike mOnclickLike) {
        this.mListUsers = mListUsers;
        this.mContext = mContext;
        this.mOnclickLike = mOnclickLike;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mListUsers.get(position);
        holder.mImgAvatar.setImageResource(mListUsers.get(position).getAvatar());
        holder.mTvUserComment.setText(mListUsers.get(position).getUsername());
        holder.mImageCook.setImageResource(mListUsers.get(position).getImage());
        if (user.getCountLike() <= 1) {
            holder.mTvCountLikes.setText(String.valueOf(user.getCountLike() +" " + mContext.getString(R.string.like)));
        } else {
            holder.mTvCountLikes.setText(String.valueOf(user.getCountLike() + " " + mContext.getString(R.string.likes)));
        }
        holder.mTvUsername.setText(mListUsers.get(position).getUsername());
        holder.mTvComment.setText(mListUsers.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }

    public interface OnclickLike {
        void sumCountLike(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button mBtnLike;
        private ImageView mImgAvatar;
        private TextView mTvUsername;
        private TextView mTvUserComment;
        private TextView mTvCountLikes;
        private TextView mTvComment;
        private ImageView mImageCook;
        ViewHolder(View itemView) {
            super(itemView);
            mBtnLike = itemView.findViewById(R.id.btnLike);
            mImgAvatar = itemView.findViewById(R.id.circleImageViewAvatar);
            mTvUsername = itemView.findViewById(R.id.tvUser);
            mImageCook = itemView.findViewById(R.id.imgCook);
            mTvCountLikes = itemView.findViewById(R.id.tvCountLike);
            mTvUserComment = itemView.findViewById(R.id.tvUserComment);
            mTvComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
            handleEvent();
        }
        private void handleEvent() {
            mBtnLike.setOnClickListener(view -> {
                if (mOnclickLike == null) {
                    return;
                }
                int position = getLayoutPosition();
                mOnclickLike.sumCountLike(position);
            });
        }
    }
}
