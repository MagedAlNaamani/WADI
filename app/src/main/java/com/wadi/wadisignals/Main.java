package com.wadi.wadisignals;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by magedalnaamani on 11/18/15.
 */
public class Main extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.activewadies);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        rootView.findViewById(R.id.btnwadies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Governorates.class);
                intent.putExtra("fromwhere","wadies");
                startActivity(intent);



            }
        });
        rootView.findViewById(R.id.btndestination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),DirectionMap.class));
            }
        });



        BuildRecyclerView();


        return rootView;
    }
    public void BuildRecyclerView() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Wadi");
        query.whereNotEqualTo("wadiStatus", 0);
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                int x = 0;
                if (e == null) {
                    recycleViewAdapter = new RecycleViewAdapter(list, getActivity());
                    recyclerView.setAdapter(recycleViewAdapter);

                } else {

                }
            }
        });
    }
    public void onClick(View v) {
        Fragment fragment = null;
        Class fragmentClass = null;
        Bundle bundle = new Bundle();

        switch (v.getId()) {

        }
    }
}

