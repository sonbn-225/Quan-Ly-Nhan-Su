package xyz.sonbn.quanlynhansu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SonBN on 7/13/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PersonDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_PERSONNEL_TABLE = "CREATE TABLE personnel ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "age TEXT, "+
                "address TEXT, "+
                "phone TEXT, "+
                "email TEXT )";

        // create books table
        db.execSQL(CREATE_PERSONNEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    private static final String TABLE_PERSONNEL = "PERSONNEL";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHNO = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE = "image";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_AGE,KEY_ADDRESS,KEY_PHNO,KEY_EMAIL};


    public void addRow(Personnel person){
        //for logging
        Log.d("addRow", person.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName()); // get name
        values.put(KEY_AGE, person.getAge()); // get age
        values.put(KEY_ADDRESS, person.getAddress());
        values.put(KEY_PHNO, person.getPhone());
        values.put(KEY_EMAIL, person.getEmail());


        // 3. insert
        db.insert(TABLE_PERSONNEL, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Personnel getRow(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_PERSONNEL, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Personnel person = new Personnel();
        person.setId(Integer.parseInt(cursor.getString(0)));
        person.setName(cursor.getString(1));
        person.setAge(cursor.getString(2));
        person.setAddress(cursor.getString(3));
        person.setPhone(cursor.getString(4));
        person.setEmail(cursor.getString(5));

        //log
        Log.d("getBook("+id+")", person.toString());

        // 5. return book
        return person;
    }

    public List<Personnel> getAllRow() {
        List<Personnel> persons = new LinkedList<Personnel>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PERSONNEL;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Personnel person = null;
        if (cursor.moveToFirst()) {
            do {
                person = new Personnel();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setAge(cursor.getString(2));
                person.setAddress(cursor.getString(3));
                person.setPhone(cursor.getString(4));
                person.setEmail(cursor.getString(5));

                // Add book to books
                persons.add(person);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRow()", persons.toString());

        // return persons
        return persons;
    }


    //Get all ID
    public int[] getAllId() {
        List<Personnel> persons = new LinkedList<Personnel>();
        int i=0;

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PERSONNEL;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int[] allId = new int[cursor.getCount()];

        // 3. go over each row, build book and add it to list
        Personnel person = null;
        if (cursor.moveToFirst()) {
            do {
                person = new Personnel();
                person.setId(Integer.parseInt(cursor.getString(0)));
                allId[i] = Integer.parseInt(cursor.getString(0));
                person.setName(cursor.getString(1));
                person.setAge(cursor.getString(2));
                person.setAddress(cursor.getString(3));
                person.setPhone(cursor.getString(4));
                person.setEmail(cursor.getString(5));

                // Add book to books
                persons.add(person);
                i++;
            } while (cursor.moveToNext());
        }


        // return persons
        return allId;
    }


    //getAllName
    public String[] getAllName() {
        List<Personnel> persons = new LinkedList<Personnel>();
        int i=0;

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PERSONNEL;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] allName = new String[cursor.getCount()];

        // 3. go over each row, build book and add it to list
        Personnel person = null;
        if (cursor.moveToFirst()) {
            do {
                person = new Personnel();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                allName[i] = cursor.getString(1);
                person.setAge(cursor.getString(2));
                person.setAddress(cursor.getString(3));
                person.setPhone(cursor.getString(4));
                person.setEmail(cursor.getString(5));

                // Add book to books
                persons.add(person);
                i++;
            } while (cursor.moveToNext());
        }

        Log.d("getAllRow()", persons.toString());

        // return persons
        return allName;
    }

    //Get all Age
    public String[] getAllAge() {
        List<Personnel> persons = new LinkedList<Personnel>();
        int i=0;

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PERSONNEL;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] allAge = new String[cursor.getCount()];

        // 3. go over each row, build book and add it to list
        Personnel person = null;
        if (cursor.moveToFirst()) {
            do {
                person = new Personnel();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setAge(cursor.getString(2));
                allAge[i] = cursor.getString(2) + " tuổi";
                person.setAddress(cursor.getString(3));
                person.setPhone(cursor.getString(4));
                person.setEmail(cursor.getString(5));

                // Add book to books
                persons.add(person);
                i++;
            } while (cursor.moveToNext());
        }

        Log.d("getAllRow()", persons.toString());

        // return persons
        return allAge;
    }

    //Get all Phone number
    public String[] getAllPhoneNumber() {
        List<Personnel> persons = new LinkedList<Personnel>();
        int i=0;

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PERSONNEL;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] allPhoneNumber = new String[cursor.getCount()];

        // 3. go over each row, build book and add it to list
        Personnel person = null;
        if (cursor.moveToFirst()) {
            do {
                person = new Personnel();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setAge(cursor.getString(2));
                person.setAddress(cursor.getString(3));
                person.setPhone(cursor.getString(4));
                allPhoneNumber[i] = cursor.getString(4);
                person.setEmail(cursor.getString(5));

                // Add book to books
                persons.add(person);
                i++;
            } while (cursor.moveToNext());
        }

        Log.d("getAllRow()", persons.toString());

        // return persons
        return allPhoneNumber;
    }



    public void updateRow(Personnel person, int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName()); // get name
        values.put(KEY_AGE, person.getAge()); // get age
        values.put(KEY_ADDRESS, person.getAddress());
        values.put(KEY_PHNO, person.getPhone());
        values.put(KEY_EMAIL, person.getEmail());

        // 3. updating row
        db.update(TABLE_PERSONNEL, //table
                values, // column/value
                KEY_ID + "=" + id, // selections
                null); //selection args

        // 4. close
        db.close();
    }

    public void deleteRow(Personnel person) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_PERSONNEL, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(person.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBook", person.toString());

    }
}
