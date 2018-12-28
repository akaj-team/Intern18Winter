package asiantech.internship.summer.restapi;

import android.content.Context;
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
import asiantech.internship.summer.models.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Image> mImages;
    private Context mContext;

    ImageAdapter(List<Image> images, Context context) {
        mImages = images;
        mContext = context;
    }

    void updateList(List<Image> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.restapi_item, viewGroup, false);
        return new ImageAdapter.ImageViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItem;
        private TextView mTvItem;

        ImageViewHolder(@NonNull View itView) {
            super(itView);
            mImgItem = itView.findViewById(R.id.imgItem);
            mTvItem = itView.findViewById(R.id.tvItem);
        }

        private void onBind() {
            Image image = mImages.get(getAdapterPosition());
            if (!image.getImageId().isEmpty()) {
                mTvItem.setText(image.getImageId());
                Glide.with(mContext).load(image.getUrl()).into(mImgItem);
            }
        }
    }
}
