package com.example.twitterdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by aditya.sawant on 14-11-2016.
 */
public class Tools {

    private static Toast currentToast;
    public static boolean isNetworkAvailable(Context context) {
        boolean networkAvailable = false;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connMgr) {
            NetworkInfo nwInfo = connMgr.getActiveNetworkInfo();
            networkAvailable = null != nwInfo && nwInfo.isAvailable() && nwInfo.isConnected();
        }
        return networkAvailable;
    }

    public static void showToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }
    public static void showToast(Context context, String message, int duration) {
            currentToast = Toast.makeText(context, message, duration);
            currentToast.show();

    }
}
