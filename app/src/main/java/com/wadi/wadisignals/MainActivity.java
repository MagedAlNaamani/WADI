package com.wadi.wadisignals;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String SELECTED_ITEM_ID = "selected";
    private static final String FIRST_TIME = "first_time";
    NavigationView navigation;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    protected int selectedID;
    protected boolean userSawDrawer = false;
    protected FragmentManager fragmentManager;
    protected TextView email;
    protected TextView fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        setupNavigationView();
        setupToolbar();
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        if(!didUserSeeDrawer()) {
            showDrawer();
        }
        else {
            hideDrawer();
        }
        drawerLayout.setDrawerListener(drawerToggle);
        selectedID = savedInstanceState == null ? R.id.Home : savedInstanceState.getInt(SELECTED_ITEM_ID);
        navigate(selectedID);
        //ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        selectedID = menuItem.getItemId();
        navigate(selectedID);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, selectedID);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    private void setupNavigationView() {
        //set drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //set navigation
        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(this);
    }
    private void navigate(int selectedID) {
        Fragment fragment = null;

        Class fragmentClass=null;
        if(selectedID==R.id.Home) {
            fragmentClass = Main.class;
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(selectedID==R.id.Profile){
            fragmentClass = ActiveWadies.class;
            drawerLayout.closeDrawer(GravityCompat.START);

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        if(fragmentClass!=null) {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }
    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            setSupportActionBar(toolbar);

        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);
    }
    private boolean didUserSeeDrawer(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userSawDrawer = sharedPreferences.getBoolean(FIRST_TIME,false);
        return userSawDrawer;
    }

    private void clearLogin() {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }
    private boolean isLogin() {
        boolean login;
        SharedPreferences settings;
        int state;
        settings = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE);
        state = settings.getInt("state", 0);

        if(state == 1){
            login = true;
        } else{
            login = false;
        }

        return login;
    }
    private void markDrawerSeen(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME,userSawDrawer).apply();
    }
    private void showDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    private void hideDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
