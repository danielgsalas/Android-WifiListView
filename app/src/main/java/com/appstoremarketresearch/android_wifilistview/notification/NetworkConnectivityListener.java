package com.appstoremarketresearch.android_wifilistview.notification;

import android.net.NetworkInfo;

/**
 * NetworkConnectivityListener
 */
public interface NetworkConnectivityListener {

    /**
     * onNetworkConnectivityChanged
     */
    void onNetworkConnectivityChanged(NetworkInfo.State state);
}
