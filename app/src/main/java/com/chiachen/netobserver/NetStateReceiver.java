package com.chiachen.netobserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jianjiacheng on 2018/7/17.
 */

public class NetStateReceiver extends BroadcastReceiver {
    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.chiachen.netobserver.connectivity_change";
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final static String TAG = NetStateReceiver.class.getSimpleName();

    private static boolean isNetAvailable = false;
    private static NetUtils.NetType mNetType;
    private static ArrayList<NetChangeObserver> mNetChangeObservers = new ArrayList<>();
    private static BroadcastReceiver mBroadcastReceiver;

    public static boolean isNetAvailable() {
        return isNetAvailable;
    }

    public static void setIsNetAvailable(boolean isNetAvailable) {
        NetStateReceiver.isNetAvailable = isNetAvailable;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mBroadcastReceiver = NetStateReceiver.this;

        if (null != intent.getAction() &&
                (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_ANDROID_NET_CHANGE_ACTION))) {
            Log.d("JASON_CHIEN", "\n" + intent.getAction());
            if (!NetUtils.isNetAvailable(context)) {
                isNetAvailable = false;
            } else {
                isNetAvailable = true;
                mNetType = NetUtils.getAPNType(context);
            }

            notifyObserver();
        }
    }

    private void notifyObserver() {
        if (Collections.isNullOrEmpty(mNetChangeObservers)) {
            return;
        }
        for (int i = 0; i < mNetChangeObservers.size(); i++) {
            NetChangeObserver observer = mNetChangeObservers.get(i);
            if (null == observer) {
                continue;
            }
            if (isNetAvailable()) {
                observer.onNetConnected(mNetType);
            } else {
                observer.onNetDisConnect();
            }
        }
    }

    public static void registerObserver(NetChangeObserver observer) {
        if (null == mNetChangeObservers) {
            mNetChangeObservers = new ArrayList<>();
        }
        mNetChangeObservers.add(observer);
    }

    public static void unRegisterObserver(NetChangeObserver observer) {
        if (null == mNetChangeObservers) {
            return;
        }

        if (mNetChangeObservers.contains(observer)) {
            mNetChangeObservers.remove(observer);
        }
    }

    //==================================================================
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    private static BroadcastReceiver getReceiver() {
        if (null == mBroadcastReceiver) {
            synchronized (NetStateReceiver.class) {
                if (null == mBroadcastReceiver) {
                    mBroadcastReceiver = new NetStateReceiver();
                }
            }
        }
        return mBroadcastReceiver;
    }

    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (mBroadcastReceiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            } catch (Exception e) {
            }
        }
    }

    //==================================================================

    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    //==================================================================
}
