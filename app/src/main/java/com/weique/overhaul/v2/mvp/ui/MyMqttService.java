//package com.weique.overhaul.v2.mvp.ui;
//
//import android.annotation.SuppressLint;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.PowerManager;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//
//import com.google.gson.Gson;
//import com.jess.arms.integration.AppManager;
//import com.jess.arms.utils.ArmsUtils;
//import com.weique.overhaul.v2.app.common.RouterHub;
//import com.weique.overhaul.v2.app.utils.SystemHelper;
//import com.weique.overhaul.v2.app.utils.UserInfoUtil;
//import com.weique.overhaul.v2.mvp.model.entity.ChatSelectBaen;
//import com.weique.overhaul.v2.mvp.model.entity.PushMessageBean;
//import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
//import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.SingleCallActivity;
//import com.weique.overhaul.v2.mvp.ui.push.NotificationUtils;
//
//import org.eclipse.paho.android.service.MqttAndroidClient;
//import org.eclipse.paho.client.mqttv3.IMqttActionListener;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.IMqttToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.simple.eventbus.EventBus;
//
//import static com.weique.overhaul.v2.app.common.EventBusConstant.ISRUNNINGFOREGROUND_NO;
//import static com.weique.overhaul.v2.mvp.ui.activity.MainActivity.isChat;
//
///**
// * Author       wildma
// * Github       https://github.com/wildma
// * CreateDate   2018/11/08
// * Desc         ${MQTT服务}
// */
//
//public class MyMqttService extends Service {
//
//    public final String TAG = MyMqttService.class.getSimpleName();
//    private static MqttAndroidClient mqttAndroidClient;
//    private MqttConnectOptions mMqttConnectOptions;
//    public String HOST = "tcp://192.168.20.125:61613";//服务器地址（协议+地址+端口号）
//    public String USERNAME = "admin";//用户名
//    public String PASSWORD = "password";//密码
//    public static String PUBLISH_TOPIC = "tourist_enter";//发布主题
//    public static String RESPONSE_TOPIC = "message_arrived";//响应主题
//    @SuppressLint("MissingPermission")
//    @RequiresApi(api = 26)
//    public String CLIENTID = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
//            ? Build.getSerial() : Build.SERIAL;//客户端ID，一般以客户端唯一标识符表示，这里用设备序列号表示
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        init();
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    /**
//     * 开启服务
//     */
//    public static void startService(Context mContext) {
//        mContext.startService(new Intent(mContext, MyMqttService.class));
//    }
//
//    /**
//     * 发布 （模拟其他客户端发布消息）
//     *
//     * @param message 消息
//     */
//    public static void publish(String message) {
//        String topic = PUBLISH_TOPIC;
//        Integer qos = 2;
//        Boolean retained = false;
//        try {
//            //参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
//            mqttAndroidClient.publish(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 响应 （收到其他客户端的消息后，响应给对方告知消息已到达或者消息有问题等）
//     *
//     * @param message 消息
//     */
//    public void response(String message) {
//        String topic = RESPONSE_TOPIC;
//        Integer qos = 2;
//        Boolean retained = false;
//        try {
//            //参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
//            mqttAndroidClient.publish(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 初始化
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void init() {
//        String serverURI = HOST; //服务器地址（协议+地址+端口号）
//        mqttAndroidClient = new MqttAndroidClient(this, serverURI, CLIENTID);
//        mqttAndroidClient.setCallback(mqttCallback); //设置监听订阅消息的回调
//        mMqttConnectOptions = new MqttConnectOptions();
//        mMqttConnectOptions.setCleanSession(true); //设置是否清除缓存
//        mMqttConnectOptions.setConnectionTimeout(15); //设置超时时间，单位：秒
//        mMqttConnectOptions.setKeepAliveInterval(15); //设置心跳包发送间隔，单位：秒
//        mMqttConnectOptions.setUserName(USERNAME); //设置用户名
//        mMqttConnectOptions.setPassword(PASSWORD.toCharArray()); //设置密码
//
//        // last will message
//        boolean doConnect = true;
////        String message = "{\"terminal_uid\":\"" + CLIENTID + "\"}";
////        String topic = PUBLISH_TOPIC;
////        Integer qos = 2;
////        Boolean retained = false;
////        if ((!message.equals("")) || (!topic.equals(""))) {
////            // 最后的遗嘱
////            try {
////                mMqttConnectOptions.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
////            } catch (Exception e) {
////                Log.i(TAG, "Exception Occured", e);
////                doConnect = false;
////                iMqttActionListener.onFailure(null, e);
////            }
////        }
//        if (doConnect) {
//            doClientConnection();
//        }
//    }
//
//    /**
//     * 连接MQTT服务器
//     */
//    private void doClientConnection() {
//        if (!mqttAndroidClient.isConnected() && isConnectIsNomarl()) {
//            try {
//                mqttAndroidClient.connect(mMqttConnectOptions, null, iMqttActionListener);
//            } catch (MqttException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 判断网络是否连接
//     */
//    private boolean isConnectIsNomarl() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//        if (info != null && info.isAvailable()) {
//            String name = info.getTypeName();
//            Log.i(TAG, "当前网络名称：" + name);
//            return true;
//        } else {
//            Log.i(TAG, "没有可用网络");
//            /*没有可用网络的时候，延迟3秒再尝试重连*/
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    doClientConnection();
//                }
//            }, 3000);
//            return false;
//        }
//    }
//
//    //MQTT是否连接成功的监听
//    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {
//
//        @Override
//        public void onSuccess(IMqttToken arg0) {
//            Log.i(TAG, "连接成功 ");
//            try {
//                mqttAndroidClient.subscribe(PUBLISH_TOPIC, 2);//订阅主题，参数：主题、服务质量
//            } catch (MqttException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(IMqttToken arg0, Throwable arg1) {
//            arg1.printStackTrace();
//            Log.i(TAG, "连接失败 ");
//            doClientConnection();//连接失败，重连（可关闭服务器进行模拟）
//        }
//    };
//
//    //订阅主题的回调
//    private MqttCallback mqttCallback = new MqttCallback() {
//
//        @Override
//        public void messageArrived(String topic, MqttMessage message) throws Exception {
//            Log.i(TAG, "收到消息： " + new String(message.getPayload()));
//            //收到消息，这里弹出Toast表示。如果需要更新UI，可以使用广播或者EventBus进行发送
////            Toast.makeText(getApplicationContext(), "messageArrived: " + new String(message.getPayload()), Toast.LENGTH_LONG).show();
//            //收到其他客户端的消息后，响应给对方告知消息已到达或者消息有问题等
//            response("message arrived");
//
//            String s = new String(message.getPayload());
//
//            ChatSelectBaen chatSelectBaen = new ChatSelectBaen();
//            chatSelectBaen = new Gson().fromJson(s, ChatSelectBaen.class);
//
//            if (chatSelectBaen.getIsSend().equals("send")) {
//                String replace = UserInfoUtil.getUserInfo().getUid().replace("-", "");
//                for (String o : chatSelectBaen.getSendPeople()) {
//                    if (o.equals(replace)) {
//                        enterRoomId(chatSelectBaen);
//                    }
//                }
//            } else if (chatSelectBaen.getIsSend().equals("fankui")) {
//                String replace = UserInfoUtil.getUserInfo().getUid().replace("-", "");
//                for (String o : chatSelectBaen.getSendPeople()) {
//                    if (o.equals(replace)) {
//                        ArmsUtils.makeText(chatSelectBaen.getWhoSendName() + "拒绝了您的视频！");
//                    }
//                }
//            }
//
//
////            AsyncPlayer ringPlayer = new AsyncPlayer(null);
////            Uri uri = Uri.parse("android.resource://" + AppManager.getAppManager().getmApplication().getPackageName() + "/" + R.raw.gulou);
////            ringPlayer.play(AppManager.getAppManager().getmApplication(), uri, true, AudioManager.STREAM_RING);
//        }
//
//        @Override
//        public void deliveryComplete(IMqttDeliveryToken arg0) {
//
//        }
//
//        @Override
//        public void connectionLost(Throwable arg0) {
//            Log.i(TAG, "连接断开 ");
//            doClientConnection();//连接断开，重连
//        }
//    };
//
//    @Override
//    public void onDestroy() {
//        try {
//            mqttAndroidClient.disconnect(); //断开连接
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//        super.onDestroy();
//    }
//
//    private void enterRoomId(ChatSelectBaen chatSelectBaen) {
////        SystemHelper.setTopApp(AppManager.getAppManager().getmApplication());
//        if (SystemHelper.isRunningForeground(this)) {
//            if ("close".equals(isChat)) {
//
//                String who = chatSelectBaen.getWhoSendName();
//                String whoId = chatSelectBaen.getWhoSendId();
//                SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(), chatSelectBaen.getRoomId(), who,whoId);
////                wakeUpScreen(this);
//
//
//
//            }
//        } else {
//
//            if ("close".equals(isChat)) {
//
//                String who = chatSelectBaen.getWhoSendName();
//                String whoId = chatSelectBaen.getWhoSendId();
//
//                PushMessageBean listBean = new PushMessageBean();
//                listBean.setRoomId(chatSelectBaen.getRoomId());
//                listBean.setWho(who);
//                listBean.setWhoId(whoId);
//                EventBus.getDefault().post(new EventBusBean(ISRUNNINGFOREGROUND_NO, listBean), RouterHub.APP_MAINACTIVITY);
////                wakeUpScreen(this);
//
//                NotificationUtils notificationUtils = new NotificationUtils(this);
//                notificationUtils.sendNotificationFullScreen("江苏微雀", chatSelectBaen.getRoomId(), who,whoId);
//
//            }
//        }
//    }
//
//
//    private void wakeUpScreen(Context context) {
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        boolean screenOn = pm.isScreenOn();
//        if (!screenOn) {
//            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
//            wl.acquire(10 * 60 * 1000L /*10 minutes*/); // 点亮屏幕
//            wl.release(); // 释放
//        }
//    }
//}
