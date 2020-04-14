package com.example.fashionshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashionshop.MainActivity;
import com.example.fashionshop.R;
import com.example.fashionshop.activity.GioHangActivity;
import com.example.fashionshop.connection.Server;
import com.example.fashionshop.model.GioHang;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    int resource;
    List<GioHang> arrGioHang;

    public GioHangAdapter(Context context, int resource, List<GioHang> arrGioHang) {
        this.context = context;
        this.resource = resource;
        this.arrGioHang = arrGioHang;
    }

    @Override
    public int getCount() {
        return arrGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView img_Hinhgiohang;
        TextView txt_Tengiohang, txt_Slgiohang, txt_Giagiohang;



    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_giohang, null);

            viewHolder.img_Hinhgiohang = (ImageView) convertView.findViewById(R.id.img_giohang);
            viewHolder.txt_Tengiohang = (TextView) convertView.findViewById(R.id.txt_tengiohang);
            viewHolder.txt_Slgiohang = (TextView) convertView.findViewById(R.id.txt_slgiohang);
            viewHolder.txt_Giagiohang = (TextView) convertView.findViewById(R.id.txt_giagiohang);
            convertView.setTag(viewHolder);
        }{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final GioHang gioHang = arrGioHang.get(position);
        viewHolder.txt_Tengiohang.setText(gioHang.getTensp());
        viewHolder.txt_Giagiohang.setText("Giá:  "+gioHang.getGiasp());
        viewHolder.txt_Slgiohang.setText("Số lượng: "+gioHang.getSoluongsp());
        Picasso.get().load(gioHang.getHinhsp())
                .placeholder(R.drawable.imagenotfound)
                .error(R.drawable.loadimageerror)
                .into(viewHolder.img_Hinhgiohang);



        return convertView;
    }


}
