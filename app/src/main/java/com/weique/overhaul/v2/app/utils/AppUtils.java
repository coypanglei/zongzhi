package com.weique.overhaul.v2.app.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.text.NoCopySpan;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.model.api.Api;
import com.weique.overhaul.v2.mvp.ui.activity.LoginActivity;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import timber.log.Timber;

public class AppUtils {

    /**
     * 路由跳转
     *
     * @param context context
     * @param path    path
     */
    public static void launchActivityByRouter(Context context, String path) {
        ARouterUtils.navigation(context, path);
    }

    /**
     * 获取当前程序包名
     *
     * @param context 上下文
     * @return 程序包名
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取程序版本信息
     *
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            String pkName = context.getPackageName();
            versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e("VersionInfo" + "Exception");
        }
        return versionName;
    }

    /**
     * 获取程序版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        String pkName = context.getPackageName();
        try {
            versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e("VersionInfo" + "Exception");
        }
        return versionCode;
    }

    /**
     * 打开浏览器
     *
     * @param context context
     * @param url     url
     */
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Timber.i("链接错误或无浏览器");
        }
    }

    /**
     * 判断是否安装某个应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 是否安装
     */
    public static boolean isAvailable(Context context, String packageName) {
        //获取packagemanager
        try {
            final PackageManager packageManager = context.getPackageManager();
            //获取所有已安装程序的包信息
            List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
            //从pinfo中将包名字逐一取出，压入pName list中
            if (pInfo != null) {
                for (int i = 0; i < pInfo.size(); i++) {
                    String pn = pInfo.get(i).packageName;
                    if (pn.equals(packageName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void installApk(Context context, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", file);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
//            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    public static void logout() {
        try {
            UserInfoUtil.loginOut(AppManager.getAppManager().getmApplication());
            GlideUtil.clearImageAllCache(AppManager.getAppManager().getmApplication());
            ARouterUtils.navigation(RouterHub.APP_LOGINACTIVITY);
            ArmsUtils.killAll(LoginActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ClickableSpan 产生的 泄露  解决  不适用
     */
    public static class NoRefCopySpan extends ClickableSpan implements NoCopySpan {

        @Override
        public void onClick(@NonNull View widget) {

        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
        }

    }


    /**
     * 判断 url地址是否可以访问
     *
     * @param address address
     * @return Boolean
     */
    public static Observable<Boolean> checkUrl(String address) {
        return Observable.create(emitter -> {
            try {
                URL url = new URL(address);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(true);
                conn.setConnectTimeout(15 * 1000);
                conn.setReadTimeout(15 * 1000);
                conn.connect();
                int code = conn.getResponseCode();
                if ((code >= 100) && (code < 400)) {
                    emitter.onNext(true);
                } else {
                    emitter.onNext(false);
                }
                conn.disconnect();
                conn = null;
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(false);
            }
        });
    }

    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public static boolean isLocationEnabled(Context context) {
        try {
            LocationManager locationManager
                    = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            return gps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置全局 url
     */
    public static void setGlobalDomain(Context base, String url) {
        try {
            if (StringUtil.isNotNullString(url) && url.startsWith(Constant.HTTP)) {
                RetrofitUrlManager.getInstance().setGlobalDomain(url);
                ACache.get(base).put(ACacheConstant.DYNAMIC_URL, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全局url
     */
    public static String getGlobalDomain(Context base) {
        String asString = "";
        try {
            asString = ACache.get(base).getAsString(ACacheConstant.DYNAMIC_URL);
            if (StringUtil.isNullString(asString) || !asString.startsWith(Constant.HTTP)) {
                asString = Api.APP_DOMAIN;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asString;
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        try {
            if (StringUtil.isNullString(ServiceName)) {
                return false;
            }
            ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
            for (int i = 0; i < runningService.size(); i++) {
                if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static long lastClickTime = 0;//上次点击的时间
    private static int spaceTime = 500;//时间间隔

    public static boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击

        if (currentTime - lastClickTime > spaceTime) {

            isAllowClick = false;

        } else {
            isAllowClick = true;

        }

        lastClickTime = currentTime;
        return isAllowClick;
    }
}

