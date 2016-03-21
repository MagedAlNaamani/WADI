package com.wadi.wadisignals;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by magedalnaamani on 2/9/16.
 */
public class RVDiscoverWadi extends RecyclerView.Adapter<RVDiscoverWadi.Holder>

{

    List<ParseObject> data;
    Context context;

    /**TO FORMAT THE DATE*/
    SimpleDateFormat dateFormat;

    public RVDiscoverWadi(List data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_discover_wadi,viewGroup,false);
        //dateFormat=new SimpleDateFormat("yyyy.MM.dd");
        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        if(data!=null)
        {
//            if(data.get(position).getNumber("wadiStatus") != 0 ) {
//                if (data.get(position).getNumber("wadiStatus") == 1) {
//                    //holder.sStatus = "Cross";
//                }
//                else if (data.get(position).getNumber("wadiStatus") == 2) {
//                    //holder.sStatus = "No Cross";
//                }
                holder.name.setText(data.get(position).getString("wadiName"));
                //holder.status.setText(holder.sStatus);
                //holder.date.setText(dateFormat.format(data.get(position).getUpdatedAt()));
//            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView status,name,date;
        String sStatus;

        public Holder(View itemView) {
            super(itemView);
            name   = (TextView)itemView.findViewById(R.id.tvDiscoverWadiName);
            //status = (TextView)itemView.findViewById(R.id.tvStatus);
            //date =(TextView)itemView.findViewById(R.id.wadiactivedate);
        }
    }
}
