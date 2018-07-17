package com.chiachen.netobserver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * Created by jianjiacheng on 2018/7/17.
 */

public class NetUtils {
    public static enum NetType {
        WIFI, CMNET, CMWAP, NONE;
    }

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if (null != infos) {
            for (int i = 0; i < infos.length; i++) {
                if (NetworkInfo.State.CONNECTED == infos[i].getState()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static NetType getAPNType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return NetType.NONE;
        }

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int type = networkInfo.getType();

        if (ConnectivityManager.TYPE_MOBILE == type) {
            if ("cmnet".equals(networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()))) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (ConnectivityManager.TYPE_WIFI == type) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

}
