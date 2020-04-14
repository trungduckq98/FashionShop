package com.example.fashionshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DangKiActivity extends AppCompatActivity {
    private EditText edt_TKdangki, edt_MKdangki, edt_SDTdangki, edt_Emaildk, edt_Diachidk;
    private Button btn_Dangki;
    private Toolbar toolbarDangki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        anhXa();
        suKien();
    }



    private void suKien(){
        btn_Dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edt_TKdangki.getText().toString().trim();
                String mk = edt_MKdangki.getText().toString().trim();
                String sdt = edt_SDTdangki.getText().toString().trim();
                String em = edt_Emaildk.getText().toString().trim();
                String dc = edt_Diachidk.getText().toString().trim();
                if (TextUtils.isEmpty(tk) || TextUtils.isEmpty(mk) ||TextUtils.isEmpty(sdt) ||TextUtils.isEmpty(em) ||TextUtils.isEmpty(dc) ){
                    Toast.makeText(DangKiActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    dangki(tk,mk,sdt,em,dc);
                }



            }
        });
        setSupportActionBar(toolbarDangki);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDangki.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void anhXa(){
        edt_TKdangki = (EditText) findViewById(R.id.edt_tkdangki);
        edt_MKdangki = (EditText) findViewById(R.id.edt_mkdangki);
        edt_SDTdangki = (EditText) findViewById(R.id.edt_sdtdangki);
        edt_Emaildk = (EditText) findViewById(R.id.edt_emaildangki);
        edt_Diachidk = (EditText) findViewById(R.id.edt_diachidangki);
        btn_Dangki = (Button) findViewById(R.id.btn_dangki) ;
        toolbarDangki = (Toolbar) findViewById(R.id.toolbardangki);
    }

    private void dangki(final String username, final String password, final String sdt, final String email, final String addreess){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL_DANGKI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getInt("success")==1){
                                String messenger = object.getString("message");
                                Toast.makeText(DangKiActivity.this, messenger, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("ttk", username);
                                intent.putExtra("mk", password);
                                setResult(RESULT_OK, intent);


                            }else if (object.getInt("success")==0)  {
                                String messenger = object.getString("message");
                                Toast.makeText(DangKiActivity.this, messenger, Toast.LENGTH_SHORT).show();
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
                params.put("sdt", sdt);
                params.put("email", email);
                params.put("address", addreess);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
