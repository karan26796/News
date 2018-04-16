package com.example.karan.news.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.karan.news.R;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;

/**This Activity is used as the Settings Activity for the app with
 * options to tweak the app theme, text size and download images from
 * the internet.*/

public class SettingsActivity extends BaseActivity {
    private Window mWindow;
    private Toolbar mToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        configureToolbar(this);
        mWindow=getWindow();
        mToolbar=(Toolbar) findViewById(R.id.settings_toolbar);
        setTheme(mToolbar,mWindow);
        fragmentInflate();

    }

    private void fragmentInflate(){
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        SettingsFragment mPrefsFragment = new SettingsFragment();
        mFragmentTransaction.replace(R.id.settings_frame_layout, mPrefsFragment);
        mFragmentTransaction.commit();
    }
    //App theme is fetched using shared preference from activity_settings activity
    private void loadPreference() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setTheme(Toolbar toolbar, Window window) {
        this.mToolbar=toolbar;
        this.mWindow=window;
        // fetches shared preferences for value of 'theme' set by the user
        loadPreference();

        // set toolbar popup them for different themes.
        if (theme.equals(getString(R.string.theme_light))) {
            toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.green));
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        } else if (theme.equals(getString(R.string.theme_dark))) {
            toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_blue));
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.dark_blue));
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        toolbar.setTitle(SettingsActivity.class.getName());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getToolbarID() {
        return R.id.settings_toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
        }
    }
}