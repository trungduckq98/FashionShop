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

public class SpmoiAdapter extends BaseAdapter {
    Context context;
    int resource;
    List<SanPham> arrSanpham;

    public SpmoiAdapter(Context context, int resource, List<SanPham> arrSanpham) {
        this.context = context;
        this.resource = resource;
        this.arrSanpham = arrSanpham;
    }

    @Override
    public int getCount() {
        return arrSanpham.size();
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
        TextView txt_Tenspmoi;
        ImageView img_Hinhspmoi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_sanphammoi, null);
            viewHolder.txt_Tenspmoi = (TextView) convertView.findViewById(R.id.txt_tenspmoi);
            viewHolder.img_Hinhspmoi = (ImageView) convertView.findViewById(R.id.img_hinhspmoi);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SanPham sanPham = arrSanpham.get(position);
        viewHolder.txt_Tenspmoi.setText(sanPham.getTensp());
        Picasso.get().load(sanPham.getHinhsp())
                .placeholder(R.drawable.imagenotfound)
                .error(R.drawable.loadimageerror)
                .into(viewHolder.img_Hinhspmoi);
        return convertView;
    }
}
