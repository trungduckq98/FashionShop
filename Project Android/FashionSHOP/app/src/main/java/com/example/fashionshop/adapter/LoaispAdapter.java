package com.example.fashionshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fashionshop.R;
import com.example.fashionshop.model.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaispAdapter extends BaseAdapter {
    Context context;
    int resource;
    List<LoaiSanPham> arrLoaisp;


    public LoaispAdapter(Context context, int resource, List<LoaiSanPham> arrLoaisp) {
        this.context = context;
        this.resource = resource;
        this.arrLoaisp = arrLoaisp;
    }

    @Override
    public int getCount() {
        return arrLoaisp.size();
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
        TextView txt_Tenloaisp;
        ImageView img_Hinhloaisp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_loaisp, null);
            viewHolder.txt_Tenloaisp = (TextView) convertView.findViewById(R.id.txt_tenloaisp);
            viewHolder.img_Hinhloaisp = (ImageView) convertView.findViewById(R.id.img_hinhloaisp);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LoaiSanPham loaisp = (LoaiSanPham) arrLoaisp.get(position);
        viewHolder.txt_Tenloaisp.setText(loaisp.getTenloaisp());
        Picasso.get().load(loaisp.getHinhloaisp())
                .placeholder(R.drawable.imagenotfound)
                .error(R.drawable.loadimageerror)
                .into(viewHolder.img_Hinhloaisp);
        return convertView;
    }

}
