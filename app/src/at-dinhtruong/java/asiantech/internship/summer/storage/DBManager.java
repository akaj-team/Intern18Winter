package asiantech.internship.summer.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.models.Company;
import asiantech.internship.summer.models.Employee;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "company_list";
    private static final String TABLE_COMPANY = "company";
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String ID_COMPANY = "id_company";
    private static final String NAME_COMPANY = "name_company";
    private static final String ID_EMPLOYEE = "id_employee";
    private static final String NAME_EMPLOYEE = "name_employee";
    private static final String COMPANY_ID = "company_id";

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQueryCompany = "CREATE TABLE " + TABLE_COMPANY + " (" +
                ID_COMPANY + " integer primary key AUTOINCREMENT, " +
                NAME_COMPANY + " TEXT)";
        sqLiteDatabase.execSQL(sqlQueryCompany);
        String sqlQueryEmployee = "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
                ID_EMPLOYEE + " integer primary key AUTOINCREMENT, " + COMPANY_ID + " integer, " +
                NAME_EMPLOYEE + " TEXT)";
        sqLiteDatabase.execSQL(sqlQueryEmployee);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(sqLiteDatabase);
    }


    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COMPANY_ID, employee.getCompanyId());
        values.put(NAME_EMPLOYEE, employee.getNameEmployee());
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
    }

    public Employee getEmployeeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{ID_EMPLOYEE,
                        COMPANY_ID,NAME_EMPLOYEE}, ID_EMPLOYEE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Employee employee = new Employee(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        cursor.close();
        db.close();
        return employee;
    }

    public int Update(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_EMPLOYEE, employee.getNameEmployee());
        return db.update(TABLE_EMPLOYEE, values, ID_EMPLOYEE + "=?", new String[]{String.valueOf(employee.getIdEmployee())});
    }

    public List<Employee> getAllEmployee() {
        List<Employee> employees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setIdEmployee(cursor.getInt(0));
                employee.setCompanyId(cursor.getInt(1));
                employee.setNameEmployee(cursor.getString(2));
                employees.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
    }

    public List<Employee> getAllEmployeeById(int idCompany) {
        List<Employee> employees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE COMPANY_ID = " + idCompany;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setIdEmployee(cursor.getInt(0));
                employee.setCompanyId(cursor.getInt(1));
                employee.setNameEmployee(cursor.getString(2));
                employees.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
    }


    public void deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, ID_EMPLOYEE + " = ?",
                new String[]{String.valueOf(employee.getIdEmployee())});
        db.close();
    }

    public int getEmployeesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int countEmployee = cursor.getCount();
        cursor.close();
        return countEmployee;
    }

    public List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setIdCompany(cursor.getInt(0));
                company.setNameCompany(cursor.getString(1));
                companies.add(company);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return companies;
    }

    public int getCompaniesCount() {
        String countQuery = "SELECT * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int countCompaniew = cursor.getCount();
        cursor.close();
        return countCompaniew;
    }

    public void addCompany(Company company) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COMPANY, company.getNameCompany());
        db.insert(TABLE_COMPANY, null, values);
        db.close();
    }


    public void createDefaultEmployee() {
        int count = this.getEmployeesCount();
        if (count == 0) {
            for (int i = 1; i <= 20; i++) {
                Random rd = new Random();
                int idCompany = rd.nextInt(6) + 1;
                this.addEmployee(new Employee(idCompany, "Nhan vien " + i));
            }
        }
    }

    public void createDefaultCompany() {
        int count = this.getCompaniesCount();
        if (count == 0) {
            this.addCompany(new Company("Công ty 1"));
            this.addCompany(new Company("Công ty 2"));
            this.addCompany(new Company("Công ty 3"));
            this.addCompany(new Company("Công ty 4"));
            this.addCompany(new Company("Công ty 5"));
        } else {
            Log.d("xxxxxxxxxxxxx", "OK ");
        }
    }
}
