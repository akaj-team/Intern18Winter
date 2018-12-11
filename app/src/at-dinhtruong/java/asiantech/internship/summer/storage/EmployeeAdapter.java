package asiantech.internship.summer.storage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employees;
    private onClickEmployee mOnClickEmployee;


    @NonNull
    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itView = layoutInflater.inflate(R.layout.my_text_view, viewGroup, false);
        return new EmployeeViewHolder(itView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, final int position) {
        final Employee employee = employees.get(position);
        EmployeeViewHolder employeeViewHolder = holder;
        employeeViewHolder.mTvEmployee.setText(employee.getmNameEmployee());
        employeeViewHolder.mTvEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickEmployee.onSelectEmployee(employee.getmIdEmployee());
            }
        });

    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvEmployee;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            mTvEmployee = itemView.findViewById(R.id.my_text_view);
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    EmployeeAdapter(List<Employee> employees, RecyclerView recyclerView, onClickEmployee onClickEmployee) {
        this.employees = employees;
        this.mOnClickEmployee = onClickEmployee;

    }

    public interface onClickEmployee {
        void onSelectEmployee(int idEmployee);
    }

}
