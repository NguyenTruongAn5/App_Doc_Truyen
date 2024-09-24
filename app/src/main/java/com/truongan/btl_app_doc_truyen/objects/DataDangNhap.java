package com.truongan.btl_app_doc_truyen.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class DataDangNhap {
    String userName, passWord;

    public DataDangNhap(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public DataDangNhap() {
    }
    public DataDangNhap(JSONObject o) throws JSONException {
        userName = o.getString("username");
        passWord = o.getString("password");
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
