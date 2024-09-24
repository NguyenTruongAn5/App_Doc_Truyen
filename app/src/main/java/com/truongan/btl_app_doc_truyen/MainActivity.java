package com.truongan.btl_app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.truongan.btl_app_doc_truyen.Adapter.TruyenAdapter;
import com.truongan.btl_app_doc_truyen.api.APILayTruyen;
import com.truongan.btl_app_doc_truyen.interfaces.LayTruyenVe;
import com.truongan.btl_app_doc_truyen.objects.TruyenTranh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity implements LayTruyenVe {
    GridView gridView;
    TextView userName;
    Toolbar toolbar;
    NavigationView navigationView;
    ListView listView_Menu;
    DrawerLayout drawerLayout;
    EditText editTK;
    TruyenAdapter adapter;
    ArrayList<TruyenTranh> arrayListTruyenTranh;
    ArrayList<item_menu> listMenu;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        AnhXa();
        SetUp();
        SetClick();
        ActionToolBar();
        ActionMenu();
        new APILayTruyen(this).execute();

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("USERNAME");
            if (username != null) {
                userName.setText(username);
            }
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionMenu() {
        listMenu = new ArrayList<>();
        listMenu.add(new item_menu("Home", R.drawable.home));
        listMenu.add(new item_menu("Đăng nhập", R.drawable.enter));
        listMenu.add(new item_menu("Thoát", R.drawable.arrow));

        menuAdapter = new MenuAdapter(this, R.layout.itemmenu, listMenu);
        listView_Menu.setAdapter(menuAdapter);

        listView_Menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_menu selectedItem = listMenu.get(position);
                String itemName = selectedItem.itemName;

                switch (itemName) {
                    case "Home":
                        break;
                    case "Đăng nhập":
                        Intent intent = new Intent(MainActivity.this, dang_nhap.class);
                        startActivity(intent);
                        break;
                    case "Thoát":
                        finish();
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void init() {
        arrayListTruyenTranh = new ArrayList<>();
        adapter = new TruyenAdapter(this, 0, arrayListTruyenTranh);
    }

    private void AnhXa() {
        gridView = findViewById(R.id.dgvListTruyen);
        editTK = findViewById(R.id.editTimKiem);
        toolbar = findViewById(R.id.toobar);
        listView_Menu = findViewById(R.id.listviewMenu);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawlayout);
        userName = findViewById(R.id.userName);
    }

    private void SetUp() {
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TruyenTranh selectedTruyen = arrayListTruyenTranh.get(position);

                Intent intent = new Intent(MainActivity.this, activity_doc_truyen.class);

                intent.putExtra("TEN_TRUYEN", selectedTruyen.getTenTruyen());
                intent.putExtra("TEN_CHAPTER", selectedTruyen.getTenChap());
                intent.putExtra("NOI_DUNG_TRUYEN", selectedTruyen.getNoiDung());

                startActivity(intent);
            }
        });
    }

    private void SetClick() {
        editTK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editTK.getText().toString();
                adapter.SortTruyen(s);
            }
        });
    }

    @Override
    public void BatDauTai() {
        Toast.makeText(this, "Đang lấy dữ liệu về", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DaTaiVe(String data) {
        try {
            arrayListTruyenTranh.clear();
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++){
                JSONObject o = arr.getJSONObject(i);
                arrayListTruyenTranh.add(new TruyenTranh(o));
            }
            adapter = new TruyenAdapter(this, 0, arrayListTruyenTranh);
            gridView.setAdapter(adapter);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void LoiKetNoi() {
        Toast.makeText(this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
    }
}