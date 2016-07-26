package com.appstoremarketresearch.android_wifilistview.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstoremarketresearch.android_wifilistview.R;
import com.appstoremarketresearch.android_wifilistview.model.WifiService;
import com.appstoremarketresearch.android_wifilistview.notification.NetworkBroadcastReceiver;

/**
 * MainFragment
 */
public class MainFragment extends Fragment {

    private WifiArrayAdapter    mWifiArrayAdapter;
    private WifiListView        mWifiListView;
    private WifiService         mWifiService;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        // create ArrayAdapter for ListView
        mWifiArrayAdapter = new WifiArrayAdapter(getActivity());

        // initialize ListView
        int id = R.id.wifiListView;
        mWifiListView = (WifiListView) rootView.findViewById(id);
        mWifiListView.setAdapter(mWifiArrayAdapter);
        mWifiListView.initializeNetworkStatusHeader();

        // subscribe for event notification
        NetworkBroadcastReceiver.subscribe(mWifiListView);
        NetworkBroadcastReceiver.subscribe(mWifiArrayAdapter);

        // initialize and start WifiService
        mWifiService = new WifiService(getActivity());
        mWifiService.subscribe(mWifiArrayAdapter);
        Thread thread = new Thread(mWifiService);
        thread.start();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // stop listening for network information
        mWifiService.unsubscribe(mWifiArrayAdapter);
        mWifiService.stop();

        // unsubscribe from event notification
        NetworkBroadcastReceiver.unsubscribe(mWifiListView);
        NetworkBroadcastReceiver.unsubscribe(mWifiArrayAdapter);
    }
}
