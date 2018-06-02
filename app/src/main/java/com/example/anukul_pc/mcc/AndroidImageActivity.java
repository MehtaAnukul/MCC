package com.example.anukul_pc.mcc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class AndroidImageActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_image);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Intent gotoLogin = new Intent(AndroidImageActivity.this, UploadDownloadActivity.class);
                startActivity(gotoLogin);
                finish();
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 3000);
    }
}
