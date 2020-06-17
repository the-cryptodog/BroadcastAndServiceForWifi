package com.example.broadcastandserviceforwifi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MyService extends Service {

    private final String LOG_TAG = "ServiceDemo";
    private LocalBinder localBinder = new LocalBinder();//建立物件
    private ConnectivityManager cm;

    public MyService() {
    }

    public class LocalBinder extends Binder{ //LocalBinder類別
        MyService getService(){
            return MyService.this;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void myMethod(){
        Log.d(LOG_TAG,"onBind()");

        cm = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : cm.getAllNetworks()) {
            NetworkInfo networkInfo = cm.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(LOG_TAG,"Wifi connected: " + isWifiConn);
        Log.d(LOG_TAG,"Mobile connected: " + isMobileConn);

    }


    @Override
    public void onCreate() {
        Log.d(LOG_TAG,"onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG,"onStartCommand()");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG,"onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG,"onBind()");
        return localBinder;
    }
}
