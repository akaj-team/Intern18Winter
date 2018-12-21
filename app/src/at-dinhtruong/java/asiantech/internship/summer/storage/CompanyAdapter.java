package asiantech.internship.summer.storage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private List<Company> mCompanies;
    private OnItemClickListener mOnItemClickListener;

    CompanyAdapter(List<Company> companies, OnItemClickListener onClickItem) {
        mCompanies = companies;
        mOnItemClickListener = onClickItem;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.filestorage_item, viewGroup, false);
        return new CompanyViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        holder.bind(mCompanies.get(position));
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameCompany;

        CompanyViewHolder(View v) {
            super(v);
            mTvNameCompany = v.findViewById(R.id.tvItem);
            mTvNameCompany.setOnClickListener(view ->
                    mOnItemClickListener.onItemClicked(mCompanies.get(getAdapterPosition()).getIdCompany()));
        }

        private void bind(Company company) {
            mTvNameCompany.setText(company.getNameCompany());
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

}
