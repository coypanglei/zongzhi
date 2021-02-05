package com.weique.overhaul.v2.mvp.ui.push;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.baidu.mapapi.NetworkUtil;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.SystemHelper;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.model.entity.PushMessageBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.SingleCallActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.Iterator;
import java.util.Objects;

import cn.jpush.android.api.JPushInterface;

import static com.weique.overhaul.v2.app.common.EventBusConstant.ISRUNNINGFOREGROUND_NO;
import static com.weique.overhaul.v2.app.common.EventBusConstant.ISRUNNINGFOREGROUND_YES;
import static com.weique.overhaul.v2.mvp.ui.activity.MainActivity.isChat;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBusManager.getInstance().register(this);

        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

             if (NetworkUtil.isNetworkAvailable(AppManager.getAppManager().getmApplication())) {
                if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                    String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                    Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                    //send the Registration Id to your server...

                } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    processCustomMessage(context, bundle);

                } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                    int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                    Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

                    if (UserInfoUtil.getUserInfo() != null) {
                        if (SystemHelper.isRunningForeground(context)) {
                            if ("close".equals(isChat)) {
                                JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
                                String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
                                SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"),jsonExtra.getString("HeadUrl"), who);
                                JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
                                wakeUpScreen(context);

                            }
                        } else {
                            SystemHelper.setTopApp(AppManager.getAppManager().getmApplication());

                            if ("close".equals(isChat)) {

                                JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
                                String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
//                                SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"), who);
//                                JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);

                                PushMessageBean listBean = new PushMessageBean();
                                listBean.setRoomId(jsonExtra.getString("RoomId"));
                                listBean.setHeadUrl(jsonExtra.getString("HeadUrl"));
                                listBean.setWho(who);
                                EventBus.getDefault().post(new EventBusBean(ISRUNNINGFOREGROUND_NO, listBean), RouterHub.APP_MAINACTIVITY);
                                wakeUpScreen(context);


                            }
                        }
                    }else {
                        JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
                    }


//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        //android10.0以上通过startForegroundService启动service
//                        if (SystemHelper.isRunningForeground(context)) {
//                            if ("close".equals(isChat)) {
//                                JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
//                                String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
//                                SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"), who);
//                                JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
//                            }
//                        } else {
//
//                            if ("close".equals(isChat)) {
//
//                                JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
//                                String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
////                                SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"), who);
////                                JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
//
//                                PushMessageBean listBean = new PushMessageBean();
//                                listBean.setRoomId(jsonExtra.getString("RoomId"));
//                                listBean.setWho(who);
//                                EventBus.getDefault().post(new EventBusBean(ISRUNNINGFOREGROUND_NO, listBean), RouterHub.APP_MAINACTIVITY);
//                                wakeUpScreen(context);
//
//
//                            }
//                        }
//
////                        if ("close".equals(isChat)) {
////                            JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
////                            String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
//////                    SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"), who);
////
////
////                            NotificationUtils notificationUtils = new NotificationUtils(AppManager.getAppManager().getmApplication());
////                            String content = "fullscreen intent test";
////                            notificationUtils.sendNotificationFullScreen("江苏微雀", jsonExtra.getString("RoomId"), who);
////                            JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
////
////                        }
//
//
//                    } else {
//                        if ("close".equals(isChat)) {
//                            JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
//                            String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
//                            SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"), who);
//                            JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
//                        } else {
//                            JPushInterface.clearNotificationById(AppManager.getAppManager().getmApplication(), notifactionId);
//                        }
//
//                    }


                } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        //android10.0以上通过startForegroundService启动service
                        if ("close".equals(isChat)) {
//                            JSONObject jsonExtra = new JSONObject(Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)));
//                            String who = String.valueOf(bundle.get(JPushInterface.EXTRA_ALERT));
//                            SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), jsonExtra.getString("RoomId"), who);
                            EventBus.getDefault().post(new EventBusBean(ISRUNNINGFOREGROUND_YES, ""), RouterHub.APP_MAINACTIVITY);
                        }

                    }

//                //打开自定义的Activity
//                Intent i = new Intent(context, TestActivity.class);
//                i.putExtras(bundle);
//                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);

                } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                    //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                    boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                    Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
                } else {
                    Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
                }
            }

        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        /*if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }*/
    }

    private void wakeUpScreen(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10 * 60 * 1000L /*10 minutes*/); // 点亮屏幕
            wl.release(); // 释放
        }
    }

}
