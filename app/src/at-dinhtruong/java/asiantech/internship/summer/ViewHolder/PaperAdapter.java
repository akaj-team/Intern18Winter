package asiantech.internship.summer.ViewHolder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import asiantech.internship.summer.Model.Paper;
import asiantech.internship.summer.R;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder> {
    private ArrayList<Paper> dataFirm;

    public PaperAdapter(ArrayList<Paper> dataFirm) {
        this.dataFirm = dataFirm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.initData();
    }

    @Override
    public int getItemCount() {
        return dataFirm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvNameFirm;
        ImageView imgIcon;
        RelativeLayout rlCustomView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvNameFirm = itemView.findViewById(R.id.tvFirmName);
            rlCustomView = itemView.findViewById(R.id.rlCustomView);
        }

        void initData() {
            int mPosition = getAdapterPosition();
            tvDescription.setText(dataFirm.get(getAdapterPosition()).getTxtDescription());
            imgIcon.setImageResource(dataFirm.get(getAdapterPosition()).getImgIcon());
            tvNameFirm.setText(dataFirm.get(getAdapterPosition()).getTxtNameFirm());
            if (mPosition % 4 == 0 || (mPosition + 1) % 4 == 0) {
                rlCustomView.setBackgroundColor(Color.parseColor("#f4f2f2"));
            }
        }
    }
}
