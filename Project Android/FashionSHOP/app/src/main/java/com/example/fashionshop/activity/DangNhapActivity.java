package com.example.fashionshop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    private EditText edt_Tkdangnhap, edt_Mkdangnhap;
    private Button btn_Dangnhap, btn_Didangki;
    int REQUEST_CODE_DN=1998;
    CheckBox checbx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        suKien();
        getdulieu();




    }

    private void suKien(){
        btn_Dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edt_Tkdangnhap.getText().toString().trim();
                String mk = edt_Mkdangnhap.getText().toString().trim();
                if (TextUtils.isEmpty(tk) || TextUtils.isEmpty(mk)){
                    Toast.makeText(DangNhapActivity.this, "Hãy nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                }else {
                    if (checbx.isChecked()==true){
                        login(tk, mk);
                        luuAcc(tk,mk);
                    }else {
                        login(tk, mk);
                        khongluu();
                    }

                }

            }
        });

        btn_Didangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DN);
            }
        });
    }

    private void anhXa(){
        edt_Tkdangnhap = (EditText) findViewById(R.id.edt_tkdangnhap);
        edt_Mkdangnhap = (EditText) findViewById(R.id.edt_mkdangnhap);
        btn_Dangnhap = (Button) findViewById(R.id.btn_dangnhap);
        btn_Didangki = (Button) findViewById(R.id.btn_didangki);
        checbx = (CheckBox) findViewById(R.id.chbox);
    }

    private void login(final String username, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DANGNHAP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getInt("success")==1){
                                Server.ID_USER = object.getInt("user_id");
                                Log.d("iduser", Server.ID_USER+"" );
                                Intent intent = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                                startActivity(intent);
                            }else if (object.getInt("success")==0)  {
                                String messenger = object.getString("message");
                            Toast.makeText(DangNhapActivity.this, messenger, Toast.LENGTH_SHORT).show();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DN && resultCode == RESULT_OK &&data!=null){
            String tk = data.getStringExtra("ttk");
            String mk = data.getStringExtra("mk");
            edt_Tkdangnhap.setText(tk);
            edt_Mkdangnhap.setText(mk);

        }
    }

    private void luuAcc(String tk, String mk){
        SharedPreferences sharedPreferences = getSharedPreferences("luuacc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("taikhoan", tk);
        editor.putString("matkhau", mk);
        editor.commit();

    }

    private void khongluu(){
        SharedPreferences sharedPreferences = getSharedPreferences("luuacc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("taikhoan", "");
        editor.putString("matkhau", "");
        editor.commit();

    }

    private void getdulieu(){
        SharedPreferences sharedPreferences = getSharedPreferences("luuacc", Context.MODE_PRIVATE);
        String tk = sharedPreferences.getString("taikhoan", null);
        String mk = sharedPreferences.getString("matkhau", null);
        edt_Tkdangnhap.setText(tk);
        edt_Mkdangnhap.setText(mk);
    }



}
