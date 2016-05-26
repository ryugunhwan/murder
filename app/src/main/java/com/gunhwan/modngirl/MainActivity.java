package com.gunhwan.modngirl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    String test_Wifi = "Ryu's";
    int len = 0;
    List<ScanResult> scanResult;
    WifiManager wm;
    public void searchWifi() {
        scanResult= wm.getScanResults();
        Toast.makeText(getApplication() ,"wifi scan\nindex 0: "+ scanResult.get(0).SSID+"\nsize: "+scanResult.size(),Toast.LENGTH_LONG).show();
        len = scanResult.size();
        connectWifi(test_Wifi);
    }

    public void connectWifi(String name){

        for(int i = 0; i<len; i++){
            if(scanResult.get(i).SSID.equals(name)){
                Toast.makeText(getApplication(),"==",Toast.LENGTH_LONG).show();

            }
        }
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                searchWifi();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.call).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2475-9007"));
                        startActivity(intent);
                    }
                }

        );
        findViewById(R.id.wifi).setOnClickListener(
                new Button.OnClickListener(){
                    //Wifi Scan
                    public void onClick(View v){
                        wm = (WifiManager) getSystemService(WIFI_SERVICE);
                        wm.startScan();
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                        registerReceiver(wifiReceiver, filter);
                    }
                }
        );
    }
}
