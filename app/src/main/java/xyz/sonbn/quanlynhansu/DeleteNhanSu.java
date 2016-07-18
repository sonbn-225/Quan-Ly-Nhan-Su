package xyz.sonbn.quanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DeleteNhanSu extends AppCompatActivity {
    private Button btnDeleteRow, btnBack;
    private Bundle dataBundle;
    private DAOdb daOdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_nhan_su);
        daOdb = new DAOdb(this);

        btnDeleteRow = (Button) findViewById(R.id.btnDeleteRow);
        btnBack = (Button) findViewById(R.id.btnBack);

        Intent data = getIntent();
        dataBundle = data.getBundleExtra("DataToDelete");
        btnDeleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daOdb.deleteRow(dataBundle.getInt("Id"));
                startActivity(new Intent(DeleteNhanSu.this, MainActivity.class));
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeleteNhanSu.this, MainActivity.class));
                finish();
            }
        });
    }
}
