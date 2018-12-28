package asiantech.internship.summer.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
    private static final String CHECK_DATA = " = ? ";
    private static final String AND_DATA = " AND ";
    private static final String SQL_QUERY_COMPANY = "CREATE TABLE " + TABLE_COMPANY + " (" +
            ID_COMPANY + " integer primary key AUTOINCREMENT, " + NAME_COMPANY + " TEXT)";
    private static final String SQL_QUERY_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
            ID_EMPLOYEE + " integer, " + COMPANY_ID + " integer, " + NAME_EMPLOYEE + " TEXT)";

    DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_QUERY_COMPANY);
        sqLiteDatabase.execSQL(SQL_QUERY_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(sqLiteDatabase);
    }

    long addEmployee(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_EMPLOYEE, employee.getIdEmployee());
        values.put(COMPANY_ID, employee.getCompanyId());
        values.put(NAME_EMPLOYEE, employee.getNameEmployee());
        long insert = db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
        return insert;
    }

    int updateEmployee(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_EMPLOYEE, employee.getNameEmployee());
        int updated = db.update(TABLE_EMPLOYEE, values, ID_EMPLOYEE + CHECK_DATA + AND_DATA + COMPANY_ID + CHECK_DATA,
                new String[]{String.valueOf(employee.getIdEmployee()), String.valueOf(employee.getCompanyId())});
        db.close();
        return updated;
    }

    List<Employee> getAllEmployeeById(int idCompany) {
        List<Employee> employees = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE COMPANY_ID = " + idCompany;
        SQLiteDatabase db = getWritableDatabase();
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

    Company getCompanyById(int idCompany) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_COMPANY, new String[]{ID_COMPANY,
                        NAME_COMPANY}, ID_COMPANY + CHECK_DATA,
                new String[]{String.valueOf(idCompany)}, null, null, null, null);
        cursor.moveToFirst();
        Company company = new Company(cursor.getInt(0), cursor.getString(1));
        cursor.close();
        db.close();
        return company;
    }

    int deleteEmployee(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        int deleted = db.delete(TABLE_EMPLOYEE, ID_EMPLOYEE + CHECK_DATA + AND_DATA + COMPANY_ID + CHECK_DATA,
                new String[]{String.valueOf(employee.getIdEmployee()),
                        String.valueOf(employee.getCompanyId())});
        db.close();
        return deleted;
    }

    List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = getWritableDatabase();
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

    private int getCompaniesCount() {
        String countQuery = "SELECT * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    private void addCompany(Company company) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COMPANY, company.getNameCompany());
        db.insert(TABLE_COMPANY, null, values);
        db.close();
    }

    void createDefaultCompany() {
        int count = getCompaniesCount();
        if (count == 0) {
            addCompany(new Company("Công ty 1"));
            addCompany(new Company("Công ty 2"));
            addCompany(new Company("Công ty 3"));
            addCompany(new Company("Công ty 4"));
            addCompany(new Company("Công ty 5"));
        }
    }
}
