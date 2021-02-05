package com.weique.overhaul.v2.app.service.localtion;

import android.content.Context;
import android.webkit.WebView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.weique.overhaul.v2.app.common.Constant;

/**
 * 定位服务LocationClient 相关
 *
 * @author baidu
 */
public class LocationService {
    private static LocationClient client = null;
    private static LocationClientOption locationOption;
    private static LocationClientOption DIYoption;
    private Object objLock;


    /***
     * 初始化 LocationClient
     *
     * @param locationContext
     */
    public LocationService(Context locationContext) {
        objLock = new Object();
        synchronized (objLock) {
            if (client == null) {
                client = new LocationClient(locationContext);
                client.setLocOption(getDefaultLocationClientOption());
            }
        }
    }


    public LocationClient getLocationClient() {
        return client;
    }

    /***
     * 注册定位监听
     *
     * @param listener
     * @return
     */

    public boolean registerListener(BDAbstractLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null) {
            client.registerLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener(BDAbstractLocationListener listener) {
        if (listener != null) {
            client.unRegisterLocationListener(listener);
        }
    }

    /**
     * @return 获取sdk版本
     */
    public String getSDKVersion() {
        if (client != null) {
            String version = client.getVersion();
            return version;
        }
        return null;
    }

    /***
     * 设置定位参数
     *
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(LocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (client.isStarted()) {
                client.stop();
            }
            DIYoption = option;
            client.setLocOption(option);
        }
        return isSuccess;
    }

    /**
     * 开发者应用如果有H5页面使用了百度JS接口，该接口可以辅助百度JS更好的进行定位
     *
     * @param webView 传入webView控件
     */
    public void enableAssistanLocation(WebView webView) {
        if (client != null) {
            client.enableAssistantLocation(webView);
        }
    }

    /**
     * 停止H5辅助定位
     */
    public void disableAssistantLocation() {
        if (client != null) {
            client.disableAssistantLocation();
        }
    }

    /***
     *
     * @return DefaultLocationClientOption  默认O设置
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (locationOption == null) {
            locationOption = new LocationClientOption();
            //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            locationOption.setCoorType(Constant.CoorType_BD09LL);
            //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
            locationOption.setScanSpan(0);
            //可选，设置是否需要地址信息，默认不需要
            locationOption.setIsNeedAddress(true);
            //可选，设置是否需要地址描述
            locationOption.setIsNeedLocationDescribe(true);
            //可选，设置是否需要设备方向结果
            locationOption.setNeedDeviceDirect(false);
            //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            locationOption.setLocationNotify(true);
            //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            locationOption.setIgnoreKillProcess(true);
            //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            locationOption.setIsNeedLocationDescribe(true);
            //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            locationOption.setIsNeedLocationPoiList(true);
            //可选，默认false，设置是否收集CRASH信息，默认收集
            locationOption.SetIgnoreCacheException(false);
            //可选，默认false，设置是否开启Gps定位
            locationOption.setOpenGps(true);
            //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
            locationOption.setIsNeedAltitude(false);
            //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，
            // 该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
            locationOption.setOpenAutoNotifyMode();
            //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
            locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        }
        return locationOption;
    }

    public void setScanSpan(int scanSpan) {
        if (locationOption != null) {
            locationOption.setScanSpan(scanSpan);
        }
    }

    /**
     * @return DIYOption 自定义Option设置
     */
    public LocationClientOption getOption() {
        if (DIYoption == null) {
            DIYoption = new LocationClientOption();
        }
        return DIYoption;
    }

    public void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.start();
            }
        }
    }

    public void requestLocation() {
        if (client != null) {
            client.requestLocation();
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }

    public boolean isStart() {
        return client.isStarted();
    }

    public boolean requestHotSpotState() {
        return client.requestHotSpotState();
    }
}
