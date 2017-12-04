package com.example.vicinia.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.vicinia.activities.SplashActivity;

public class ConnectivityUtilities {
    private static final String TAG = "VICINITA/ConnectivityUtilities";

    /**
     * method used to check connectivity
     * @param context is a reference to the calling activity, used when creating the dialog
     *                in case not connected
     * @return boolean. True if there's an active connection and false if not connected
     *
     * @called_from: {@link SplashActivity#onCreate(Bundle)}
     * @calls: none
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
