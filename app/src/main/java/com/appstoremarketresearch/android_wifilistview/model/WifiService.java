package com.appstoremarketresearch.android_wifilistview.model;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.appstoremarketresearch.android_wifilistview.notification.WifiDataReceiver;

import java.util.AbstractSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WifiService
 */
public class WifiService implements Runnable {

    private WifiManager     mWifiManager;

    private boolean mKeepRunning = true;
    private long    mSleepMilliseconds = 3000;

    private static AbstractSet<WifiDataReceiver> mListeners =
            new CopyOnWriteArraySet<>();

    /**
     * WifiService
     */
    public WifiService(Context context) {
        this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * notifyWifiDataReceivers
     */
    private void notifyWifiDataReceivers() {

        List<WifiConfiguration> networks = mWifiManager.getConfiguredNetworks();
        WifiInfo info = mWifiManager.getConnectionInfo();

        for (WifiDataReceiver listener : mListeners) {
            listener.onGetConfiguredNetworks(networks);
            listener.onGetConnectionInfo(info);
        }
    }

    @Override
    public void run() {

        while (mKeepRunning) {
            try {
                notifyWifiDataReceivers();
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

    /**
     * subscribe
     */
    public static void subscribe(WifiDataReceiver listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
    }

    /**
     * unsubscribe
     */
    public static void unsubscribe(WifiDataReceiver listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
    }
}
