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
    public onClickItem mOnClickItem;

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvNameCompany;

        public CompanyViewHolder(View v) {
            super(v);
            mTvNameCompany = v.findViewById(R.id.custom_view_item);
        }
    }

    public CompanyAdapter(List<Company> companies, onClickItem onClickItem) {
        this.mCompanies = companies;
        this.mOnClickItem = onClickItem;

    }

    @Override
    public CompanyAdapter.CompanyViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                               int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.custom_view_item, viewGroup, false);
        return new CompanyViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, final int position) {
        Company company = mCompanies.get(position);
        final int idCompany = company.getIdCompany();
        CompanyViewHolder companyViewHolder = holder;
        companyViewHolder.mTvNameCompany.setText(company.getNameCompany());
        companyViewHolder.mTvNameCompany.setOnClickListener(view -> mOnClickItem.onSelectItem(idCompany));
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    public interface onClickItem {
        void onSelectItem(int position);
    }

}
