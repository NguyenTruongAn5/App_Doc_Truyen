package com.truongan.btl_app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_doc_truyen extends AppCompatActivity {
TextView txtTenTruen, txtChap, txtNoiDung;
Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);

        AnhXa();
        TroVe();
        Intent intent = getIntent();

        String tenTruyen = intent.getStringExtra("TEN_TRUYEN");
        String tenChapter = intent.getStringExtra("TEN_CHAPTER");
        String noiDungTruyen = intent.getStringExtra("NOI_DUNG_TRUYEN");

        txtTenTruen.setText(tenTruyen);
        txtChap.setText(tenChapter);
        txtNoiDung.setText(noiDungTruyen);

    }
    private void TroVe(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(activity_doc_truyen.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }
    private void AnhXa(){
        txtTenTruen = findViewById(R.id.textTenTruyen);
        txtChap = findViewById(R.id.textTenChapter);
        txtNoiDung = findViewById(R.id.textNoiDungTruyen);
        btnHome = findViewById(R.id.btnHome);
    }
}