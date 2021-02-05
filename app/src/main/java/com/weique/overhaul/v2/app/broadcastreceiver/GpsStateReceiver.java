package com.weique.overhaul.v2.app.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.jess.arms.utils.ArmsUtils;

/**
 * @author GK
 * @description:
 * @date :2020/7/31 10:00
 */
public class GpsStateReceiver extends BroadcastReceiver {


    private String flag;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!gps) {
                if (flag != null) {
                    return;
                }
                flag = new String();
                ArmsUtils.makeText("GPS关闭，会导致您的位置信息错误");
            } else {
                if (flag != null) {
                    flag = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
