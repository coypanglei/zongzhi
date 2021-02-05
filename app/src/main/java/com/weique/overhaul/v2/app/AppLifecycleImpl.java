/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weique.overhaul.v2.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mobstat.StatService;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.service.localtion.LocationService;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.MetaDataUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import map.baidu.ar.init.ArSdkManager;
import map.baidu.ar.init.MKGeneralListener;
import map.baidu.ar.utils.ArBDLocation;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <p>
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
public class AppLifecycleImpl implements AppLifecycles {

    private static Application mApplication;

    private static LocationService locationService;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void attachBaseContext(@NonNull Context base) {
        //65535 处理
        try {
            MultiDex.install(base);
            //bug监控
            initBugly(base);
            //测试环境 使用切换服务器的包
            if (BuildConfig.DEBUG) {
                initUrl(base);
            }

            //扫描二维码
            ZXingLibrary.initDisplayOpinion(base);
            //百度统计
            initBaiduStatistics(base);
            //部分OPPO机型 AssetManager.finalize()
            fixOppoAssetManager();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyGeneralListener implements MKGeneralListener {
        // 事件监听，用来处理通常的网络错误，授权验证错误等
        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                Timber.i("arsdk 验证异常，请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError);
            } else {
                Timber.i("key认证成功");
            }
        }

        // 回调给ArSDK获取坐标（demo调用百度定位sdk）
        @Override
        public ArBDLocation onGetBDLocation() {
            BDLocation location =
                    LocSdkClient.getInstance(mApplication).getLocationStart()
                            .getLastKnownLocation();
            if (location == null) {
                return null;
            }
            ArBDLocation arBDLocation = new ArBDLocation();
            // 设置经纬度信息
            arBDLocation.setLongitude(location.getLongitude());
            arBDLocation.setLatitude(location.getLatitude());
            return arBDLocation;
        }
    }


    /**
     * 判断要是缓存中有url 就是用 缓存url
     */
    private void initUrl(Context base) {
        try {
            String asString = ACache.get(base).getAsString(ACacheConstant.DYNAMIC_URL);
            AppUtils.setGlobalDomain(base, asString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 统计信息
     *
     * @param base
     */
    private void initBaiduStatistics(Context base) {
        try {
            StatService.setDebugOn(true);
            StatService.autoTrace(base, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化监控日志
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void initBugly(Context context) {
        try {
            if (BuildConfig.DEBUG) {
                CrashReport.initCrashReport(context, Constant.BUGLY_APP_ID_DEBUG, false);
            } else {
                CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
                strategy.setAppChannel(MetaDataUtil.getChannelName() + "区域：" + MetaDataUtil.getCoverage());
                strategy.setAppVersion(AppUtils.getVersionName(context));
                CrashReport.initCrashReport(context, Constant.BUGLY_APP_ID_RELASE, false, strategy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        try {
            mApplication = application;
            if (BuildConfig.LOG_DEBUG) {
                Timber.plant(new Timber.DebugTree());
                ButterKnife.setDebug(true);
            }
            if (BuildConfig.LOG_DEBUG) {
                ARouter.openLog();
                ARouter.openDebug();
            }
            //初始化路由
            ARouter.init(application);

            initJpush(application);

            // ArSDK模块初始化
            ArSdkManager.getInstance().initApplication(mApplication, new MyGeneralListener());
            // 若用百度定位sdk,需要在此初始化定位SDK
            LocSdkClient.getInstance(mApplication).getLocationStart();
            // 若用探索功能需要再这集成检索模块 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
            SDKInitializer.initialize(mApplication);
            // 检索模块 自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
            // 包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
            SDKInitializer.setCoordType(CoordType.BD09LL);
            locationService = new LocationService(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LocationService getLocationService() {
        return locationService;
    }

    public static Application getApplication() {
        return mApplication;
    }

    /**
     * 初始化jpush
     *
     * @param application
     */
    private void initJpush(@NonNull Application application) {
        try {
            JPushInterface.setDebugMode(BuildConfig.DEBUG);
            JPushInterface.init(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTerminate(@NonNull Application application) {
        ARouter.getInstance().destroy();
    }


    /**
     * fix 部分OPPO机型 AssetManager.finalize() timed out
     *
     * @author zy
     * @time 19-5-30 下午3:54
     */
    private void fixOppoAssetManager() {
        try {
            String device = DeviceUtils.getPhoneType();
            LogUtils.debugInfo("devive" + device);
            if (!TextUtils.isEmpty(device)) {
                if (device.contains("OPPO R9") || device.contains("OPPO A57") || device.contains("OPPO A3")) {
                    try {
                        // 关闭掉FinalizerWatchdogDaemon
                        Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
                        Method method = clazz.getSuperclass().getDeclaredMethod("stop");
                        method.setAccessible(true);
                        Field field = clazz.getDeclaredField("INSTANCE");
                        field.setAccessible(true);
                        method.invoke(field.get(null));
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
