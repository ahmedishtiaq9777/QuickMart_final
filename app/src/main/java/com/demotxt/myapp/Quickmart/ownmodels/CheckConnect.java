package com.demotxt.myapp.Quickmart.ownmodels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnect {
    private final Context context;

    public CheckConnect(Context con) {
        context = con;

    }

    public boolean CheckConnection() {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

            if (null != activeNetwork &&  activeNetwork.isConnectedOrConnecting()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                } else {
                    return false;
                }

            }
            else {
                return  false;
            }
        } catch (Exception e) {
           // Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }


    }

}