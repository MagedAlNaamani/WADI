package com.wadi.wadisignals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Governorates extends Fragment {

    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private RVDiscoverWadi recyclerViewDiscover;
    Fragment fragment = null;
    Class fragmentClass = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_governorates, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.governorates);
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
                        fragmentClass = DiscoverWadies.class;
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

        RVGovernorates governetAdapter=new RVGovernorates(name);
        recyclerView.setAdapter(governetAdapter);

    }

    public static void discoverWadies(String governet){

    }
}


