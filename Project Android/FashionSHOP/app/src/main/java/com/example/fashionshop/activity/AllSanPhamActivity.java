package com.example.fashionshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
@SuppressWarnings("unchecked")
public class AllSanPhamActivity extends AppCompatActivity {
    //Toolbar toolbarallsp;
    ListView lv_Allsp;
    Spinner sp_Sapxep;
    ArrayList<String> arrSapxep;
    SanPhamAdapter adapterSanPham;
    ArrayAdapter adapter;
    ArrayList<SanPham> arrSanpham;
    int vitrisp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_san_pham);
        anhXa();
        setAdapter();
        getSanpham();
        suKien();
    }

    private void anhXa() {
        // toolbarallsp = (Toolbar) findViewById(R.id.toolbarallsp);
        lv_Allsp = (ListView) findViewById(R.id.lv_allsp);
        sp_Sapxep = (Spinner) findViewById(R.id.sp_sapxep);
    }

    private void setAdapter() {
        arrSanpham = new ArrayList<>();
        adapterSanPham = new SanPhamAdapter(this, R.layout.item_sanpham, arrSanpham);
        lv_Allsp.setAdapter(adapterSanPham);

        arrSapxep = new ArrayList<>();
        arrSapxep.add("Mới nhất");
        arrSapxep.add("Giá: Cao->Thấp");
        arrSapxep.add("Giá: Thấp->Cao");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrSapxep);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sp_Sapxep.setAdapter(adapter);

    }

    private void suKien() {
        lv_Allsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                SanPham sanPham = (SanPham) arrSanpham.get(position);
                intent1.putExtra("thongtinsanpham", sanPham);
                startActivity(intent1);
            }
        });



        sp_Sapxep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sapXep(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                for (int i = 0; i < arrSanpham.size() - 1; i++) {
                    for (int j = i + 1; j < arrSanpham.size(); j++) {
                        if (arrSanpham.get(i).getId() < arrSanpham.get(j).getId()) {
                            SanPham temp = arrSanpham.get(i);
                            arrSanpham.set(i, arrSanpham.get(j));
                            arrSanpham.set(j, temp);
                        }
                    }
                }
                adapterSanPham.notifyDataSetChanged();
            }
        });
    }
    private void sapXep(int position){
        vitrisp = position;
        switch (position) {
            case 0:
                for (int i = 0; i < arrSanpham.size() - 1; i++) {
                    for (int j = i + 1; j < arrSanpham.size(); j++) {
                        if (arrSanpham.get(i).getId() < arrSanpham.get(j).getId()) {
                            SanPham temp = arrSanpham.get(i);
                            arrSanpham.set(i, arrSanpham.get(j));
                            arrSanpham.set(j, temp);
                        }
                    }
                }
                adapterSanPham.notifyDataSetChanged();

                break;
            case 1:
                for (int i = 0; i < arrSanpham.size() - 1; i++) {
                    for (int j = i + 1; j < arrSanpham.size(); j++) {
                        if (arrSanpham.get(i).getGiasp() < arrSanpham.get(j).getGiasp()) {
                            SanPham temp = arrSanpham.get(i);
                            arrSanpham.set(i, arrSanpham.get(j));
                            arrSanpham.set(j, temp);
                        }
                    }
                    adapterSanPham.notifyDataSetChanged();
                }

                break;
            case 2:
                for (int i = 0; i < arrSanpham.size() - 1; i++) {
                    for (int j = i + 1; j < arrSanpham.size(); j++) {
                        if (arrSanpham.get(i).getGiasp() > arrSanpham.get(j).getGiasp()) {
                            SanPham temp = arrSanpham.get(i);
                            arrSanpham.set(i, arrSanpham.get(j));
                            arrSanpham.set(j, temp);
                        }
                    }
                   adapterSanPham.notifyDataSetChanged();

                }

                break;

        }
    }

    private void getSanpham() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrSanpham.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_GETALLSP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < response.length(); i++) {
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

                        //adapterSanPham.notifyDataSetChanged();
                        sapXep(vitrisp);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void search(final String textaa) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrSanpham.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < response.length(); i++) {
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

                      //  adapterSanPham.notifyDataSetChanged();
                        sapXep(vitrisp);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("text", textaa);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusearch, menu);
        MenuItem item = menu.findItem(R.id.searchview);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast.makeText(LoaiSanPhamActivity.this, "khong co chu nao", Toast.LENGTH_SHORT).show();
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("ducsearch", newText);
                search(newText);
                return false;
            }


        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.xdigiohang:
                Intent xdigiohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(xdigiohang);
                break;
            case R.id.xdiuser:
                Intent xdiuser = new Intent(getApplicationContext(), ThongTinUserActivity.class);
                startActivity(xdiuser);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
