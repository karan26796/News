package com.example.karan.news.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *  Broadcast receiver to listen to change in network state
 *
 *  Sets up an error snackbar and error fragment when there is no internet connection.
 *  When network connection becomes available, snackbar and error fragment are dismissed.
 */

public abstract class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {

        dismissSnackbar();

        if (intent.getAction().equals(CONNECTIVITY_CHANGE_ACTION)) {

            setUpLayout();
        }
    }

    /**
     * PURPOSE: Dismisses error snackbar when metwork becomes available
     */
    protected abstract void dismissSnackbar();

    /**
     * PURPOSE: Show error fragment when there is no network state
     */
    protected abstract void setUpLayout();
}
