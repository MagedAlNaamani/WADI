package com.wadi.wadisignals;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by magedalnaamani on 11/18/15.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.Holder>

{

    List<ParseObject> data;
    Context context;

    public RecycleViewAdapter(List<ParseObject> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout,viewGroup,false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if(data!=null)
        {
            if(data.get(position).getNumber("wadiStatus") != 0 ) {
                if (data.get(position).getNumber("wadiStatus") == 1) {
                    holder.sStatus = "Cross";
                }
                else if (data.get(position).getNumber("wadiStatus") == 2) {
                    holder.sStatus = "No Cross";
                }
                holder.name.setText(data.get(position).getString("wadiName"));
                holder.status.setText(holder.sStatus);
            }
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView status,name;
        String sStatus;

        public Holder(View itemView) {
            super(itemView);
            name   = (TextView)itemView.findViewById(R.id.tvName);
            status = (TextView)itemView.findViewById(R.id.tvStatus);
        }
    }


}
