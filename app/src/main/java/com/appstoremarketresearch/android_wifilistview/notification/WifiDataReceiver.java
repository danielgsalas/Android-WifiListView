package com.appstoremarketresearch.android_wifilistview.notification;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;

import java.util.List;

/**
 * WifiDataReceiver
 */
public interface WifiDataReceiver {

    /**
     * onGetConfiguredNetworks
     *
     * @param networks A list of network configurations in the form of a list of
     *                 WifiConfiguration objects. Upon failure to fetch or when
     *                 Wi-Fi is turned off, it can be null.
     */
    void onGetConfiguredNetworks(List<WifiConfiguration> networks);

    /**
     * onGetConnectionInfo
     *
     * @param info Dynamic information about the current Wi-Fi connection,
     *             if any is active.
     */
    void onGetConnectionInfo(WifiInfo info);
}
