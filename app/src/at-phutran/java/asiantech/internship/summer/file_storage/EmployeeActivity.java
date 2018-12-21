package asiantech.internship.summer.file_storage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Employee;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener, EmployeeAdapter.OnclickItem {
    private Database mDatabase;
    private EditText mEdtIDEmployee;
    private EditText mEdtNameEmployee;
    private TextView mTvNameCompany;
    private TextView mTvListEmployee;
    private Button mBtnAddEmployee;
    private Button mBtnUpdateEmployee;
    private Button mBtnDeleteEmployee;
    private Employee mEmployee;
    private List<Employee> listEmployees;
    private ListView mListViewEmployee;
    private String mNameCompany;
    private EmployeeAdapter mEmployeeAdapter;
    private int mIDCompany;
    private long mResult;
    private int mDem = 0;
    private int mPosition = -1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        listEmployees = new ArrayList<>();
        mDatabase = new Database(getApplicationContext());
        mEmployee = new Employee();
        mEdtIDEmployee = findViewById(R.id.edtIDEmployee);
        mEdtNameEmployee = findViewById(R.id.edtNameEmployee);
        mTvNameCompany = findViewById(R.id.tvNameEmployee);
        mTvListEmployee = findViewById(R.id.tvListEmployee);
        mBtnAddEmployee = findViewById(R.id.btnAddEmployee);
        mBtnUpdateEmployee = findViewById(R.id.btnUpdateEmployee);
        mBtnDeleteEmployee = findViewById(R.id.btnDeleteEmployee);
        mListViewEmployee = findViewById(R.id.lvEmployee);
        Intent intent = getIntent();
        mIDCompany = intent.getIntExtra("position", 0);
        listEmployees = mDatabase.getAllEmployeeById(mIDCompany);
        mNameCompany = intent.getStringExtra("name_company");
        mTvNameCompany.setText(getString(R.string.inputEmployee) + " " + mNameCompany);
        if (listEmployees.size() != 0) {
            listEmployees = mDatabase.getAllEmployeeById(mIDCompany);
            mDem = listEmployees.get(listEmployees.size() - 1).getIdEmployee() + 1;
            resetViewInsertEmployee(mDem);
            mTvListEmployee.setText(getString(R.string.listEmployee) + " " + intent.getStringExtra("name_company"));
            updateListView(listEmployees);
        } else {
            mDem += 1;
            resetViewInsertEmployee(mDem);
            mTvListEmployee.setText("Employee is empty!");
            updateListView(listEmployees);
        }
        mBtnAddEmployee.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddEmployee: {
                int id = Integer.parseInt(mEdtIDEmployee.getText().toString());
                String name = mEdtNameEmployee.getText().toString();
                mEmployee = new Employee(id, name, mIDCompany, false);
                if (listEmployees.size() == 0) {
                    mTvListEmployee.setText(getString(R.string.listEmployee) + " " + mNameCompany);
                    mResult = mDatabase.insertEmployee(mEmployee);
                    Log.i("xxx", "onClick: " + mResult);
                    if (mResult > 0) {
                        listEmployees.add(mEmployee);
                        mDem = listEmployees.get(listEmployees.size() - 1).getIdEmployee() + 1;
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    resetViewInsertEmployee(mDem);
                    updateListView(listEmployees);
                } else {
                    mResult = mDatabase.insertEmployee(mEmployee);
                    if (mResult > 0) {
                        listEmployees.add(mEmployee);
                        mDem = listEmployees.get(listEmployees.size() - 1).getIdEmployee() + 1;
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        resetViewInsertEmployee(mDem);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    updateListView(listEmployees);
                }
                break;
            }
        }
    }

    private void updateListView(List<Employee> list) {
        mEmployeeAdapter = new EmployeeAdapter(list, this);
        mListViewEmployee.setAdapter(mEmployeeAdapter);
    }

    private void resetViewInsertEmployee(int i) {
        mEdtIDEmployee.setText(String.valueOf(i));
        mEdtNameEmployee.setText("");
    }

    @Override
    public void selectedItem(int position) {
        if (mPosition >= 0) {
            listEmployees.get(mPosition).setSelected(false);
        }
        listEmployees.get(position).setSelected(true);
        mPosition = position;
        mEmployeeAdapter.notifyDataSetChanged();
    }

}
