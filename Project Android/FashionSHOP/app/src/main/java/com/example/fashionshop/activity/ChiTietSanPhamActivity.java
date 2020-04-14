package com.example.fashionshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.fashionshop.connection.Server;
import com.example.fashionshop.model.GioHang;
import com.example.fashionshop.model.SanPham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    ImageView img_Hinhsp;
    TextView txt_Tensp, txt_Giasp, txt_Doituongsp, txt_Sizesp, txt_Motasp;
    Spinner sp_Soluong;
    Button btn_Them;
    Toolbar toolbarChitietsp;
    private ArrayList<GioHang> arrGioHangX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        setAdapter();
        suKien();
        getGioHang();
    }

    private void anhXa(){
        img_Hinhsp = (ImageView) findViewById(R.id.img_hinhsp);
        txt_Tensp = (TextView) findViewById(R.id.txt_tensp);
        txt_Giasp = (TextView) findViewById(R.id.txt_giasp);
        txt_Doituongsp = (TextView) findViewById(R.id.doituongsp);
        txt_Sizesp = (TextView) findViewById(R.id.txt_sizesp);
        txt_Motasp = (TextView) findViewById(R.id.txt_motasp);
        sp_Soluong = (Spinner) findViewById(R.id.sp_sl);
        btn_Them = (Button) findViewById(R.id.btn_themgiohang);
        toolbarChitietsp = (Toolbar) findViewById(R.id.toolbarchitietsp);
    }

    private void setAdapter(){
        arrGioHangX = new ArrayList<>();
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        sp_Soluong.setAdapter(arrayAdapter);
    }

    private void suKien(){
        setSupportActionBar(toolbarChitietsp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitietsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarChitietsp.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.digiohang){
                    Intent ghintent = new Intent(getApplicationContext(), GioHangActivity.class);
                    startActivity(ghintent);
                }else if (item.getItemId() == R.id.diuser){
                    Intent userintent = new Intent(getApplicationContext(), ThongTinUserActivity.class);
                    startActivity(userintent);
                }
                return false;
            }
        });

        Intent intent = getIntent();
        final SanPham sanPham = (SanPham) intent.getSerializableExtra("thongtinsanpham");
        txt_Tensp.setText(sanPham.getTensp());
        txt_Giasp.setText("Giá: "+sanPham.getGiasp());
        txt_Doituongsp.setText("Giới tính: "+sanPham.getDoituongsp());
        txt_Sizesp.setText("Size: "+sanPham.getSizesp());
        txt_Motasp.setText("Mô tả chi tiết: + \n"+sanPham.getMotasp());
        Picasso.get().load(sanPham.getHinhsp())
                .placeholder(R.drawable.imagenotfound)
                .error(R.drawable.loadimageerror)
                .into(img_Hinhsp);

        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int soluongget = (int) sp_Soluong.getSelectedItem();
                int tonggia =  (sanPham.getGiasp() * soluongget);
                if (soluongget ==  0){
                    soluongget =1;
                    tonggia = sanPham.getGiasp();
                }


                if (ktra(sanPham.getId())){
                    themGioHang(Server.ID_USER, sanPham.getId(), sanPham.getTensp(), sanPham.getHinhsp(), tonggia, soluongget );
                }else {
                    Toast.makeText(ChiTietSanPhamActivity.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }






            }
        });

    }

    private void themGioHang(final int iduser, final int idsp, final String tensp, final String hinhsp, final int giasp, final int soluongsp){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrGioHangX.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_ADDGIOHANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrGioHangX.add(new GioHang(
                                        object.getInt("idgiohang"),
                                        object.getInt("iduser"),
                                        object.getInt("idsp"),
                                        object.getString("tensp"),
                                        object.getString("hinhsp"),
                                        object.getInt("giasp"),
                                        object.getInt("soluongsp")

                                ));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietSanPhamActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("iduser", String.valueOf(iduser) );
                params.put("idsp" , String.valueOf(idsp));
                params.put("tensp", tensp);
                params.put("hinhsp",  hinhsp );
                params.put("giasp", String.valueOf(giasp));
                params.put("soluongsp", String.valueOf(soluongsp));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getGioHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrGioHangX.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_GETGIOHANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("duc", response.length() + "");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                arrGioHangX.add(new GioHang(
                                        object.getInt("idgiohang"),
                                        object.getInt("iduser"),
                                        object.getInt("idsp"),
                                        object.getString("tensp"),
                                        object.getString("hinhsp"),
                                        object.getInt("giasp"),
                                        object.getInt("soluongsp")

                                ));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("iduser", String.valueOf(Server.ID_USER));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private boolean ktra(int n){
        for (int i= 0; i<arrGioHangX.size();i++){
            if (n == arrGioHangX.get(i).getIdsp()){
                return false;
            }
        }
        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutest, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
