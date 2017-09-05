package com.example.karan.news.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.karan.news.R;
import com.example.karan.news.utils.LaunchManager;

/**This Activity is used as the Settings Activity for the app with
 * options to tweak the app theme, text size and download images or not*/

public class AppPreferences extends BaseActivity implements View.OnClickListener {
    private Window window;
    private ImageButton back_button;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.green));

        back_button=(ImageButton)findViewById(R.id.settings_back_button);
        back_button.setOnClickListener(this);

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        SettingsFragment mPrefsFragment = new SettingsFragment();
        mFragmentTransaction.replace(R.id.settings_frame, mPrefsFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.settings;
    }

    @Override
    protected int getToolbarID() {
        return 0;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    @Override
    public void onClick(View v) {
        LaunchManager.launchHome(this);
    }

    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
        }
    }
}