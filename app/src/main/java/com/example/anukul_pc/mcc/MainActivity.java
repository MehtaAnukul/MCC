package com.example.anukul_pc.mcc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Intent gotoLogin = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(gotoLogin);
                finish();
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 3000);
    }
}
