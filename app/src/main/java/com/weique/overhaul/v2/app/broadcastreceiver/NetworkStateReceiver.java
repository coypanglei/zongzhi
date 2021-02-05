package com.weique.overhaul.v2.app.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jess.arms.utils.ArmsUtils;

/**
 * @author GK
 * @description:
 * @date :2020/7/30 17:26
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
    //            ArmsUtils.makeText("WiFi");
            } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
    //            ArmsUtils.makeText("移动流量");
            } else {
                ArmsUtils.makeText("无网络状态");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
