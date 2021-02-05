package com.weique.overhaul.v2.app.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 系统帮助类
 */
public class SystemHelper {
    /**
     * 判断本地是否已经安装好了指定的应用程序包
     *
     * @param packageNameTarget ：待判断的 App 包名，如 微博 com.sina.weibo
     * @return 已安装时返回 true,不存在时返回 false
     */
    public static boolean appIsExist(Context context, String packageNameTarget) {
        if (!"".equals(packageNameTarget.trim())) {
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            for (PackageInfo packageInfo : packageInfoList) {
                String packageNameSource = packageInfo.packageName;
                if (packageNameSource.equals(packageNameTarget)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将本应用置顶到最前端
     * 当本应用位于后台时，则将它切换到最前端
     *
     * @param context
     */
    public static void setTopApp(Context context) {
        try {
            if (!isRunningForeground(context)) {
                /**获取ActivityManager*/
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

                /**获得当前运行的task(任务)*/
                List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
                for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                    /**找到本应用的 task，并将它切换到前台*/
                    if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                        activityManager.moveTaskToFront(taskInfo.id, 0);
                        break;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断本应用是否已经位于最前端
     *
     * @param context
     * @return 本应用已经位于最前端时，返回 true；否则返回 false
     */
    public static boolean isRunningForeground(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
            /**枚举进程*/
            for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
                if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @SuppressLint("NewApi")
    public static void isRunningForegroundToApp1(Context context, final Class Class) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
        /**枚举进程*/

        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
            //*找到本应用的 task，并将它切换到前台
            if (taskInfo.baseActivity.getPackageName().equals(context.getPackageName())) {
                Log.e("timerTask", "timerTask  pid " + taskInfo.id);
                Log.e("timerTask", "timerTask  processName " + taskInfo.topActivity.getPackageName());
                Log.e("timerTask", "timerTask  getPackageName " + context.getPackageName());
                activityManager.moveTaskToFront(taskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
                Intent intent = new Intent(context, Class);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setAction(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(intent);
                break;
            }
        }
    }

}
