package xyz.sonbn.quanlynhansu;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditNhanSu extends AppCompatActivity {
    static final int EDIT_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Button btnEditRow, btnBack;
    private String picturePath;
    private Uri mCapturedImageURI;
    private ImageView imagePreview;
    private EditText nameEditView, birthdayEditView, addressEditView, phoneEditView, emailEditView;
    private int idToEdit;
    private DAOdb daOdb;
    private Bundle dataBundle;
    private boolean allowSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nhan_su);
        daOdb = new DAOdb(this);

        btnEditRow = (Button) findViewById(R.id.btnEditRow);
        btnBack = (Button) findViewById(R.id.btnBack);

        nameEditView = (EditText) findViewById(R.id.nameEditText);
        birthdayEditView = (EditText) findViewById(R.id.birthdayEditText);
        addressEditView = (EditText) findViewById(R.id.addressEditText);
        phoneEditView = (EditText) findViewById(R.id.phoneEditText);
        emailEditView = (EditText) findViewById(R.id.emailEditText);
        imagePreview = (ImageView) findViewById(R.id.imagePreview);

        Intent data = getIntent();
        dataBundle = data.getBundleExtra("DataToEdit");

        idToEdit = dataBundle.getInt("Id");
        nameEditView.setText(dataBundle.getString("Name"));
        birthdayEditView.setText(dataBundle.getString("Birthday"));
        addressEditView.setText(dataBundle.getString("Address"));
        phoneEditView.setText(dataBundle.getString("Phone"));
        emailEditView.setText(dataBundle.getString("Email"));
        if (dataBundle.getString("Image") != null) {
            Glide.with(this).load(dataBundle.getString("Image")).into(imagePreview);
        }

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                birthdayEditView.setText(sdf.format(myCalendar.getTime()));
            }
        };

        birthdayEditView.setFocusable(false);
        birthdayEditView.setClickable(true);
        birthdayEditView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditNhanSu.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditNhanSu.this);
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
        btnEditRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanSu nhanSu = new NhanSu();
                allowSave = true;
                if (nameEditView.getText().toString().length() == 0) {
                    nameEditView.setError("Bắt buộc");
                    allowSave = false;
                }

                if (birthdayEditView.getText().toString().length() == 0) {
                    birthdayEditView.setError("");
                    allowSave = false;
                }

                if (phoneEditView.getText().toString().length() > 11) {
                    phoneEditView.setError("");
                    allowSave = false;
                }

                if (!isValidEmail(emailEditView.getText().toString())) {
                    emailEditView.setError("Sai định dạng email");
                    allowSave = false;
                }

                if (allowSave) {
                    nhanSu.setId(idToEdit);
                    nhanSu.setName(nameEditView.getText().toString());
                    nhanSu.setBirthday(birthdayEditView.getText().toString());
                    nhanSu.setAddress(addressEditView.getText().toString());
                    nhanSu.setPhone(phoneEditView.getText().toString());
                    nhanSu.setEmail(emailEditView.getText().toString());
                    if (picturePath == null) {
                        nhanSu.setImage(dataBundle.getString("Image"));
                    } else {
                        nhanSu.setImage(picturePath);
                    }

                    daOdb.updateRow(nhanSu);
                    startActivityForResult(new Intent(EditNhanSu.this, MainActivity.class), EDIT_REQUEST);
                    finish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditNhanSu.this, MainActivity.class));
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

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
