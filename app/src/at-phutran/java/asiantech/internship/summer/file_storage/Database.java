package asiantech.internship.summer.file_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.model.Company;
import asiantech.internship.summer.model.Employee;

public class Database extends SQLiteOpenHelper {
    private static final String TABLE_COMPANY = "company";
    private static final String COL_ID_COMPANY = "id_company";
    private static final String COL_NAME_COMPANY = "name_company";
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String COL_ID_EMPOYEE = "id_employee";
    private static final String COL_NAME_EMPLOYEE = "name_employee";
    private static final String DATABASE_NAME = "company_management.sqlite";

    Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_COMPANY + " (" + COL_ID_COMPANY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME_COMPANY + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_EMPLOYEE + " (" + COL_ID_EMPOYEE + " INTEGER, " + COL_NAME_EMPLOYEE + " TEXT, " + COL_ID_COMPANY + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    void insertCompany(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (!name.equals("")) {
            contentValues.put(COL_NAME_COMPANY, name);
            db.insert(TABLE_COMPANY, null, contentValues);
        }
        db.close();
    }

    long insertEmployee(Employee employee) {
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(!employee.getNameEmployee().equals("")){
            contentValues.put(COL_ID_EMPOYEE, employee.getIdEmployee());
            contentValues.put(COL_NAME_EMPLOYEE, employee.getNameEmployee());
            contentValues.put(COL_ID_COMPANY, employee.getId_company());
            result = db.insert(TABLE_EMPLOYEE, null, contentValues);
        }
        db.close();
        return result;
    }

    List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(cursor.getInt(0));
                company.setName(cursor.getString(1));
                companies.add(company);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return companies;
    }

    List<Employee> getAllEmployeeById(int idCompany) {
        List<Employee> listEmployees = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COL_ID_COMPANY + " = ?", new String[]{String.valueOf(idCompany)});
        if(cursor.moveToFirst()){
            do {
                Employee employee = new Employee();
                employee.setIdEmployee(cursor.getInt(0));
                employee.setNameEmployee(cursor.getString(1));
                employee.setId_company(cursor.getInt(2));
                listEmployees.add(employee);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listEmployees;
    }
    int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME_EMPLOYEE, employee.getNameEmployee());
        int updated = db.update(TABLE_EMPLOYEE, values, COL_ID_EMPOYEE + " = ? " + "AND " + COL_ID_COMPANY + " = ? ",
                new String[]{String.valueOf(employee.getIdEmployee()), String.valueOf(employee.getId_company())});
        db.close();
        return updated;
    }
    int deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleted = db.delete(TABLE_EMPLOYEE,COL_ID_EMPOYEE + " = ? " + "AND " + COL_ID_COMPANY + " = ? ",
                new String[]{String.valueOf(employee.getIdEmployee()), String.valueOf(employee.getId_company())});
        db.close();
        return deleted;
    }
}
