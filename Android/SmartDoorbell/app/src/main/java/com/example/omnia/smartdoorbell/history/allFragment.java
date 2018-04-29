package com.example.omnia.smartdoorbell.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.models.history;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class allFragment extends Fragment {

    RecyclerView rec;
    List<history> all;
    String ip,url;

    //AdpterListHistory adpterListHistory;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
     View   v = inflater.inflate(R.layout.fragment_all, container, false);


        Log.e(" 2- ","on creat view fragment all  :");
        rec = (RecyclerView) v.findViewById(R.id.AllHistory);
        Log.e(" 3- ","get rec :");
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e(" 5- "," rec.setLayoutManager:");


        Log.e(" 1- ", "on creat fragment :");
        all = new ArrayList<>();
        //  ip=getArguments().getString("ip");
        ip = "192.168.43.115:5005";
//        ip="192.168.1.4:5005";
        url = "http://" + ip + "/show_history/all".trim();
        System.out.println(" url :" + url);
        Log.e(" url log ", url + "");
        System.out.println(" url :" + url);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Start the queue
        queue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("history");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject currentobj = jsonArray.getJSONObject(i);

                        String id = currentobj.getString("id");
                        String imagename = currentobj.getString("imagename");
                        String state = currentobj.getString("state");
                        String action = currentobj.getString("action");
                        String time = currentobj.getString("time");
                        String name = currentobj.getString("name");
                        String relation = currentobj.getString("relation");

                        history history = new history();
                        history.setId(id);
                        history.setImage(imagename);
                        history.setName(name);
                        history.setTime(time);
                        history.setState(state);
                        history.setActhion(action);
                        history.setRelation(relation);

                        all.add(history);

                        AdpterListHistory adpterListHistoryall = new AdpterListHistory(getContext(), all,ip);
                        Log.e(" 4- ","create adpter : ip ="+ip);

                        Toast.makeText(getContext(), "list size : "+all.size(), Toast.LENGTH_LONG).show();

                        rec.setAdapter(adpterListHistoryall);

                        Log.e(" 6- "," rec.setadpter:");



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error : " + error);
                Log.e("error ", error + "");
                Toast.makeText(getActivity(), "error" + error, Toast.LENGTH_LONG).show();


            }
        });

        queue.add(stringRequest);





        return v;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        history h1 = new history();
//        h1.setImg(R.drawable.denied_32);
//        h1.setActhion("unlock");
//        h1.setState("block");
//        h1.setTime("5-8-2018 12:30 Am");
//        list.add(h1);
//        history h2 = new history();
//        h2.setImg(R.drawable.unkown_32);
//        h2.setActhion("unlock");
//        h2.setState("unkown");
//        h2.setTime("5-8-2018 12:30 Am");
//        list.add(h2);
//        history h3 = new history();
//        h3.setImg(R.drawable.unkown_32);
//        h3.setActhion("lock");
//        h3.setState("unkown");
//        h3.setTime("5-8-2018 12:30 Am");
//        list.add(h3);
//        history h14 = new history();
//        h14.setImg(R.drawable.apply_32);
//        h14.setActhion("lock");
//        h14.setState("trusted");
//        h14.setTime("5-8-2018 12:30 Am");
//        list.add(h14);


    }

    @Override
    public void onStart() {
        super.onStart();
    }
}