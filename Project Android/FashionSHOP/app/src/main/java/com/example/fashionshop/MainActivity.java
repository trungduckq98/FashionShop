package com.example.fashionshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fashionshop.activity.DangNhapActivity;
import com.example.fashionshop.activity.LoiKetNoiActivity;
import com.example.fashionshop.connection.KiemTraKetNoi;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT=3000;
    TextView txt_Wait;
    private Toolbar Toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_Wait = (TextView) findViewById(R.id.txt_wait);
//        final Animation animAnpha = AnimationUtils.loadAnimation(this, R.anim.anim_anpha);
//        txt_Wait.setAnimation(animAnpha);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (KiemTraKetNoi.check(MainActivity.this)){
                    Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(MainActivity.this, LoiKetNoiActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },TIME_OUT);
    }


}
