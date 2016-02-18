package com.wadi.wadisignals;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class governetsFragments extends Fragment {

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_governorates, container, false);

        Bundle extras= getArguments();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.governonates_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        String[] name={
                getResources().getString(R.string.addakhiliyah),
                getResources().getString(R.string.addhahirah),
                getResources().getString(R.string.albatinahnorth),
                getResources().getString(R.string.albatinahsouth),
                getResources().getString(R.string.alburaimi),
                getResources().getString(R.string.alwusta),
                getResources().getString(R.string.ashsharqiyahnorth),
                getResources().getString(R.string.ashsharqiyahsouth),
                getResources().getString(R.string.dhofar),
                getResources().getString(R.string.muscat),
                getResources().getString(R.string.musandam),
        };

        GovernetAdapter governetAdapter=new GovernetAdapter(name,extras.getString("fromwhere"));
        recyclerView.setAdapter(governetAdapter);


        return rootView;

    }

    class GovernetAdapter extends RecyclerView.Adapter<GovernetAdapter.Holder>

    {

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
        String fromwhere;
        public GovernetAdapter(String[] goverents,String fromwhere) {

            this.goverents=goverents;
            this.fromwhere=fromwhere;
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
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("data", fromwhere);

                    Governorates.discoverWadies(goverents[position]);


                }
            });

        }


        @Override
        public int getItemCount() {
            return governetImg.length;
        }

        public class Holder extends RecyclerView.ViewHolder
        {
            TextView governtname;
            ImageView governtimg;
            CardView cardView;

            public Holder(View itemView) {
                super(itemView);
                governtname=(TextView)itemView.findViewById(R.id.governtname);
                governtimg=(ImageView)itemView.findViewById(R.id.goverentimg);
                cardView=(CardView)itemView.findViewById(R.id.card_view_governets);
            }
        }


    }
}
