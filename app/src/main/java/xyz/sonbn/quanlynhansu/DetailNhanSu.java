package xyz.sonbn.quanlynhansu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailNhanSu extends AppCompatActivity {
    static final int DELETE_REQUEST = 1;
    final Context context = this;
    private Button btnEdit, btnDelete, btnBack;
    private ImageView imageView;
    private TextView nameView, ageView, addressView, phoneView, emailView;
    private Bundle dataBundle;
    private DAOdb daOdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nhan_su);
        daOdb = new DAOdb(this);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);

        Intent data = getIntent();
        dataBundle = data.getBundleExtra("Data");

        imageView = (ImageView) findViewById(R.id.imagePreview);
        nameView = (TextView) findViewById(R.id.nameDetail);
        ageView = (TextView) findViewById(R.id.ageDetail);
        addressView = (TextView) findViewById(R.id.addressDetail);
        phoneView = (TextView) findViewById(R.id.phoneDetail);
        emailView = (TextView) findViewById(R.id.emailDetail);

        nameView.setText(dataBundle.getString("Name"));
        ageView.setText(dataBundle.getString("Age"));
        addressView.setText(dataBundle.getString("Address"));
        phoneView.setText(dataBundle.getString("Phone"));
        emailView.setText(dataBundle.getString("Email"));
        if (dataBundle.getString("Image") != null) {
            Glide.with(this).load(dataBundle.getString("Image")).into(imageView);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataToEdit = new Intent(DetailNhanSu.this, EditNhanSu.class);
                dataToEdit.putExtra("DataToEdit", dataBundle);
                startActivity(dataToEdit);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc muốn xóa người này?");
                Log.d("TEST", "TEST");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        daOdb.deleteRow(dataBundle.getInt("Id"));
                        startActivityForResult(new Intent(DetailNhanSu.this, MainActivity.class), DELETE_REQUEST);
                        finish();
                    }
                });

                builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailNhanSu.this, MainActivity.class));
                finish();
            }
        });
    }
}
