package xyz.sonbn.quanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DeleteNhanSu extends AppCompatActivity {
    private Button btnDeleteRow, btnBack;
    private Bundle dataBundle;
    private DAOdb daOdb;
    static final int DELETE_REQUEST = 1;

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
                startActivityForResult(new Intent(DeleteNhanSu.this, MainActivity.class), DELETE_REQUEST);
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
