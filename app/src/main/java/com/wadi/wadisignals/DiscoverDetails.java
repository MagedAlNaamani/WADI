package com.wadi.wadisignals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by magedalnaamani on 2/16/16.
 */
public class DiscoverDetails extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover_details, container, false);


        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imgDiscoverDetails);

        Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);

        return rootView;
    }

}
