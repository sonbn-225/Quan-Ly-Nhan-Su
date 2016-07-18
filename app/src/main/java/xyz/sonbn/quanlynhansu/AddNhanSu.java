package xyz.sonbn.quanlynhansu;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNhanSu extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mCapturedImageURI;
    private DAOdb daOdb;
    private String picturePath;
    private ImageView imagePreview;
    private EditText nameAddLayout;
    private EditText ageAddLayout;
    private EditText addressAddLayout;
    private EditText phonenumberAddLayout;
    private EditText emailAddLayout;
    private Button btnChooseImage, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_su);
        daOdb = new DAOdb(this);

        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        nameAddLayout = (EditText) findViewById(R.id.nameEdit);
        ageAddLayout = (EditText) findViewById(R.id.ageEdit);
        addressAddLayout = (EditText) findViewById(R.id.addressEdit);
        phonenumberAddLayout = (EditText) findViewById(R.id.phonenumberEdit);
        emailAddLayout = (EditText) findViewById(R.id.emailEdit);
        btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddNhanSu.this);
                dialog.setContentView(R.layout.custom_dialog_box);
                dialog.setTitle("Alert Dialog View");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeGallery();
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeTakePhoto();
                        dialog.dismiss();
                    }
                });

                // show dialog on screen
                dialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNhanSu.this, MainActivity.class));
                finish();
            }
        });
    }


    /**
     * take a photo
     */
    public void activeTakePhoto() {
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
    public void activeGallery() {
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
                    this.picturePath = cursor.getString(columnIndex);
                    cursor.close();
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    this.picturePath = cursor.getString(column_index_data);
                    cursor.close();
                }
        }
        imagePreview.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(this.picturePath));
    }

    public void btnAddRow(View view) {
        NhanSu nhanSu = new NhanSu();
        if (nameAddLayout.getText().toString().length() == 0) {
            nameAddLayout.setError("Bắt buộc");
        }

        if (ageAddLayout.getText().toString().length() == 0) {
            ageAddLayout.setError("");
        }

        if (phonenumberAddLayout.getText().toString().length() > 10) {
            phonenumberAddLayout.setError("");
        }

        if (!isValidEmail(emailAddLayout.getText().toString())) {
            emailAddLayout.setError("Sai định dạng email");
        }

        nhanSu.setName(nameAddLayout.getText().toString());
        nhanSu.setAge(ageAddLayout.getText().toString());
        nhanSu.setAddress(addressAddLayout.getText().toString());
        nhanSu.setPhone(phonenumberAddLayout.getText().toString());
        nhanSu.setEmail(emailAddLayout.getText().toString());
        nhanSu.setImage(this.picturePath);

        daOdb.addRow(nhanSu);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public String getImagePath() {
        return this.picturePath;
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

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
