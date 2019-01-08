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
import asiantech.internship.summer.models.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Image> mImages;

    ImageAdapter(List<Image> images) {
        mImages = images;
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
        holder.onBind(mImages.get(position));
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

        private void onBind(Image image) {
            if (image != null) {
                mTvItem.setText(image.getImageId());
                Glide.with(itemView.getContext()).load(image.getUrl()).into(mImgItem);
            }
        }
    }
}
