package com.truongan.btl_app_doc_truyen.api;

import android.os.AsyncTask;

import com.truongan.btl_app_doc_truyen.interfaces.LayTruyenVe;

import java.io.IOException;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class APILayTruyen extends AsyncTask<Void, Void, Void> {

    String data;
    LayTruyenVe layTruyenVe;

    public APILayTruyen(LayTruyenVe layTruyenVe) {
        this.layTruyenVe = layTruyenVe;
        this.layTruyenVe.BatDauTai();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://mitdthu.000webhostapp.com/data_truyen.php")
                .build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
        } catch (IOException e) {
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        if(data == null){
            this.layTruyenVe.LoiKetNoi();
        }else{
            this.layTruyenVe.DaTaiVe(data);
        }
    }
}
