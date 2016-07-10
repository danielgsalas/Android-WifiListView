package com.appstoremarketresearch.android_wifilistview.view;

import android.app.Activity;
import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appstoremarketresearch.android_wifilistview.R;
import com.appstoremarketresearch.android_wifilistview.controller.MainActivity;
import com.appstoremarketresearch.android_wifilistview.model.WifiConfigurationComparator;
import com.appstoremarketresearch.android_wifilistview.notification.NetworkConnectivityListener;
import com.appstoremarketresearch.android_wifilistview.notification.WifiDataReceiver;

import java.util.List;

/**
 * WifiArrayAdapter is a subclass of
 * https://developer.android.com/reference/android/widget/ArrayAdapter.html
 */
public class WifiArrayAdapter
    extends ArrayAdapter<WifiConfiguration>
    implements
        NetworkConnectivityListener,
        WifiDataReceiver{

    private Activity            mActivity;
    private NetworkInfo.State   mNetworkState;
    private WifiInfo            mCurrentNetwork;

    private static WifiConfigurationComparator COMPARATOR = new WifiConfigurationComparator();

    private static final String LOG_TAG = WifiArrayAdapter.class.getSimpleName();

    /**
     * WifiArrayAdapter
     */
    public WifiArrayAdapter(Activity activity) {
        super(activity, -1);
        mActivity = activity;
    }

    /**
     * getView
     */
    public View getView (
        int position,
        View convertView,
        ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);

        WifiConfiguration network = this.getItem(position);

        if (network == null) {
            Log.e(LOG_TAG, "No network found for position " + position);
        }
        else {
            showNetworkName(network, rowView);
        }

        return rowView;
    }

    /**
     * isConnectedToWifi
     */
    private boolean isConnectedToWifi() {
        return mNetworkState == NetworkInfo.State.CONNECTED ||
                MainActivity.isConnectedToWifi(getContext());
    }

    /**
     * isCurrentNetwork
     */
    private boolean isCurrentNetwork(WifiConfiguration network) {
        return mCurrentNetwork != null &&
               mCurrentNetwork.getSSID() != null &&
               mCurrentNetwork.getSSID().equals(network.SSID);
    }

    /**
     * onGetConfiguredNetworks
     */
    public void onGetConfiguredNetworks(final List<WifiConfiguration> networks) {

        if (networks == null || networks.size() == 0) {
            // do not wipe out the existing list, if any
            return;
        }

        /*
         E/AndroidRuntime: FATAL EXCEPTION: Thread-103181
                           Process: com.appstoremarketresearch.android_wifilistview, PID: 21127
                           android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                               at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:6909)
                               at android.view.ViewRootImpl.clearChildFocus(ViewRootImpl.java:3214)
         */

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                WifiArrayAdapter.this.clear();

                if (networks != null && networks.size() > 0) {
                    WifiArrayAdapter.this.addAll(networks);
                    WifiArrayAdapter.this.sort(COMPARATOR);
                }
             }
        });
    }

    /**
     * onGetConnectionInfo
     */
    public void onGetConnectionInfo(WifiInfo info) {
        mCurrentNetwork = info;
    }


    @Override
    public void onNetworkConnectivityChanged(NetworkInfo.State state) {
        mNetworkState = state;
        this.notifyDataSetChanged();
    }

    /**
     * showNetworkName
     */
    private void showNetworkName(
        WifiConfiguration network,
        View rowView) {

        String networkName = new String(network.SSID).replace("\"", "").replace("\"", "");

        if (isConnectedToWifi() && isCurrentNetwork(network)) {
            networkName += " (CONNECTED)";
        }

        TextView textview = (TextView)rowView.findViewById(R.id.networkTitle);
        textview.setText(networkName);
    }
}
