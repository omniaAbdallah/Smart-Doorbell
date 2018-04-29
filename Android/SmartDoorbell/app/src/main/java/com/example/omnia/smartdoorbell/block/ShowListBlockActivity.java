package com.example.omnia.smartdoorbell.block;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.models.block;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowListBlockActivity extends AppCompatActivity {
    List<block> list1;
    String url1,ip;
    RecyclerView re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_block);

        list1=new ArrayList<>();
        re =(RecyclerView) findViewById(R.id.rec);
        re.setLayoutManager(new LinearLayoutManager(this));
        //  Adpter_List a=new Adpter_List(list1,ListActivity.this);
        System.out.println("send adapyer");
        // re.setAdapter(a);

        RequestQueue queue = Volley.newRequestQueue(this);
        // Start the queue
        queue.start();
        System.out.println("Start the queue");

        Intent intent=getIntent();
        ip=intent.getStringExtra("ip");
        url1="http://"+ip+"/show_block".trim();
        System.out.println(" url :"+url1);
        Log.e(" url log ",url1+"");
//        ip="http://192.168.43.115:5005/";
//        url1=ip+"show_trusted".trim();
        System.out.println(" url :"+url1);
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("url: "+url1);
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array= object.getJSONArray("block");

                    System.out.println("size: "+array.length());

                 //   Toast.makeText(ShowListBlockActivity.this,"url: "+ url1, Toast.LENGTH_SHORT).show();
                    for(int i=0;i<array.length();i++){

                        JSONObject current =array.getJSONObject(i);

// from url1 from localhost:5005/trusted
                        String name =current.getString("name");
                        System.out.println("name: "+name);
                        String imgname =current.getString("imagename");
                        System.out.println("image: "+imgname);
                        String id =current.getString("id");
                        System.out.println("id: "+id);

                        block t = new block();
                        t.setName(name);
                        System.out.println("name 2: "+t.getName());
                        t.setImage(imgname);
                        t.setId(id);


                        list1.add(t);

                    }
                    Adpter_ListBlock a=new Adpter_ListBlock(list1,ShowListBlockActivity.this,ip);

                    re.setAdapter(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("re.setAdapter");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error : "+error);
                Log.e("error ",error+"");
                Toast.makeText(ShowListBlockActivity.this, "error"+error, Toast.LENGTH_LONG).show();
            }


        });
        // Adding request to request queue
        queue.add(stringRequest);


    }
}
