package asiantech.internship.summer.filestorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.filestorage.model.Company;
import asiantech.internship.summer.filestorage.model.Employee;

public class DataAccess extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "company.sqlite";

    private static final String TABLE_COMPANY = "company";
    private static final String ID_COMPANY = "id_company";
    private static final String KEY_NAME_COMPANY = "name";

    private static final String TABLE_EMPLOYEE = "employee";
    private static final String KEY_ID_EMPLOYEE = "id_employee";
    private static final String KEY_ID_COMPANY = "id_company";
    private static final String KEY_NAME_EMPLOYEE = "name";
    private static final String CHECK_DATA = " = ? ";
    private static final String AND_DATA = " AND ";

    private static final String CREATE_TABLE_COMPANY =
            "CREATE TABLE " + TABLE_COMPANY + "(" +
                    ID_COMPANY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ", " + KEY_NAME_COMPANY + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_EMPLOYEE =
            "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
                    KEY_ID_EMPLOYEE + " INTEGER NOT NULL" +
                    ", " + KEY_ID_COMPANY + " INTEGER NOT NULL" +
                    ", " + KEY_NAME_EMPLOYEE + " TEXT NOT NULL" +
                    ")";
    private static final int DATA_VERSION = 1;

    DataAccess(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COMPANY);
        db.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_EMPLOYEE, employee.getEmployeeId());
        values.put(KEY_ID_COMPANY, employee.getCompanyId());
        values.put(KEY_NAME_EMPLOYEE, employee.getEmployeeName());
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
    }

    Employee getEmployeeById(int idEmployee, int companyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID_EMPLOYEE, KEY_ID_COMPANY, KEY_NAME_EMPLOYEE}, KEY_ID_EMPLOYEE + CHECK_DATA + AND_DATA + KEY_ID_COMPANY + CHECK_DATA,
                new String[]{String.valueOf(idEmployee), String.valueOf(companyId)}, null, null, null, null);
        cursor.moveToFirst();
        Employee employee = new Employee(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        cursor.close();
        db.close();
        return employee;
    }

    int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_EMPLOYEE, employee.getEmployeeName());
        return db.update(TABLE_EMPLOYEE, values, KEY_ID_EMPLOYEE + CHECK_DATA + AND_DATA + KEY_ID_COMPANY + CHECK_DATA,
                new String[]{String.valueOf(employee.getEmployeeId()), String.valueOf(employee.getCompanyId())});
    }

    List<Employee> getAllEmployeeById(int idCompany) {
        List<Employee> employees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_ID_COMPANY + " = " + idCompany;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setEmployeeId(cursor.getInt(0));
                employee.setCompanyId(cursor.getInt(1));
                employee.setEmployeeName(cursor.getString(2));
                employees.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
    }

    Company getCompanyById(int idCompany) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COMPANY, new String[]{ID_COMPANY, KEY_NAME_COMPANY}, ID_COMPANY + CHECK_DATA,
                new String[]{String.valueOf(idCompany)}, null, null, null, null);
        cursor.moveToFirst();
        Company company = new Company(cursor.getInt(0), cursor.getString(1));
        cursor.close();
        db.close();
        return company;
    }

    int deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deteleEmployee = db.delete(TABLE_EMPLOYEE, KEY_ID_EMPLOYEE + CHECK_DATA + AND_DATA + KEY_ID_COMPANY + CHECK_DATA,
                new String[]{String.valueOf(employee.getEmployeeId()),
                        String.valueOf(employee.getCompanyId())});
        db.close();
        return deteleEmployee;
    }

    List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setCompanyId(cursor.getInt(0));
                company.setCompanyName(cursor.getString(1));
                companies.add(company);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return companies;
    }

    int getCompaniesCount() {
        String countQuery = "SELECT * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    void addCompany(Company company) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_COMPANY, company.getCompanyName());
        db.insert(TABLE_COMPANY, null, values);
        db.close();
    }
}
