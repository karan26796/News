package com.example.karan.news.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.example.karan.news.R;
import com.example.karan.news.utils.Constants;

public class AppPreferences extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        SettingsFragment mPrefsFragment = new SettingsFragment();
        mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.settings;
    }

    @Override
    protected int getToolbarID() {
        return 0 ;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    protected void loadPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));
    }

    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
        }
    }
}
