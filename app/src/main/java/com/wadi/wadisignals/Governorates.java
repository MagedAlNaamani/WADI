package com.wadi.wadisignals;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;

public class Governorates extends AppCompatActivity {

    private RecyclerView recyclerView;
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
   public static FragmentTransaction fragmentTransaction;
    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.governets_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent extras=getIntent();

         fragmentManager = getFragmentManager();
        GovernetsFragments governetsFragments = new GovernetsFragments();
        Bundle bundle=new Bundle();
        bundle.putString("fromwhere",extras.getStringExtra("fromwhere"));
        governetsFragments.setArguments(bundle);

        // get the Fragment Transction
        //
         fragmentTransaction = fragmentManager.beginTransaction();
        // add the transction



        fragmentTransaction.replace(R.id.mainfragmentscontainer, governetsFragments, "mainFragment");
        // comit
        fragmentTransaction.commit();


    }

    public static void discoverWadies(String governet){

        DiscoverWadiFragment governetsFragments = new DiscoverWadiFragment();
        Bundle bundle=new Bundle();
        bundle.putString("governet",governet);
        governetsFragments.setArguments(bundle);

        // get the Fragment Transction
        //
        fragmentTransaction = fragmentManager.beginTransaction();
        // add the transction



        fragmentTransaction.replace(R.id.mainfragmentscontainer, governetsFragments, "mainFragment");
        // comit
        fragmentTransaction.commit();
    }


}


