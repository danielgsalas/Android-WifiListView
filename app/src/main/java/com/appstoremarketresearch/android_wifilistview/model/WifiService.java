package com.appstoremarketresearch.android_wifilistview.model;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * WifiService
 */
public class WifiService implements Runnable {

    private WifiListener    mWifiListener;
    private WifiManager     mWifiManager;

    private boolean mKeepRunning;
    private long    mSleepMilliseconds = 2000;

    /**
     * WifiService
     */
    public WifiService(
        Context context,
        WifiListener wifiListener) {

        this.mWifiListener = wifiListener;
        this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * notifyWifiListener
     */
    private void notifyWifiListener() {
        if (mWifiListener != null) {
            List<WifiConfiguration> networks = mWifiManager.getConfiguredNetworks();
            mWifiListener.onGetConfiguredNetworks(networks);

            WifiInfo info = mWifiManager.getConnectionInfo();
            mWifiListener.onGetConnectionInfo(info);
        }
    }

    @Override
    public void run() {

        while (mKeepRunning) {
            try {
                notifyWifiListener();
                Thread.sleep(mSleepMilliseconds);
            }
            catch (InterruptedException ex) {
                mKeepRunning = false;
            }
        }
    }

    /**
     * stop
     */
    public void stop() {
        mKeepRunning = false;
    }
}
