package com.appstoremarketresearch.android_wifilistview.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appstoremarketresearch.android_wifilistview.R;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * isConnectedToWifi
     */
    public static boolean isConnectedToWifi(Context context) {

        String service = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(service);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
