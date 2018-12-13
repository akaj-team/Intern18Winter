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
    private onClickItem mOnClickItem;

    CompanyAdapter(List<Company> companies, onClickItem onClickItem) {
        this.mCompanies = companies;
        this.mOnClickItem = onClickItem;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.item_company_employee, viewGroup, false);
        return new CompanyViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        holder.initDataCompany(mCompanies.get(position));
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameCompany;

        CompanyViewHolder(View v) {
            super(v);
            mTvNameCompany = v.findViewById(R.id.custom_view_item);
            mTvNameCompany.setOnClickListener(view ->
                    mOnClickItem.onSelectItem(mCompanies.get(getAdapterPosition()).getIdCompany()));
        }

        private void initDataCompany(Company company) {
            mTvNameCompany.setText(company.getNameCompany());
        }
    }

    public interface onClickItem {
        void onSelectItem(int position);
    }

}
