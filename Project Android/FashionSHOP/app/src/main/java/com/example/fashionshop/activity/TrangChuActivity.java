package com.example.fashionshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashionshop.MainActivity;
import com.example.fashionshop.R;
import com.example.fashionshop.adapter.LoaispAdapter;
import com.example.fashionshop.adapter.SpmoiAdapter;
import com.example.fashionshop.connection.Server;
import com.example.fashionshop.model.LoaiSanPham;
import com.example.fashionshop.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity {
    GridView gv_Loaisp, gv_Spmoi;
    Toolbar toolbarTrangchu;
    Button btn_Getallsp;
    ArrayList<LoaiSanPham> arrLoaisp;
    LoaispAdapter loaispAdapter;
    ArrayList<SanPham> arrSpmoi;
    SpmoiAdapter spmoiAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        anhXa();
        setAdapter();
        getLoaiSanPham();
        getSanPhamMoi();
        suKien();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutest, menu);
        return super.onCreateOptionsMenu(menu);
    }




    private void anhXa(){
        gv_Loaisp = (GridView) findViewById(R.id.gv_loaisp);
        gv_Spmoi = (GridView) findViewById(R.id.gv_spmoi) ;
        toolbarTrangchu = (Toolbar) findViewById(R.id.toolbartrangchu);
        btn_Getallsp = (Button) findViewById(R.id.btn_getallsanpham);
    }

    private void setAdapter(){
        arrLoaisp = new ArrayList<>();
        loaispAdapter = new LoaispAdapter(this, R.layout.item_loaisp, arrLoaisp);
        gv_Loaisp.setAdapter(loaispAdapter);
        arrSpmoi = new ArrayList<>();
        spmoiAdapter = new SpmoiAdapter(this, R.layout.item_sanphammoi, arrSpmoi);
        gv_Spmoi.setAdapter(spmoiAdapter);
    }

    private void suKien(){
        gv_Loaisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idloaisanpham = arrLoaisp.get(position).getId();
                String tenloaisanpham = arrLoaisp.get(position).getTenloaisp();
                Intent intent = new Intent(TrangChuActivity.this, DanhSachTheoLoaispActivity.class);
                intent.putExtra("idlsp", idloaisanpham);
                intent.putExtra("tlsp", tenloaisanpham);
                startActivity(intent);
            }
        });

        gv_Spmoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrangChuActivity.this, ChiTietSanPhamActivity.class);
                SanPham sanPham = (SanPham) arrSpmoi.get(position);
                intent.putExtra("thongtinsanpham", sanPham );
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbarTrangchu);
        toolbarTrangchu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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

        btn_Getallsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itgetall = new Intent(getApplicationContext(), AllSanPhamActivity.class);
                startActivity(itgetall);
            }
        });
    }

    private void getLoaiSanPham(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, Server.URL_GETLOAISP, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i<response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrLoaisp.add(new LoaiSanPham(
                                        object.getInt("id"),
                                        object.getString("tenloaisp"),
                                        object.getString("hinhanhloaisp")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("kiemtra", arrLoaisp.size()+"");
                        loaispAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void getSanPhamMoi(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.URL_GETSPMOI, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i<response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrSpmoi.add(new SanPham(
                                        object.getInt("id"),
                                        object.getString("tensp"),
                                        object.getInt("giasp"),
                                        object.getString("hinhanhsp"),
                                        object.getString("doituongsp"),
                                        object.getString("sizesp"),
                                        object.getString("motasp"),
                                        object.getInt("idloaisp")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        spmoiAdapter.notifyDataSetChanged();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


}
