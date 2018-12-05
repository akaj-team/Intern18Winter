package asiantech.internship.summer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.Model.User;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHoder> {
    private String mLikes = "likes";
    private String mLike = "like";
    private List<User> mListUsers;
    private Context mContext;

    public RecyclerViewAdapter(List<User> mListUsers, Context mContext) {
        this.mListUsers = mListUsers;
        this.mContext = mContext;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row, parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        holder.mImageViewUser.setImageResource(mListUsers.get(position).getAvatar());
        holder.mUsername.setText(mListUsers.get(position).getUsername());
        holder.mImageCook.setImageResource(mListUsers.get(position).getImage());
        holder.mButton.setOnClickListener(view -> {
            mListUsers.get(position).setCountLike(mListUsers.get(position).getCountLike() + 1);
            if(mListUsers.get(position).getCountLike() == 1){
                holder.mCountLikes.setText(mListUsers.get(position).getCountLike() + " " + mLike);
            }else{
                holder.mCountLikes.setText(mListUsers.get(position).getCountLike() + " " + mLikes);
            }

        });
        holder.mUserComment.setText(mListUsers.get(position).getUsername());
        holder.mComment.setText(mListUsers.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        private Button mButton;
        private ImageView mImageViewUser;
        private TextView mUsername;
        private TextView mUserComment;
        private TextView mCountLikes;
        private TextView mComment;
        private ImageView mImageCook;
        public ViewHoder(View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.btnLike);
            mImageViewUser = itemView.findViewById(R.id.circleImageViewAvatar);
            mUsername = itemView.findViewById(R.id.tvUser);
            mImageCook = itemView.findViewById(R.id.imgCook);
            mCountLikes = itemView.findViewById(R.id.tvCountLike);
            mUserComment = itemView.findViewById(R.id.tvUserComment);
            mComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
        }
    }
}
