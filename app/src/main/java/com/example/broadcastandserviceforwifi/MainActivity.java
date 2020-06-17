package com.example.broadcastandserviceforwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //此app實作廣播訊息的步驟

    private MyBroadcastReceiver myBroadcastReceiver;
    boolean mbrIsReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRegReceiver = findViewById(R.id.button4);
        btnRegReceiver.setOnClickListener(btnRegReceiverOnClick);

        Button btnUnRegReceiver = findViewById(R.id.button5);
        btnUnRegReceiver.setOnClickListener(btnUnRegReceiverOnClick);

        Button btnSendReceiver = findViewById(R.id.button6);
        btnSendReceiver.setOnClickListener(btnSendReceiverOnClick);

    }
    //Broadcast Receiver 一定要先註冊
    private View.OnClickListener btnRegReceiverOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentFilter itFilter = new IntentFilter("將此APP跟Android註冊，此字串通常會是app的程是套件路徑，如com.app.myapp.MY_BROADCAST");
            myBroadcastReceiver = new MyBroadcastReceiver();
            registerReceiver(myBroadcastReceiver,itFilter);
            mbrIsReg=true;
        }
    };

    private View.OnClickListener btnUnRegReceiverOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mbrIsReg){
                unregisterReceiver(myBroadcastReceiver);
                mbrIsReg = false;
            }else{
                Toast.makeText(MainActivity.this,"BroadcastReceiver還未註冊",Toast.LENGTH_LONG).show();
            }

        }
    };

    private View.OnClickListener btnSendReceiverOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //App廣播訊息的方法
            /*1. 建立一個Intent物件，並指定要廣播的訊息。廣播訊息其實就是一個字串，每個一個APP都可以建立自己的廣播訊息。為了避免不同的APP誤用相同的廣播訊息，
            一般建議採用類似程是套件路徑的方式來命名*/
            Intent it = new Intent("將此APP跟Android註冊，此字串通常會是app的程是套件路徑，如com.app.myapp.MY_BROADCAST");
            /*2. 如果要在訊息中附帶資料，可以把資料放入INTENT物件裡面，或是把資料儲存在Bundle物件中，如下例*/
            it.putExtra("SENDER","MainActivity");
            Bundle bundle = new Bundle();
            bundle.putString("STRING_DATA","這是Bundle夾帶的訊息");
            it.putExtras(bundle);//讓intent攜帶
            /*3. 呼叫sendBroadcast()方法廣播Intent物件*/
            sendBroadcast(it);
        }
    };


}
