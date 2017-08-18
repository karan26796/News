package com.example.karan.news.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.fragment.NewsHomeFragment;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.example.karan.news.utils.NetworkChangeReceiver;

public class NewsHome extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private String category,user;
    private int toolbarColor;
    private NewsHomeFragment newsHomeFragment;
    private Window window;
    private FirebaseAuthentication firebaseAuthentication;
    private Toolbar toolbar;

    NetworkChangeReceiver networkChangeReceiver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_page);


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.SPORTS_NEWS_CATEGORY);
        setSupportActionBar(toolbar);

        //method used to change status bar color
        window= getWindow();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawer(GravityCompat.START);

        defaultFragment();
//        new NewsDetails().getBookmarkCount();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.inflateHeaderView(R.layout.nav_header_main);

        firebaseAuthentication=new FirebaseAuthentication(this);
        user=firebaseAuthentication.getCurrentUser();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // launch animation
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
        defaultFragment();
        // register broadcast receiver which listens for change in network state
        registerReceiver(networkChangeReceiver, new IntentFilter(Constants.CONNECTIVITY_CHANGE_ACTION));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //This method handle the back button action from the user
    @Override
    public void onBackPressed() {
        //Closing of the drawer layout is carried if opened
        //Otherwise application is exited
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if(user!=null)
            LaunchManager.exitApp(this);
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
            LaunchManager.launchSettings(this);
            return true;
        }
        else if (id == R.id.log_out) {
            showDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*This method communicates with the Navigation drawer fragment click Listener
    and sends the item id and category string value along with setting the toolbar title
    and inflating list of each category in the fragment*/

    //Requires api checks the user's android api
    //It is required to change the app's status bar color
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displaySelectedScreen(int id, String child, int color) {

        Bundle bundle=new Bundle();

        //the values of category and toolbarColor are set here

        /**this keyword sets the value of category and toolbar color
         to child and color defined in the method respectively*/

        this.category=child;
        this.toolbarColor=color;
        Resources resources=getResources();

        /*Values of status bar color and category are sent using
        switch case statement for corresponding button clicks*/

        switch (id){
            case R.id.sports:
                child=Constants.SPORTS_NEWS_CATEGORY ;
                color=resources.getColor(R.color.colorAccent);
                break;
            case R.id.politics:
                child=Constants.POLITICS_NEWS_CATEGORY ;
                color=resources.getColor(R.color.gray);
                break;
            case R.id.world:
                child=Constants.WORLD_NEWS_CATEGORY ;
                color=resources.getColor(R.color.colorAccent);
                break;
            case R.id.bookmarks:
                child=Constants.BOOKMARK_CATEGORY;
                color=resources.getColor(R.color.gray);
                break;
            case R.id.top_stories:
                child=Constants.SPORTS_NEWS_CATEGORY ;
                color=resources.getColor(R.color.colorAccent);
                break;
        }

        //Action bar toolbar's title is changed on item click
        toolbar.setTitle(child);
        window.setStatusBarColor(color);
        toolbar.setBackgroundColor(color);
        //New fragment is inflated on item click in Navigation drawer
        newsHomeFragment = new NewsHomeFragment();

        //The category is sent to the fragment using bundle
        bundle.putString(Constants.CATEGORY_NAME,child);
        bundle.putInt(Constants.TOOLBAR_COLOR,color);
        newsHomeFragment.setArguments(bundle);

        fragmentInflate();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Default fragment method inflates the home fragment
     * with a predefined category.
     */

    public void defaultFragment(){
        newsHomeFragment = new NewsHomeFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Constants.CATEGORY_NAME,Constants.SPORTS_NEWS_CATEGORY);
        bundle.putInt(Constants.TOOLBAR_COLOR,R.color.colorPrimaryDark);
        newsHomeFragment.setArguments(bundle);

        //Default fragment with the aforementioned category is inflated
        fragmentInflate();
    }

    //Shows a Dialog box for user prompt on whether he wants to log out or continue using the app
    public static void showDialog(final Activity activity) {
        AlertDialog alertbox = new AlertDialog.Builder(activity)
                .setMessage(R.string.logout_out)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(activity);
                        String user =firebaseAuthentication.getCurrentUser();
                        if(user!=null){
                            firebaseAuthentication.logoutUser();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        return;
                    }
                })
                .show();
    }

    //Common method to inflate fragment every time it is called in the activity
    public void fragmentInflate(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, newsHomeFragment);
        fragmentTransaction.commit();
    }

    //Requires api checks the users android api
    //It is required to change the app's status bar color
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    /**Handles the onClick method for Navigation drawer items*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId(),category,toolbarColor);
        return true;
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected int getToolbarID() {
        return 0;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }
}
