package com.truongan.btl_app_doc_truyen.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class TruyenTranh {
    private String TenTruyen;
    private String TenChap;
    private String TenAnh;

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public TruyenTranh(String tenTruyen, String tenChap, String tenAnh, String noiDung) {
        TenTruyen = tenTruyen;
        TenChap = tenChap;
        TenAnh = tenAnh;
        NoiDung = noiDung;
    }

    private String NoiDung;
    public TruyenTranh() {

    }
    public TruyenTranh(JSONObject o) throws JSONException {
        TenTruyen = o.getString("ten_truyen");
        TenChap = o.getString("ten_chap");
        TenAnh = o.getString("ten_anh");
        NoiDung = o.getString("noi_dung");
    }
    public TruyenTranh(String tenTruyen, String tenChap, String tenAnh) {
        TenTruyen = tenTruyen;
        TenChap = tenChap;
        TenAnh = tenAnh;
    }

    public String getTenTruyen() {
        return TenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        TenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return TenChap;
    }

    public void setTenChap(String tenChap) {
        TenChap = tenChap;
    }

    public String getTenAnh() {
        return TenAnh;
    }

    public void setTenAnh(String tenAnh) {
        TenAnh = tenAnh;
    }
}
