package asiantech.internship.summer.file_storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Employee;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener, EmployeeAdapter.OnclickItem {
    private static final String FORMAT = "%s %s";
    private TextView mTvListEmployee;
    private Database mDatabase;
    private EditText mEdtIDEmployee;
    private EditText mEdtNameEmployee;
    private Employee mEmployee;
    private List<Employee> mListEmployeeById;
    private RecyclerView mRecylerViewEmployee;
    private String mNameCompany;
    private EmployeeAdapter mEmployeeAdapter;
    private int mIDCompany;
    private int mCount = 0;
    private int mExist = 0;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        mListEmployeeById = new ArrayList<>();
        mDatabase = new Database(getApplicationContext());
        mEmployee = new Employee();
        mEdtIDEmployee = findViewById(R.id.edtIDEmployee);
        mEdtNameEmployee = findViewById(R.id.edtNameEmployee);
        mTvListEmployee = findViewById(R.id.tvListEmployee);
        TextView tvNameCompany = findViewById(R.id.tvNameEmployee);
        Button btnAddEmployee = findViewById(R.id.btnAddEmployee);
        Button btnUpdateEmployee = findViewById(R.id.btnUpdateEmployee);
        Button btnDeleteEmployee = findViewById(R.id.btnDeleteEmployee);
        mRecylerViewEmployee = findViewById(R.id.recyclerViewEmployee);
        btnAddEmployee.setOnClickListener(this);
        btnUpdateEmployee.setOnClickListener(this);
        btnDeleteEmployee.setOnClickListener(this);
        getDataCompanyClicked();
        tvNameCompany.setText(String.format(FORMAT, getString(R.string.inputEmployee), mNameCompany));
        mListEmployeeById = mDatabase.getAllEmployeeById(mIDCompany);
        if (mListEmployeeById.size() != 0) {
            mTvListEmployee.setText(String.format(FORMAT, getString(R.string.listEmployee), mNameCompany));
            mCount = mListEmployeeById.get(mListEmployeeById.size() - 1).getIdEmployee() + 1;
        } else {
            mCount += 1;
            mTvListEmployee.setText(R.string.empty);
        }

        resetViewInsertEmployee(mCount);
        initView(mListEmployeeById);
    }

    private void getDataCompanyClicked() {
        Intent intent = getIntent();
        mIDCompany = intent.getIntExtra(getString(R.string.position), 0);
        mNameCompany = intent.getStringExtra(getString(R.string.nameCompany));
    }

    @Override
    public void onClick(View view) {
        createEmployee();
        switch (view.getId()) {
            case R.id.btnAddEmployee: {
                if (mListEmployeeById.size() > 0) {
                    for (int i = 0; i < mListEmployeeById.size(); i++) {
                        if (mEmployee.getIdEmployee() == mListEmployeeById.get(i).getIdEmployee()) {
                            mExist += 1;
                        }
                    }
                    if (mExist != 0) {
                        mExist = 0;
                        Toast.makeText(this, R.string.employeeExist, Toast.LENGTH_SHORT).show();
                    } else {
                        mTvListEmployee.setText(String.format(FORMAT, getString(R.string.listEmployee), mNameCompany));
                        if (mDatabase.insertEmployee(mEmployee) > 0) {
                            mListEmployeeById.add(mEmployee);
                            mCount = mListEmployeeById.get(mListEmployeeById.size() - 1).getIdEmployee() + 1;
                            Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mTvListEmployee.setText(String.format(FORMAT, getString(R.string.listEmployee), mNameCompany));
                    if (mDatabase.insertEmployee(mEmployee) > 0) {
                        mListEmployeeById.add(mEmployee);
                        mCount = mListEmployeeById.get(mListEmployeeById.size() - 1).getIdEmployee() + 1;
                        Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
                resetViewInsertEmployee(mCount);
                mEmployeeAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.btnUpdateEmployee: {
                if (mEmployee.getNameEmployee().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.noteInputEmployee, Toast.LENGTH_LONG).show();
                } else if (!mEmployee.getNameEmployee().isEmpty() && mDatabase.updateEmployee(mEmployee) > 0) {
                    mListEmployeeById.get(mPosition).setNameEmployee(mEmployee.getNameEmployee());
                    Toast.makeText(getApplicationContext(), R.string.successful, Toast.LENGTH_SHORT).show();
                    resetViewInsertEmployee(mCount);
                    mEmployeeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.btnDeleteEmployee: {
                if (mListEmployeeById.size() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_LONG).show();
                } else {
                    if (mDatabase.deleteEmployee(mEmployee) > 0) {
                        mListEmployeeById.remove(mPosition);
                        Toast.makeText(getApplicationContext(), R.string.successful, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
                mEmployeeAdapter.notifyDataSetChanged();
                resetViewInsertEmployee(mCount);
                break;
            }
        }
    }

    private void createEmployee() {
        int id = Integer.parseInt(mEdtIDEmployee.getText().toString());
        String name = mEdtNameEmployee.getText().toString();
        mEmployee = new Employee(id, name, mIDCompany);
    }

    private void initView(List<Employee> list) {
        mRecylerViewEmployee.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecylerViewEmployee.setLayoutManager(linearLayoutManager);
        mEmployeeAdapter = new EmployeeAdapter(list, this);
        mRecylerViewEmployee.setAdapter(mEmployeeAdapter);
    }

    private void resetViewInsertEmployee(int position) {
        mEdtIDEmployee.setText(String.valueOf(position));
        mEdtNameEmployee.setText("");
    }

    @Override
    public void selectedItem(int position) {
        mEmployee = mListEmployeeById.get(position);
        mEdtIDEmployee.setText(String.valueOf(mEmployee.getIdEmployee()));
        mEdtNameEmployee.setText(mEmployee.getNameEmployee());
        mPosition = position;
    }
}
