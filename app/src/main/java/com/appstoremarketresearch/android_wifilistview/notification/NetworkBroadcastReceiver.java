package com.appstoremarketresearch.android_wifilistview.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.AbstractSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * NetworkBroadcastReceiver
 */
public class NetworkBroadcastReceiver
    extends BroadcastReceiver
    implements NetworkConnectivityListener {

    // see http://stackoverflow.com/questions/6169059/android-event-for-internet-connectivity-state-change

    private static AbstractSet<NetworkConnectivityListener> mListeners =
            new CopyOnWriteArraySet<>();

    private static final String LOG_TAG = NetworkBroadcastReceiver.class.getSimpleName();

    @Override
    public void onNetworkConnectivityChanged(NetworkInfo.State state) {

        for (NetworkConnectivityListener listener : mListeners) {
            listener.onNetworkConnectivityChanged(state);
        }
    }

    public void onReceive(Context context, Intent intent) {

        if (intent == null) {
            Log.e(LOG_TAG, "Recevied null Intent");
        }
        else {

            switch (intent.getAction()) {
                case "android.net.conn.CONNECTIVITY_CHANGE":
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = (NetworkInfo) extras.getParcelable("networkInfo");
                    NetworkInfo.State state = info.getState();
                    onNetworkConnectivityChanged(state);
                    break;
            }
        }
    }

    /**
     * subscribe
     */
    public static void subscribe(NetworkConnectivityListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
    }

    /**
     * unsubscribe
     */
    public static void unsubscribe(NetworkConnectivityListener listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
    }
}
