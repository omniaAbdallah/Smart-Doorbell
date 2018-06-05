package com.example.omnia.smartdoorbell.trusted;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.omnia.smartdoorbell.R;

public class MainTrustedActivity extends AppCompatActivity {
String ip ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_trusted);
//
//        Intent intent=getIntent();
//        ip=intent.getStringExtra("ip");

    }

    public void show(View view) {
        Intent intent =new Intent(MainTrustedActivity.this, ShowTrustedActivity.class);
//        intent.putExtra("ip",ip);
        startActivity(intent);
    }

    public void add(View view) {
        Intent intent =new Intent(MainTrustedActivity.this, InsertTrustedActivity.class);
//        intent.putExtra("ip",ip);
        intent.putExtra("image","null");

        startActivity(intent);
    }


}
