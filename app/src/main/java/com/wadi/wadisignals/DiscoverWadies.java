package com.wadi.wadisignals;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

/**
 * Created by magedalnaamani on 2/8/16.
 */
public class DiscoverWadies extends Fragment {

    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private RVDiscoverWadi recyclerViewDiscover;
    Fragment fragment = null;
    Class fragmentClass = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover_wadies, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.discoverWadies);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        BuildRecyclerView();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item Clicked = " + position, Toast.LENGTH_LONG).show();
                        // TODO Handle item click
                        fragmentClass = DiscoverDetails.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (java.lang.InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                    }
                })
        );

        return rootView;
    }

    public void BuildRecyclerView() {
        ParseQuery<ParseObject> regions = new ParseQuery<ParseObject>("regions");
        regions.whereEqualTo("region_name", "Muscat");
        regions.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                int x = 0;
                if (e == null) {
                    for (final ParseObject r : list) {
                        ParseRelation<ParseObject> relation = r.getRelation("wadies");

                        ParseQuery wadies = relation.getQuery();
                        wadies.whereNotEqualTo("wadiStatus", 0);
                        wadies.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    recyclerViewDiscover = new RVDiscoverWadi(list, getActivity());
                                    recyclerView.setAdapter(recyclerViewDiscover);
                                } else {

                                }
                            }

                        });
                    }
                } else {

                }
            }
        });
    }





}
