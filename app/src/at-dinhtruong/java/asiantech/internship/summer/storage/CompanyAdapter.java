package asiantech.internship.summer.storage;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private List<Company> companies;
    public onClickItem onClickItem;

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvNameCompany;

        public CompanyViewHolder(View v) {
            super(v);
            mTvNameCompany = v.findViewById(R.id.my_text_view);
        }
    }

    public CompanyAdapter(FragmentActivity activity, List<Company> companies, RecyclerView mRecyclerView, onClickItem onClickItem) {
        this.companies = companies;
        this.onClickItem = onClickItem;

    }

    @Override
    public CompanyAdapter.CompanyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        CompanyViewHolder vh = new CompanyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, final int position) {
        Company company = companies.get(position);
        final int idCompany = company.getmIdCompany();
        CompanyViewHolder companyViewHolder = holder;
        companyViewHolder.mTvNameCompany.setText(company.getmNameCompany());
        companyViewHolder.mTvNameCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.onSelectItem(idCompany);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public interface onClickItem {
        void onSelectItem(int position);
    }

}
