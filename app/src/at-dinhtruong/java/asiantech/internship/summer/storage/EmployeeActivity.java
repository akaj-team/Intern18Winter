package asiantech.internship.summer.storage;

import android.os.Bundle;
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
import asiantech.internship.summer.models.Company;
import asiantech.internship.summer.models.Employee;

public class EmployeeActivity extends AppCompatActivity implements EmployeeAdapter.OnEmployeeClickListener, View.OnClickListener {
    private int mIdCompany;
    private int mPositionItem;
    private List<Employee> mEmployeesById;
    private EditText mEdtIdEmployee;
    private EditText mEdtNameEmployee;
    private EmployeeAdapter mEmployeeAdapter;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private DBManager mDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBManager = new DBManager(getApplicationContext());
        setContentView(R.layout.activity_filestorage_employee);
        mIdCompany = getIntent().getIntExtra(getString(R.string.idCompany), 0);
        initViews();
    }

    private void initViews() {
        Company company = mDBManager.getCompanyById(mIdCompany);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEmployee);
        TextView tvNameCompany = findViewById(R.id.tvNameCompany);
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
        mEmployeesById = mDBManager.getAllEmployeeById(mIdCompany);
        int idEmployee;
        if (mEmployeesById.size() == 0) {
            idEmployee = 1;
        } else {
            idEmployee = mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1;
        }
        mEdtIdEmployee.setText(String.valueOf(idEmployee));
        tvNameCompany.setText(company.getNameCompany());
        mEmployeeAdapter = new EmployeeAdapter(mEmployeesById, this);
        recyclerView.setAdapter(mEmployeeAdapter);
    }

    @Override
    public void onEmployeeClicked(int position) {
        Employee employee = mEmployeesById.get(position);
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
                if (employee.getNameEmployee().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.pleaseFillTheEmployeeName, Toast.LENGTH_LONG).show();
                } else {
                    if (mDBManager.updateEmployee(employee) > 0) {
                        mEmployeesById.get(mPositionItem).setNameEmployee(employee.getNameEmployee());
                        mEmployeeAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.updateError, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case R.id.btnInsert: {
                employee.setIdEmployee(mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1);
                if (employee.getNameEmployee().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.pleaseFillTheEmployeeName, Toast.LENGTH_LONG).show();
                } else {
                    if (mDBManager.addEmployee(employee) > 0) {
                        mEmployeesById.add(employee);
                        mEmployeeAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.insertError, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case R.id.btnDelete: {
                if (mDBManager.deleteEmployee(employee) > 0) {
                    mEmployeesById.remove(mPositionItem);
                    mEmployeeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.deleteError, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        int idEmployee;
        if (mEmployeesById.size() == 0) {
            idEmployee = 1;
        } else {
            idEmployee = mEmployeesById.get(mEmployeesById.size() - 1).getIdEmployee() + 1;
        }
        mEdtIdEmployee.setText(String.valueOf(idEmployee));
        mEdtNameEmployee.setText("");
    }

    private Employee getDataForUpdate() {
        String nameEmployee = mEdtNameEmployee.getText().toString();
        int idEmployee = Integer.parseInt(mEdtIdEmployee.getText().toString());
        return new Employee(idEmployee, mIdCompany, nameEmployee);
    }
}
