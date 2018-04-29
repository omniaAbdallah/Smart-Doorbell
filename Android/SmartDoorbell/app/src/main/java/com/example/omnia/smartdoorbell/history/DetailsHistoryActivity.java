package com.example.omnia.smartdoorbell.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.block.InsertBlockActivity;
import com.example.omnia.smartdoorbell.trusted.InsertTrustedActivity;
import com.squareup.picasso.Picasso;

public class DetailsHistoryActivity extends AppCompatActivity {
    ImageView image;
    TextView name, state, relation, time, action;
    Button trusted, block;
    String imgg,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_history);

        image = (ImageView) findViewById(R.id.image_details_history);

        name = (TextView) findViewById(R.id.name_detail_history);
        relation = (TextView) findViewById(R.id.relation_detail_history);
        state = (TextView) findViewById(R.id.state_detail_history);
        action = (TextView) findViewById(R.id.action_detail_history);
        time = (TextView) findViewById(R.id.time_detail_history);

        Intent intent = getIntent();
        imgg = intent.getStringExtra("image");
        ip=intent.getStringExtra("ip");
        String path = "http://" + ip+ "/" + imgg.trim();
        Log.e("ip from detail : ", path);

        Picasso.with(this).load(path).into(image);

        name.setText(intent.getStringExtra("name"));
        relation.setText(intent.getStringExtra("relation"));
        state.setText(intent.getStringExtra("state"));
        action.setText(intent.getStringExtra("action"));
        time.setText(intent.getStringExtra("time"));

        trusted = (Button) findViewById(R.id.add_trusted_button);
        block = (Button) findViewById(R.id.add_block_button);

        if (intent.getStringExtra("state").equals("unkown")) {
            trusted.setVisibility(View.VISIBLE);
            block.setVisibility(View.VISIBLE);

        }

    }

    public void add_block(View view) {
        String img = imgg;
        Intent intent = new Intent(this, InsertBlockActivity.class);
        intent.putExtra("image", img);
        intent.putExtra("ip",ip);
        startActivity(intent);

    }

    public void add_trusted(View view) {
        String img = imgg;
        Intent intent = new Intent(this, InsertTrustedActivity.class);
        intent.putExtra("image", img);
        intent.putExtra("ip",ip);
        startActivity(intent);


    }
}
