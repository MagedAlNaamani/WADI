package com.wadi.wadisignals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by magedalnaamani on 11/18/15.
 */
public class ActiveWadies extends Fragment
{

    RecyclerView recyclerView;
    Chronometer chronometer;
    RVWadies recycleViewAdapter;
    int x=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_active_wadies, container, false);

        TextView nameHeader = (TextView) rootView.findViewById(R.id.tvNameHeader);
        nameHeader.setText("Name");
        TextView statusHeader = (TextView) rootView.findViewById(R.id.tvStatusHeader);
        statusHeader.setText("Status");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.DemocardList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        BuildRecyclerView();

        chronometer = (Chronometer) rootView.findViewById(R.id.Demochronometer);
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                x++;
                if (x == 5) {
                    BuildRecyclerView();
                    x = 0;
                }
            }
        });

        return rootView;
    }

    public void BuildRecyclerView() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Wadi");
        query.whereNotEqualTo("wadiStatus",0);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                int x=0;
                if (e == null) {
                    recycleViewAdapter = new RVWadies(list, getActivity().getApplicationContext());
                    recyclerView.setAdapter(recycleViewAdapter);

                } else {

                }
            }
        });
    }
}
