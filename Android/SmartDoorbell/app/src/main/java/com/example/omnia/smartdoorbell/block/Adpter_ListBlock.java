package com.example.omnia.smartdoorbell.block;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.models.block;

import java.util.List;

/**
 * Created by omnia on 10/04/2018.
 */

public class Adpter_ListBlock extends RecyclerView.Adapter<Adpter_ListBlock.holder> {
    List<block> list;
    Context mContext;
    String ip;

    public Adpter_ListBlock(List<block> list, Context mContext, String ip) {
        this.list = list;
        this.mContext = mContext;
        this.ip = ip;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_trusted,parent,false);
        holder h = new holder(row);
        return h;
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        final block item=list.get(position);

        holder.id.setText((position+1)+"- ");
        System.out.println("id: "+item.getId()+"- ");
        holder.name.setText(item.getName());
        System.out.println("Name: "+item.getName()+"- ");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent det=new Intent(mContext,detailBlockActivity.class);
                det.putExtra("name",item.getName());
                det.putExtra("img",item.getImage());
                det.putExtra("ip",ip);
                Log.e("ip from list : ",ip);
                det.putExtra("id",item.getId());
                mContext.startActivity(det);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class holder extends RecyclerView.ViewHolder{


        TextView id,name,relation;
        LinearLayout layout;
        public holder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.id);
            name=(TextView)itemView.findViewById(R.id.name);
            relation=(TextView)itemView.findViewById(R.id.relation);
            relation.setVisibility(View.INVISIBLE);
            layout=(LinearLayout)itemView.findViewById(R.id.la);
        }

    }
}
