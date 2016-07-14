package xyz.sonbn.quanlynhansu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SonBN on 7/14/2016.
 */
public class DAOdb {
    private SQLiteDatabase database;
    private DBhelper dbHelper;

    public DAOdb(Context context) {
        dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close any database object
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insert a text report item to the location database table
     *
     * @param nhanSu
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public void addRow(NhanSu nhanSu) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.COLUMN_NAME, nhanSu.getName());
        cv.put(DBhelper.COLUMN_AGE, nhanSu.getAge());
        cv.put(DBhelper.COLUMN_ADDRESS, nhanSu.getAddress());
        cv.put(DBhelper.COLUMN_PHONE, nhanSu.getPhone());
        cv.put(DBhelper.COLUMN_EMAIL, nhanSu.getEmail());
        cv.put(DBhelper.COLUMN_IMAGE, nhanSu.getImage());
        Log.d("TEST ADD NAME",nhanSu.getName());

        database.insert(DBhelper.TABLE_NAME, null, cv);
    }

    //READ
    //Get 1 row with para is cursor
    public NhanSu getRow(Cursor cursor){

        NhanSu ns = new NhanSu();

        ns.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_ID))));
        ns.setName(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_NAME)));
        ns.setAge(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_AGE)));
        ns.setAddress(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_ADDRESS)));
        ns.setPhone(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_PHONE)));
        ns.setEmail(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_EMAIL)));
        ns.setImage(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_IMAGE)));

        return ns;
    }

    //Get all data
    public List<NhanSu> getAll() {
        List<NhanSu> NhanSu = new ArrayList<>();
        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu NhanViens = getRow(cursor);
            NhanSu.add(NhanViens);
            cursor.moveToNext();
        }
        cursor.close();

        return NhanSu;
    }

    //Get column name
    public String[] getAllName() {
        int i = 0;

        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        String[] allName = new String[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu nhanSu = getRow(cursor);
            allName[i] = nhanSu.getName();
            cursor.moveToNext();
        }
        cursor.close();

        return allName;
    }

    //Get Column ID
    public int[] getAllId() {
        int i = 0;

        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        int[] allAge = new int[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu nhanSu = getRow(cursor);
            allAge[i] = nhanSu.getId();
            cursor.moveToNext();
        }
        cursor.close();

        return allAge;
    }

    //Get Column Age
    public String[] getAllAge() {
        int i = 0;

        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        String[] allAge = new String[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu nhanSu = getRow(cursor);
            allAge[i] = nhanSu.getAge();
            cursor.moveToNext();
        }
        cursor.close();

        return allAge;
    }

    //Get column phone
    public String[] getAllPhone() {
        int i = 0;

        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        String[] allPhone = new String[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu nhanSu = getRow(cursor);
            allPhone[i] = nhanSu.getPhone();
            cursor.moveToNext();
        }
        cursor.close();

        return allPhone;
    }

    //Get column Image
    public String[] getAllImage() {
        int i = 0;

        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        String[] allImage = new String[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu nhanSu = getRow(cursor);
            allImage[i] = nhanSu.getImage();
            cursor.moveToNext();
        }
        cursor.close();

        return allImage;
    }


    //Update
    public void updateRow(NhanSu nhanSu){
        ContentValues values = new ContentValues();

        values.put(DBhelper.COLUMN_NAME, nhanSu.getName());
        values.put(DBhelper.COLUMN_AGE, nhanSu.getAge());
        values.put(DBhelper.COLUMN_ADDRESS, nhanSu.getAddress());
        values.put(DBhelper.COLUMN_PHONE, nhanSu.getPhone());
        values.put(DBhelper.COLUMN_EMAIL, nhanSu.getEmail());
        values.put(DBhelper.COLUMN_IMAGE, nhanSu.getImage());

        database.update(DBhelper.TABLE_NAME,
                values,
                DBhelper.COLUMN_ID + "=" + nhanSu.getId(),
                null);
    }

    //Delete
    public void deleteRow(NhanSu nhanSu){
        database.delete(DBhelper.TABLE_NAME,
                DBhelper.COLUMN_ID+"= ?",
                new String[] {String.valueOf(nhanSu.getId())});
    }
}
