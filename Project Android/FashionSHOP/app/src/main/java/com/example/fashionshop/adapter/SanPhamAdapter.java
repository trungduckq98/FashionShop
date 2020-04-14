package com.example.fashionshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fashionshop.R;
import com.example.fashionshop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter  extends BaseAdapter {
    Context context;
    int resource;
    List<SanPham> arrSanPham;



    public SanPhamAdapter(Context context, int resource, List<SanPham> arrSanPham) {
        this.context = context;
        this.resource = resource;
        this.arrSanPham = arrSanPham;

    }

    @Override
    public int getCount() {
        return arrSanPham.size();
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
        ImageView img_Hinhanhsanpham;
        TextView txt_Tensanpham, txt_Giasanpham, txt_Doituongsanpham, txt_Sizesanpham;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_sanpham, null);

            viewHolder.img_Hinhanhsanpham = (ImageView) convertView.findViewById(R.id.img_hinhsanpham);
            viewHolder.txt_Tensanpham = (TextView) convertView.findViewById(R.id.txt_tensanpham);
            viewHolder.txt_Giasanpham = (TextView) convertView.findViewById(R.id.txt_giasanpham);
            viewHolder.txt_Doituongsanpham = (TextView) convertView.findViewById(R.id.txt_doituongsanpham);
            viewHolder.txt_Sizesanpham = (TextView) convertView.findViewById(R.id.txt_sizesanpham);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SanPham sanPham = arrSanPham.get(position);
        viewHolder.txt_Tensanpham.setText(sanPham.getTensp());
        viewHolder.txt_Giasanpham.setText("Giá: "+sanPham.getGiasp());
        viewHolder.txt_Doituongsanpham.setText("Giới tính: "+sanPham.getDoituongsp());
        viewHolder.txt_Sizesanpham.setText("Size: "+sanPham.getSizesp());
        Picasso.get().load(sanPham.getHinhsp())
                .placeholder(R.drawable.imagenotfound)
                .error(R.drawable.loadimageerror)
                .into(viewHolder.img_Hinhanhsanpham);
        return convertView;

    }
}
