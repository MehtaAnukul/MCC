package com.example.anukul_pc.mcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDataActivity extends AppCompatActivity {


    private ListView listView;

    private ArrayList<String> fileNameArrayList;
    private ArrayList<String> fileUrlArrayList;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        initView();

        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(ViewDataActivity.this, android.R.layout.simple_list_item_1, fileNameArrayList);


        listView.setAdapter(stringArrayAdapter);


        mDatabase.child(AppConstant.FIREBASE_TABLE_DATA).child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataList : dataSnapshot.getChildren()) {
                    PostDataModel postDataModel = dataList.getValue(PostDataModel.class);
                    fileNameArrayList.add(postDataModel.getFileName());
                    fileUrlArrayList.add(postDataModel.getFileUrl());
                }
                stringArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(fileUrlArrayList.get(position)));
                startActivity(intent);
            }
        });

    }

    private void initView() {
        listView = findViewById(R.id.activity_view_data_ls);

        fileNameArrayList = new ArrayList<>();
        fileUrlArrayList = new ArrayList<>();


    }
}
