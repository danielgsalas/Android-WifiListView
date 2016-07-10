package com.appstoremarketresearch.android_wifilistview.model;

import android.net.wifi.WifiConfiguration;

import java.util.Comparator;

/**
 * WifiConfigurationComparator
 */
public class WifiConfigurationComparator implements Comparator<WifiConfiguration> {

    /**
     * compare
     */
    public int compare(
        WifiConfiguration config1,
        WifiConfiguration config2) {

        int comparison;

        if (config1 == null && config2 == null) {
            comparison = 0;
        }
        else if (config1 == null || config1.SSID == null) {
            comparison = 1;
        }
        else if (config2 == null || config2.SSID == null ) {
            comparison = -1;
        }
        else {
            String ssid1 = config1.SSID.toLowerCase();
            String ssid2 = config2.SSID.toLowerCase();
            comparison = ssid1.compareTo(ssid2);
        }

        return comparison;
    }
}
