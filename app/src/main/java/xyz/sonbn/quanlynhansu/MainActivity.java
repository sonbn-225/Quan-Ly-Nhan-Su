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

        btnAddRow = (ImageButton) findViewById(R.id.btnAdd);
        btnAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd();
            }
        });

        init();
    }

    //Init onCreate
    private void init(){
        TextView mainTitle = (TextView) findViewById(R.id.mainTitle);

        // Construct the data source
        nhanSus = new ArrayList();
        // Create the adapter to convert the array to views
        displayAdapter = new DisplayAdapter(this, nhanSus);
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
