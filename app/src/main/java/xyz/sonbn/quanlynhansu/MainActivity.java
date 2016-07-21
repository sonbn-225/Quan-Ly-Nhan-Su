package xyz.sonbn.quanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NhanSu> nhanSus;
    private DisplayAdapter displayAdapter;
    private ListView listView;
    private DAOdb daOdb;
    private ImageButton btnAddRow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(0);
    }

    /**
     * initialize database
     */
    private void init(int i) {
        btnAddRow = (ImageButton) findViewById(R.id.btnAdd);
        daOdb = new DAOdb(this);

        btnAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd();
            }
        });

        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(displayAdapter);
        addItemClickListener(listView);
        addItemLongClickListener(listView);

        if (daOdb.getAllId().length == 0){
            mainTitle.setText("Không có nhân viên nào\n Nhấn dấu '+' để thêm nhân viên");
            mainTitle.setTextSize(16);
            mainTitle.setPadding(0,8,0,0);
        }
        else {
            mainTitle.setAllCaps(true);
        }
        //        add data from database to images ArrayList
        switch (i){
            case 1:
                for (NhanSu mi : daOdb.getAllSortByNameAsc()) {
                nhanSus.add(mi);
            }
                break;
            case 2:
                for (NhanSu mi : daOdb.getAllSortByNameDesc()) {
                    nhanSus.add(mi);
                }
                break;
            case 3:
                for (NhanSu mi : daOdb.getAllSortByAgeAsc()) {
                    nhanSus.add(mi);
                }
                break;
            case 4:
                for (NhanSu mi : daOdb.getAllSortByAgeDesc()) {
                    nhanSus.add(mi);
                }
                break;
            default:
                for (NhanSu mi : daOdb.getAll()) {
                    nhanSus.add(mi);
                }

        }
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

                Intent dataDetail = new Intent(MainActivity.this, DetailNhanSu.class);
                Bundle dataDetailBundle = new Bundle();

                dataDetailBundle.putInt("Id", nhanSu.getId());
                dataDetailBundle.putString("Name", nhanSu.getName());
                dataDetailBundle.putString("Age", nhanSu.getAge());
                dataDetailBundle.putString("Address", nhanSu.getAddress());
                dataDetailBundle.putString("Phone", nhanSu.getPhone());
                dataDetailBundle.putString("Email", nhanSu.getEmail());
                dataDetailBundle.putString("Image", nhanSu.getImage());

                dataDetail.putExtra("Data", dataDetailBundle);

                startActivity(dataDetail);
            }
        });
    }

    private void addItemLongClickListener(final ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                NhanSu nhanSu = (NhanSu) listView.getItemAtPosition(position);

                Intent dataToEdit = new Intent(MainActivity.this, EditNhanSu.class);
                Bundle dataEditBundle = new Bundle();

                dataEditBundle.putInt("Id", nhanSu.getId());
                dataEditBundle.putString("Name", nhanSu.getName());
                dataEditBundle.putString("Age", nhanSu.getAge());
                dataEditBundle.putString("Address", nhanSu.getAddress());
                dataEditBundle.putString("Phone", nhanSu.getPhone());
                dataEditBundle.putString("Email", nhanSu.getEmail());
                dataEditBundle.putString("Image", nhanSu.getImage());

                dataToEdit.putExtra("DataToEdit", dataEditBundle);

                startActivity(dataToEdit);
                return true;
            }
        });
    }

    public void btnAdd() {
        Intent i = new Intent(MainActivity.this, AddNhanSu.class);
        startActivity(i);
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
                init(1);
                return true;
            case R.id.action_sort_by_name_desc:
                init(2);
                return true;
            case R.id.action_sort_by_age_asc:
                init(3);
                return true;
            case R.id.action_sort_by_age_desc:
                init(4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
