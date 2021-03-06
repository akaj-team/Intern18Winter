package asiantech.internship.summer.restapi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.restapi.model.ImageItem;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {
    private List<ImageItem> mImageItems;

    ListImageAdapter(List<ImageItem> imageItems) {
        mImageItems = imageItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageItem item = mImageItems.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return mImageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgPhoto;
        private TextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgPhoto = itemView.findViewById(R.id.imgPhoto);
            mTvContent = itemView.findViewById(R.id.tvContent);
        }

        void bindView(ImageItem item) {
            if (item != null) {
                Glide.with(itemView.getContext()).load(item.getUrl()).into(mImgPhoto);
                mTvContent.setText(item.getType());
            }
        }
    }
}
