package com.example.omnia.smartdoorbell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.omnia.smartdoorbell.block.BlockActivity;
import com.example.omnia.smartdoorbell.history.History_Activity;
import com.example.omnia.smartdoorbell.trusted.MainTrustedActivity;

public class MainActivity extends AppCompatActivity {

    String ip ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip ="192.168.43.115:5005";
//        ip="192.168.1.4:5005";

    }

    public void trusted(View view) {

        Intent intent =new Intent(MainActivity.this, MainTrustedActivity.class);
        intent.putExtra("ip",ip);
        startActivity(intent);
    }




    public void historry(View view) {
        Intent intent =new Intent(MainActivity.this, History_Activity.class);
        intent.putExtra("ip",ip);
        startActivity(intent);
    }

    public void block(View view) {
        Intent intent =new Intent(MainActivity.this, BlockActivity.class);
        intent.putExtra("ip",ip);
        startActivity(intent);
    }
}
