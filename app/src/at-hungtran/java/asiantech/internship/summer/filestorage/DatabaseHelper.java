package asiantech.internship.summer.filestorage;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "company.db";

    public static final String TABLE_COMPANY = "company";
    public static final String KEY_ID_COMPANY = "id";
    public static final String KEY_NAME_COMPANY = "name";

    public static final String TABLE_EMPLOYEE = "employee";
    public static final String KEY_ID_EMPLOYEE = "id";
    public static final String KEY_CODE_EMPLOYEE = "code";
    public static final String KEY_NAME_EMPLOYEE = "name";

    public static final String CREATE_TABLE_COMPANY =
            "CREATE TABLE " + TABLE_COMPANY + "(" +
                    KEY_ID_COMPANY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ", " + KEY_NAME_COMPANY + " TEXT NOT NULL" +
                    "," + ")";

    public static final String CREATE_TABLE_EMPLOYEE =
            "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
                    KEY_ID_EMPLOYEE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                    ", " + KEY_CODE_EMPLOYEE + " TEXT NOT NULL" +
                    "," + KEY_NAME_EMPLOYEE + " TEXT NOT NULL" +
                    ")";

    public static final int DATA_VERSION = 1;
    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
