package xyz.sonbn.quanlynhansu;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NhanSu> nhanSus;
    private DisplayAdapter displayAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private DAOdb daOdb;
    private String picturePath;
    private NhanSu nhanSuToEdit;
    private ImageView imageView;
    private TextView nameView, ageView, addressView, phoneView, emailView;
    private Bundle ssl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ssl = savedInstanceState;
        setContentView(R.layout.activity_main);

        init();
    }

    //Init onCreate
    private void init(){
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(displayAdapter);
        addItemClickListener(listView);
        initDB();

        if (daOdb.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
        }
        else {
            mainTitle.setAllCaps(true);
        }
    }

    /**
     * initialize database
     */
    private void initDB() {
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (NhanSu mi : daOdb.getAll()) {
            nhanSus.add(mi);
        }
    }

    public void sortByNameAsc(){
        setContentView(R.layout.activity_main);
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(displayAdapter);
        addItemClickListener(listView);
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (NhanSu mi : daOdb.getAllSortByNameAsc()) {
            nhanSus.add(mi);
        }

        if (daOdb.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
        }
        else {
            mainTitle.setAllCaps(true);
        }
    }

    public void sortByNameDesc(){
        setContentView(R.layout.activity_main);
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(displayAdapter);
        addItemClickListener(listView);
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (NhanSu mi : daOdb.getAllSortByNameDesc()) {
            nhanSus.add(mi);
        }

        if (daOdb.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
        }
        else {
            mainTitle.setAllCaps(true);
        }
    }

    public void sortByAgeAsc(){
        setContentView(R.layout.activity_main);
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(displayAdapter);
        addItemClickListener(listView);
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (NhanSu mi : daOdb.getAllSortByAgeAsc()) {
            nhanSus.add(mi);
        }

        if (daOdb.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
        }
        else {
            mainTitle.setAllCaps(true);
        }
    }

    public void sortByAgeDesc(){
        setContentView(R.layout.activity_main);
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(displayAdapter);
        addItemClickListener(listView);
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (NhanSu mi : daOdb.getAllSortByAgeDesc()) {
            nhanSus.add(mi);
        }

        if (daOdb.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
        }
        else {
            mainTitle.setAllCaps(true);
        }
    }

    public void btnChooseImage (View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                activeGallery();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                activeTakePhoto();
                dialog.dismiss();
            }
        });

        // show dialog on screen
        dialog.show();
    }

    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    picturePath = cursor.getString(column_index_data);
                    cursor.close();
                }
        }
        Log.d("IMAGE", picturePath);
        ImageView imageView = (ImageView) findViewById(R.id.imageEdit);
        imageView.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(picturePath));
    }

    /**
     * item clicked listener used to implement the react action when an item is clicked.
     *
     * @param listView
     */
    private void addItemClickListener(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NhanSu nhanSu = (NhanSu) listView.getItemAtPosition(position);
                nhanSuToEdit = nhanSu;

                setContentView(R.layout.detail_layout);

                imageView = (ImageView) findViewById(R.id.imageDetail);
                nameView = (TextView) findViewById(R.id.nameDetail);
                ageView = (TextView) findViewById(R.id.ageDetail);
                addressView = (TextView) findViewById(R.id.addressDetail);
                phoneView = (TextView) findViewById(R.id.phonenumberDetail);
                emailView = (TextView) findViewById(R.id.emailDetail);

                nameView.setText(nhanSu.getName());
                ageView.setText(nhanSu.getAge());
                addressView.setText(nhanSu.getAddress());
                phoneView.setText((nhanSu.getPhone()));
                emailView.setText(nhanSu.getEmail());

                imageView.setImageBitmap(ImageResizer
                        .decodeSampledBitmapFromFile(nhanSu.getImage()));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI", mCapturedImageURI.toString());
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(savedInstanceState.getString("mCapturedImageURI"));
        }
    }

    public void addButton (View view){
        setContentView(R.layout.add_layout);
    }

    public void addRow (View view) {
        NhanSu nhanSu = new NhanSu();

        EditText nameAddLayout = (EditText) findViewById(R.id.nameEdit);
        EditText ageAddLayout = (EditText) findViewById(R.id.ageEdit);
        EditText addressAddLayout = (EditText) findViewById(R.id.addressEdit);
        EditText phonenumberAddLayout = (EditText) findViewById(R.id.phonenumberEdit);
        EditText emailAddLayout = (EditText) findViewById(R.id.emailEdit);


        nhanSu.setName(nameAddLayout.getText().toString());
        nhanSu.setAge(ageAddLayout.getText().toString());
        nhanSu.setAddress(addressAddLayout.getText().toString());
        nhanSu.setPhone(phonenumberAddLayout.getText().toString());
        nhanSu.setEmail(emailAddLayout.getText().toString());
        nhanSu.setImage(picturePath);

        daOdb.addRow(nhanSu);
        setContentView(R.layout.activity_main);
        init();
    }



    /*public void back(){
        MySQLiteHelper db = new MySQLiteHelper(this);
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        if (db.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
        }
        else {
            mainTitle.setAllCaps(true);
        }


        arrayId = db.getAllId();
        arrayName = db.getAllName();
        arrayAge = db.getAllAge();
        arrayPhoneNumber = db.getAllPhoneNumber();
        arrayImage = db.getAllImage();


        DisplayAdapter adapter = new DisplayAdapter(MainActivity.this, arrayId, arrayName, arrayAge, arrayPhoneNumber,arrayImage);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                setContentView(R.layout.detail_layout);
                detail(arrayId[position]);
                selection = arrayId[position];
            }
        });
    }*/

    //Button Edit
    public  void btnEdit(View view) {
        setContentView(R.layout.edit_layout);

        EditText nameDetail = (EditText) findViewById(R.id.nameEditLayout);
        EditText ageDetail = (EditText) findViewById(R.id.ageEditLayout);
        EditText addressDetail = (EditText) findViewById(R.id.addressEditLayout);
        EditText phoneDetail = (EditText) findViewById(R.id.phonenumberEditLayout);
        EditText emailDetail = (EditText) findViewById(R.id.emailEditLayout);
        ImageView imageDetail = (ImageView) findViewById(R.id.imageEdit);

        nameDetail.setText(nhanSuToEdit.getName());
        ageDetail.setText(nhanSuToEdit.getAge());
        addressDetail.setText(nhanSuToEdit.getAddress());
        phoneDetail.setText(nhanSuToEdit.getPhone());
        emailDetail.setText(nhanSuToEdit.getEmail());
        picturePath = nhanSuToEdit.getImage();
        imageDetail.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(picturePath));
    }

    public void btnEditRow(View view) {
        NhanSu nhanSu = new NhanSu();

        EditText name = (EditText) findViewById(R.id.nameEditLayout);
        EditText age = (EditText) findViewById(R.id.ageEditLayout);
        EditText address = (EditText) findViewById(R.id.addressEditLayout);
        EditText phonenumber = (EditText) findViewById(R.id.phonenumberEditLayout);
        EditText email = (EditText) findViewById(R.id.emailEditLayout);

        nhanSu.setId(nhanSuToEdit.getId());
        nhanSu.setName(name.getText().toString());
        nhanSu.setAge(age.getText().toString());
        nhanSu.setAddress(address.getText().toString());
        nhanSu.setPhone(phonenumber.getText().toString());
        nhanSu.setEmail(email.getText().toString());
        nhanSu.setImage(picturePath);

        daOdb.updateRow(nhanSu);
        setContentView(R.layout.activity_main);
        init();
    }

    public void deleteRowButton(View view){
        setContentView(R.layout.delete_layout);

        daOdb.deleteRow(nhanSuToEdit);
        setContentView(R.layout.activity_main);
        init();
    }

    public void btnDelete(View v) {
        setContentView(R.layout.delete_layout);
    }

    /**
     * delete the current item;
     *
     * @param v
     */
    public void btnDeleteOnClick(View v) {
        DAOdb db = new DAOdb(this);
        db.deleteRow(nhanSuToEdit);
        db.close();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * go back to main activity
     *
     * @param v
     */
    public void btnBackOnClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_sort_by_name_asc:
                sortByNameAsc();
                return true;
            case R.id.action_sort_by_name_desc:
                sortByNameDesc();
                return true;
            case R.id.action_sort_by_age_asc:
                sortByAgeAsc();
                return true;
            case R.id.action_sort_by_age_desc:
                sortByAgeDesc();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
