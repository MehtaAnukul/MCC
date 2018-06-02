package com.example.anukul_pc.mcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectVMActivity extends AppCompatActivity {

    private TextView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vm);

        img = findViewById(R.id.textView4);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(SelectVMActivity.this,AndroidImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
