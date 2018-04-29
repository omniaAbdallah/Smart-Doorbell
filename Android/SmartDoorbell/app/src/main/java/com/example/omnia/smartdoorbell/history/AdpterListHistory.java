package com.example.omnia.smartdoorbell.history;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.models.history;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by omnia on 25/03/2018.
 */

public class AdpterListHistory  extends RecyclerView.Adapter<AdpterListHistory.viewAdpter>   {

    Context mcontext;
    List<history> mlist;
    String ip;

    public AdpterListHistory(Context mcontext, List<history> mlist,String ip) {
        this.mcontext = mcontext;
        this.mlist = mlist;
        this.ip=ip;
        Log.e(" - "," on constracture: ip= "+ip);
    }

    @Override
    public viewAdpter onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowhistory,parent,false);
        viewAdpter holder=new viewAdpter(v);
        Log.e(" - "," onCreateViewHolder:");
        return holder;
    }

    @Override
    public void onBindViewHolder(viewAdpter holder, final int position) {
        Log.e(" - "," onBindViewHolder:");

        holder.state.setText(mlist.get(position).getState());
        Log.e(" state ",mlist.get(position).getState());
        holder.action.setText(mlist.get(position).getActhion());
        Log.e(" action ",mlist.get(position).getActhion());
        holder.time.setText(mlist.get(position).getTime());
        Log.e(" time ",mlist.get(position).getTime());

//        holder.imageView.setImageResource(mlist.get(position).getImg());
//        Log.e(" img ",mlist.get(position).getImg()+" ");

String imag=mlist.get(position).getImage().toString();
        String path="http://"+ip+"/"+imag;
        Log.e("path from detail : ",path);

        Picasso.with(mcontext).load(path).into(holder.imageView);


 holder.itemHistory.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent intent=new Intent(mcontext,DetailsHistoryActivity.class);

         intent.putExtra("name",mlist.get(position).getName());
         intent.putExtra("relation",mlist.get(position).getRelation());
         intent.putExtra("state",mlist.get(position).getState());
         intent.putExtra("action",mlist.get(position).getActhion());
         intent.putExtra("time",mlist.get(position).getTime());
         intent.putExtra("image",mlist.get(position).getImage());
         intent.putExtra("ip",ip);

         mcontext.startActivity(intent);


     }
 });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class viewAdpter extends RecyclerView.ViewHolder{

TextView state,action,time;
ImageView imageView;
LinearLayout itemHistory;
        public viewAdpter(View itemView) {

            super(itemView);

            state=(TextView)itemView.findViewById(R.id.historyListState);
            action=(TextView)itemView.findViewById(R.id.historyListAction);
            time=(TextView)itemView.findViewById(R.id.historyListTime);

            imageView=(ImageView) itemView.findViewById(R.id.historyimage);

            itemHistory=(LinearLayout) itemView.findViewById(R.id.itemHistory);

        }
    }
}
