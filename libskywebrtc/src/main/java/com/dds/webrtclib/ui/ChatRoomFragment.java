package com.dds.webrtclib.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.dds.webrtclib.R;
import com.dds.webrtclib.utils.Utils;
import com.jess.arms.utils.ArmsUtils;

/**
 * 视频会议控制界面
 * Created by dds on 2019/1/2.
 * android_shuai@163.com
 */
public class ChatRoomFragment extends Fragment {

    public ChatRoomFragment(boolean videoEnable) {
        this.enableCamera = videoEnable;
    }

    public ChatRoomFragment() {
    }

    public View rootView;
    private TextView wr_switch_mute;
    private TextView wr_switch_hang_up;
    private TextView wr_switch_camera;
    private TextView wr_hand_free;
    private TextView wr_open_camera;
    private ChatRoomActivity activity;
    private Chronometer durationTextView;    // 通话时长


    private boolean enableMic = true;
    private boolean enableSpeaker = true;
    private boolean enableCamera = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChatRoomActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = onInitloadView(inflater, container, savedInstanceState);
            initView(rootView);
            initListener();

            if (durationTextView != null) {
                durationTextView.setVisibility(View.VISIBLE);
                durationTextView.setBase(SystemClock.elapsedRealtime());
            }
            toggleOpenCamera(enableCamera);
        }
        return rootView;
    }

    public void startDuration() {
        if (durationTextView != null) {
            durationTextView.start();
        }
    }

    private View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wr_fragment_room_control, container, false);
    }

    private void initView(View rootView) {
        wr_switch_mute = rootView.findViewById(R.id.wr_switch_mute);
        wr_switch_hang_up = rootView.findViewById(R.id.wr_switch_hang_up);
        wr_switch_camera = rootView.findViewById(R.id.wr_switch_camera);
        wr_hand_free = rootView.findViewById(R.id.wr_hand_free);
        wr_open_camera = rootView.findViewById(R.id.wr_open_camera);
        durationTextView = rootView.findViewById(R.id.durationTextView);
        toggleOpenCamera(enableCamera);
    }

    private void initListener() {
        wr_switch_mute.setOnClickListener(v -> {
            enableMic = !enableMic;
            toggleMic(enableMic);
            activity.toggleMic(enableMic);

        });
        wr_switch_hang_up.setOnClickListener(v -> activity.hangUp());
        wr_switch_camera.setOnClickListener(v -> activity.switchCamera());
        wr_hand_free.setOnClickListener(v -> {
            enableSpeaker = !enableSpeaker;
            toggleSpeaker(enableSpeaker);
            activity.toggleSpeaker(enableSpeaker);
        });
        wr_open_camera.setOnClickListener(v -> {
            enableCamera = !enableCamera;
            toggleOpenCamera(enableCamera);
            activity.toggleCamera(enableCamera);
        });
    }

    private void toggleMic(boolean isMicEnable) {
        if (isMicEnable) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.webrtc_mute_default);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            wr_switch_mute.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.webrtc_mute);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            wr_switch_mute.setCompoundDrawables(null, drawable, null, null);
        }
    }

    public void toggleSpeaker(boolean enableSpeaker) {
        if (enableSpeaker) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.webrtc_hands_free);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            wr_hand_free.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.webrtc_hands_free_default);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            wr_hand_free.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void toggleOpenCamera(boolean enableCamera) {
        if (enableCamera) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.webrtc_open_camera_normal);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            wr_open_camera.setCompoundDrawables(null, drawable, null, null);
            wr_open_camera.setText(R.string.webrtc_close_camera);
            wr_switch_camera.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.webrtc_open_camera_press);
            if (drawable != null) {
                drawable.setBounds(0, 0, Utils.dip2px(activity, 60), Utils.dip2px(activity, 60));
            }
            wr_open_camera.setCompoundDrawables(null, drawable, null, null);
            wr_open_camera.setText(R.string.webrtc_open_camera);
            wr_switch_camera.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
            ArmsUtils.makeText("当前通话时长" + durationTextView.getContentDescription());
        }

    }


}
