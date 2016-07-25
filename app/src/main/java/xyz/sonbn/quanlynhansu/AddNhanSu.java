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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNhanSu extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mCapturedImageURI;
    private DAOdb daOdb;
    private String picturePath;
    private ImageView imagePreview;
    private EditText nameAddLayout, birthdayAddLayout, addressAddLayout, phoneAddLayout, emailAddLayout;
    private Button btnBack, btnAddRow;
    private boolean allowSave;
    static final int ADD_REQUEST = 1;
    private CallbackManager callbackManager;
    private LoginButton btnLoginFacebook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daOdb = new DAOdb(this);

        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_add_nhan_su);
        btnLoginFacebook = (LoginButton) findViewById(R.id.btnLoginFacebook);

        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_birthday", "user_location"));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.d("tag", object.toString());
                                picturePath = "https://graph.facebook.com/" + object.optString("id") + "/picture?type=large";
                                nameAddLayout.setText(object.optString("name"));
                                Glide.with(AddNhanSu.this)
                                        .load(picturePath)
                                        .into(imagePreview);
                                birthdayAddLayout.setText(convertDate(object.optString("birthday")));
                                addressAddLayout.setText(object.optJSONObject("location").optString("name"));
                                emailAddLayout.setText(object.optString("email"));
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender,birthday,email,location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(AddNhanSu.this, "Login attempt cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(AddNhanSu.this, "Login attempt failed.", Toast.LENGTH_SHORT).show();
            }
        });

        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        nameAddLayout = (EditText) findViewById(R.id.nameEdit);
        birthdayAddLayout = (EditText) findViewById(R.id.birthdayEdit);
        addressAddLayout = (EditText) findViewById(R.id.addressEdit);
        phoneAddLayout = (EditText) findViewById(R.id.phoneEdit);
        emailAddLayout = (EditText) findViewById(R.id.emailEdit);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnAddRow = (Button) findViewById(R.id.btnAddRow);

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

                birthdayAddLayout.setText(sdf.format(myCalendar.getTime()));
            }
        };

        birthdayAddLayout.setFocusable(false);
        birthdayAddLayout.setClickable(true);
        birthdayAddLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNhanSu.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imagePreview.setOnClickListener(new View.OnClickListener() {
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

        btnAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanSu nhanSu = new NhanSu();
                allowSave = true;
                if (nameAddLayout.getText().toString().length() == 0) {
                    nameAddLayout.setError("Bắt buộc");
                    allowSave = false;
                }

                if (birthdayAddLayout.getText().toString().length() == 0) {
                    birthdayAddLayout.setError("");
                    allowSave = false;
                }

                if (phoneAddLayout.getText().toString().length() > 10) {
                    phoneAddLayout.setError("");
                    allowSave = false;
                }

                if (!isValidEmail(emailAddLayout.getText().toString())) {
                    emailAddLayout.setError("Sai định dạng email");
                    allowSave = false;
                }

                if (allowSave){
                    nhanSu.setName(nameAddLayout.getText().toString());
                    nhanSu.setBirthday(birthdayAddLayout.getText().toString());
                    nhanSu.setAddress(addressAddLayout.getText().toString());
                    nhanSu.setPhone(phoneAddLayout.getText().toString());
                    nhanSu.setEmail(emailAddLayout.getText().toString());
                    nhanSu.setImage(picturePath);

                    daOdb.addRow(nhanSu);
                    startActivityForResult(new Intent(AddNhanSu.this, MainActivity.class), ADD_REQUEST);
                    LoginManager.getInstance().logOut();
                    finish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddNhanSu.this, MainActivity.class), ADD_REQUEST);
                finish();
            }
        });
    }

    private String convertDate(String s) {
        return s.substring(3,5) +"/" + s.substring(0,2) + s.substring(5);
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
