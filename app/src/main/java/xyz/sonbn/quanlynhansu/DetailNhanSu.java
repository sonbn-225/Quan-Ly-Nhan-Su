package xyz.sonbn.quanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailNhanSu extends AppCompatActivity {
    private Button btnEdit, btnDelete, btnBack;
    private ImageView imageView;
    private TextView nameView, ageView, addressView, phoneView, emailView;
    private Bundle dataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nhan_su);

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
            imageView.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(dataBundle.getString("Image")));
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
                Intent dataToDelete = new Intent(DetailNhanSu.this, DeleteNhanSu.class);
                Bundle idBundle = new Bundle();
                idBundle.putInt("Id", dataBundle.getInt("Id"));
                dataToDelete.putExtra("DataToDelete", idBundle);
                startActivity(dataToDelete);
                finish();
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
