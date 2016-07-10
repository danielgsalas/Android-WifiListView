package com.appstoremarketresearch.android_wifilistview.view;

import android.content.Context;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.appstoremarketresearch.android_wifilistview.controller.MainActivity;
import com.appstoremarketresearch.android_wifilistview.notification.NetworkConnectivityListener;

/**
 * WifiListView is a subclass of
 * https://developer.android.com/reference/android/widget/ListView.html
 */
public class WifiListView
    extends ListView
    implements NetworkConnectivityListener
{
    private TextView mHeader;

    /**
     * WifiListView
     */
    public WifiListView(Context context) {
        super(context);
    }

    /**
     * WifiListView
     */
    public WifiListView(
        Context context,
        AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * WifiListView
     */
    public WifiListView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Add the header view to show the network status. This method must be called when
     * the Activity and Fragment are ready, not from this ListView's constructor.
     */
    public void initializeNetworkStatusHeader() {

        // create TextView with colors and padding
        mHeader = new TextView(getContext());
        mHeader.setBackgroundColor(Color.BLACK);
        mHeader.setTextColor(Color.WHITE);
        mHeader.setPadding(10, 10, 10, 10);

        // set TextView text based on connectivity to Wi-Fi
        if (MainActivity.isConnectedToWifi(getContext())) {
            mHeader.setText("NETWORK STATUS: CONNECTED");
        }
        else {
            mHeader.setText("NETWORK STATUS: NOT CONNECTED");
        }

        // set width and height of TextView
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
        mHeader.setLayoutParams(params);

        this.addHeaderView(mHeader);
    }

    @Override
    public void onNetworkConnectivityChanged(NetworkInfo.State state) {

        if (state == null) {
            mHeader.setText("NETWORK STATUS: NOT CONNECTED");
        }
        else {
            mHeader.setText("NETWORK STATUS: " + state.name());
        }
    }
}
