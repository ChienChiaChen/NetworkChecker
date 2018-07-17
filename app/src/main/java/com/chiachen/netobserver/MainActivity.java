package com.chiachen.netobserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mNetworkStateTv;
    private Button mCheckNetowrkBtn;

    protected NetChangeObserver mNetChangeObserver = new NetChangeObserver() {
        @Override
        public void onNetConnected(NetUtils.NetType type) {
            super.onNetConnected(type);
            mNetworkStateTv.setText(new StringBuilder(mNetworkStateTv.getText()).append("\n").append(type));
            Log.d("JASON_CHIEN", "\nonNetConnected: " + type);
        }

        @Override
        public void onNetDisConnect() {
            super.onNetDisConnect();
            mNetworkStateTv.setText(new StringBuilder(mNetworkStateTv.getText()).append("\n").append("onNetDisConnect"));
            Log.d("JASON_CHIEN", "\nonNetDisConnect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetStateReceiver.registerObserver(mNetChangeObserver);
        NetStateReceiver.registerNetworkStateReceiver(this);

        mCheckNetowrkBtn = findViewById(R.id.check_network_btn);
        mCheckNetowrkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetStateReceiver.checkNetworkState(v.getContext());
            }
        });

        mNetworkStateTv = findViewById(R.id.network_state_tv);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateReceiver.unRegisterObserver(mNetChangeObserver);
        NetStateReceiver.unRegisterNetworkStateReceiver(this);
    }
}
