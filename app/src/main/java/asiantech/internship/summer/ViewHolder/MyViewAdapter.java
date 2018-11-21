package asiantech.internship.summer.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import asiantech.internship.summer.Model.ModelPaper;
import asiantech.internship.summer.R;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.ViewHolder> {
    private ArrayList<ModelPaper> dataFirm;
    private Context context;

    public MyViewAdapter(ArrayList<ModelPaper> dataFirm, Context context) {
        this.dataFirm = dataFirm;
        this.context = context;
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
        viewHolder.tvDescription.setText(dataFirm.get(i).getTxtDescription());
        viewHolder.imgIcon.setImageResource(dataFirm.get(i).getImgIcon());
        viewHolder.tvNameFirm.setText(dataFirm.get(i).getTxtNameFirm());
    }

    @Override
    public int getItemCount() {
        return dataFirm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvNameFirm;
        ImageView imgIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvNameFirm = itemView.findViewById(R.id.tvFirmName);


        }
    }
}
