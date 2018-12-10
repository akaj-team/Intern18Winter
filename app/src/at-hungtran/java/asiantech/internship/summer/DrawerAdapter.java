package asiantech.internship.summer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.model.Object;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private onClick onclick;
    private Context context;
    private List<Object> objects;

    DrawerAdapter(List<Object> objects, Context context, onClick onClick) {
        this.objects = objects;
        this.onclick = onClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            View itemView = layoutInflater.inflate(R.layout.list_item_layout, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = layoutInflater.inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mIconImage.setImageResource(objects.get(position).getmIconImage());
            ((ItemViewHolder) holder).mCat.setText(objects.get(position).getmCat());
            ((ItemViewHolder) holder).mLinearLayout.setSelected(objects.get(position).isChecked());

            ((ItemViewHolder) holder).mLinearLayout.setOnClickListener(view -> {
                onclick.selectItem(position);
            });

        } else if (holder instanceof HeaderViewHolder) {
            Object object = objects.get(0);

            if (objects.get(position).getAvatarUri() != null) {
                Log.d("VVVV", "1");
                ((HeaderViewHolder) holder).mImgAvt.setImageURI(object.getAvatarUri());
            } else {
                Log.d("VVVV", "2");
                ((HeaderViewHolder) holder).mImgAvt.setImageResource(object.getmImgAvt());
            }

//            ((HeaderViewHolder) holder).mImgAvt.setImageResource(object.getmImgAvt());
            ((HeaderViewHolder) holder).mImgAvt.setOnClickListener(view -> {
                onclick.avatarClick();

            });
            ((HeaderViewHolder) holder).mGmail.setText(object.getmGmail());
            ((HeaderViewHolder) holder).mImgCheck.setImageResource(object.getmImgCheck());
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    public interface onClick {
        void avatarClick();

        void selectItem(int position);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvt;
        private TextView mGmail;
        private ImageView mImgCheck;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mImgAvt = itemView.findViewById(R.id.img_avt);
            mGmail = itemView.findViewById(R.id.tvGmail);
            mImgCheck = itemView.findViewById(R.id.ic_check);
            this.setIsRecyclable(false);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIconImage;
        private TextView mCat;
        private LinearLayout mLinearLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            mIconImage = itemView.findViewById(R.id.ic_img);
            mCat = itemView.findViewById(R.id.tvCat);
            mLinearLayout = itemView.findViewById(R.id.linearlayout);
            this.setIsRecyclable(false);
        }
    }
}
