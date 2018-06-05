package com.example.omnia.smartdoorbell;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omnia.smartdoorbell.trusted.InsertTrustedActivity;
import com.example.omnia.smartdoorbell.trusted.ShowTrustedActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ReplayActivity extends AppCompatActivity {

    String  ip ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedfile",MODE_PRIVATE);
        ip = sharedPreferences.getString("ipESP", "nulllllllll");


    }



    public void unlock(View view) {
        String url="http://" + ip + "/door/open".trim();
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        // Start the queue
        queue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                JSONObject object = new JSONObject();
//                    if (object.getString("state").equals("ok")) {
                Toast.makeText(ReplayActivity.this, "open", Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(getBaseContext(),ShowTrustedActivity.class);
//                        intent.putExtra("ip",ip);
//                        startActivity(intent);
//                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding request to request queue
        queue.add(stringRequest);


    }

    public void lock(View view) {
        String url="http://" + ip + "/door/close".trim();
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        // Start the queue
        queue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                JSONObject object = new JSONObject();
//                    if (object.getString("state").equals("ok")) {
                Toast.makeText(ReplayActivity.this, "close", Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(getBaseContext(),ShowTrustedActivity.class);
//                        intent.putExtra("ip",ip);
//                        startActivity(intent);
//                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding request to request queue
        queue.add(stringRequest);
    }
}
