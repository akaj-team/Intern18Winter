package asiantech.internship.summer.file_storage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Employee;

class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> mListEmployees;
    private OnclickItem mOnclickItem;

    EmployeeAdapter(List<Employee> listEmployees, OnclickItem onclickItem) {
        mListEmployees = listEmployees;
        mOnclickItem = onclickItem;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_row_employee, parent, false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = mListEmployees.get(position);
        holder.tvContent.setText(employee.getNameEmployee());
    }


    @Override
    public int getItemCount() {
        return mListEmployees.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvEmployee);
            handleEvent();
        }
        private void handleEvent() {
            itemView.setOnClickListener(view -> {
                int position = getLayoutPosition();
                mOnclickItem.selectedItem(position);
            });
        }
    }

    public interface OnclickItem {
        void selectedItem(int position);
    }
}
