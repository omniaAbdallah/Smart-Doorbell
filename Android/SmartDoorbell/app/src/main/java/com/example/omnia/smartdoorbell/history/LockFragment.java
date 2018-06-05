package com.example.omnia.smartdoorbell.history;

import android.content.SharedPreferences;
import android.net.Uri;
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


public class LockFragment extends Fragment {
    RecyclerView rec;
    List<history> lock;
    String ip, url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lock, container, false);
        Log.e(" 2- ", "on creat view fragment all  :");
        rec = (RecyclerView) v.findViewById(R.id.locklistHistory);
        Log.e(" 3- ", "get rec :");
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e(" 5- ", " rec.setLayoutManager:");


        Log.e(" 1- ", "on creat fragment :");
        lock = new ArrayList<>();
        //  ip=getArguments().getString("ip");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedfile", getActivity().MODE_PRIVATE);
        ip = sharedPreferences.getString("ipServer", "nulllllllll");
//        ip = "192.168.1.4:5005";
        url = "http://" + ip + "/show_history/lock".trim();
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

                        lock.add(history);
                        AdpterListHistory adpterListHistorylock = new AdpterListHistory(getContext(), lock, ip);
                        Log.e(" 4- ", "create adpter : ip =" + ip);

                        Toast.makeText(getContext(), "list size : " + lock.size(), Toast.LENGTH_LONG).show();

                        rec.setAdapter(adpterListHistorylock);

                        Log.e(" 6- ", " rec.setadpter:");


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
