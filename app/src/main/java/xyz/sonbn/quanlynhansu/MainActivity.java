package xyz.sonbn.quanlynhansu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ListView list;
    String[] arrayName, arrayAge, arrayPhoneNumber;
    int[] arrayId;
    Integer[] imageId;
    int selection;
    Uri imageURI;
    byte[] imageByte;
    static final int PICK_IMAGE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back();
    }

    public void addButton (View view){
        setContentView(R.layout.add_layout);
    }

    public void detail (int id) {
        setContentView(R.layout.detail_layout);
        final MySQLiteHelper db = new MySQLiteHelper(this);

        Personnel person = db.getRow(id);
        TextView nameDetail = (TextView) findViewById(R.id.nameDetail);
        TextView ageDetail = (TextView) findViewById(R.id.ageDetail);
        TextView addressDetail = (TextView) findViewById(R.id.addressDetail);
        TextView phoneDetail = (TextView) findViewById(R.id.phonenumberDetail);
        TextView emailDetail = (TextView) findViewById(R.id.emailDetail);

        nameDetail.setText(person.getName());
        ageDetail.setText(person.getAge());
        addressDetail.setText(person.getAddress());
        phoneDetail.setText(person.getPhone());
        emailDetail.setText(person.getEmail());
    }

    public void back(){
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


        DisplayAdapter adapter = new DisplayAdapter(MainActivity.this, arrayId, arrayName, arrayAge, arrayPhoneNumber,R.drawable.chopper);
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
    }

    public void backButton (View view) {
        setContentView(R.layout.activity_main);
        back();
    }

    public void editButton (View view) {
        setContentView(R.layout.edit_layout);
        final MySQLiteHelper db = new MySQLiteHelper(this);

        Personnel person = db.getRow(selection);
        EditText nameDetail = (EditText) findViewById(R.id.nameEditLayout);
        EditText ageDetail = (EditText) findViewById(R.id.ageEditLayout);
        EditText addressDetail = (EditText) findViewById(R.id.addressEditLayout);
        EditText phoneDetail = (EditText) findViewById(R.id.phonenumberEditLayout);
        EditText emailDetail = (EditText) findViewById(R.id.emailEditLayout);

        nameDetail.setText(person.getName());
        ageDetail.setText(person.getAge());
        addressDetail.setText(person.getAddress());
        phoneDetail.setText(person.getPhone());
        emailDetail.setText(person.getEmail());
    }

    public void editRowButton(View view) {
        final MySQLiteHelper db = new MySQLiteHelper(this);

        EditText name = (EditText) findViewById(R.id.nameEditLayout);
        EditText age = (EditText) findViewById(R.id.ageEditLayout);
        EditText address = (EditText) findViewById(R.id.addressEditLayout);
        EditText phonenumber = (EditText) findViewById(R.id.phonenumberEditLayout);
        EditText email = (EditText) findViewById(R.id.emailEditLayout);

        db.updateRow(new Personnel(name.getText().toString(),
                age.getText().toString(),
                address.getText().toString(),
                phonenumber.getText().toString(),
                email.getText().toString()), selection);
        setContentView(R.layout.activity_main);
        back();


    }

    public void deleteButton (View view) {
        setContentView(R.layout.delete_layout);
    }

    public void deleteRowButton(View view){
        setContentView(R.layout.delete_layout);
        MySQLiteHelper db = new MySQLiteHelper(this);

        db.deleteRow(db.getRow(selection));
        setContentView(R.layout.activity_main);
        back();
    }

    public void addRow (View view) {
        MySQLiteHelper db = new MySQLiteHelper(this);

        EditText name = (EditText) findViewById(R.id.nameEdit);
        EditText age = (EditText) findViewById(R.id.ageEdit);
        EditText address = (EditText) findViewById(R.id.addressEdit);
        EditText phonenumber = (EditText) findViewById(R.id.phonenumberEdit);
        EditText email = (EditText) findViewById(R.id.emailEdit);


        db.addRow(new Personnel(name.getText().toString(),
                age.getText().toString(),
                address.getText().toString(),
                phonenumber.getText().toString(),
                email.getText().toString()));
        setContentView(R.layout.activity_main);
        back();
    }

    public void chooseImage (View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            imageURI = uri;

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ImageView imageView = (ImageView) findViewById(R.id.imageEdit);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
