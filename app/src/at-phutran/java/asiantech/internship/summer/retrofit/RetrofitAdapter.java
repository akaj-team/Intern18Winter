package asiantech.internship.summer.retrofit;

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
import asiantech.internship.summer.model.ImageItem;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder> {
    private List<ImageItem> mListImage;
    private Context mContext;

    RetrofitAdapter(List<ImageItem> listImage, Context context) {
        mListImage = listImage;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mListImage.get(position));
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            imgContent = itemView.findViewById(R.id.imgContent);
            tvContent = itemView.findViewById(R.id.tvContent);
        }

        private void onBind(ImageItem image) {
            assert image != null;
            if (!image.getImageId().isEmpty()) {
                tvContent.setText(R.string.commentImage);
                Glide.with(mContext).load(image.getUrl()).into(imgContent);
            }
        }
    }
}
