package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.model.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private List<Company> mCompanies;
    private OnClickCompany mOnClickCompanyListener;
    private Context mContext;

    CompanyAdapter(List<Company> companies, OnClickCompany onClickCompany, Context context) {
        this.mCompanies = companies;
        this.mOnClickCompanyListener = onClickCompany;
        this.mContext = context;
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
        holder.mTvNameCompany.setText(mCompanies.get(position).getCompanyName());
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    public interface OnClickCompany {
        void onSelectCompany(int position);
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameCompany;

        CompanyViewHolder(View itemView) {
            super(itemView);
            mTvNameCompany = itemView.findViewById(R.id.tvItemCompany);
            mTvNameCompany.setOnClickListener(v -> {
                mOnClickCompanyListener.onSelectCompany(mCompanies.get(getAdapterPosition()).getCompanyId());
                Toast.makeText(mContext, mContext.getString(R.string.selectedEmployeeID) + " "
                        + mCompanies.get(getAdapterPosition()).getCompanyName(), Toast.LENGTH_LONG).show();
            });
        }
    }
}
