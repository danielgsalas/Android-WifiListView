package com.appstoremarketresearch.android_wifilistview.model;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;

import java.util.List;

/**
 * WifiListener
 */
public interface WifiListener {

    /**
     * onGetConfiguredNetworks
     */
    void onGetConfiguredNetworks(List<WifiConfiguration> networks);

    /**
     * onGetConnectionInfo
     */
    void onGetConnectionInfo(WifiInfo info);
}
