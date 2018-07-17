package com.chiachen.netobserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    protected NetChangeObserver mNetChangeObserver = new NetChangeObserver() {
        @Override
        public void onNetConnected(NetUtils.NetType type) {
            super.onNetConnected(type);
            Log.d("JASON_CHIEN", "\nonNetConnected: " + type);
        }

        @Override
        public void onNetDisConnect() {
            super.onNetDisConnect();
            Log.d("JASON_CHIEN", "\nonNetDisConnect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetStateReceiver.registerObserver(mNetChangeObserver);
        NetStateReceiver.registerNetworkStateReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateReceiver.unRegisterObserver(mNetChangeObserver);
        NetStateReceiver.unRegisterNetworkStateReceiver(this);
    }
}
