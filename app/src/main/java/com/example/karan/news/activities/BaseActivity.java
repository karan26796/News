package com.example.karan.news.activities;

import android.app.Activity;
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
 * its methods help implement features like changing font size, theme etc. across
 * activities.*/

public abstract class BaseActivity extends AppCompatActivity {

    String textSize;
    String theme;
    boolean downloadImages;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // sets up app theme
        // declared before onCreate to apply theme
        setUpTheme();

        // set up app font size based on user selection
        // declared before onCreate to apply theme
        setUpFont();

        super.onCreate(savedInstanceState);

        // preference manager shared preferences listener from activity_settings activity for app customisation.
        sharedPreferencesListener();

        // set up activity layout
        setContentView(getLayoutResourceId());

        // sets up activity toolbar if any
        configureToolbar(this);
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

    //get toolbar ID if any
    protected abstract int getToolbarID();

    // get toolbar title if any
    protected abstract String getToolbarTitle();

    /**
     * Sets up activity toolbar.
     *
     * Retrieves the toolbar ID along with title if any.
     */
    protected void configureToolbar(Activity activity) {

        toolbar = (Toolbar) findViewById(getToolbarID());

        if (toolbar != null) {

            setSupportActionBar(toolbar);

            // sets activity title
            // handle NullPointerException
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(getToolbarTitle());
        }
    }

    /**
     * Sets up app theme.
     *
     * The app theme selected by the user is retrieved using the method below.
     */
    protected void setUpTheme() {

        // fetches shared preferences for user selected app theme
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

        // fetches shared preferences for text size selected by user
        loadPreferences();

        // set font size from app styles
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
     * Loads preference manager shared preferences from activity_settings activity.
     */
    protected void loadPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        textSize = sharedPreferences.getString(Constants.KEY_TEXT_SIZE, getString(R.string.text_size_small));

        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));

        downloadImages  = sharedPreferences.getBoolean(Constants.KEY_DOWNLOAD_IMAGES, true);
    }

    // preference manager shared preferences listener
    protected void sharedPreferencesListener() {

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                /*The activity_settings activity too is given the app theme, therefore it is recreated
                in case of change in app theme*/
                /*In case of other two customisations, they are to be applied in the details page only.*/
                if (key.equals(Constants.KEY_APP_THEME)) {
                    recreate();
                }
            }
        };
    }
}
