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

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.dds.webrtclib.ui.ChatRoomActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PicturePreviewActivity;
import com.luck.picture.lib.PictureSelectorActivity;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.MetaDataUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.app.utils.Watermark;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.activity.GuideActivity;
import com.weique.overhaul.v2.mvp.ui.activity.LoginActivity;
import com.weique.overhaul.v2.mvp.ui.activity.MainActivity;
import com.weique.overhaul.v2.mvp.ui.activity.SplashActivity;
import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.SingleCallActivity;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;

import org.simple.eventbus.EventBus;

import timber.log.Timber;

import static com.weique.overhaul.v2.app.common.EventBusConstant.ISRUNNINGFOREGROUND_YES;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * ================================================
 *
 * @author GK
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        try {
            EventBusManager.getInstance().register(this);
            ImmersionBar.with(activity)
                    .statusBarDarkFont(true)
                    .navigationBarDarkIcon(false)
                    .init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        try {
            //水印显示
            if (!(activity instanceof SplashActivity)
                    && !(activity instanceof GuideActivity)
                    && !(activity instanceof CaptureActivity)
                    && !(activity instanceof CameraActivity)
                    && !(activity instanceof PictureLookActivity)
                    && !(activity instanceof PictureSelectorActivity)
                    && !(activity instanceof PictureVideoPlayActivity)
                    && !(activity instanceof PicturePreviewActivity)
                    && !(activity instanceof SingleCallActivity)
                    && !(activity instanceof ChatRoomActivity)
                    && !(activity instanceof LoginActivity)) {
                if (UserInfoUtil.getUserInfo() != null) {
                    GlobalUserInfoBean userInfo = UserInfoUtil.getUserInfo();
                    Watermark.getInstance()
                            .setText((userInfo.getName()) + "|" + (userInfo.getDepartmentName()))
                            .setTextColor(activity.getResources().getColor(R.color.gray_7EEBEBEB))
                            .setTextSize(40)
                            .setRotation(-30)
                            .show(activity);
                }
            }
            Timber.i(activity + " - onActivityStarted");
            if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
                //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
                //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
                activity.getIntent().putExtra("isInitToolbar", true);
                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                if (activity.findViewById(R.id.toolbar) != null) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                            activity.getActionBar().setDisplayShowTitleEnabled(false);
                        }
                    }
                }
                if (activity.findViewById(R.id.toolbar_back) != null) {
                    activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                        try {
                            activity.onBackPressed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    activity.findViewById(R.id.toolbar_back).setOnLongClickListener(v -> {
                        ArmsUtils.killAll(MainActivity.class);
                        return false;
                    });
                }
                if (activity.findViewById(R.id.toolbar_title) != null) {
                    if (TextUtils.isEmpty(activity.getTitle())) {
                        return;
                    }
                    String title = activity.getTitle().toString();
                    /*if (StringUtil.isNullString(title)) {
                        title = MetaDataUtil.getAppName(activity);
                    }*/
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.i(activity + " - onActivityResumed");
        if (MainActivity.isRunningForeground.equals("YES")) {
            EventBus.getDefault().post(new EventBusBean(ISRUNNINGFOREGROUND_YES, ""), RouterHub.APP_MAINACTIVITY);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.i(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.i(activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.i(activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.i(activity + " - onActivityDestroyed");
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");
        EventBusManager.getInstance().unregister(this);

    }
}
