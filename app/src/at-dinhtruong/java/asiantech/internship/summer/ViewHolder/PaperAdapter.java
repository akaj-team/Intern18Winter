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

import java.util.List;

import asiantech.internship.summer.Model.Paper;
import asiantech.internship.summer.R;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperViewHolder> {
    private List<Paper> paperList;

    public PaperAdapter(List<Paper> dataFirm) {
        this.paperList = dataFirm;
    }

    @NonNull
    @Override
    public PaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new PaperViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaperViewHolder viewHolder, int position) {
        viewHolder.initData(paperList.get(position));
    }

    @Override
    public int getItemCount() {
        return paperList.size();
    }

    class PaperViewHolder extends RecyclerView.ViewHolder {
        TextView mDescription;
        TextView mNameFirm;
        ImageView mIcon;
        RelativeLayout mRlCustomPaper;

        PaperViewHolder(@NonNull View itemView) {
            super(itemView);
            mDescription = itemView.findViewById(R.id.tvDescription);
            mIcon = itemView.findViewById(R.id.imgIcon);
            mNameFirm = itemView.findViewById(R.id.tvFirmName);
            mRlCustomPaper = itemView.findViewById(R.id.rlCustomPaper);
        }

        private void initData(Paper paper) {
            mDescription.setText(paper.getmDescription());
            mIcon.setImageResource(paper.getmIcon());
            mNameFirm.setText(paper.getmNameFirm());
            mRlCustomPaper.setBackgroundColor(Color.parseColor(paper.getmColor()));
        }
    }
}
