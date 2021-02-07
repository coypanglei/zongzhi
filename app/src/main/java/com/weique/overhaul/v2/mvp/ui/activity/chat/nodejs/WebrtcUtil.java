package com.weique.overhaul.v2.mvp.ui.activity.chat.nodejs;

import android.app.Activity;
import android.text.TextUtils;

import com.dds.webrtclib.WebRTCManager;
import com.dds.webrtclib.bean.MediaType;
import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.ui.ChatRoomActivity;
import com.dds.webrtclib.ui.ChatSingleActivity;
import com.dds.webrtclib.ws.IConnectEvent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.common.Constant;

/**
 * Created by dds on 2019/1/7.
 * android_shuai@163.com
 */
public class WebrtcUtil {

    // turn and stun
    private static MyIceServer[] iceServers = {
            new MyIceServer("stun:stun.l.google.com:19302"),

//            new MyIceServer("stun:" + HOST + ":3478?transport=udp"),
//            new MyIceServer("turn:" + HOST + ":3478?transport=udp",
//                    "ddssingsong",
//                    "123456"),
//            new MyIceServer("turn:" + HOST + ":3478?transport=tcp",
//                    "ddssingsong",
//                    "123456")

            new MyIceServer("stun:" + Constant.CHAT_HOST + ":3478?transport=udp"),
            new MyIceServer("turn:" + Constant.CHAT_HOST + ":3478?transport=udp",
                    "weique",
                    "weique@888"),
            new MyIceServer("turn:" + Constant.CHAT_HOST + ":3478?transport=tcp",
                    "weique",
                    "weique@888")
    };

    // signalling
//    private static String WSS = "wss://47.114.133.51/wss";
//    private static String WSS = "wss://121.40.168.83:4443/wss";
//    private static String WSS = "wss://" + HOST + "/wss";
//    private static String WSS = "ws://" + HOST + ":3000";

//     private static String WSS = "ws://192.168.20.80:3000";

    // one to one
    public static void callSingle(Activity activity, String wss, String roomId, boolean videoEnable, String name, String headUrl) {

        try {
            if (TextUtils.isEmpty(wss)) {
                wss = Constant.CHAT_IP;
            }
            WebRTCManager.getInstance().init(wss, iceServers, new IConnectEvent() {
                @Override
                public void onSuccess() {
                    ChatSingleActivity.openActivity(activity, videoEnable);
                }

                @Override
                public void onFailed(String msg) {

                }
            });
            WebRTCManager.getInstance().connect(videoEnable ? MediaType.TYPE_VIDEO : MediaType.TYPE_AUDIO, roomId, name, headUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Videoconferencing
    public static void call(Activity activity, String wss, String roomId, String name, String headUrl) {
        try {
            if (TextUtils.isEmpty(wss)) {
                wss = Constant.CHAT_IP;
            }
            WebRTCManager.getInstance().init(wss, iceServers, new IConnectEvent() {
                @Override
                public void onSuccess() {
                    ChatRoomActivity.openActivity(activity);
//                    JPushInterface.stopPush(AppManager.getAppManager().getmApplication());
                }

                @Override
                public void onFailed(String msg) {
                    ArmsUtils.snackbarText(msg);
                }
            });
            WebRTCManager.getInstance().connect(MediaType.TYPE_MEETING, roomId, name, headUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Videoconferencing
    public static void call(Activity activity, String wss, String roomId, boolean videoEnable, String name, String headUrl) {
        try {
            if (TextUtils.isEmpty(wss)) {
                wss = Constant.CHAT_IP;
            }
            WebRTCManager.getInstance().init(wss, iceServers, new IConnectEvent() {
                @Override
                public void onSuccess() {
                    ChatRoomActivity.openActivity(activity, videoEnable);
                }

                @Override
                public void onFailed(String msg) {
                    ArmsUtils.snackbarText(msg);
                }
            });
            if (videoEnable) {
                WebRTCManager.getInstance().connect(MediaType.TYPE_MEETING, roomId, name, headUrl);
            } else {
                WebRTCManager.getInstance().connect(MediaType.TYPE_AUDIO, roomId, name, headUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
