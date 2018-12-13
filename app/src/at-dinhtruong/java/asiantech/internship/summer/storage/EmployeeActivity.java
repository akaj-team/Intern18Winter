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
    private DBManager mDbManager;
    private int mIdCompany;
    private EditText mEdtIdEmployee;
    private EditText mEdtNameEmployee;
    private EmployeeAdapter mEmployeeAdapter;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = new DBManager(getApplicationContext());
        setContentView(R.layout.activity_employee);
        Intent intent = getIntent();
        mIdCompany = intent.getIntExtra("idCompany", 0);
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
        List<Employee> employees = mDbManager.getAllEmployee();
        if (employees.size() == 0) {
            mDbManager.createDefaultEmployee();
        }
        List<Employee> employeesById = mDbManager.getAllEmployeeById(mIdCompany);
        mEmployeeAdapter = new EmployeeAdapter(employeesById, this);
        recyclerView.setAdapter(mEmployeeAdapter);
    }

    @Override
    public void onSelectEmployee(int idEmployee) {
        Employee employee = mDbManager.getEmployeeById(idEmployee);
        mEdtIdEmployee.setText(String.valueOf(employee.getIdEmployee()));
        mEdtNameEmployee.setText(employee.getNameEmployee());
        mBtnUpdate.setEnabled(true);
        mBtnDelete.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        String idEmployeeString = mEdtIdEmployee.getText().toString();
        String nameEmployee = mEdtNameEmployee.getText().toString();
        int idEmployee = 0;
        if (!idEmployeeString.equals("")) {
            idEmployee = Integer.parseInt(mEdtIdEmployee.getText().toString());
        }
        Employee employee = new Employee(idEmployee, mIdCompany, nameEmployee);
        switch (view.getId()) {
            case R.id.btnUpdate: {
                if (nameEmployee.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill the employee name!", Toast.LENGTH_LONG).show();
                } else if (!nameEmployee.equals("") && mDbManager.UpdateEmployee(employee) > 0) {
                    mEmployeeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Update Error!", Toast.LENGTH_LONG).show();
                }
                mEmployeeAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.btnInsert: {
                if (nameEmployee.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill the employee name!", Toast.LENGTH_LONG).show();
                } else {
                    mDbManager.addEmployee(employee);
                    mEmployeeAdapter.notifyDataSetChanged();
                }
                break;
            }
            case R.id.btnDelete: {
                mDbManager.deleteEmployee(employee);
                mEmployeeAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
