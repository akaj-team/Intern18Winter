package asiantech.internship.summer.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Employee;

public class EmployeeActivity extends AppCompatActivity implements EmployeeAdapter.onClickEmployee, View.OnClickListener {
    private int mIdCompany;
    private int mPositionItem;
    private List<Employee> mEmployeesById;
    private EditText mEdtIdEmployee;
    private EditText mEdtNameEmployee;
    private EmployeeAdapter mEmployeeAdapter;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private DBManager mDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = new DBManager(getApplicationContext());
        setContentView(R.layout.activity_employee);
        Intent intent = getIntent();
        mIdCompany = intent.getIntExtra(SqliteFragment.ID_COMPANY, 0);
        initEmployee();
    }

    private void initEmployee() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEmployee);
        mEdtIdEmployee = findViewById(R.id.edtIdEmployee);
        mEdtNameEmployee = findViewById(R.id.edtNameEmployee);
        mBtnUpdate = findViewById(R.id.btnUpdate);
        mBtnDelete = findViewById(R.id.btnDelete);
        Button btnInsert = findViewById(R.id.btnInsert);
        mBtnUpdate.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mEmployeesById = mDbManager.getAllEmployeeById(mIdCompany);
        mEdtIdEmployee.setText(String.valueOf(mEmployeesById.size() + 1));
        mEmployeeAdapter = new EmployeeAdapter(mEmployeesById, this);
        recyclerView.setAdapter(mEmployeeAdapter);
    }

    @Override
    public void onSelectEmployee(int position) {
        Employee employee = mDbManager.getEmployeeById(mEmployeesById.get(position).getIdEmployee(), mEmployeesById.get(position).getCompanyId());
        mEdtIdEmployee.setText(String.valueOf(employee.getIdEmployee()));
        mEdtNameEmployee.setText(employee.getNameEmployee());
        mBtnUpdate.setEnabled(true);
        mBtnDelete.setEnabled(true);
        mPositionItem = position;
    }

    @Override
    public void onClick(View view) {
        Employee employee = getDataForUpdate();
        switch (view.getId()) {
            case R.id.btnUpdate: {
                if (employee.getNameEmployee().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.pleaseFillTheEmployeeName, Toast.LENGTH_LONG).show();
                } else if (!employee.getNameEmployee().equals("") && mDbManager.UpdateEmployee(employee) > 0) {
                    mEmployeesById.get(mPositionItem).setNameEmployee(employee.getNameEmployee());
                    mEmployeeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.updateError, Toast.LENGTH_LONG).show();
                }
                mEdtIdEmployee.setText(String.valueOf(mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1));
                mEdtNameEmployee.setText("");
                break;
            }
            case R.id.btnInsert: {
                int idEmployee;
                if (mEmployeesById.size() == 0) {
                    idEmployee = 1;
                } else {
                    idEmployee = mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1;
                }
                String nameEmployee = mEdtNameEmployee.getText().toString();
                employee = new Employee(idEmployee, mIdCompany, nameEmployee);
                if (nameEmployee.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.pleaseFillTheEmployeeName, Toast.LENGTH_LONG).show();
                } else {
                    mDbManager.addEmployee(employee);
                    mEmployeesById.add(employee);
                    mEmployeeAdapter.notifyDataSetChanged();
                    mEdtIdEmployee.setText(String.valueOf(mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1));
                    mEdtNameEmployee.setText("");
                }
                break;
            }
            case R.id.btnDelete: {
                mDbManager.deleteEmployee(employee);
                mEmployeesById.remove(mPositionItem);
                mEmployeeAdapter.notifyDataSetChanged();
                mEdtIdEmployee.setText(String.valueOf(mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1));
                mEdtNameEmployee.setText("");
                break;
            }
        }
    }

    private Employee getDataForUpdate() {
        String nameEmployee = mEdtNameEmployee.getText().toString();
        int idEmployee = Integer.parseInt(mEdtIdEmployee.getText().toString());
        return new Employee(idEmployee, mIdCompany, nameEmployee);
    }
}
