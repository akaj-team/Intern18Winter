package asiantech.internship.summer.filestorage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private List<Company> mItemCompany;
    private OnClickCompany mOnClickCompany;

    CompanyAdapter(List<Company> companies, OnClickCompany onClickCompany){
        this.mItemCompany = companies;
        this.mOnClickCompany = onClickCompany;
    }
    @NonNull
    @Override
    public CompanyAdapter.CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.CompanyViewHolder holder, int position) {
        holder.mTvNameCompany.setText(mItemCompany.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mItemCompany.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameCompany;

        CompanyViewHolder(View itemView) {
            super(itemView);
            mTvNameCompany = itemView.findViewById(R.id.tvItemCompany);
            mTvNameCompany.setOnClickListener(v -> mOnClickCompany.onSelectItem(mItemCompany.get(getAdapterPosition()).getId()));
        }
    }
    public interface OnClickCompany {
        void onSelectItem(int position);
    }
}
