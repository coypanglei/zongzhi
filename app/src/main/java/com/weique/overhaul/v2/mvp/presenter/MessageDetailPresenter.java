package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppProgressListenerUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.DownLoadUtil;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.MessageDetailContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;


/**
 * ================================================
 * Description:
 * ================================================
 *
 * @author GreatKing
 */
@ActivityScope
public class MessageDetailPresenter extends ReworkBasePresenter<MessageDetailContract.Model, MessageDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    public MessageDetailPresenter(MessageDetailContract.Model model, MessageDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setMessageIsReading(String id) {
        commonGetData(mModel.setMessageIsReading(id), mErrorHandler, o -> {
            mRootView.setIsReadingSuccess();
        });

    }

    /**
     * @param url      文件url
     * @param fileName 文件名称
     */
    public void getPermission(String url, String fileName, int pos) {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
            try {
                String url_ = GlideUtil.handleUrl(mRootView.getActivity(), url);
                if (StringUtil.isNullString(url_)) {
                    ArmsUtils.makeText("下载地址为空");
                    return;
                }
                AppUtils.checkUrl(url_)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
//                                NotificationUtils notificationUtils = new NotificationUtils(AppManager.getAppManager().getmApplication()
//                                        , R.mipmap.ic_launcher, "文件下载", "content......");
//                                notificationUtils.notified();
                                //进度管理
                                ProgressManager.getInstance().addResponseListener(url_, AppProgressListenerUtil.getDownloadListenerToNotif(pos, RouterHub.APP_MESSAGEDETAILACTIVITY));
                                //下载文件
                                DownLoadUtil.downloadStart(mRootView.getActivity(),
                                        GlideUtil.handleUrl(mRootView.getActivity(), url),
                                        okHttpClient, fileName);
                            } else {
                                ArmsUtils.makeText("文件出现错误，无法下载");
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Constant.READ_WRITE);
    }

    /**
     * 回复 信息
     *
     * @param id      id
     * @param content content
     */
    public void replyMessage(String id, String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        map.put("Content", content);
        commonGetData(mModel.replyMessage(map), mErrorHandler, o -> {
//            mRootView.setIsReadingSuccess();
        });
    }
}
