package com.example.fashionshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fashionshop.MainActivity;
import com.example.fashionshop.R;
import com.example.fashionshop.connection.KiemTraKetNoi;

public class LoiKetNoiActivity extends AppCompatActivity {
    Button btn_Retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loi_ket_noi);
        btn_Retry = (Button) findViewById(R.id.btn_retry);
        btn_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (KiemTraKetNoi.check(LoiKetNoiActivity.this)){
                    Intent intent = new Intent(LoiKetNoiActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoiKetNoiActivity.this, "Kết nối thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
