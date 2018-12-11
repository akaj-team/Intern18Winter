package asiantech.internship.summer.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Employee;

public class EmployeeActivity extends AppCompatActivity implements EmployeeAdapter.onClickEmployee, View.OnClickListener {
    private DBManager mDbManager;
    private int mIdCompany;
    private EditText mEdtIdEmployee;
    private EditText mEdtNameEmployee;
    private Button mBtnInsert;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private RecyclerView mRecyclerView;

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
        mRecyclerView = findViewById(R.id.recyclerViewEmployee);
        mEdtIdEmployee = findViewById(R.id.edtIdEmployee);
        mEdtNameEmployee = findViewById(R.id.edtNameEmployee);
       /* mBtnUpdate.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnInsert.setOnClickListener(this);*/
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDbManager.createDefaultEmployee();
        List<Employee> employeesById = mDbManager.getAllEmployeeById(mIdCompany);
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeesById, mRecyclerView, this);
        mRecyclerView.setAdapter(employeeAdapter);
    }

    @Override
    public void onSelectEmployee(int idEmployee) {
        Employee employee = mDbManager.getEmployeeById(idEmployee);
        Log.d("xxxxx", "onSelectEmployee: " + employee.getIdEmployee() + employee.getNameEmployee());
        mEdtIdEmployee.setText(String.valueOf(employee.getIdEmployee()));
        mEdtNameEmployee.setText(employee.getNameEmployee());
    }

    @Override
    public void onClick(View view) {

    }
}
