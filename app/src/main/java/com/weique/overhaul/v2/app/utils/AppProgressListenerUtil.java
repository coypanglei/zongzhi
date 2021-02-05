package com.weique.overhaul.v2.app.utils;


import android.os.Handler;

import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.model.entity.event.ProgressChangeBean;

import org.simple.eventbus.EventBus;

import io.reactivex.annotations.NonNull;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.body.ProgressInfo;

/**
 * 下载进度监听
 */
public class AppProgressListenerUtil {
    private static Handler mHandler = new Handler();
    private static ProgressInfo mLastDownloadingInfo;

    /**
     * apk 下载监听
     *
     * @return ProgressListener
     */
    @NonNull
    public static ProgressListener getApkDownloadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                try {
                    if (mLastDownloadingInfo == null) {
                        mLastDownloadingInfo = progressInfo;
                    }
                    if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
                        return;
                    } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
                        mLastDownloadingInfo = progressInfo;
                    }
                    int progress = mLastDownloadingInfo.getPercent();
                    EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS, progress), RouterHub.APP_MAINACTIVITY);
                    EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS, progress), RouterHub.APP_SETTINGSACTIVITY);
                    if (mLastDownloadingInfo.isFinish()) {
                        //说明已经下载完成
                        EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS_OK, progress), RouterHub.APP_MAINACTIVITY);
                        EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS_OK, progress), RouterHub.APP_SETTINGSACTIVITY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(long id, Exception e) {
                try {
                    mHandler.post(() ->
                            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS, 0), RouterHub.APP_MAINACTIVITY));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };
    }

    /**
     * 文件下载监听  调用通知栏
     *
     * @param pos    pos 要更新的条目
     * @param target 要回调更新进度的地方
     * @return ProgressListener
     */
    @NonNull
    public static ProgressListener getDownloadListenerToNotif(int pos, String target) {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                try {
                    int progress = progressInfo.getPercent();
                    EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS, new ProgressChangeBean(pos, progress)), target);
                    if (progressInfo.isFinish()) {
                        EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS_OK, new ProgressChangeBean(pos, progress)), target);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(long id, Exception e) {
                try {
                    mHandler.post(() ->
                            EventBus.getDefault().post(new EventBusBean(EventBusConstant.DOWNLOAD_PREGRESS, new ProgressChangeBean(pos, 0)), RouterHub.APP_MAINACTIVITY));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };
    }
}
