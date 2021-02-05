package com.weique.overhaul.v2.mvp.ui.activity.chat.voip;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.MetaDataUtils;
import com.dds.skywebrtc.permission.Permissions;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.ui.activity.MainActivity;
import com.weique.overhaul.v2.mvp.ui.activity.chat.nodejs.WebrtcUtil;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleCallActivity extends AppCompatActivity {

    @BindView(R.id.portraitImageView)
    ImageView portraitImageView;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.descTextView)
    TextView descTextView;


    private static String roomId;
    private static String who;//谁发起的会话
    private static String header;//谁发起的会话

    private AsyncPlayer ringPlayer;


    public static void openActivity(Context context, String targetId, String HeadUrl, String name) {
        try {
            Intent voip = new Intent(context, SingleCallActivity.class);
            voip.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(voip);
            roomId = targetId;
            who = name;
            header = HeadUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try { //全屏显示
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            getWindow().getDecorView().setSystemUiVisibility(getSystemUiVisibility());
            setContentView(R.layout.activity_single_call);
            ButterKnife.bind(this);

            ImmersionBar.with(this)
                    .statusBarDarkFont(true)
                    .navigationBarDarkIcon(true)
                    .init();
            EventBus.getDefault().post("chat", "close");
            MainActivity.isRunningForeground = "NO";

            GlideUtil.loadImage(SingleCallActivity.this, header, portraitImageView);
            nameTextView.setText(UserInfoUtil.getUserInfo().getName());
            descTextView.setText(who);

            // 权限检测
            String[] per;
            per = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
            Permissions.request(this, per, integer -> {
                if (integer == 0) {
                    // 权限同意
                    //                    init(targetId, isOutgoing, isAudioOnly);
                } else {
                    // 权限拒绝
                    SingleCallActivity.this.finish();
                }
            });
            ringPlayer = new AsyncPlayer(null);
            int bell;
            if (Boolean.parseBoolean(MetaDataUtils.getMetaDataInApp("isGuLouZF"))) {
                bell = R.raw.gulou;
            } else {
                bell = R.raw.naoling;
            }
            Uri uri = Uri.parse("android.resource://" + AppManager.getAppManager().getmApplication().getPackageName() + "/" + bell);
            ringPlayer.play(AppManager.getAppManager().getmApplication(), uri, true, AudioManager.STREAM_RING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================================================================


    @TargetApi(19)
    private static int getSystemUiVisibility() {
        int flags = 0;
        try {
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                flags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flags;
    }

    @OnClick({R.id.close, R.id.agree})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.close:
                    ringPlayer.stop();
                    EventBus.getDefault().post("close", "close");
                    finish();
                    break;
                case R.id.agree:
                    WebrtcUtil.call(SingleCallActivity.this, Constant.CHAT_IP, roomId);
                    ringPlayer.stop();
                    finish();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            EventBus.getDefault().post("close", "close");
            ringPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (ringPlayer != null) {
                ringPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainActivity.isRunningForeground = "NO";
    }
}
