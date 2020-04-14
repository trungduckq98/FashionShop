package com.example.fashionshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashionshop.R;
import com.example.fashionshop.adapter.SanPhamAdapter;
import com.example.fashionshop.connection.Server;
import com.example.fashionshop.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachTheoLoaispActivity extends AppCompatActivity {
    ListView lv_DsSanPham;
    Toolbar tb;
    SanPhamAdapter adapterSanPham;
    ArrayList<SanPham> arrSanpham;
    int idloaisanpham;
    String tenloaisanpham;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_theo_loaisp);
        anhXa();
        setAdapter();
        suKien();


        getSanpham(page);
    }

    private void anhXa(){
        lv_DsSanPham = (ListView) findViewById(R.id.lv_dssanpham);
        tb = (Toolbar) findViewById(R.id.toolbarloaisp);
    }
    private void setAdapter(){
        arrSanpham = new ArrayList<>();
        adapterSanPham = new SanPhamAdapter(this, R.layout.item_sanpham, arrSanpham);
        lv_DsSanPham.setAdapter(adapterSanPham);
    }
    private void suKien(){
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Intent intent = getIntent();
        idloaisanpham =  intent.getIntExtra("idlsp", 0);
        tenloaisanpham = intent.getStringExtra("tlsp");
        tb.setTitle(tenloaisanpham);


        lv_DsSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(DanhSachTheoLoaispActivity.this, ChiTietSanPhamActivity.class);
                SanPham sanPham = (SanPham) arrSanpham.get(position);
                intent1.putExtra("thongtinsanpham", sanPham);
                startActivity(intent1);
            }
        });


        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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

    }

    private void getSanpham(int Page){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.URL_GETSANPHAM +String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<response.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrSanpham.add(new SanPham(
                                        object.getInt("id"),
                                        object.getString("tensp"),
                                        object.getInt("giasp"),
                                        object.getString("hinhanhsp"),
                                        object.getString("doituongsp"),
                                        object.getString("sizesp"),
                                        object.getString("motasp"),
                                        object.getInt("idloaisp")
                                ));
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterSanPham.notifyDataSetChanged();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idsanpham", String.valueOf(idloaisanpham));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutest, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
