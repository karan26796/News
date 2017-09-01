package com.example.karan.news.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.karan.news.R;
import com.example.karan.news.utils.Constants;

/**Base Activity.
 *
 * This activity is used/extended by all the activities in the app to use its
 * methods.
 *
 * its method help implement features like changing font size, theme etc. across
 * all activities.*/

public abstract class BaseActivity extends AppCompatActivity {

    String textSize;
    String theme;
    boolean downloadImages;
    Toolbar mToolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set up app theme
        // declare before onCreate to apply theme
        setUpTheme();

        // set up app font size based on shared preferences listener
        // declare before onCreate to apply theme
        setUpFont();

        super.onCreate(savedInstanceState);

        // preference manager shared preferences listener from settings activity
        sharedPreferencesListener();

        // set up activity layout
        setContentView(getLayoutResourceId());

        // set up activity toolbar
        configureToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // register shared preferences listener
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister shared preferences listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(prefListener);
    }

    // get layout resource ID
    protected abstract int getLayoutResourceId();

    //get toolbar ID
    protected abstract int getToolbarID();

    // get toolbar title
    protected abstract String getToolbarTitle();

    /**
     * PURPOSE: Set up activity toolbar.
     *
     * Retrieves the toolbar ID and title using abstract methods and sets up activity toolbar.
     */
    protected void configureToolbar() {

        mToolbar = (Toolbar) findViewById(getToolbarID());

        if (mToolbar != null) {

            setSupportActionBar(mToolbar);

            // set activity title
            // handle NullPointerException
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(getToolbarTitle());
        }
    }

    /**
     * PURPOSE: Set up app theme.
     *
     * Retrieves user selected option of the app theme from the shared preferences and sets up app theme.
     */
    protected void setUpTheme() {

        // fetches shared preferences for value of 'theme' set by the user
        loadPreferences();

        // set app theme
        if (theme.equals(getString(R.string.theme_light))) {
            setTheme(R.style.AppFullScreenTheme);
        } else if (theme.equals(getString(R.string.theme_dark))) {
            setTheme(R.style.AppFullScreenDarkTheme);
        }
    }

    /**
     * PURPOSE: Set up details activity font size.
     *
     * Retrieves font size from the preference manager shared preferences and sets up font size.
     */
    protected void setUpFont() {

        // fetches shared preferences for value of 'textSize' set by the user
        loadPreferences();

        // set font size
       if (textSize.equals(getString(R.string.text_size_small))) {
            setTheme(R.style.TextSizeSmall);
        }
        else if (textSize.equals(getString(R.string.text_size_medium))) {
            setTheme(R.style.TextSizeMedium);
        }
        else if (textSize.equals(getString(R.string.text_size_large))) {
            setTheme(R.style.TextSizeLarge);
        }
    }

    /**
     * Loads preference manager shared preferences from settings activity.
     */
    protected void loadPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        textSize = sharedPreferences.getString(Constants.KEY_TEXT_SIZE, getString(R.string.text_size_small));

        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));

        downloadImages  = sharedPreferences.getBoolean(Constants.KEY_DOWNLOAD_IMAGES, true);
    }

    //-----------------------------------------------------------------------------------
    // preference manager shared preferences listener
    //-----------------------------------------------------------------------------------
    protected void sharedPreferencesListener() {

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                // since theme is to be applied to the settings activity as well,
                // recreate base activity to apply app theme.
                // for downloadImages and fontSize, no changes are required in settings activity
                // these changes take place in respective activities' onResume
                if (key.equals(Constants.KEY_APP_THEME)) {

                    recreate();
                }
            }
        };
    }
}
