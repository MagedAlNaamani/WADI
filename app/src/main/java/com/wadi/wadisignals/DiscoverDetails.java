package com.wadi.wadisignals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

/**
 * Created by magedalnaamani on 2/18/16.
 */
public class DiscoverDetails extends Fragment {

    TextView tvDiscoverDetails;
    ImageView imageView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover_details, container, false);

        tvDiscoverDetails = (TextView) rootView.findViewById(R.id.tvDiscoverDetails);
        imageView = (ImageView) rootView.findViewById(R.id.imgDiscoverDetails);

        vFillDetails();

        return rootView;
    }

    public void onBackPressed()
    {

    }

    public void vFillDetails()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Wadi");
        query.whereEqualTo("wadiName", "Wadi Al Hail");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    tvDiscoverDetails.setText(list.get(0).getString("about_en"));
                    for (final ParseObject r : list) {
                        ParseRelation<ParseObject> relation = r.getRelation("wadi_imgs");
                        ParseQuery image = relation.getQuery();
                        image.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    Glide.with(getActivity())
                                            .load(list.get(0).getParseFile("image").getUrl())
                                            .placeholder(R.drawable.abc_tab_indicator_material)
                                            .crossFade()
                                            .into(imageView);
                                }
                                else {

                                }
                            }
                        });
                    }
                }
                else {

                }
            }
        });



//        ParseQuery<ParseObject> regions = new ParseQuery<ParseObject>("regions");
//        //regions.whereEqualTo("region_name", "Muscat");
//        regions.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                int x = 0;
//                if (e == null) {
//                    for (final ParseObject r : list) {
//                        ParseRelation<ParseObject> relation = r.getRelation("wadies");
//                        ParseQuery wadies = relation.getQuery();
//                        wadies.whereNotEqualTo("wadiStatus", 0);
//                        wadies.getFirstInBackground(new GetCallback() {
//                            @Override
//                            public void done(ParseObject parseObject, ParseException e) {
//                                tvDiscoverDetails.setText(parseObject.get("Activities_ar").toString());
//                                Glide.with(getActivity()).load("http://goo.gl/gEgYUd").into(imageView);
//                            }
//
//                            @Override
//                            public void done(Object o, Throwable throwable) {
//
//                            }
//                        });
//                    }
//                } else {
//
//                }
//            }
//        });
    }

}
