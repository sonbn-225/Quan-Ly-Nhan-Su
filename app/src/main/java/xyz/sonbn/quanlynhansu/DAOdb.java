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
        cv.put(DBhelper.COLUMN_BIRTHDAY, nhanSu.getBirthday());
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
        ns.setBirthday(cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_BIRTHDAY)));
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

    //Get all data
    public List<NhanSu> getAllSortByNameAsc() {
        List<NhanSu> NhanSu = new ArrayList<NhanSu>();
        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu NhanViens = getRow(cursor);
            NhanSu.add(NhanViens);
            cursor.moveToNext();
        }
        cursor.close();

        return NhanSu;
    }

    //Get all data
    public List<NhanSu> getAllSortByNameDesc() {
        List<NhanSu> NhanSu = new ArrayList<NhanSu>();
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

    //Get all data
    public List<NhanSu> getAllSortByBirthdayAsc() {
        List<NhanSu> NhanSu = new ArrayList<NhanSu>();
        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_BIRTHDAY + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu NhanViens = getRow(cursor);
            NhanSu.add(NhanViens);
            cursor.moveToNext();
        }
        cursor.close();

        return NhanSu;
    }

    //Get all data
    public List<NhanSu> getAllSortByBirthdayDesc() {
        List<NhanSu> NhanSu = new ArrayList<NhanSu>();
        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_BIRTHDAY + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu NhanViens = getRow(cursor);
            NhanSu.add(NhanViens);
            cursor.moveToNext();
        }
        cursor.close();

        return NhanSu;
    }

    //Get Column ID
    public int[] getAllId() {
        int i = 0;

        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, DBhelper.COLUMN_NAME + " DESC");
        int[] allId = new int[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanSu nhanSu = getRow(cursor);
            allId[i] = nhanSu.getId();
            cursor.moveToNext();
        }
        cursor.close();

        return allId;
    }

    //Update
    public void updateRow(NhanSu nhanSu){
        ContentValues values = new ContentValues();

        values.put(DBhelper.COLUMN_NAME, nhanSu.getName());
        values.put(DBhelper.COLUMN_BIRTHDAY, nhanSu.getBirthday());
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
    public void deleteRow(int id) {
        database.delete(DBhelper.TABLE_NAME,
                DBhelper.COLUMN_ID+"= ?",
                new String[]{String.valueOf(id)});
    }
}
