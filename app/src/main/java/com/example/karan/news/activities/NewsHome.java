package com.example.karan.news.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.karan.news.R;
import com.example.karan.news.fragment.NewsHomeFragment;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.utils.RecyclerViewClickListener;


public class NewsHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RecyclerViewClickListener{
    private TextView tv,user_name;
    private NewsHomeFragment newsHomeFragment;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_page);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String m = prefs.getString("userName","a");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        newsHomeFragment =new NewsHomeFragment();

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relative, newsHomeFragment);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.inflateHeaderView(R.layout.nav_header_main);

        tv=(TextView) header.findViewById(R.id.app_name);
        tv.setText(loadPreferences());

        user_name=(TextView) header.findViewById(R.id.tv);
        user_name.setText(m);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, AppPreferences.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.log_out) {
            FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(this);
            firebaseAuthentication.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle=new Bundle();
        String child;

        switch (id){
            case R.id.nav_camera:
                child="item";
                break;
            case R.id.nav_gallery:
                child="rent";
                break;
            case R.id.nav_slideshow:
                child="rent";
                break;
            case R.id.nav_manage:
                child="item";
                break;
            default:
                child="item";
                break;
        }

        bundle.putString("category",child);
        newsHomeFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, newsHomeFragment);
        fragmentTransaction.commit();

        newsHomeFragment.setArguments(bundle);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private String loadPreferences(){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        String toolbar_title;
        boolean Title=pref.getBoolean("bg_color", false);
        if(Title) {
            toolbar_title="New";
        }
        else{
            toolbar_title="News";
        }
        return toolbar_title;
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
