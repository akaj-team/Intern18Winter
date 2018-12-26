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
import asiantech.internship.summer.filestorage.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> mEmployees;
    private onClickEmployee mOnClickEmployeeListener;
    private Context mContext;

    EmployeeAdapter(List<Employee> mEmployeesById, onClickEmployee onClickEmployee, Context context) {
        mEmployees = mEmployeesById;
        mOnClickEmployeeListener = onClickEmployee;
        mContext = context;
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
        holder.mTvIdEmployee.setText(String.valueOf(mEmployees.get(position).getEmployeeId()));
        holder.mTvNameEmployee.setText(mEmployees.get(position).getEmployeeName());
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public interface onClickEmployee {
        void onSelectEmployee(int position);
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvIdEmployee;
        private TextView mTvNameEmployee;

        EmployeeViewHolder(View itemView) {
            super(itemView);
            mTvIdEmployee = itemView.findViewById(R.id.tvItemEmployeeId);
            mTvNameEmployee = itemView.findViewById(R.id.tvItemEmployee);
            mTvIdEmployee.setOnClickListener(v -> mOnClickEmployeeListener.onSelectEmployee(getAdapterPosition()));
            mTvNameEmployee.setOnClickListener(v -> {
                mOnClickEmployeeListener.onSelectEmployee(getAdapterPosition());
                Toast.makeText(mContext,mContext.getString(R.string.selectedEmployeeID) + " "
                        + mEmployees.get(getAdapterPosition()).getEmployeeId() + "\n"
                        + mContext.getString(R.string.employeeName) + ": "
                        + mEmployees.get(getAdapterPosition()).getEmployeeName(), Toast.LENGTH_LONG).show();
            });
        }
    }
}
