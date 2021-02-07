package com.dds.webrtclib.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.ProxyVideoSink;
import com.dds.webrtclib.R;
import com.dds.webrtclib.WebRTCManager;
import com.dds.webrtclib.bean.ChatRoomItemBean;
import com.dds.webrtclib.bean.MemberBean;
import com.dds.webrtclib.ui.adapter.ChatAdapter;
import com.dds.webrtclib.utils.PermissionUtil;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.EventBus;
import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群聊界面
 * 支持 9 路同時通信
 */
public class ChatRoomActivity extends AppCompatActivity implements IViewCallback {

    private FrameLayout wr_video_view;
    private RecyclerView rv;

    private WebRTCManager manager;
    private Map<String, View> _videoViews = new HashMap<>();
    private Map<String, ProxyVideoSink> _sinks = new HashMap<>();
    private List<MemberBean> _infos = new ArrayList<>();
    private VideoTrack _localVideoTrack;

    private int mScreenWidth;
    int maxSpanCount = 3;
    private EglBase rootEglBase;
    private boolean videoEnable = true;
    private boolean isFirst = true;
    private ChatAdapter chatAdapter;
    private GridLayoutManager gridLayoutManager;
    private ChatRoomFragment chatRoomFragment;

    public static void openActivity(Activity activity) {
        try {
            Intent intent = new Intent(activity, ChatRoomActivity.class);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void openActivity(Activity activity, boolean videoEnable) {
        try {
            Intent intent = new Intent(activity, ChatRoomActivity.class);
            intent.putExtra("videoEnable", videoEnable);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            EventBusManager.getInstance().register(this);
            EventBus.getDefault().post("chat", "close");
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.wr_activity_chat_room);
            videoEnable = getIntent().getBooleanExtra("videoEnable", false);
            initView();
            initVar();
            chatRoomFragment = new ChatRoomFragment(videoEnable);
            replaceFragment(chatRoomFragment);
            startCall();
            timer.start();
            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        chatAdapter = new ChatAdapter(rootEglBase, videoEnable);
        gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(chatAdapter);
    }

    private CountDownTimer timer = new CountDownTimer(30 * 1000, 1000) {
        /**
         * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
        }

        /**
         * 倒计时完成时被调用
         */
        @Override
        public void onFinish() {
            ArmsUtils.makeText("当前无人进入房间！");
        }
    };


    private void initView() {
        rv = findViewById(R.id.rv);
        wr_video_view = findViewById(R.id.wr_video_view);

    }

    private void initVar() {
        // 设置宽高比例
        try {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            if (manager != null) {
                mScreenWidth = manager.getDefaultDisplay().getWidth();
            }
            wr_video_view.setLayoutParams(new RelativeLayout.
                    LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mScreenWidth));
            rootEglBase = EglBase.create();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startCall() {
        try {
            manager = WebRTCManager.getInstance();
            manager.setCallback(this);

            if (!PermissionUtil.isNeedRequestPermission(ChatRoomActivity.this)) {
                manager.joinRoom(getApplicationContext(), rootEglBase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSetLocalStream(MediaStream stream, String userId, String name, String headUrl) {
        try {
            List<VideoTrack> videoTracks = stream.videoTracks;
            if (videoTracks.size() > 0) {
                _localVideoTrack = videoTracks.get(0);
            }
            runOnUiThread(() -> {
                addView(userId, stream, name, headUrl);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddRemoteStream(MediaStream stream, String userId, String name, String headUrl) {
        timer.cancel();
        runOnUiThread(() -> {
            try {
                addView(userId, stream, name, headUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void onCloseWithId(String userId) {
        runOnUiThread(() -> {
            try {
                removeView(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }


    private void addView(String id, MediaStream stream, String name, String headUrl) {
        try {
            if (chatAdapter != null) {
                ChatRoomItemBean chatRoomItemBean = new ChatRoomItemBean();
                chatRoomItemBean.setId(id);
                chatRoomItemBean.setImageUrl(headUrl);
                chatRoomItemBean.setMediaStream(stream);
                chatRoomItemBean.setName(name);
                chatAdapter.addData(chatRoomItemBean);
                //有人加入房间后开始计时
                if (isFirst && chatAdapter.getData() != null && chatAdapter.getData().size() > 1) {
                    if (chatRoomFragment != null) {
                        isFirst = false;
                        chatRoomFragment.startDuration();
                    }
                }
            }

            /*View inflate = View.inflate(this, R.layout.surface_view_and_name, null);
            TextView name = inflate.findViewById(R.id.name);
            name.setText("1111111111111");
            SurfaceViewRenderer renderer = inflate.findViewById(R.id.svr);
            ImageView imagePhoto = inflate.findViewById(R.id.image_photo);
            if (videoEnable) {
                renderer.setVisibility(View.VISIBLE);
                imagePhoto.setVisibility(View.GONE);
                renderer.init(rootEglBase.getEglBaseContext(), null);
                renderer.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
                renderer.setMirror(true);
                // set render
                ProxyVideoSink sink = new ProxyVideoSink();
                sink.setTarget(renderer);
                if (stream.videoTracks.size() > 0) {
                    stream.videoTracks.get(0).addSink(sink);
                }
                _sinks.put(id, sink);
            } else {
                renderer.setVisibility(View.GONE);
                imagePhoto.setVisibility(View.VISIBLE);
//                imagePhoto.setImageResource(R.drawable.bay_p);
//                Glide.with(this).load("http://goo.gl/gEgYUd").into(imagePhoto);
            }
            _videoViews.put(id, inflate);
            _infos.add(new MemberBean(id));
            wr_video_view.addView(inflate);

            int size = _infos.size();
            for (int i = 0; i < size; i++) {
                MemberBean memberBean = _infos.get(i);
                View view = _videoViews.get(memberBean.getId());
                if (view != null) {
                    RelativeLayout rd = inflate.findViewById(R.id.layout_f);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.height = getWidth(size);
                    layoutParams.width = getWidth(size);
                    layoutParams.leftMargin = getX(size, i);
                    layoutParams.topMargin = getY(size, i);
                    rd.setLayoutParams(layoutParams);
                }

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void removeView(String userId) {
        try {
            if (chatAdapter != null) {
                chatAdapter.setRemoveItem(userId);
                List<ChatRoomItemBean> data = chatAdapter.getData();
                if (data.size() <= 1) {
                    Toast.makeText(this, "通话人员已退出房间", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            /*ProxyVideoSink sink = _sinks.get(userId);
            View view = _videoViews.get(userId);
            SurfaceViewRenderer renderer = view.findViewById(R.id.svr);
            if (sink != null) {
                sink.setTarget(null);
            }
            if (renderer != null) {
                renderer.release();
            }
            _sinks.remove(userId);
            _videoViews.remove(userId);
            _infos.remove(new MemberBean(userId));
            wr_video_view.removeView(renderer);


            int size = _infos.size();
            for (int i = 0; i < _infos.size(); i++) {
                MemberBean memberBean = _infos.get(i);
                View vv = _videoViews.get(memberBean.getId());
                RelativeLayout renderer1 = vv.findViewById(R.id.layout_f);
                if (renderer1 != null) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.height = getWidth(size);
                    layoutParams.width = getWidth(size);
                    layoutParams.leftMargin = getX(size, i);
                    layoutParams.topMargin = getY(size, i);
                    renderer1.setLayoutParams(layoutParams);
                }

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getWidth(int size) {
        if (size <= 4) {
            return mScreenWidth / 2;
        } else if (size <= 9) {
            return mScreenWidth / 3;
        }
        return mScreenWidth / 3;
    }

    private int getX(int size, int index) {
        if (size <= 4) {
            if (size == 3 && index == 2) {
                return mScreenWidth / 4;
            }
            return (index % 2) * mScreenWidth / 2;
        } else if (size <= 9) {
            if (size == 5) {
                if (index == 3) {
                    return mScreenWidth / 6;
                }
                if (index == 4) {
                    return mScreenWidth / 2;
                }
            }

            if (size == 7 && index == 6) {
                return mScreenWidth / 3;
            }

            if (size == 8) {
                if (index == 6) {
                    return mScreenWidth / 6;
                }
                if (index == 7) {
                    return mScreenWidth / 2;
                }
            }
            return (index % 3) * mScreenWidth / 3;
        }
        return 0;
    }

    private int getY(int size, int index) {
        if (size < 3) {
            return mScreenWidth / 4;
        } else if (size < 5) {
            if (index < 2) {
                return 0;
            } else {
                return mScreenWidth / 2;
            }
        } else if (size < 7) {
            if (index < 3) {
                return mScreenWidth / 2 - (mScreenWidth / 3);
            } else {
                return mScreenWidth / 2;
            }
        } else if (size <= 9) {
            if (index < 3) {
                return 0;
            } else if (index < 6) {
                return mScreenWidth / 3;
            } else {
                return mScreenWidth / 3 * 2;
            }

        }
        return 0;
    }


    @Override  // 屏蔽返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        exit();
        timer.cancel();
        EventBus.getDefault().post("close", "close");
        EventBusManager.getInstance().unregister(this);
        super.onDestroy();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.wr_container, fragment)
                .commitAllowingStateLoss();

    }

    // 切换摄像头
    public void switchCamera() {
        manager.switchCamera();
    }

    // 挂断
    public void hangUp() {
        exit();
        this.finish();
    }

    // 静音
    public void toggleMic(boolean enable) {
        manager.toggleMute(enable);
    }

    // 免提
    public void toggleSpeaker(boolean enable) {
        manager.toggleSpeaker(enable);
    }

    // 打开关闭摄像头
    public void toggleCamera(boolean enableCamera) {
        if (_localVideoTrack != null) {
            _localVideoTrack.setEnabled(enableCamera);
        }

    }

    private void exit() {
        try {
            manager.exitRoom();
            List<ChatRoomItemBean> data = chatAdapter.getData();
            for (View view : _videoViews.values()) {
                SurfaceViewRenderer renderer = view.findViewById(R.id.svr);
                renderer.release();
            }
            for (ChatRoomItemBean bean : data) {
                if (bean.getSink() != null) {
                    bean.getSink().setTarget(null);
                }
            }
            _videoViews.clear();
            _sinks.clear();
            _infos.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                finish();
                break;
            }
        }
        manager.joinRoom(getApplicationContext(), rootEglBase);
    }

}
