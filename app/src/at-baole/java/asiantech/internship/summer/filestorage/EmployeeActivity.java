package asiantech.internship.summer.filestorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import asiantech.internship.summer.filestorage.model.Company;
import asiantech.internship.summer.filestorage.model.Employee;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener, EmployeeAdapter.onClickEmployee {
    private DataAccess mDataAccess;
    private List<Employee> mEmployees;
    private EditText mEdtEmployeeId;
    private EditText mEdtEmployeeName;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private EmployeeAdapter mEmployeeAdapter;
    private int mCompanyId;
    private int mItemPosition;
    private Employee mEmployee;
    private int mEmployeeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataAccess = new DataAccess(getApplicationContext());
        setContentView(R.layout.activity_employee);
        Intent intent = getIntent();
        mCompanyId = intent.getIntExtra(SaveDatabaseFragment.ID_COMPANY, 0);
        initEmployeeView();
    }

    private void initEmployeeView() {
        Company mCompany = mDataAccess.getCompanyById(mCompanyId);
        RecyclerView mRvEmployee = findViewById(R.id.rvEmployee);
        TextView mTvNameCompany = findViewById(R.id.tvCompany);
        Button mBtnInsert = findViewById(R.id.btnInsert);
        mBtnDelete = findViewById(R.id.btnDelete);
        mBtnUpdate = findViewById(R.id.btnUpdate);
        mEdtEmployeeId = findViewById(R.id.edtIdEmployee);
        mEdtEmployeeName = findViewById(R.id.edtEmployeeName);

        mBtnInsert.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);

        mRvEmployee.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvEmployee.setLayoutManager(linearLayoutManager);

        mEmployees = mDataAccess.getAllEmployeeById(mCompanyId);
        mEdtEmployeeId.setText(String.valueOf(mEmployees.size() + 1));
        mTvNameCompany.setText(mCompany.getCompanyName());
        mEdtEmployeeId.setEnabled(false);
        mBtnUpdate.setEnabled(false);
        mBtnDelete.setEnabled(false);
        mEmployeeAdapter = new EmployeeAdapter(mEmployees, this, getApplicationContext());
        mRvEmployee.setAdapter(mEmployeeAdapter);
    }

    @Override
    public void onSelectEmployee(int position) {
        Employee employee = mDataAccess.getEmployeeById(mEmployees.get(position).getEmployeeId(), mEmployees.get(position).getCompanyId());
        mEdtEmployeeId.setText(String.valueOf(employee.getEmployeeId()));
        mEdtEmployeeName.setText(employee.getEmployeeName());
        mBtnUpdate.setEnabled(true);
        mBtnDelete.setEnabled(true);
        mItemPosition = position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsert: {
                int employeeId;
                if (mEmployees.size() == 0) {
                    employeeId = 1;
                } else {
                    employeeId = mEmployees.get(mEmployees.size() - 1).getEmployeeId() + 1;
                }
                String nameEmployee = mEdtEmployeeName.getText().toString();
                mEmployee = new Employee(employeeId, mCompanyId, nameEmployee);
                if (nameEmployee.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.fieldNotNull, Toast.LENGTH_LONG).show();
                } else {
                    confirmInsertDialog();
                }
                break;
            }
            case R.id.btnUpdate: {
                int idEmployee = Integer.parseInt(mEdtEmployeeId.getText().toString());
                String nameEmployee = mEdtEmployeeName.getText().toString();
                mEmployee = new Employee(idEmployee, mCompanyId, nameEmployee);
                if (mEmployee.getEmployeeName().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.fieldNotNull, Toast.LENGTH_LONG).show();
                } else if (!mEmployee.getEmployeeName().equals("") && mDataAccess.updateEmployee(mEmployee) > 0) {
                    confirmUpdateDialog();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.errorOccured, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.btnDelete: {
                mEmployeeId = Integer.parseInt(mEdtEmployeeId.getText().toString());
                String nameEmployee = mEdtEmployeeName.getText().toString();
                mEmployee = new Employee(mEmployeeId, mCompanyId, nameEmployee);
                if (mDataAccess.deleteEmployee(mEmployee) > 0) {
                    confirmDeleteDialog();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.errorOccured, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void confirmInsertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmInsert);
        builder.setPositiveButton(R.string.yes, (dialogInterface, yes) -> {
            mDataAccess.addEmployee(mEmployee);
            mEmployees.add(mEmployee);
            mEmployeeAdapter.notifyDataSetChanged();
            mEdtEmployeeId.setText(String.valueOf(mEmployees.get(mEmployees.size() - 1).getEmployeeId() + 1));
            mEdtEmployeeName.setText("");
            Toast.makeText(this, R.string.insertSuccessfully, Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton(R.string.no, (dialogInterface, no) -> dialogInterface.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void confirmUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmUpdate);
        builder.setPositiveButton(R.string.yes, (dialogInterface, yes) -> {
            mEmployees.get(mItemPosition).setEmployeeName(mEmployee.getEmployeeName());
            mEmployeeAdapter.notifyDataSetChanged();
            mEdtEmployeeId.setText(String.valueOf(mEmployees.get(mEmployees.size() - 1).getEmployeeId() + 1));
            mEdtEmployeeName.setText("");
            Toast.makeText(this, R.string.updateSuccessfully, Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton(R.string.no, (dialogInterface, no) -> dialogInterface.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmDelete);
        builder.setPositiveButton(R.string.yes, (dialogInterface, yes) -> {
            mEmployees.remove(mItemPosition);
            mEmployeeAdapter.notifyDataSetChanged();
            if (mEmployees.size() == 0) {
                mEmployeeId = 1;
            } else {
                mEmployeeId = mEmployees.get(mEmployees.size() - 1).getEmployeeId() + 1;
            }
            mEdtEmployeeId.setText(String.valueOf(mEmployeeId));
            mEdtEmployeeName.setText("");
            Toast.makeText(this, R.string.deleteSuccessfully, Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton(R.string.no, (dialogInterface, no) -> dialogInterface.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
