package asiantech.internship.summer.file_storage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    private List<Company> mListCompany;
    private OnclickNameCompany mOnclickNameCompany;

    CompanyAdapter(List<Company> listCompany, OnclickNameCompany onclickNameCompany) {
        mListCompany = listCompany;
        mOnclickNameCompany = onclickNameCompany;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row_company, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTvNameCompany.setText(mListCompany.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mListCompany.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameCompany;

        ViewHolder(View itemView) {
            super(itemView);
            mTvNameCompany = itemView.findViewById(R.id.tvCompany);
            handleEvent();
        }

        private void handleEvent() {
            mTvNameCompany.setOnClickListener(view -> {
                if (mOnclickNameCompany == null) {
                    return;
                }
                int position = getLayoutPosition();
                mOnclickNameCompany.viewEmployee(position);
            });
        }
    }
    public interface OnclickNameCompany {
        void viewEmployee(int position);
    }
}
