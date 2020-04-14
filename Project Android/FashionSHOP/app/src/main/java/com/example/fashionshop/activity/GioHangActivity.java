package com.example.fashionshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
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
import com.example.fashionshop.adapter.GioHangAdapter;
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

public class GioHangActivity extends AppCompatActivity {
    TextView txt_Tongcong;
    ListView lv_Giohang;
    Toolbar toolbarGiohang;
    Button btn_Tieptuc, btn_Thanhtoan;
    public static GioHangAdapter adapterGiohang;
    public static ArrayList<GioHang> arrGioHang;
    ArrayList<SanPham> arrSanPham;
    int tongcong;
    int soluonghientai;
    int idgiohang;
    int giasanpham;
    String reponJSON;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        setAdapter();
        getGioHang();
        suKien();


    }

    private void anhXa() {
        lv_Giohang = (ListView) findViewById(R.id.lv_giohang);
        toolbarGiohang = (Toolbar) findViewById(R.id.toolbargiohang);
        txt_Tongcong = (TextView) findViewById(R.id.txt_tongtien);
        btn_Tieptuc = (Button) findViewById(R.id.btn_tieptuc);
        btn_Thanhtoan = (Button) findViewById(R.id.btn_thanhtoan);
    }

    private void setAdapter() {
        arrGioHang = new ArrayList<>();
        adapterGiohang = new GioHangAdapter(this, R.layout.item_giohang, arrGioHang);
        lv_Giohang.setAdapter(adapterGiohang);
        arrSanPham = new ArrayList<>();
    }

    private void suKien() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GioHangActivity.this, TrangChuActivity.class);
                startActivity(i);
            }
        });
        toolbarGiohang.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.digiohang) {
                    getGioHang();
                }else if (item.getItemId() == R.id.diuser){
                    Intent userintent = new Intent(getApplicationContext(), ThongTinUserActivity.class);
                    startActivity(userintent);
                }
                return false;
            }
        });

        lv_Giohang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GioHang gioHang = arrGioHang.get(position);
                 idgiohang = gioHang.getIdgiohang();

                int idsp = gioHang.getIdsp();
                soluonghientai=gioHang.getSoluongsp();
                int giasp = gioHang.getGiasp();
                giasanpham =giasp / soluonghientai;

                getsanphamtheoid(idsp);


                onclick(view);
            }
        });

        btn_Tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itx = new Intent(getApplicationContext(), TrangChuActivity.class);
                startActivity(itx);
            }
        });

        btn_Thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThanhtoan();
            }
        });
    }

    public void getGioHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrGioHang.clear();
        tongcong=0;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_GETGIOHANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        reponJSON = response;
                        Log.d("xxxjson", reponJSON);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("duc", response.length() + "");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                tongcong = tongcong+object.getInt("giasp");
                                arrGioHang.add(new GioHang(
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
                        Log.d("bbb", arrGioHang.size() + "");
                        adapterGiohang.notifyDataSetChanged();
                        txt_Tongcong.setText(tongcong+"");
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

    public void getsanphamtheoid(final int idsp) {
        arrSanPham.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_GETSPID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            Log.d("trungduc", "reponse: " + response);

                            int xid = object.getInt("id");
                            String xten = object.getString("tensp");
                            int xgia = object.getInt("giasp");
                            String xhinh = object.getString("hinhsp");
                            String xdoituong = object.getString("doituongsp");
                            String xsize = object.getString("sizesp");
                            String xmota = object.getString("motasp");
                            int xidloai = object.getInt("idloaisp");
                            SanPham sanPham = new SanPham(xid, xten, xgia, xhinh, xdoituong, xsize, xmota, xidloai);
                            arrSanPham.add(sanPham);


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
                Map<String, String> params = new HashMap<>();
                params.put("idsanpham", String.valueOf(idsp));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void onclick(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu = popupMenu.getMenu();
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menupopup, menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.viewsanpham:
                        SanPham sanPham = arrSanPham.get(0);
                        Intent intent = new Intent(GioHangActivity.this, ChiTietSanPhamActivity.class);
                        intent.putExtra("thongtinsanpham", sanPham);
                        startActivity(intent);
                        break;
                    case R.id.edit:
                        showDialog();
                        break;
                    case R.id.delete:
                        xoaGiohang(idgiohang);
                        break;
                }

                return false;
            }
        });

        popupMenu.show();

    }

    public void xoaGiohang(final int id) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrGioHang.clear();
        tongcong=0;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DELETEGIOHANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        reponJSON = response;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("duc", response.length() + "");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                tongcong = tongcong+object.getInt("giasp");
                                arrGioHang.add(new GioHang(
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
                        Log.d("bbb", arrGioHang.size() + "");
                        adapterGiohang.notifyDataSetChanged();
                        txt_Tongcong.setText(tongcong+"");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GioHangActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idgiohang", String.valueOf(id));
                params.put("iduser", String.valueOf(Server.ID_USER));
                return params;

            }

        };
        requestQueue.add(stringRequest);



    }
    public void xoaGiohangtheoiduser() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrGioHang.clear();
        tongcong=0;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DELETEGIOHANGTHEOUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("duc", response.length() + "");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                tongcong = tongcong+object.getInt("giasp");
                                arrGioHang.add(new GioHang(
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
                        Log.d("bbb", arrGioHang.size() + "");
                        adapterGiohang.notifyDataSetChanged();
                        txt_Tongcong.setText(tongcong+"");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GioHangActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("iduser", String.valueOf(Server.ID_USER));
                return params;

            }

        };
        requestQueue.add(stringRequest);



    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhập số lượng: ");

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view);
        final EditText editText = (EditText) view.findViewById(R.id.edt_nhapsoluong);
        builder.setPositiveButton("Thay đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(editText.getText().toString().trim())){
                    Toast.makeText(GioHangActivity.this, "Hãy nhập số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                }else {
                    int soluongthaydoi = Integer.parseInt(editText.getText().toString());
                    int giathaydoi = giasanpham * soluongthaydoi;
                    if (soluongthaydoi == soluonghientai){
                        Toast.makeText(GioHangActivity.this, "Nhập số lượng khác số ban đầu", Toast.LENGTH_SHORT).show();
                    }else  if(soluongthaydoi == 0){
                        Toast.makeText(GioHangActivity.this, "Số lượng sp khác 0", Toast.LENGTH_SHORT).show();
                    }else{
                        thayDoiSoluong(idgiohang, soluongthaydoi, giathaydoi);
                    }
                }


            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialogThanhtoan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int slgh = arrGioHang.size();
        builder.setTitle("Bạn xác định mua " +slgh +" mặt hàng với giá "+ tongcong +" VND?");


        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taiDonHangLenSV();
                xoaGiohangtheoiduser();
                Toast.makeText(GioHangActivity.this, "Đơn hàng của bạn đã được gửi đi, chúng tôi sẽ liên hệ lại để xác nhận", Toast.LENGTH_LONG).show();



            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void thayDoiSoluong(final int idgiohang, final int soluong, final int gia){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        arrGioHang.clear();
        tongcong=0;
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Server.URL_UPDATESL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        reponJSON = response;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("duc", response.length() + "");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                tongcong = tongcong+object.getInt("giasp");
                                arrGioHang.add(new GioHang(
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
                        Log.d("bbb", arrGioHang.size() + "");
                        adapterGiohang.notifyDataSetChanged();
                        txt_Tongcong.setText(tongcong+"");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idgiohang", String.valueOf(idgiohang));
                params.put("iduser", String.valueOf(Server.ID_USER));
                params.put("soluong", String.valueOf(soluong));
                params.put("giathaydoi", String.valueOf(gia));
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    private void taiDonHangLenSV(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Server.URL_TAIDONHANGLENSV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("donhang", reponJSON);

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
