package com.example.omnia.smartdoorbell.trusted;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.models.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailTrustedActivity extends AppCompatActivity {
    TextView name,relation,idd;
    ImageView img;
    String id,ip,url1,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trusted);
        name=(TextView)findViewById(R.id.pname);
        relation=(TextView)findViewById(R.id.prelation);
        idd=(TextView)findViewById(R.id.pid);

        img=(ImageView)findViewById(R.id.pimg);


        Intent intent = getIntent();
        id=intent.getStringExtra("id");

        name.setText(intent.getStringExtra("name"));
        relation.setText(intent.getStringExtra("relation"));
        idd.setText(id+" ");
       // ip =("http://"+ip).trim();
//        System.out.print("ip from detail : "+ip);
//        Log.e("ip from detail : ",ip);
         image=intent.getStringExtra("img");
        SharedPreferences sharedPreferences = getSharedPreferences("sharedfile",MODE_PRIVATE);
        ip = sharedPreferences.getString("ipServer", "nulllllllll");
        String path="http://"+ip+"/"+image.trim();
        Log.e("ip from detail : ",path);

        Picasso.with(this).load(path).into(img);
    }

    public void delete(View view) {
        delete(id);



    }
    public void delete (final String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // Start the queue
        queue.start();
        System.out.println("Start the queue");
//        Intent intent=getIntent();
//        ip=intent.getStringExtra("ip");
        url1="http://"+ip+"/delete_trusted".trim();
        System.out.println(" url :"+url1);
        Log.e(" url log ",url1+"");
//        ip="http://192.168.43.115:5005/";
//        url1=ip+"show_trusted".trim();
        System.out.println(" url :"+url1);
        //delete_trusted
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject object = new JSONObject(new String(response.data));
                            if (object.getString("state").equals("ok")) {
                                Toast.makeText(DetailTrustedActivity.this, "success", Toast.LENGTH_LONG).show();


                                Intent intent=new Intent(getBaseContext(),ShowTrustedActivity.class);
                                intent.putExtra("ip",ip);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(" error :" + error);
                Log.e(" error ", error + "");
                Toast.makeText(DetailTrustedActivity.this, "no internetConnection"+error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id );

                return params;
            }


        };
//        volleyMultipartRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
        // Adding request to request queue
        queue.add(volleyMultipartRequest);


    }


    public void update(View view) {
        Intent intent=new Intent(getApplicationContext(),UpdateTrustedActivity.class);

        intent.putExtra("name",name.getText().toString());
        intent.putExtra("relation",relation.getText().toString());
        intent.putExtra("id",idd.getText().toString());
        intent.putExtra("image",image);
        intent.putExtra("ip",ip);

        // intent.putExtra("img",img.getText().toString());

        startActivity(intent);
    }
}
