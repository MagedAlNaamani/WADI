package com.wadi.wadisignals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private RecycleViewAdapter recycleViewAdapter;
    private RVDiscoverWadi recyclerViewDiscover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover_wadies, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.discoverWadies);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        BuildRecyclerView();

        return rootView;
    }

    public void BuildRecyclerView() {
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Wadi");
//        query.whereNotEqualTo("wadiStatus", 0);
//        query.orderByDescending("updatedAt");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                int x = 0;
//                if (e == null) {
//                    recyclerViewDiscover = new RVDiscoverWadi(list, getActivity());
//                    //recycleViewAdapter = new RecycleViewAdapter(list, getActivity());
//                    recyclerView.setAdapter(recyclerViewDiscover);
//
//                } else {
//
//                }
//            }
//        });


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
//                        wadies.getFirstInBackground(new GetCallback() {
//                            @Override
//                            public void done(ParseObject parseObject, ParseException e) {
////                                recycleViewAdapter = new RecycleViewAdapter((List<ParseObject>) parseObject, getActivity());
////                                recyclerView.setAdapter(recycleViewAdapter);
//                                recyclerViewDiscover = new RVDiscoverWadi((List) parseObject, getActivity());
//                                //recycleViewAdapter = new RecycleViewAdapter(list, getActivity());
//                                recyclerView.setAdapter(recyclerViewDiscover);
//                            }
//
//                            @Override
//                            public void done(Object o, Throwable throwable) {
//                            }
//                        });
                    }

//                        wadies.getFirstInBackground(new GetCallback() {
//                            @Override
//                            public void done(ParseObject parseObject, ParseException e) {
//                                if(e==null) {
//                                    recyclerViewDiscover = new RVDiscoverWadi(parseObject, getActivity());
//                                    recyclerView.setAdapter(recyclerViewDiscover);
//                                }
//                                else {
//
//                                }
//                            }
//
//                            @Override
//                            public void done(Object o, Throwable throwable) {
//
//                            }
//                        });



                } else {

                }
            }
        });
    }
}
