package asiantech.internship.summer.file_storage;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Employee;

class EmployeeAdapter extends BaseAdapter {
    private List<Employee> mListEmployees;
    private OnclickItem mOnclickItem;

    EmployeeAdapter(List<Employee> mListEmployees, OnclickItem onclickItem) {
        this.mListEmployees = mListEmployees;
        this.mOnclickItem = onclickItem;
    }

    @Override
    public int getCount() {
        return mListEmployees.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Employee employee = mListEmployees.get(i);
        View view = convertView;
        if (convertView == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_employee, viewGroup, false);
            TextView tvContent = view.findViewById(R.id.tvEmployee);
            tvContent.setText(employee.getNameEmployee());
            view.setOnClickListener(view1 -> {
//                mOnclickItem.selectedItem(i);
                Log.i("xxx", "getView: " + employee.getIdEmployee());
                /*mOnclickItem.showSizeItem(employee.getIdEmployee());*/
            });
        }
        return view;
    }

    public interface OnclickItem{
        void selectedItem(int position);
    }
}
