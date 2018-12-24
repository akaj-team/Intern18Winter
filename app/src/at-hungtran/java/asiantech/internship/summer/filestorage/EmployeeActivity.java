package asiantech.internship.summer.filestorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Company;
import asiantech.internship.summer.model.Employee;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener, EmployeeAdapter.onClickEmployee {
    private DatabaseHelper mDatabaseHelper;
    private List<Employee> mEmployeesById;
    private EditText mEdtIdEmployee;
    private EditText mEdtNameEmployee;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private EmployeeAdapter mEmployeeAdapter;
    private int mIdCompany;
    private int mPositionItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_employee);
        Intent intent = getIntent();
        mIdCompany = intent.getIntExtra(DatabaseFragment.ID_COMPANY, 0);
        initEmployee();
    }

    private void initEmployee() {
        Company mCompany = mDatabaseHelper.getCompanyById(mIdCompany);
        RecyclerView mRvEmployee = findViewById(R.id.rvEmployee);
        TextView mTvNameCompany = findViewById(R.id.tvNameCompany);
        Button mBtnInsert = findViewById(R.id.btnInsert);
        mBtnDelete = findViewById(R.id.btnDelete);
        mBtnUpdate = findViewById(R.id.btnUpdate);
        mEdtIdEmployee = findViewById(R.id.edtIdEmployee);
        mEdtNameEmployee = findViewById(R.id.edtNameEmployee);

        mBtnInsert.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);

        mRvEmployee.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvEmployee.setLayoutManager(linearLayoutManager);

        mEmployeesById = mDatabaseHelper.getAllEmployeeById(mIdCompany);
        mEdtIdEmployee.setText(String.valueOf(mEmployeesById.size() + 1));
        mTvNameCompany.setText(mCompany.getName());
        mEdtIdEmployee.setEnabled(false);
        mBtnUpdate.setEnabled(false);
        mBtnDelete.setEnabled(false);
        mEmployeeAdapter = new EmployeeAdapter(mEmployeesById, this);
        mRvEmployee.setAdapter(mEmployeeAdapter);
    }

    @Override
    public void onSelectEmployee(int position) {
        Employee employee = mDatabaseHelper.getEmployeeById(mEmployeesById.get(position).getId(), mEmployeesById.get(position).getCode());
        mEdtIdEmployee.setText(String.valueOf(employee.getId()));
        mEdtNameEmployee.setText(employee.getName());
        mBtnUpdate.setEnabled(true);
        mBtnDelete.setEnabled(true);
        mPositionItem = position;
    }

    @Override
    public void onClick(View v) {
        Employee employee;
        switch (v.getId()) {
            case R.id.btnInsert: {
                int idEmployee;
                if (mEmployeesById.size() == 0) {
                    idEmployee = 1;
                } else {
                    idEmployee = mEmployeesById.get(mEmployeesById.size() - 1).getId() + 1;
                }
                String nameEmployee = mEdtNameEmployee.getText().toString();
                employee = new Employee(idEmployee, mIdCompany, nameEmployee);
                if (nameEmployee.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.thisFieldIsNotEmpty, Toast.LENGTH_LONG).show();
                } else {
                    mDatabaseHelper.addEmployee(employee);
                    mEmployeesById.add(employee);
                    mEmployeeAdapter.notifyDataSetChanged();
                    mEdtIdEmployee.setText(String.valueOf(mEmployeesById.get(mEmployeesById.size() - 1).getId() + 1));
                    mEdtNameEmployee.setText("");
                }
                break;
            }
            case R.id.btnUpdate: {
                int idEmployee = Integer.parseInt(mEdtIdEmployee.getText().toString());
                String nameEmployee = mEdtNameEmployee.getText().toString();
                employee = new Employee(idEmployee, mIdCompany, nameEmployee);
                if (employee.getName().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.thisFieldIsNotEmpty, Toast.LENGTH_LONG).show();
                } else if (!employee.getName().equals("") && mDatabaseHelper.updateEmployee(employee) > 0) {
                    mEmployeesById.get(mPositionItem).setName(employee.getName());
                    mEmployeeAdapter.notifyDataSetChanged();
                    mEdtIdEmployee.setText(String.valueOf(mEmployeesById.get(mEmployeesById.size() - 1).getId() + 1));
                    mEdtNameEmployee.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.btnDelete: {
                int idEmployee = Integer.parseInt(mEdtIdEmployee.getText().toString());
                String nameEmployee = mEdtNameEmployee.getText().toString();
                employee = new Employee(idEmployee, mIdCompany, nameEmployee);
                if (mDatabaseHelper.deleteEmployee(employee) > 0) {
                    mEmployeesById.remove(mPositionItem);
                    mEmployeeAdapter.notifyDataSetChanged();
                    if (mEmployeesById.size() == 0) {
                        idEmployee = 1;
                    } else {
                        idEmployee = mEmployeesById.get(mEmployeesById.size() - 1).getId() + 1;
                    }
                    mEdtIdEmployee.setText(String.valueOf(idEmployee));
                    mEdtNameEmployee.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}

