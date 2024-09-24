package com.truongan.btl_app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.truongan.btl_app_doc_truyen.api.APILayUsers;
import com.truongan.btl_app_doc_truyen.interfaces.LayTruyenVe;
import com.truongan.btl_app_doc_truyen.objects.DataDangNhap;
import com.truongan.btl_app_doc_truyen.objects.MD5Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class dang_nhap extends AppCompatActivity implements LayTruyenVe {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnHome;
    private Button btnDangKi;
    List<DataDangNhap> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnHome = findViewById(R.id.btnHome);
        btnDangKi = findViewById(R.id.btnDangki);
        new APILayUsers(this).execute();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(dang_nhap.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    for (DataDangNhap data : dataList) {
                        if (username.equals(data.getUserName()) && data.getPassWord().equals(MD5Utils.md5(password))) {
                            Intent intent = new Intent(dang_nhap.this, MainActivity.class);
                            intent.putExtra("USERNAME", username);
                            startActivity(intent);
                            finish();
                            return;
                        }else{
                            Toast.makeText(dang_nhap.this, "Sai mật khẩu hoặc tài khoản!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(dang_nhap.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dangKiIntent = new Intent(dang_nhap.this, dang_ki.class);
                startActivity(dangKiIntent);
            }
        });
    }


    private void parseJsonData(String jsonData) {
        dataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DataDangNhap data = new DataDangNhap(jsonObject);
                dataList.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void BatDauTai() {
    }

    @Override
    public void DaTaiVe(String data) {
        parseJsonData(data);
    }

    @Override
    public void LoiKetNoi() {

    }
}
