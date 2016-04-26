package com.example.winola.wifiscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "WiFiScan";
    private WifiManager wifiManager;
    private WifiReceiver wifiReceiver;
    private int scanCounter = 0;
    private class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> resultList = wifiManager.getScanResults();
            if (resultList.size() > 0) {
                Log.i(TAG, "********* " + (++scanCounter) + " scan(s) ********");
                for (int i = 0; i < resultList.size(); i++) {
                    ScanResult result = resultList.get(i);
                    Log.i(TAG, result.BSSID + ", " + result.SSID + ", " + result.level);
                }
            }
            wifiManager.startScan();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(wifiReceiver);
        super.onDestroy();
    }
}
