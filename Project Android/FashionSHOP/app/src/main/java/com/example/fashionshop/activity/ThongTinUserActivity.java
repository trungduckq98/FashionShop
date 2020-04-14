package com.example.fashionshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashionshop.R;
import com.example.fashionshop.connection.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinUserActivity extends AppCompatActivity {
    TextView txt_User, txt_Sdt, txt_Email, txt_Diachi;
    Toolbar toolbarTTuser;
    Button btn_Doithongtin, btn_Doimk;
    String xUser ,xSDT, xEmail, xDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_user);
        anhXa();
        suKien();
        getThongtinUser();
    }

    private void anhXa(){
        txt_User = (TextView) findViewById(R.id.txt_username);
        txt_Sdt = (TextView) findViewById(R.id.txt_sdt);
        txt_Email = (TextView) findViewById(R.id.txt_email);
        txt_Diachi = (TextView) findViewById(R.id.txt_diachi);
        toolbarTTuser = (Toolbar) findViewById(R.id.toolbarthongtinuser);
        btn_Doithongtin = (Button) findViewById(R.id.btn_doithongtin);
        btn_Doimk = (Button)findViewById(R.id.btn_doimk);
    }

    private void suKien(){
        setSupportActionBar(toolbarTTuser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTTuser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarTTuser.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.digiohang){
                    Intent ghintent = new Intent(getApplicationContext(), GioHangActivity.class);
                    startActivity(ghintent);
                }else if (item.getItemId() == R.id.diuser){
                    getThongtinUser();
                }
                return false;
            }
        });

        btn_Doithongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDialogDoitt();
            }
        });

        btn_Doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDoimk();
            }
        });


    }

    private void showDialogDoitt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhập thông tin cần thay đổi: ");

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doitt, null);

        builder.setView(view);
        final EditText edt_tdsdt = (EditText) view.findViewById(R.id.edt_nhapsdt);
        final EditText edt_tdemail = (EditText) view.findViewById(R.id.edt_nhapemail);
        final EditText edt_ttdiachi = (EditText) view.findViewById(R.id.edt_nhapdiachi);
        edt_tdsdt.setText(xSDT);
        edt_tdemail.setText(xEmail);
        edt_ttdiachi.setText(xDiaChi);
        builder.setPositiveButton("Thay đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edt_tdsdt.getText().toString().trim()) || TextUtils.isEmpty(edt_tdsdt.getText().toString().trim()) || TextUtils.isEmpty(edt_tdsdt.getText().toString().trim())  ){
                    Toast.makeText(ThongTinUserActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (edt_tdsdt.getText().toString().trim().equals(xSDT) && edt_tdemail.getText().toString().trim().equals(xEmail) && edt_ttdiachi.getText().toString().trim().equals(xDiaChi)  ){
                        Toast.makeText(ThongTinUserActivity.this, "Bạn chưa thay đổi thông tin nào", Toast.LENGTH_SHORT).show();
                    }else {
                        doiThongtinUser(edt_tdsdt.getText().toString().trim(), edt_tdemail.getText().toString().trim(), edt_ttdiachi.getText().toString().trim() );
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

    private void showDialogDoimk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhập thông tin: ");

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimk, null);

        builder.setView(view);
        final EditText edt_nhapmkcu = (EditText) view.findViewById(R.id.edt_nhapmkcu);
        final EditText edt_nhapmkmoi = (EditText) view.findViewById(R.id.edt_nhapmkmoi);
        final EditText edt_nhaplaimkmoi = (EditText) view.findViewById(R.id.edt_nhaplaimkmoi);

        builder.setPositiveButton("Thay đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edt_nhapmkcu.getText().toString().trim()) || TextUtils.isEmpty(edt_nhapmkmoi.getText().toString().trim()) || TextUtils.isEmpty(edt_nhaplaimkmoi.getText().toString().trim())  ){
                    Toast.makeText(ThongTinUserActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    String mkcu = edt_nhapmkcu.getText().toString().trim();
                    String mkmoi = edt_nhapmkmoi.getText().toString().trim();
                    String mkmoi2 = edt_nhaplaimkmoi.getText().toString().trim();
                    if  ( mkmoi.equals(mkmoi2) ){
                        doimatkhau(mkcu, mkmoi);
                    } else {

                        Toast.makeText(ThongTinUserActivity.this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
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

    private void getThongtinUser(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_GETTTUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            xUser = object.getString("ten");
                            xSDT = object.getString("sdt");
                            xEmail = object.getString("email");
                            xDiaChi = object.getString("diachi");

                            toolbarTTuser.setTitle("Chào mừng "+xUser);
                            txt_User.setText(xUser);
                            txt_Sdt.setText("SDT: "+xSDT);
                            txt_Email.setText("Email: "+xEmail);
                            txt_Diachi.setText("Địa chỉ: "+xDiaChi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("iduser", String.valueOf(Server.ID_USER));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void doiThongtinUser(final String sdt, final String email, final String diachi){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DOITT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ThongTinUserActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject object = new JSONObject(response);
                            xUser = object.getString("ten");
                            xSDT = object.getString("sdt");
                            xEmail = object.getString("email");
                            xDiaChi = object.getString("diachi");

                            toolbarTTuser.setTitle("Chào mừng "+xUser);
                            txt_User.setText(xUser);
                            txt_Sdt.setText("SDT: "+xSDT);
                            txt_Email.setText("Email: "+xEmail);
                            txt_Diachi.setText("Địa chỉ: "+xDiaChi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("iduser", String.valueOf(Server.ID_USER));
                params.put("sdtuser", sdt);
                params.put("emailuser", email);
                params.put("diachiuser", diachi);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void doimatkhau(final String mkcu, final String mkmoi){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DOIMK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseaaa", response);
                        if (response.trim().equals("thanhcong")){
                            Toast.makeText(ThongTinUserActivity.this, "Thay đổi mk thành công", Toast.LENGTH_SHORT).show();
                        } else if (response.trim().equals("mkcukhongchinhxac")){
                            Toast.makeText(ThongTinUserActivity.this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                        }
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
                params.put("iduser", String.valueOf(Server.ID_USER));
                params.put("mkcu", mkcu);
                params.put("mkmoi", mkmoi);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}
