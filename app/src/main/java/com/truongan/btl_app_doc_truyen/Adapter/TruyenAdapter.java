package com.truongan.btl_app_doc_truyen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.truongan.btl_app_doc_truyen.R;
import com.truongan.btl_app_doc_truyen.objects.TruyenTranh;

import java.util.ArrayList;
import java.util.List;

public class TruyenAdapter extends ArrayAdapter<TruyenTranh> {
    private Context ct;
    private ArrayList<TruyenTranh> arr;

    public TruyenAdapter(Context context, int resource, List<TruyenTranh> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }
    public TruyenTranh getTruyenAtPosition(int position) {
        return getItem(position);
    }

    public void SortTruyen(String s){
        s = s.toUpperCase();
        int k = 0;
        for(int i = 0; i < arr.size(); i++){
            TruyenTranh t = arr.get(i);
            String ten = t.getTenTruyen().toUpperCase();
            if(ten.indexOf(s)>= 0){
                arr.set(i, arr.get(k));
                arr.set(k, t);
                k++;
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(ct);
            convertView = inflater.inflate(R.layout.truyen_item, parent, false);
        }

        if (!arr.isEmpty() && position < arr.size()) {
            TruyenTranh truyenTranh = arr.get(position);

            TextView tenTruyen = convertView.findViewById(R.id.TenTruyen);
            TextView tenChap = convertView.findViewById(R.id.Chapter);
            ImageView AnhTruyen = convertView.findViewById(R.id.img_Truyen);

            tenTruyen.setText(truyenTranh.getTenTruyen());
            tenChap.setText(truyenTranh.getTenChap());
            Glide.with(ct).load(truyenTranh.getTenAnh()).into(AnhTruyen);
        }

        return convertView;
    }
}
