package xyz.sonbn.quanlynhansu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SonBN on 7/14/2016.
 */
public class DBhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sqlitenhansu.db";
    public static final int DB_VERSION = 1;

    public static final String COMMA_SEP = ",";
    public static final String TEXT_TYPE = " TEXT";
    public static final String NUMERIC_TYPE = " NUMERIC";

    public static final String TABLE_NAME = "nhansu";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_IMAGE = "image";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            COLUMN_AGE + NUMERIC_TYPE + COMMA_SEP +
            COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
            COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
            COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP +
            COLUMN_IMAGE + TEXT_TYPE +
            " )";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
