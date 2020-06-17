package com.example.broadcastandserviceforwifi;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity {

    private ConnectivityManager cm;
    private NetBroadcastReceiver receiver = new NetBroadcastReceiver();
    private final  String LOG_TAG = "ServiceDemo";
    boolean serviceOnBind;

    private MyService myService = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(LOG_TAG,"onServiceConnected()" + componentName.getClassName());
            myService = ((MyService.LocalBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(LOG_TAG,"onServiceDisconnected()"+ componentName.getClassName());
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);



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
//        Toast.makeText(ServiceActivity.this,"Wifi connected: " + isWifiConn,Toast.LENGTH_SHORT ).show();
//        Toast.makeText(ServiceActivity.this,"Wifi connected: " + isMobileConn,Toast.LENGTH_SHORT ).show();



        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, filter);
        receiver.setNetConnectedListener(new NetBroadcastReceiver.NetConnectedListener() {
            @Override
            public void netContent(boolean isConnected) {
                if(isConnected){
                Toast.makeText(ServiceActivity.this,"有網路了connected: " ,Toast.LENGTH_SHORT ).show();
                }else{
                Toast.makeText(ServiceActivity.this,"沒網路啦 " ,Toast.LENGTH_SHORT ).show();
                }
            }
        });



        Button btnStartService = findViewById(R.id.button);
        Button btnStopService = findViewById(R.id.button2);
        Button btnBindService = findViewById(R.id.button3);
        Button btnUnBindService = findViewById(R.id.button8);
        Button btnCallServiceMethod = findViewById(R.id.button7);

        btnStartService.setOnClickListener(btnStartServiceOnClick);
        btnStopService.setOnClickListener(btnStopServiceOnClick);
        btnBindService.setOnClickListener(btnBindServiceOnClick);
        btnUnBindService.setOnClickListener(btnUnBindServiceOnClick);
        btnCallServiceMethod.setOnClickListener(btnCallServiceMethodOnClick);
    }


    private View.OnClickListener btnStartServiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService = null;
            Intent intent = new Intent(ServiceActivity.this, MyService.class);
            startService(intent);
        }
    };
    private View.OnClickListener btnStopServiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService = null;
            Intent intent = new Intent(ServiceActivity.this, MyService.class);
            stopService(intent);
        }
    };
    private View.OnClickListener btnBindServiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myService = null;
            Intent intent = new Intent(ServiceActivity.this, MyService.class);
            bindService(intent,serviceConnection,BIND_AUTO_CREATE);
            serviceOnBind = true;
        }
    };
    private View.OnClickListener btnUnBindServiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(serviceOnBind) {
                myService = null;
                unbindService(serviceConnection);
            }
        }
    };

    private View.OnClickListener btnCallServiceMethodOnClick = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            if(myService!=null){
                myService.myMethod();
            }
        }
    };

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null) {
            //檢查版本，如果版本小於SDK版本大於API 29則採用新方法NetworkCapabilities，若小於則舊方法
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true;
                    }
                }
            }
        }else{
            //舊方法用getActiveNetworkInfo()取得activeNetworkInfo再用isConnected()判斷
            try {
                NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    Log.i("update_status", "Network is available : true");
                    return true;
                }
            } catch (Exception e) {
                Log.i("update_statut", "" + e.getMessage());
            }
        }
        return false;
    }
    //branch1fix2
    //fix3
    //fdsfasdf
    //new





}
