package asiantech.internship.summer.viewandviewgroup;

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

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Paper;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperViewHolder> {
    private List<Paper> mPapers;

    public PaperAdapter(List<Paper> dataFirm) {
        this.mPapers = dataFirm;
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
        viewHolder.initData(mPapers.get(position));
    }

    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    class PaperViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDescription;
        private TextView mTvNameFirm;
        private ImageView mImgIcon;
        private RelativeLayout mRlCustomPaper;

        PaperViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mImgIcon = itemView.findViewById(R.id.imgIcon);
            mTvNameFirm = itemView.findViewById(R.id.tvFirmName);
            mRlCustomPaper = itemView.findViewById(R.id.rlCustomPaper);
        }

        private void initData(Paper paper) {
            mTvDescription.setText(paper.getDescription());
            mImgIcon.setImageResource(paper.getIcon());
            mTvNameFirm.setText(paper.getNameFirm());
            mRlCustomPaper.setBackgroundColor(Color.parseColor(paper.getColor()));
        }
    }
}
