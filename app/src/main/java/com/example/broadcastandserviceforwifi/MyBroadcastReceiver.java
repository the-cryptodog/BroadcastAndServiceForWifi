package com.example.broadcastandserviceforwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver  {

    //此類別 onReceive 決定接受到廣播訊息時的動作，需先註冊

    @Override
    public void onReceive(Context context, Intent intent) {
        String sender = intent.getStringExtra("SENDER");
        String getBundleString = intent.getExtras().getString("STRING_DATA");
        Toast.makeText(context,"MyBroadcastReceiver收到"+getBundleString+"的廣播訊息",Toast.LENGTH_LONG).show();
    }
}
