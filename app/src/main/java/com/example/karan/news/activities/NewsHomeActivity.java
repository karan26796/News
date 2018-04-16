package com.example.karan.news.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.karan.news.R;
import com.example.karan.news.firebasemanager.FirebaseAuthentication;
import com.example.karan.news.fragment.NewsHomeFragment;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;

/**
 * News home activity.
 * <p>
 * This is the first page/ home page of the app, a user is opened to
 * this activity after login, this activity contains all the necessary
 * views for smooth user interaction, all news categories can be
 * accessed from the home page of the app.
 */

public class NewsHomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String category, user;
    private int toolbarColor;
    private NewsHomeFragment newsHomeFragment;
    private Window mWindow;
    boolean status;
    private NavigationView navigationView;
    private FirebaseAuthentication firebaseAuthentication;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        configureToolbar(this);
        status = getIntent().getBooleanExtra(Constants.READ_ARTICLE, true);

        toolbar.setTitle(Constants.SPORTS_NEWS_CATEGORY);
        setTheme();

        //sets the toolbar specified as the action bar

        //method used to change status bar color
        mWindow = getWindow();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawer(GravityCompat.START);
        if (savedInstanceState == null)
            defaultFragment();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        firebaseAuthentication = new FirebaseAuthentication(this);
        user = firebaseAuthentication.getCurrentUser();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_news_activity, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
            if (user != null)
                LaunchManager.exitApp(this);
        }
    }

    //App theme is fetched using shared preference from activity_settings activity
    private void loadPreference() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));
    }

    //change the popup theme of the toolbar items for change in theme
    private void setTheme() {
        // fetches shared preferences for value of 'theme' set by the user
        loadPreference();
        // set toolbar popup theme
        if (theme.equals(getString(R.string.theme_light))) {
            toolbar.setPopupTheme(R.style.AppFullScreenTheme);
        } else if (theme.equals(getString(R.string.theme_dark))) {
            toolbar.setPopupTheme(R.style.AppFullScreenDarkTheme);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here.*/

        int id = item.getItemId();

        //no inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LaunchManager.launchSettings(this);
            return true;
        } else if (id == R.id.log_out) {
            showDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*This method communicates with the Navigation drawer fragment_news_activity click Listener
    and sends the item id and category string value along with setting the toolbar title
    and inflating list of the desired category in the fragment_news_activity*/

    //Requires api checks the user's android api.
    //It is required to change the app's status bar color among other things.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    /**
     * Default fragment_news_activity method inflates the home fragment_news_activity
     * with a predefined category.
     */

    public void defaultFragment() {
        newsHomeFragment = new NewsHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CATEGORY_NAME, Constants.SPORTS_NEWS_CATEGORY);
        bundle.putInt(Constants.TOOLBAR_COLOR, R.color.colorPrimaryDark);
        newsHomeFragment.setArguments(bundle);

        //Default fragment with the aforementioned category is inflated
        fragmentInflate();
    }

    //Shows a Dialog box for user prompt on whether he wants to log out or continue using the app
    public static void showDialog(final Activity activity) {
        AlertDialog alertbox = new AlertDialog.Builder(activity)
                .setMessage(R.string.action_logout)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(activity);
                        String user = firebaseAuthentication.getCurrentUser();
                        if (user != null) {
                            firebaseAuthentication.logoutUser();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    //Common method to inflate fragment every time it is called in the activity
    public void fragmentInflate() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.news_activity_content_frame, newsHomeFragment);
        fragmentTransaction.commit();
    }

    //Requires api checks the users android api
    //It is required to change the app's status bar color
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    /*Handles the onClick method for Navigation drawer items*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle bundle = new Bundle();

        switch (item.getItemId()) {
            case R.id.sports:
                category = Constants.SPORTS_NEWS_CATEGORY;
                toolbarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
                break;
            case R.id.politics:
                category = Constants.POLITICS_NEWS_CATEGORY;
                toolbarColor = ContextCompat.getColor(this, R.color.gray);
                break;
            case R.id.world:
                category = Constants.WORLD_NEWS_CATEGORY;
                toolbarColor = ContextCompat.getColor(this, R.color.green);
                break;

            case R.id.top_stories:
                category = Constants.TOP_STORIES_NEWS_CATEGORY;
                toolbarColor = ContextCompat.getColor(this, R.color.dark_blue);
                break;

            case R.id.bookmarks:
                category = Constants.BOOKMARK_CATEGORY;
                toolbarColor = ContextCompat.getColor(this, R.color.purple_dark);
                break;
        }

        toolbar.setTitle(category);
        mWindow.setStatusBarColor(toolbarColor);
        toolbar.setBackgroundColor(toolbarColor);

        newsHomeFragment = new NewsHomeFragment();

        bundle.putString(Constants.CATEGORY_NAME, category);
        bundle.putInt(Constants.TOOLBAR_COLOR, toolbarColor);
        newsHomeFragment.setArguments(bundle);

        fragmentInflate();

        //Once a category is selected from the Navigation drawer it closed without user prompt to ease app usage
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected int getToolbarID() {
        return R.id.action_bar;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
