package com.example.karan.news.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Native class to return current network state.
 */

public class NetworkUtil {

    /**
     * Returns the current network state.
     * If available, it returns true. Else false.
     *
     * @param context is the context of the activity/fragment.
     * @return a boolean value indicating whether network connection is available or not.
     */
    public static boolean getConnectivityStatus(Context context) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (null != netInfo) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
                if (netInfo.isConnected())
                    haveConnectedWifi = true;
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                if (netInfo.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
