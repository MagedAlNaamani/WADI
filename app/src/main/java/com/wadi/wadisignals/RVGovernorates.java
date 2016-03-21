package com.wadi.wadisignals;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by magedalnaamani on 3/19/16.
 */
public class RVGovernorates extends RecyclerView.Adapter<RVGovernorates.Holder> {

    /**
     *
     Ad Dakhiliyah
     Ad Dhahirah
     Al Batinah North
     Al Batinah South
     Al Buraimi
     Al Wusta
     Ash Sharqiyah North
     Ash Sharqiyah South
     Dhofar
     Muscat
     Musandam
     * */

    int[] governetImg={
            R.drawable.adakhiliyah,
            R.drawable.adhahirah,
            R.drawable.northbatinah,
            R.drawable.southbatinah,
            R.drawable.alburaymi,
            R.drawable.alwasta,
            R.drawable.northsharqiyah,
            R.drawable.southsharqiyah,
            R.drawable.dhofar,
            R.drawable.muscat,
            R.drawable.musandam

    };


    String[] goverents;
    public RVGovernorates(String[] goverents) {

        this.goverents=goverents;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_governate,viewGroup,false);



        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.governtimg.setImageResource(governetImg[position]);
        holder.governtname.setText(goverents[position]);

    }


    @Override
    public int getItemCount() {
        return governetImg.length;
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView governtname;
        ImageView governtimg;

        public Holder(View itemView) {
            super(itemView);
            governtname=(TextView)itemView.findViewById(R.id.governtname);
            governtimg=(ImageView)itemView.findViewById(R.id.goverentimg);
        }
    }


}
