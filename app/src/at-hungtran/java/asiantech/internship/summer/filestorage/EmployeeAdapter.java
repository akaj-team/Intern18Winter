package asiantech.internship.summer.filestorage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> mEmployees;
    private onClickEmployee mOnClickEmployee;

    EmployeeAdapter(List<Employee> mEmployeesById, onClickEmployee onClickEmployee) {
        mEmployees = mEmployeesById;
        mOnClickEmployee = onClickEmployee;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_employee, parent, false);
        return new EmployeeAdapter.EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.mTvNameEmployee.setText(mEmployees.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameEmployee;

        EmployeeViewHolder(View itemView) {
            super(itemView);
            mTvNameEmployee = itemView.findViewById(R.id.tvItemEmployee);
            mTvNameEmployee.setOnClickListener(v -> mOnClickEmployee.onSelectEmployee(getAdapterPosition()));
        }
    }
    public interface onClickEmployee {
        void onSelectEmployee(int position);
    }
}
